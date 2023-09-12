import CoreNFC

public enum NFCError: Error {
    case unavailable
}

public class NFCReader: NSObject {
    private var activeContinuation: CheckedContinuation<String?, any Error>?
    private var session: NFCReaderSession?

    public override init() {}

    public func read() async throws -> String? {
        guard NFCNDEFReaderSession.readingAvailable else {
            throw NFCError.unavailable
        }

        return try await withCheckedThrowingContinuation { continuation in
            activeContinuation = continuation

            session = NFCNDEFReaderSession(delegate: self, queue: nil, invalidateAfterFirstRead: false)

            session?.alertMessage = "Hold your iPhone near the item to learn more about it."

            session?.begin()
        }
    }

    private func invalidateSession() {
        session?.invalidate()
        session = nil
        activeContinuation = nil
    }
}

extension NFCReader: NFCNDEFReaderSessionDelegate {
    public func readerSession(_ session: NFCNDEFReaderSession, didDetectNDEFs messages: [NFCNDEFMessage]) {
        if let message = messages.first, let record = message.records.first {
            let result = switch String(data: record.type, encoding: .utf8) {
            case "T":
                record.wellKnownTypeTextPayload().0
            case "U":
                record.wellKnownTypeURIPayload()?.absoluteString
            default:
                record.wellKnownTypeTextPayload().0
            }

            activeContinuation?.resume(returning: result)
            invalidateSession()
        }
    }

    public func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
        activeContinuation?.resume(throwing: error)
        invalidateSession()
    }
}
