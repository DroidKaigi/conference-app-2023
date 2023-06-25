package io.github.droidkaigi.confsched2023.data.user

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserDataStore(private val dataStore: DataStore<Preferences>) {
    fun getFavoriteSessionStream(): Flow<PersistentSet<TimetableItemId>> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences: Preferences ->
                (preferences[KEY_FAVORITE_SESSION_IDS]?.split(",") ?: listOf())
                    .map { TimetableItemId(it) }
                    .toPersistentSet()
            }
    }

    suspend fun toggleFavorite(id: TimetableItemId) {
        val updatedFavorites = getFavoriteSessionStream().first().toMutableSet()

        if (updatedFavorites.contains(id)) {
            updatedFavorites.remove(id)
        } else {
            updatedFavorites.add(id)
        }
        dataStore.edit { preferences ->
            preferences[KEY_FAVORITE_SESSION_IDS] =
                updatedFavorites.joinToString(",") { it.value }
        }
    }

    companion object {
        private val KEY_FAVORITE_SESSION_IDS = stringPreferencesKey("favorite_session_ids")
    }
}
