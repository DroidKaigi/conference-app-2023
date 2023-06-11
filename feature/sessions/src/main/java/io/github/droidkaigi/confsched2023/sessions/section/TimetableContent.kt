package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.ListTimetable

sealed interface TimetableContentUiState {
    object Empty : TimetableContentUiState
    data class ListTimetable(
        val timetableListUiState: TimetableListUiState
    ) : TimetableContentUiState
}

fun LazyListScope.timetableContent(
    timetableContentUiState: TimetableContentUiState,
    onFavoriteClick: (Session) -> Unit,
) {
    when (timetableContentUiState) {
        Empty -> item {
            Text("empty")
        }

        is ListTimetable -> {
            timetableList(timetableContentUiState.timetableListUiState, onFavoriteClick)
        }
    }
}
