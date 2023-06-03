package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.TimetableScreenUiState

sealed interface TimetableContentUiState {
    object Empty : TimetableContentUiState
    data class ListTimetable(val timetableListUiState: TimetableListUiState) : TimetableContentUiState
}

fun LazyListScope.timetableContent(
    uiState: TimetableScreenUiState,
    onFavoriteClick: (TimetableItem.Session) -> Unit
) {
    when (val contentUiState = uiState.timetableSessionListUiState) {
        TimetableContentUiState.Empty -> item {
            Text("empty")
        }

        is TimetableContentUiState.ListTimetable -> {
            timetableList(contentUiState.timetableListUiState, onFavoriteClick)
        }
    }
}

