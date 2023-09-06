import Foundation

public struct EventKitClientMock: EventKitClientProtocol {
    public init() {}

    public func requestAccessIfNeeded() async throws -> Bool {
        return true
    }

    public func addEvent(title: String, startDate: Date, endDate: Date) throws {}
}
