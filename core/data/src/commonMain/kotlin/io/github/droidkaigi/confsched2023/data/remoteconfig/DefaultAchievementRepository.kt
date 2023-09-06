package io.github.droidkaigi.confsched2023.data.remoteconfig

import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultAchievementRepository(
    private val remoteConfigApi: RemoteConfigApi,
) : AchievementRepository {
    private val isAchievementsEnabledStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val achievementDetailDescriptionStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    private suspend fun fetchAchievementsEnabled() {
        isAchievementsEnabledStateFlow.value = remoteConfigApi.getBoolean(IS_STAMPS_ENABLED_KEY)
    }

    private suspend fun fetchAchievementDetailDescription() {
        achievementDetailDescriptionStateFlow.value =
            remoteConfigApi.getString(ACHIEVEMENTS_DETAIL_DESCRIPTION_KEY)
    }

    override fun getAchievementEnabledStream(): Flow<Boolean> {
        return isAchievementsEnabledStateFlow.onStart { fetchAchievementsEnabled() }
    }

    override fun getAchievementDetailDescriptionStream(): Flow<String> {
        return achievementDetailDescriptionStateFlow.onStart { fetchAchievementDetailDescription() }
    }

    companion object {
        const val IS_STAMPS_ENABLED_KEY = "is_stamps_enable"
        const val ACHIEVEMENTS_DETAIL_DESCRIPTION_KEY = "achievements_detail_description"
    }
}
