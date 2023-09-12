import CoreNFC

public enum NFCError: Error {
    case unavailable
}

public final class NFCReader: NSObject {
    private var activeContinuation: CheckedContinuation<URL, any Error>?
    private var session: NFCTagReaderSession?
    private var didInvalidateHandler: (() -> Void)?
    private var urlResolver: ((URL) async -> Bool)?
    private var tagReaderSessionDidDetectTask: Task<(), Never>?

    public func read(
        didInvalidateHandler: @escaping () -> Void,
        urlResolver: @escaping @Sendable (URL) async -> Bool
    ) async throws -> URL {
        guard NFCTagReaderSession.readingAvailable,
              let session = NFCTagReaderSession(pollingOption: .iso14443, delegate: self) else {
            throw NFCError.unavailable
        }
        self.session = session
        self.didInvalidateHandler = didInvalidateHandler
        self.urlResolver = urlResolver

        tagReaderSessionDidDetectTask?.cancel()
        return try await withCheckedThrowingContinuation { continuation in
            activeContinuation = continuation
            self.session?.alertMessage = AlertMessage.scanning
            self.session?.begin()
        }
    }

    private func releaseSession() {
        tagReaderSessionDidDetectTask?.cancel()
        tagReaderSessionDidDetectTask = nil
        session = nil
        activeContinuation = nil
    }

    private func restartPolling(session: NFCTagReaderSession, alertMessage: String) {
        session.alertMessage = alertMessage
        DispatchQueue.global().asyncAfter(deadline: .now() + .seconds(1)) {
            session.alertMessage = AlertMessage.scanning
            session.restartPolling()
        }
    }
}

extension NFCReader: NFCTagReaderSessionDelegate {
    public func tagReaderSessionDidBecomeActive(_ session: NFCTagReaderSession) {
        // do nothing...
    }

    public func tagReaderSession(_ session: NFCTagReaderSession, didInvalidateWithError error: Error) {
        activeContinuation?.resume(throwing: error)
        releaseSession()
        didInvalidateHandler?()
        didInvalidateHandler = nil
    }

    public func tagReaderSession(_ session: NFCTagReaderSession, didDetect tags: [NFCTag]) {
        let ndefTags = tags.compactMap {
            switch $0 {
            case .feliCa(let tag as NFCNDEFTag), .iso7816(let tag as NFCNDEFTag), .iso15693(let tag as NFCNDEFTag), .miFare(let tag as NFCNDEFTag):
                return ($0, tag)
            @unknown default:
                return nil
            }
        }

        guard ndefTags.count < 2 else {
            restartPolling(session: session, alertMessage: AlertMessage.moreThanOneTag)
            return
        }

        guard let (tag, ndefTag) = ndefTags.first else {
            restartPolling(session: session, alertMessage: AlertMessage.notSupported)
            return
        }

        tagReaderSessionDidDetectTask = Task {
            do {
                try await session.connect(to: tag)
                let message = try await ndefTag.readNDEF()
                let record = message.records.first
                guard let url = record?.wellKnownTypeURIPayload() else {
                    restartPolling(session: session, alertMessage: AlertMessage.notSupported)
                    return
                }
                let success = await urlResolver?(url) ?? false
                guard success else {
                    restartPolling(session: session, alertMessage: AlertMessage.notSupported)
                    return
                }
                session.alertMessage = AlertMessage.done
                session.invalidate()
                activeContinuation?.resume(returning: url)
                releaseSession()
            } catch {
                restartPolling(session: session, alertMessage: AlertMessage.notSupported)
            }
        }
    }
}

extension NFCReader {
    private enum AlertMessage {
        static let scanning = "Hold the top of your iPhone on the NFC tag and avoid metallic accessories."
        static let moreThanOneTag = "More than 1 tag is detected, please remove all tags and try again."
        static let notSupported = "The tag is not supported. Try again."
        static let done = "Done!"
    }
}
