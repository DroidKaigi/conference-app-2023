package io.github.droidkaigi.confsched2023.model

import kotlinx.coroutines.flow.Flow

interface SessionsRepository {
    fun getSessionsStream(): Flow<Timetable>
    suspend fun toggleFavorite(id: TimetableItemId)
}