import EventKit
import UIKit

public protocol EventKitClientProtocol {
    func requestAccessIfNeeded() async throws -> Bool
    func addEvent(title: String, startDate: Date, endDate: Date) throws
}

public struct EventKitClient: EventKitClientProtocol {
    private let eventStore: EKEventStore = .init()

    public init() {}

    public func requestAccessIfNeeded() async throws -> Bool {
        switch EKEventStore.authorizationStatus(for: .event) {
        case .denied, .restricted:
            _ = await UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
        case .authorized, .fullAccess, .writeOnly:
            return true
        case .notDetermined:
            break
        @unknown default:
            break
        }
        if #available(iOS 17.0, *) {
            return try await eventStore.requestWriteOnlyAccessToEvents()
        } else {
            return try await eventStore.requestAccess(to: .event)
        }
    }

    public func addEvent(title: String, startDate: Date, endDate: Date) throws {
        guard let defaultCalendar = eventStore.defaultCalendarForNewEvents else {
            return
        }
        let event = EKEvent(eventStore: eventStore)
        event.title = title
        event.startDate = startDate
        event.endDate = endDate
        event.calendar = defaultCalendar

        try eventStore.save(event, span: .thisEvent)
    }
}
