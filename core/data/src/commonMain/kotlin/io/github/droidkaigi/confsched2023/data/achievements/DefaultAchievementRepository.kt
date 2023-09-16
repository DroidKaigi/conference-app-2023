package io.github.droidkaigi.confsched2023.data.achievements

import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigApi
import io.github.droidkaigi.confsched2023.model.Achievement
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

public class DefaultAchievementRepository(
    private val remoteConfigApi: RemoteConfigApi,
    private val achievementsDataStore: AchievementsDataStore,
) : AchievementRepository {
    private val isAchievementsEnabledStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val achievementDetailDescriptionStateFlow: MutableStateFlow<String> =
        MutableStateFlow("")
    private val isResetAchievementsEnabledStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    override fun getAchievementEnabledStream(): Flow<Boolean> {
        return isAchievementsEnabledStateFlow.onStart {
            isAchievementsEnabledStateFlow.value = remoteConfigApi.getBoolean(
                IS_ACHIEVEMENTS_ENABLED_KEY,
            )
        }
    }

    override fun getAchievementDetailDescriptionStream(): Flow<String> {
        return achievementDetailDescriptionStateFlow.onStart {
            achievementDetailDescriptionStateFlow.value =
                remoteConfigApi.getString(ACHIEVEMENT_DETAIL_DESCRIPTION_KEY)
        }
    }

    override fun getResetAchievementsEnabledStream(): Flow<Boolean> {
        return isResetAchievementsEnabledStateFlow.onStart {
            isResetAchievementsEnabledStateFlow.value =
                remoteConfigApi.getBoolean(IS_RESET_ACHIEVEMENTS_ENABLED_KEY)
        }
    }

    override fun getAchievementsStream(): Flow<PersistentSet<Achievement>> {
        return achievementsDataStore.getAchievementsStream()
    }

    override suspend fun saveAchievements(achievement: Achievement) {
        achievementsDataStore.saveAchievements(achievement)
    }

    override suspend fun resetAchievements() {
        achievementsDataStore.resetAchievements()
    }

    override fun getIsInitialDialogDisplayStateStream(): Flow<Boolean> {
        return achievementsDataStore.isInitialDialogDisplayStateStream()
    }

    override suspend fun displayedInitialDialog() {
        achievementsDataStore.saveInitialDialogDisplayState(true)
    }

    private companion object {
        private const val IS_ACHIEVEMENTS_ENABLED_KEY = "is_achievements_enable"
        private const val ACHIEVEMENT_DETAIL_DESCRIPTION_KEY = "achievements_detail_description"
        private const val IS_RESET_ACHIEVEMENTS_ENABLED_KEY = "is_reset_achievements_enable"
    }
}
