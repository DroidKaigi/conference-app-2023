package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Achievement
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow

public interface AchievementRepository {
    public fun getAchievementEnabledStream(): Flow<Boolean>
    public fun getAchievementDetailDescriptionStream(): Flow<String>
    public fun getResetAchievementsEnabledStream(): Flow<Boolean>
    public fun getAchievementsStream(): Flow<PersistentSet<Achievement>>
    public fun getIsInitialDialogDisplayStateStream(): Flow<Boolean>
    public suspend fun saveAchievements(achievement: Achievement)
    public suspend fun resetAchievements()
    public suspend fun displayedInitialDialog()
}
