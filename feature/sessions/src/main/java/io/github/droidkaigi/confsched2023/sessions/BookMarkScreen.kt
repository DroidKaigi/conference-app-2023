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
import io.github.droidkaigi.confsched2023.sessions.component.BookMarkTopArea
import io.github.droidkaigi.confsched2023.sessions.section.BookMarkSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet

const val bookMarkScreenRoute = "bookMark"

fun NavController.navigateToBookMarkScreen() {
    navigate(bookMarkScreenRoute)
}

sealed interface BookMarkScreenUiState {

    val currentDayFilter: PersistentList<DroidKaigi2023Day>

    data class Empty(
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookMarkScreenUiState

    data class ListBookMark(
        val bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
        val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookMarkScreenUiState
}

@Composable
fun BookMarkScreen(
    onClickBackPress: () -> Unit,
    viewModel: BookMarkScreenViewModel = hiltViewModel<BookMarkScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    BookMarkScreen(
        uiState = uiState,
        onClickBackPress = onClickBackPress,
        onClickBooMarkIcon = { viewModel.updateBookmark(it) },
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
    onClickBackPress: () -> Unit,
    onClickBooMarkIcon: (TimetableItemId) -> Unit,
    onClickAllFilterChip: () -> Unit,
    onClickDayFirstChip: () -> Unit,
    onClickDaySecondChip: () -> Unit,
    onClickDayThirdChip: () -> Unit,
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.testTag(BookScreenTestTag),
        topBar = {
            BookMarkTopArea(
                scrollState = scrollState,
                onClickBackPress = onClickBackPress,
            )
        },
        containerColor = Color(0xFFF8FAF6),
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        BookMarkSheet(
            modifier = Modifier.padding(padding),
            scrollState = scrollState,
            onClickBookMarkIcon = onClickBooMarkIcon,
            onClickAllFilterChip = onClickAllFilterChip,
            onClickDayFirstChip = onClickDayFirstChip,
            onClickDaySecondChip = onClickDaySecondChip,
            onClickDayThirdChip = onClickDayThirdChip,
            uiState = uiState,
        )
    }
}
