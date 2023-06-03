package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.section.TimetableListUiState

@Composable
fun TimetableListItem(
    session: TimetableItem,
    uiState: TimetableListUiState,
    onFavoriteClick: (Session) -> Unit
) {
    Text(session.title.currentLangTitle)
    if (session is Session) {
        if (uiState.timetable.favorites.contains(session.id)) {
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
