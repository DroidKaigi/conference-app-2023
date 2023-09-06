import Dependencies
import shared

public struct StampDataProvider {
    private static var stampRepository: AchievementRepository {
        Container.shared.get(type: AchievementRepository.self)
    }

    public let stampEnabled: () -> AsyncThrowingStream<Bool, Error>
    public let stampDetailDescription: () -> AsyncThrowingStream<String, Error>
    public let achievements: () -> AsyncThrowingStream<Set<AchievementsItemId>, Error>
    public let saveAchievement: (AchievementsItemId) async throws -> Void
}

extension StampDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            stampRepository.getAchievementEnabledStream().stream()
        },
        stampDetailDescription: {
            stampRepository.getStampDetailDescriptionStream().stream()
        },
        achievements: {
            stampRepository.getAchievementsStream().stream()
        },
        saveAchievement: { @MainActor id in
            try await stampRepository.saveAchievements(id: id)
        }
    )

    public static var testValue: StampDataProvider = StampDataProvider(
        stampEnabled: {
            .init {
                true
            }
        },
        stampDetailDescription: {
            .init {
                ""
            }
        },
        achievements: {
            .init {
                Set()
            }
        }, 
        saveAchievement: {_ in}
    )
}

public extension DependencyValues {
    var stampData: StampDataProvider {
        get { self[StampDataProvider.self] }
        set { self[StampDataProvider.self] = newValue }
    }
}
