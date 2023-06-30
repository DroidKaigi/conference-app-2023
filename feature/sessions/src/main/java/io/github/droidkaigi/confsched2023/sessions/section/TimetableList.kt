package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem

data class TimetableListUiState(val timetable: Timetable)

@Composable
fun TimetableList(
    modifier: Modifier,
    uiState: TimetableListUiState,
    onFavoriteClick: (Session) -> Unit,
    onContributorsClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Text(
                text = "Go to ContributorsScreen",
                modifier = Modifier.clickable {
                    onContributorsClick()
                },
            )
        }
        items(uiState.timetable.timetableItems) { session ->
            val isBookmarked = uiState.timetable.favorites.contains(session.id)
            TimetableListItem(session, isBookmarked, onFavoriteClick)
        }
    }
}
