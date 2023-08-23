package io.github.droidkaigi.confsched2023.sessions

sealed interface TimetableItemDetailEvent {
    data object ViewBookmarkList : TimetableItemDetailEvent
}
