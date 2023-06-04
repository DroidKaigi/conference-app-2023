package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.TimetableScreenUiState
import io.github.droidkaigi.confsched2023.sessions.component.TimetableFilter
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState.ListTimetable

const val TimetableContentTestTag = "TimetableContent"

sealed interface TimetableContentUiState {
    object Empty : TimetableContentUiState
    data class ListTimetable(
        val timetableListUiState: TimetableListUiState
    ) : TimetableContentUiState
}

@Composable
fun TimetableContent(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onFavoriteClick: (Session) -> Unit,
    onContributorsClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .testTag(TimetableContentTestTag)
        ) {
            item {
                Text(
                    text = "Go to ContributorsScreen",
                    modifier = Modifier.clickable {
                        onContributorsClick()
                    }
                )
            }
            item {
                TimetableFilter(
                    timetableFilterUiState = uiState.timetableFilterUiState,
                    onFilterClick = onFilterClick
                )
            }
            when (val contentUiState = uiState.timetableSessionListUiState) {
                Empty -> item {
                    Text("empty")
                }

                is ListTimetable -> {
                    timetableList(contentUiState.timetableListUiState, onFavoriteClick)
                }
            }
        }
    }
}
