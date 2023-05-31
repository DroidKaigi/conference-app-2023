package io.github.droidkaigi.confsched2023.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApi,
    dataStore: DataStore<Preferences>
) : SessionsRepository {
    // TODO: Introduce Jetpack DataStore
    private val favoriteSessionIds: MutableStateFlow<PersistentSet<TimetableItemId>> =
        MutableStateFlow(
            persistentSetOf()
        )

    override fun getSessionsStream(): Flow<Timetable> {
        return callbackFlow {
            val timetable = sessionsApi.sessions()
            launch {
                favoriteSessionIds.map {
                    timetable.copy(
                        favorites = it
                    )
                }.collectLatest {
                    send(it)
                }
            }
            awaitClose {
            }
        }
    }

    override suspend fun toggleFavorite(id: TimetableItemId) {
        favoriteSessionIds.update { set: PersistentSet<TimetableItemId> ->
            if (set.contains(id)) {
                set.remove(id)
            } else {
                set.add(id)
            }
        }
    }
}
