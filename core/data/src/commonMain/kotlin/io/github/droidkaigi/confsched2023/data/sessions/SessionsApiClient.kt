package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.model.Timetable

interface SessionsApiClient {
    suspend fun timetable(): Timetable
}
