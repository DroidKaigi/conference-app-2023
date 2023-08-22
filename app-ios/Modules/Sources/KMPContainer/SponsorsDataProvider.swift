import Dependencies
import shared

public struct SponsorsDataProvider {
    public let sponsors: () async throws -> [Sponsor]
}

extension SponsorsDataProvider: DependencyKey {
    public static var liveValue: SponsorsDataProvider = SponsorsDataProvider(
        sponsors: {
            Sponsor.companion.fakes()
        }
    )
}

public extension DependencyValues {
    var sponsorsData: SponsorsDataProvider {
        get { self[SponsorsDataProvider.self] }
        set { self[SponsorsDataProvider.self] = newValue }
    }
}
