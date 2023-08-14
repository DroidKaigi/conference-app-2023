package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse

interface SessionsApiClient {
    suspend fun sessionsAllResponse(): SessionsAllResponse
}
