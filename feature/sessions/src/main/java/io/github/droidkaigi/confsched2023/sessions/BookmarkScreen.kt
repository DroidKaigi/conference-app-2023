package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkTopArea
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheet
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheetUiState

const val bookmarkScreenRoute = "bookmark"

fun NavController.navigateToBookmarkScreen() {
    navigate(bookmarkScreenRoute)
}

data class BookmarkScreenUiState(
    val contentUiState: BookmarkSheetUiState,
)

@Composable
fun BookmarkScreen(
    onBackPressClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    viewModel: BookmarkScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    BookmarkScreen(
        uiState = uiState,
        onBackPressClick = onBackPressClick,
        onTimetableItemClick = onTimetableItemClick,
        onBookmarkClick = { timetableItem, _ ->
            viewModel.onBookmarkClick(timetableItem)
        },
        onAllFilterChipClick = viewModel::onAllFilterChipClick,
        onDayFirstChipClick = viewModel::onDayFirstChipClick,
        onDaySecondChipClick = viewModel::onDaySecondChipClick,
        onDayThirdChipClick = viewModel::onDayThirdChipClick,
    )
}

const val BookmarkScreenTestTag = "BookmarkScreenTestTag"

@Composable
private fun BookmarkScreen(
    uiState: BookmarkScreenUiState,
    onBackPressClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkClick: (TimetableItem, Boolean) -> Unit,
    onAllFilterChipClick: () -> Unit,
    onDayFirstChipClick: () -> Unit,
    onDaySecondChipClick: () -> Unit,
    onDayThirdChipClick: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.testTag(BookmarkScreenTestTag),
        topBar = {
            BookmarkTopArea(
                scrollState = scrollState,
                onBackPressClick = onBackPressClick,
            )
        },
    ) { padding ->
        BookmarkSheet(
            modifier = Modifier,
            scrollState = scrollState,
            onTimetableItemClick = onTimetableItemClick,
            onBookmarkClick = onBookmarkClick,
            onAllFilterChipClick = onAllFilterChipClick,
            onDayFirstChipClick = onDayFirstChipClick,
            onDaySecondChipClick = onDaySecondChipClick,
            onDayThirdChipClick = onDayThirdChipClick,
            contentPadding = padding,
            uiState = uiState.contentUiState,
        )
    }
}
