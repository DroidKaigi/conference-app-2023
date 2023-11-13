package io.github.droidkaigi.confsched2023.model

import kotlinx.coroutines.flow.Flow

public interface SessionsRepository {
    public fun getTimetableStream(): Flow<Timetable>
    public fun getTimetableItemWithBookmarkStream(id: TimetableItemId): Flow<Pair<TimetableItem, Boolean>>
    public suspend fun toggleBookmark(id: TimetableItemId)
}
