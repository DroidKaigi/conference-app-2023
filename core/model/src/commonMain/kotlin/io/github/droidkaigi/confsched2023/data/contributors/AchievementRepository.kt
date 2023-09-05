package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {

    fun getAchievementEnabledStream(): Flow<Boolean>
    fun getAchievementDetailDescriptionStream(): Flow<String>
    fun getResetAchievementsEnabledStream(): Flow<Boolean>
    fun getAchievementsStream(): Flow<PersistentSet<AchievementsItemId>>
    suspend fun saveAchievements(id: AchievementsItemId)
    suspend fun resetAchievements()
}
