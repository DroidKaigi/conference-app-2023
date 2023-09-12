package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Achievement
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAchievementEnabledStream(): Flow<Boolean>
    fun getAchievementDetailDescriptionStream(): Flow<String>
    fun getResetAchievementsEnabledStream(): Flow<Boolean>
    fun getAchievementsStream(): Flow<PersistentSet<Achievement>>
    fun getIsInitialDialogDisplayStateStream(): Flow<Boolean>
    suspend fun saveAchievements(achievement: Achievement)
    suspend fun resetAchievements()
    suspend fun displayedInitialDialog()
}
