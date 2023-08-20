import Dependencies
import shared

public struct SessionsDataProvider {
    public let timetable: () async throws -> Timetable
}

extension SessionsDataProvider: DependencyKey {
    static public var liveValue: SessionsDataProvider = SessionsDataProvider(
        timetable: {
            Timetable.companion.fake()
        }
    )
}

public extension DependencyValues {
    var sessionsData: SessionsDataProvider {
        get { self[SessionsDataProvider.self] }
        set { self[SessionsDataProvider.self] = newValue }
    }
}
