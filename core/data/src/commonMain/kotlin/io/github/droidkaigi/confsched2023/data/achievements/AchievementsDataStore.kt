package io.github.droidkaigi.confsched2023.data.achievements

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
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

public class AchievementsDataStore(private val dataStore: DataStore<Preferences>) {

    public fun getAchievementsStream(): Flow<PersistentSet<Achievement>> {
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

    public suspend fun saveAchievements(achievement: Achievement) {
        val updatedAchievements = getAchievementsStream().first().toMutableSet()

        updatedAchievements.add(achievement)

        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS] = updatedAchievements
                .joinToString(",") { it.id }
        }
    }

    public suspend fun resetAchievements() {
        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS] = ""
        }
    }

    internal suspend fun saveInitialDialogDisplayState(
        isInitialDialogDisplay: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_ACHIEVEMENTS_INITIAL_DIALOG_DISPLAY] = isInitialDialogDisplay.toString()
        }
    }

    public fun isInitialDialogDisplayStateStream(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences: Preferences ->
                preferences[KEY_ACHIEVEMENTS_INITIAL_DIALOG_DISPLAY]?.toBoolean() ?: false
            }
    }

    private companion object {
        private val KEY_ACHIEVEMENTS = stringPreferencesKey("KEY_ACHIEVEMENTS")
        private val KEY_ACHIEVEMENTS_INITIAL_DIALOG_DISPLAY =
            stringPreferencesKey("KEY_ACHIEVEMENTS_INITIAL_DIALOG_DISPLAY")
    }
}
