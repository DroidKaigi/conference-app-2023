package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.sessions.component.BookMarkTopArea
import io.github.droidkaigi.confsched2023.sessions.section.BookMarkSheet

const val bookMarkScreenRoute = "bookMark"

fun NavController.navigateToBookMarkScreen() {
    navigate(bookMarkScreenRoute)
}

sealed interface BookMarkScreenUiState {
    object Empty : BookMarkScreenUiState

    data class ListBookMark(
        val bookMarkedTimeline: Timetable,
        val currentDayFilter: List<DroidKaigi2023Day>,
    ) : BookMarkScreenUiState
}

@Composable
fun BookMarkScreen(
    viewModel: BookMarkScreenViewModel = hiltViewModel<BookMarkScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    BookMarkScreen(
        uiState = uiState,
        onClickAllFilterChip = { viewModel.onClickAllFilterChip() },
        onClickDayFirstChip = { viewModel.onClickDayFirstChip() },
        onClickDaySecondChip = { viewModel.onClickDaySecondChip() },
        onClickDayThirdChip = { viewModel.onClickDayThirdChip() },
    )
}

const val BookScreenTestTag = "TimetableScreenTestTag"

@Composable
private fun BookMarkScreen(
    uiState: BookMarkScreenUiState,
    onClickAllFilterChip: () -> Unit,
    onClickDayFirstChip: () -> Unit,
    onClickDaySecondChip: () -> Unit,
    onClickDayThirdChip: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(BookScreenTestTag),
        topBar = {
            BookMarkTopArea()
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        BookMarkSheet(
            modifier = Modifier.padding(padding),
            onClickAllFilterChip = onClickAllFilterChip,
            onClickDayFirstChip = onClickDayFirstChip,
            onClickDaySecondChip = onClickDaySecondChip,
            onClickDayThirdChip = onClickDayThirdChip,
            uiState = uiState,
        )
    }
}
