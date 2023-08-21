import Dependencies
import shared

public struct StampDataProvider {
    public let stampEnabled: () async throws -> Bool
}

extension StampDataProvider: DependencyKey {
    public static var liveValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            true
        }
    )
}

public extension DependencyValues {
    var stampData: StampDataProvider {
        get { self[StampDataProvider.self] }
        set { self[StampDataProvider.self] = newValue }
    }
}
