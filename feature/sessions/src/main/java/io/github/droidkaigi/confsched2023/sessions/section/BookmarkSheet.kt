package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.BookmarkScreenUiState
import io.github.droidkaigi.confsched2023.sessions.BookmarkScreenUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.BookmarkScreenUiState.ListBookmark
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.BookmarkedItemNotFound
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.BookmarkedItemNotFoundSideNote
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilters

@Composable
fun BookmarkSheet(
    uiState: BookmarkScreenUiState,
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
            currentDayFilter = uiState.currentDayFilter,
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
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (emptyText, icon) = createRefs()
        Box(
            modifier = Modifier
                .size(84.dp)
                .background(
                    color = Color(0xFFCEE9DB),
                    shape = RoundedCornerShape(24.dp),
                )
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(emptyText.top, margin = 24.dp)
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
            )
        }
        Column(
            modifier = Modifier.constrainAs(emptyText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = BookmarkedItemNotFound.asString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                color = Color(0xFF191C1A),
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
        }
    }
}
