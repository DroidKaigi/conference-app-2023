package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem

const val TimetableListTestTag = "TimetableList"

data class TimetableListUiState(val timetable: Timetable)

@Composable
fun TimetableList(
    uiState: TimetableListUiState,
    onBookmarkClick: (TimetableItem) -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag(TimetableListTestTag),
    ) {
        items(uiState.timetable.timetableItems) { session ->
            val isBookmarked = uiState.timetable.bookmarks.contains(session.id)
            TimetableListItem(
                session,
                isBookmarked = isBookmarked,
                onTimetableItemClick = onTimetableItemClick,
                onFavoriteClick = onBookmarkClick,
            )
        }
    }
}
