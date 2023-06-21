package io.github.droidkaigi.confsched2023.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApi,
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope
) : SessionsRepository {

    companion object {
        private val KEY_FAVORITE_SESSION_IDS = stringPreferencesKey("favorite_session_ids")
    }

    private val timetableFlow: Flow<Timetable> = flow {
        emit(sessionsApi.sessions())
    }

    override fun getSessionsStream(): Flow<Timetable> {
        return combine(timetableFlow, getFavoriteSessionStream()) { timetable, favorites ->
            timetable.copy(favorites = favorites)
        }
    }

    private fun getFavoriteSessionStream(): Flow<PersistentSet<TimetableItemId>> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                (preferences[KEY_FAVORITE_SESSION_IDS]?.split(",") ?: listOf())
                    .map { TimetableItemId(it) }
                    .toPersistentSet()
            }
    }

    override suspend fun toggleFavorite(id: TimetableItemId) {
        val updatedFavorites = getFavoriteSessionStream().first().toMutableSet()

        if (updatedFavorites.contains(id)) {
            updatedFavorites.remove(id)
        } else {
            updatedFavorites.add(id)
        }
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_FAVORITE_SESSION_IDS] =
                    updatedFavorites.joinToString(",") { it.value }
            }
        }
    }
}
