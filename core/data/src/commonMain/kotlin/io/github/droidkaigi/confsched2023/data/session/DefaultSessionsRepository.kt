package io.github.droidkaigi.confsched2023.data.session

import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApi,
    private val userDataStore: UserDataStore,
) : SessionsRepository {

    override fun getSessionsStream(): Flow<Timetable> {
        return combine(
            flow {
                emit(sessionsApi.sessions())
            },
            userDataStore.getFavoriteSessionStream(),
        ) { timetable, favorites ->
            timetable.copy(favorites = favorites)
        }
    }

    override suspend fun toggleFavorite(id: TimetableItemId) {
        userDataStore.toggleFavorite(id)
    }
}
