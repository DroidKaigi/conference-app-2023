package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkTopArea
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet

const val bookmarkScreenRoute = "bookmark"

fun NavController.navigateToBookmarkScreen() {
    navigate(bookmarkScreenRoute)
}

sealed interface BookmarkScreenUiState {

    val currentDayFilter: PersistentList<DroidKaigi2023Day>

    data class Empty(
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookmarkScreenUiState

    data class ListBookmark(
        val bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
        val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookmarkScreenUiState
}

@Composable
fun BookmarkScreen(
    onClickBackPress: () -> Unit,
    viewModel: BookmarkScreenViewModel = hiltViewModel<BookmarkScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    BookmarkScreen(
        uiState = uiState,
        onClickBackPress = onClickBackPress,
        onBookmarkClick = { viewModel.updateBookmark(it) },
        onClickAllFilterChip = { viewModel.onClickAllFilterChip() },
        onClickDayFirstChip = { viewModel.onClickDayFirstChip() },
        onClickDaySecondChip = { viewModel.onClickDaySecondChip() },
        onClickDayThirdChip = { viewModel.onClickDayThirdChip() },
    )
}

const val BookmarkScreenTestTag = "BookmarkScreenTestTag"

@Composable
private fun BookmarkScreen(
    uiState: BookmarkScreenUiState,
    onClickBackPress: () -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onClickAllFilterChip: () -> Unit,
    onClickDayFirstChip: () -> Unit,
    onClickDaySecondChip: () -> Unit,
    onClickDayThirdChip: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.testTag(BookmarkScreenTestTag),
        topBar = {
            BookmarkTopArea(
                scrollState = scrollState,
                onClickBackPress = onClickBackPress,
            )
        },
        containerColor = Color(0xFFF8FAF6),
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        BookmarkSheet(
            modifier = Modifier.padding(padding),
            scrollState = scrollState,
            onBookmarkClick = onBookmarkClick,
            onClickAllFilterChip = onClickAllFilterChip,
            onClickDayFirstChip = onClickDayFirstChip,
            onClickDaySecondChip = onClickDaySecondChip,
            onClickDayThirdChip = onClickDayThirdChip,
            uiState = uiState,
        )
    }
}
