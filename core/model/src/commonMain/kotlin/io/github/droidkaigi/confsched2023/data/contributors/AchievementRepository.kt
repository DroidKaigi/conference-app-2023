package io.github.droidkaigi.confsched2023.data.contributors

import kotlinx.coroutines.flow.Flow

interface AchievementRepository {

    fun getAchievementEnabledStream(): Flow<Boolean>
    fun getAchievementDetailDescriptionStream(): Flow<String>
}
