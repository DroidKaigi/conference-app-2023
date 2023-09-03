package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow

interface StampRepository {

    fun getStampEnabledStream(): Flow<Boolean>
    fun getStampDetailDescriptionStream(): Flow<String>
    fun getAchievementsStream(): Flow<PersistentSet<AchievementsItemId>>
    suspend fun saveAchievements(id: AchievementsItemId)
}
