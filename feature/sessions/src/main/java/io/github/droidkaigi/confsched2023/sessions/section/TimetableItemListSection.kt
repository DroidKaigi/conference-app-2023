package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.TimetableScreenUiState

sealed interface TimetableSessionListUiState {
    object Empty : TimetableSessionListUiState
    data class ListTimetable(val timetable: Timetable) : TimetableSessionListUiState
}

fun LazyListScope.timetableItemListSection(
    uiState: TimetableScreenUiState,
    onFavoriteClick: (TimetableItem.Session) -> Unit
) {
    when (val listState = uiState.timetableSessionListUiState) {
        TimetableSessionListUiState.Empty -> item {
            Text("empty")
        }

        is TimetableSessionListUiState.ListTimetable -> {
            items(listState.timetable.timetableItems) { session ->
                Text(session.title.currentLangTitle)
                if (session is TimetableItem.Session) {
                    if (listState.timetable.favorites.contains(session.id)) {
                        Text(
                            text = "★",
                            modifier = Modifier.clickable {
                                onFavoriteClick(session)
                            }
                        )
                    } else {
                        Text(
                            text = "☆",
                            modifier = Modifier.clickable {
                                onFavoriteClick(session)
                            }
                        )
                    }
                }
            }
        }
    }
}
