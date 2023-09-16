package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse

public interface SessionsApiClient {
    public suspend fun sessionsAllResponse(): SessionsAllResponse
}
