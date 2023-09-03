package io.github.droidkaigi.confsched2023.data.achivements

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AchievementsDataStore(private val dataStore: DataStore<Preferences>) {

    fun getAchievementsStream(): Flow<PersistentSet<AchievementsItemId>> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences: Preferences ->
                (preferences[KEY_ACHIEVEMENTS]?.split(",") ?: listOf())
                    .map { AchievementsItemId(it) }
                    .toPersistentSet()
            }
    }

    suspend fun saveAchievements(id: AchievementsItemId) {
        val updatedAchievements = getAchievementsStream().first().toMutableSet()

        updatedAchievements.add(id)

        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS] = updatedAchievements
                .joinToString(",") { it.value.toString() }
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
