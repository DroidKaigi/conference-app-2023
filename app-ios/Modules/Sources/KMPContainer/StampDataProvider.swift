import Dependencies
import shared

public struct StampDataProvider {
    private static var stampRepository: StampRepository {
        Container.shared.get(type: StampRepository.self)
    }

    public let stampEnabled: () -> AsyncThrowingStream<Bool, Error>
}

extension StampDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            stampRepository.getStampEnabledStream().stream()
        }
    )

    public static var testValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            .init {
                true
            }
        }
    )
}

public extension DependencyValues {
    var stampData: StampDataProvider {
        get { self[StampDataProvider.self] }
        set { self[StampDataProvider.self] = newValue }
    }
}
