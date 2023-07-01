package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.component.TimetableScrollState
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
    timetableScrollState: TimetableScrollState,
    onFavoriteClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    val corner by animateIntAsState(
        if (timetableScrollState.scrollOffsetLimit == 0f || timetableScrollState.enableExpandTimetable) 40 else 0,
        label = "Timetable corner state",
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
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
