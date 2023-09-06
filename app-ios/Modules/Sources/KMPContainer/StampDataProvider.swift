import Dependencies
import shared

public struct StampDataProvider {
    private static var stampRepository: AchievementRepository {
        Container.shared.get(type: AchievementRepository.self)
    }

    public let stampEnabled: () -> AsyncThrowingStream<Bool, Error>
}

extension StampDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            stampRepository.getAchievementEnabledStream().stream()
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
