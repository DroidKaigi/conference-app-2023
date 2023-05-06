package io.github.droidkaigi.confsched2023.data

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DefaultSessionsRepository(
    private val sessionsApi: SessionsApi
) : SessionsRepository {
    override fun getSessionsStream(): Flow<Timetable> {
        return callbackFlow {
            this.send(sessionsApi.sessions())
            awaitClose {
            }
        }
    }
}