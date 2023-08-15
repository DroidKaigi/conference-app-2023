package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.BookmarkedItemNotFound
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.BookmarkedItemNotFoundSideNote
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilters
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheetUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheetUiState.ListBookmark
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet

sealed interface BookmarkSheetUiState {
    val currentDayFilter: PersistentList<DroidKaigi2023Day>
    val isAll: Boolean
        get() = currentDayFilter.size == DroidKaigi2023Day.values().size
    val isDayFirst: Boolean
        get() = currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day1
    val isDaySecond: Boolean
        get() = currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day2
    val isDayThird: Boolean
        get() = currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day3

    data class Empty(
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookmarkSheetUiState

    data class ListBookmark(
        val bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
        val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
        override val currentDayFilter: PersistentList<DroidKaigi2023Day>,
    ) : BookmarkSheetUiState
}

@Composable
fun BookmarkSheet(
    uiState: BookmarkSheetUiState,
    scrollState: LazyListState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onAllFilterChipClick: () -> Unit,
    onDayFirstChipClick: () -> Unit,
    onDaySecondChipClick: () -> Unit,
    onDayThirdChipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp),
    ) {
        BookmarkFilters(
            isAll = uiState.isAll,
            isDayFirst = uiState.isDayFirst,
            isDaySecond = uiState.isDaySecond,
            isDayThird = uiState.isDayThird,
            onAllFilterChipClick = onAllFilterChipClick,
            onDayFirstChipClick = onDayFirstChipClick,
            onDaySecondChipClick = onDaySecondChipClick,
            onDayThirdChipClick = onDayThirdChipClick,
        )
        when (uiState) {
            is Empty -> {
                EmptyView()
            }

            is ListBookmark -> {
                BookmarkList(
                    scrollState = scrollState,
                    bookmarkedTimetableItemIds = uiState.bookmarkedTimetableItemIds,
                    timetableItemMap = uiState.timetableItemMap,
                    onTimetableItemClick = onTimetableItemClick,
                    onBookmarkIconClick = onBookmarkClick,
                )
            }
        }
    }
}

@Composable
private fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(24.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = BookmarkedItemNotFound.asString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = BookmarkedItemNotFoundSideNote.asString(),
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF404944),
        )
        Spacer(modifier = Modifier.height(108.dp))
    }
}
