package io.github.droidkaigi.confsched2023.data.achievements

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.droidkaigi.confsched2023.model.Achievement
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AchievementsDataStore(private val dataStore: DataStore<Preferences>) {

    fun getAchievementsStream(): Flow<PersistentSet<Achievement>> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences: Preferences ->
                (preferences[KEY_ACHIEVEMENTS]?.split(",") ?: listOf())
                    .mapNotNull { Achievement.ofOrNull(it) }
                    .toPersistentSet()
            }
    }

    suspend fun saveAchievements(achievement: Achievement) {
        val updatedAchievements = getAchievementsStream().first().toMutableSet()

        updatedAchievements.add(achievement)

        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS] = updatedAchievements
                .joinToString(",") { it.id }
        }
    }

    suspend fun resetAchievements() {
        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS] = ""
        }
    }

    companion object {
        private val KEY_ACHIEVEMENTS = stringPreferencesKey("KEY_ACHIEVEMENTS")
    }
}
