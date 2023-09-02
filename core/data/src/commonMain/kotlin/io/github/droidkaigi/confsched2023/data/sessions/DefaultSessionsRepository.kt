package io.github.droidkaigi.confsched2023.data.sessions

import co.touchlab.kermit.Logger
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

public class DefaultSessionsRepository(
    private val sessionsApi: SessionsApiClient,
    private val userDataStore: UserDataStore,
    private val sessionCacheDataStore: SessionCacheDataStore,
) : SessionsRepository {

    override fun getTimetableStream(): Flow<Timetable> = flow {
        var first = true
        combine(
            sessionCacheDataStore.getTimetableStream()
                .catch { e ->
                    Logger.d(
                        "DefaultSessionsRepository sessionCacheDataStore.getTimetableStream catch",
                        e,
                    )
                    sessionCacheDataStore.save(sessionsApi.sessionsAllResponse())
                    emitAll(sessionCacheDataStore.getTimetableStream())
                },
            userDataStore.getFavoriteSessionStream(),
        ) { timetable, favorites ->
            timetable.copy(bookmarks = favorites)
        }
            .collect {
                if (!it.isEmpty()) {
                    emit(it)
                }
                if (first) {
                    first = false
                    Logger.d("DefaultSessionsRepository onStart getTimetableStream()")
                    sessionCacheDataStore.save(sessionsApi.sessionsAllResponse())
                    Logger.d("DefaultSessionsRepository onStart fetched")
                }
            }
    }

    override fun getTimetableItemWithBookmarkStream(id: TimetableItemId): Flow<Pair<TimetableItem, Boolean>> {
        return getTimetableStream().map { timetable ->
            timetable.timetableItems.first { it.id == id } to timetable.bookmarks.contains(id)
        }
    }

    override suspend fun toggleBookmark(id: TimetableItemId) {
        userDataStore.toggleFavorite(id)
    }
}
