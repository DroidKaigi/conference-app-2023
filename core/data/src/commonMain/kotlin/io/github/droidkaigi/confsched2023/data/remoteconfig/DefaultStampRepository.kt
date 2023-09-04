package io.github.droidkaigi.confsched2023.data.remoteconfig

import io.github.droidkaigi.confsched2023.data.achievements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultStampRepository(
    private val remoteConfigApi: RemoteConfigApi,
    private val achievementsDataStore: AchievementsDataStore,
) : StampRepository {
    private val isStampsEnabledStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val stampDetailDescriptionStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isResetAchievementsEnabledStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private suspend fun fetchStampsEnabled() {
        isStampsEnabledStateFlow.value = remoteConfigApi.getBoolean(IS_STAMPS_ENABLED_KEY)
    }

    private suspend fun fetchStampDetailDescription() {
        stampDetailDescriptionStateFlow.value =
            remoteConfigApi.getString(STAMP_DETAIL_DESCRIPTION_KEY)
    }

    private suspend fun fetchResetAchievementsEnabled() {
        isResetAchievementsEnabledStateFlow.value =
            remoteConfigApi.getBoolean(IS_RESET_ACHIEVEMENTS_ENABLED_KEY)
    }

    override fun getStampEnabledStream(): Flow<Boolean> {
        return isStampsEnabledStateFlow.onStart { fetchStampsEnabled() }
    }

    override fun getStampDetailDescriptionStream(): Flow<String> {
        return stampDetailDescriptionStateFlow.onStart { fetchStampDetailDescription() }
    }

    override fun getResetAchievementsEnabledStream(): Flow<Boolean> {
        return isResetAchievementsEnabledStateFlow.onStart { fetchResetAchievementsEnabled() }
    }

    override fun getAchievementsStream(): Flow<PersistentSet<AchievementsItemId>> {
        return achievementsDataStore.getAchievementsStream()
    }

    override suspend fun saveAchievements(id: AchievementsItemId) {
        achievementsDataStore.saveAchievements(id)
    }

    override suspend fun resetAchievements() {
        achievementsDataStore.resetAchievements()
    }

    companion object {
        const val IS_STAMPS_ENABLED_KEY = "is_stamps_enable"
        const val STAMP_DETAIL_DESCRIPTION_KEY = "achievements_detail_description"
        const val IS_RESET_ACHIEVEMENTS_ENABLED_KEY = "is_reset_achievements_enable"
    }
}
