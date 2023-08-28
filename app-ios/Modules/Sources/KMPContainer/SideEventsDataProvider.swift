import Dependencies
import shared

public struct SideEventsDataProvider {
    public let sideEvents: () async throws -> [SideEvent]
}

extension SideEventsDataProvider: DependencyKey {
    public static var liveValue: SideEventsDataProvider = SideEventsDataProvider(
        sideEvents: {
            return SideEventKt.SideEvents
        })
}

public extension DependencyValues {
    var sideEventsData: SideEventsDataProvider {
        get { self[SideEventsDataProvider.self] }
        set { self[SideEventsDataProvider.self] = newValue }
    }
}
