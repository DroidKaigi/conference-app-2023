import Dependencies
import shared

public struct AchievementDataProvider {
    private static var achievementRepository: AchievementRepository {
        Container.shared.get(type: AchievementRepository.self)
    }

    public let achievementEnabled: () -> AsyncThrowingStream<Bool, Error>
    public let achievementDetailDescription: () -> AsyncThrowingStream<String, Error>
    public let achievements: () -> AsyncThrowingStream<Set<Achievement>, Error>
    public let saveAchievement: (Achievement) async throws -> Void
}

extension AchievementDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: AchievementDataProvider = AchievementDataProvider(
        achievementEnabled: {
            achievementRepository.getAchievementEnabledStream().stream()
        },
        achievementDetailDescription: {
            achievementRepository.getAchievementDetailDescriptionStream().stream()
        },
        achievements: {
            achievementRepository.getAchievementsStream().stream()
        },
        saveAchievement: { @MainActor achievement in
            try await achievementRepository.saveAchievements(achievement: achievement)
        }
    )

    public static var testValue: AchievementDataProvider = AchievementDataProvider(
        achievementEnabled: {
            .init {
                true
            }
        },
        achievementDetailDescription: {
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
    var achievementData: AchievementDataProvider {
        get { self[AchievementDataProvider.self] }
        set { self[AchievementDataProvider.self] = newValue }
    }
}
