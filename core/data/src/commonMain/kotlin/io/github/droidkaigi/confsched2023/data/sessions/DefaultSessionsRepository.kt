package io.github.droidkaigi.confsched2023.data.sessions

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Logger.Companion
import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApiClient,
    private val authApi: AuthApi,
    private val userDataStore: UserDataStore,
) : SessionsRepository {

    private val timetableStateFlow = MutableStateFlow(Timetable())

    override fun getTimetableStream(): Flow<Timetable> {
        return combine(
            timetableStateFlow,
            userDataStore.getFavoriteSessionStream(),
        ) { timetable, favorites ->
            timetable.copy(bookmarks = favorites)
        }
            .onStart {
                if (timetableStateFlow.value.isEmpty()) {
                    Logger.d("DefaultSessionsRepository onStart getTimetableStream()")
                    timetableStateFlow.value = sessionsApi.timetable()
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
