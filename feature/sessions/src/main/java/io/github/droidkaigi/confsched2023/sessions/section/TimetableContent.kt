package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.GridTimetable
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.ListTimetable

sealed interface TimetableContentUiState {
    object Empty : TimetableContentUiState
    data class ListTimetable(
        val timetableListUiState: TimetableListUiState,
    ) : TimetableContentUiState

    data class GridTimetable(
        val timetableGridUiState: TimetableGridUiState,
    ) : TimetableContentUiState
}

@Composable
fun TimetableContent(
    onContributorsClick: () -> Unit,
    uiState: TimetableContentUiState,
    onFavoriteClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
    ) {
        when (uiState) {
            is Empty -> {
                Text(
                    text = "empty",
                    modifier = Modifier.testTag("empty"),
                )
            }

            is ListTimetable -> {
                TimetableList(
                    uiState = uiState.timetableListUiState,
                    onContributorsClick = onContributorsClick,
                    onFavoriteClick = onFavoriteClick,
                )
            }

            is GridTimetable -> {
                TimetableGrid(
                    modifier = modifier,
                    uiState = uiState.timetableGridUiState,
                    onBookmarked = {},
                )
            }
        }
    }
}
