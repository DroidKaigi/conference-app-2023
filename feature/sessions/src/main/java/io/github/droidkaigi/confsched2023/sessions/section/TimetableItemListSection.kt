package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.SessionListUiState
import io.github.droidkaigi.confsched2023.sessions.SessionScreenUiState

@Composable
fun TimetableItemListSection(
    uiState: SessionScreenUiState,
    onFavoriteClick: (TimetableItem.Session) -> Unit
) {
    when (val listState = uiState.sessionListUiState) {
        SessionListUiState.Empty -> Text("empty")
        is SessionListUiState.List -> {
            listState.timetable.timetableItems.forEach { session ->
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
