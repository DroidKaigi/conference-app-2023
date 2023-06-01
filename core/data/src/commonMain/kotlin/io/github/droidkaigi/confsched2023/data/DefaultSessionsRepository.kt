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
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApi,
    private val dataStore: DataStore<Preferences>,
    coroutineScope: CoroutineScope
) : SessionsRepository {

    companion object {
        private val KEY_FAVORITE_SESSION_IDS = stringPreferencesKey("favorite_session_ids")
    }

    private val favoriteSessionIds: MutableStateFlow<PersistentSet<TimetableItemId>> =
        MutableStateFlow(persistentSetOf())

    init {
        coroutineScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    val favoriteIds = preferences[KEY_FAVORITE_SESSION_IDS] ?: ""
                    persistentSetOf<TimetableItemId>().apply {
                        favoriteIds.split(",").forEach { id ->
                            add(TimetableItemId(id))
                        }
                    }
                }
                .collect { favoriteSessionIds.value = it }
        }
    }

    private val timetableFlow: Flow<Timetable> = flow {
        emit(sessionsApi.sessions())
    }

    override fun getSessionsStream(): Flow<Timetable> {
        return combine(timetableFlow, favoriteSessionIds) { timetable, favorites ->
            timetable.copy(favorites = favorites)
        }
    }

    override suspend fun toggleFavorite(id: TimetableItemId) {
        val updatedFavorites = favoriteSessionIds.value.toMutableSet()

        if (updatedFavorites.contains(id)) {
            updatedFavorites.remove(id)
        } else {
            updatedFavorites.add(id)
        }

        dataStore.edit { preferences ->
            preferences[KEY_FAVORITE_SESSION_IDS] = updatedFavorites.joinToString(",")
        }

        favoriteSessionIds.value = updatedFavorites.toPersistentSet()
    }
}
