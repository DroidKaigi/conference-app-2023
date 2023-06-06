package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem

data class TimetableListUiState(val timetable: Timetable)

fun LazyListScope.timetableList(
    uiState: TimetableListUiState,
    onFavoriteClick: (Session) -> Unit
) {
    items(uiState.timetable.timetableItems) { session ->
        TimetableListItem(session, uiState, onFavoriteClick)
    }
}
