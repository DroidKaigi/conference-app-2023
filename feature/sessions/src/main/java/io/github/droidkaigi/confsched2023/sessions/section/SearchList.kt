package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.component.SessionTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import io.github.droidkaigi.confsched2023.sessions.component.color
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import java.util.Locale

data class SearchListUiState(
    val bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    val timetableItems: PersistentList<TimetableItem>,
)

data class SearchQuery(val queryText: String) {
    val hasQuery get() = queryText.isNotBlank()

    fun String.getMatchIndexRange(): IntRange {
        if (!hasQuery) return IntRange.EMPTY

        val startIndex = this.indexOf(queryText, ignoreCase = true)
        return if (startIndex >= 0) {
            startIndex until (startIndex + queryText.length)
        } else {
            IntRange.EMPTY
        }
    }

    companion object {
        val Empty = SearchQuery("AAA")
    }
}

@Composable
fun SearchList(
    contentPaddingValues: PaddingValues,
    searchListUiState: SearchListUiState,
    searchQuery: SearchQuery,
    scrollState: LazyListState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: (TimetableItem, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    LazyColumn(
        state = scrollState,
        contentPadding = contentPaddingValues,
        modifier = modifier.imePadding(),
    ) {
        itemsIndexed(searchListUiState.timetableItems) { index, timetableItem ->
            var rowHeight by remember { mutableIntStateOf(0) }
            var timeTextHeight by remember { mutableIntStateOf(0) }
            val timeTextOffset by remember(density) {
                derivedStateOf {
                    // 15.dp is bottom_margin of TimetableListItem
                    // 1.dp is height of Divider in TimetableListItem
                    val maxOffset = with(density) { rowHeight - (timeTextHeight + (15.dp + 1.dp).roundToPx()) }
                    if (index == scrollState.firstVisibleItemIndex) {
                        minOf(scrollState.firstVisibleItemScrollOffset, maxOffset).coerceAtLeast(0)
                    } else {
                        0
                    }
                }
            }
            Row(
                modifier = Modifier.padding(start = 16.dp).onGloballyPositioned {
                    rowHeight = it.size.height
                },
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(58.dp)
                        .onGloballyPositioned { timeTextHeight = it.size.height }
                        .offset { IntOffset(0, timeTextOffset) },
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = timetableItem.startsTimeString,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(text = "|")
                        Text(
                            text = timetableItem.endsTimeString,
                        )
                    }
                }
                TimetableListItem(
                    timetableItem = timetableItem,
                    isBookmarked = searchListUiState.bookmarkedTimetableItemIds.contains(
                        timetableItem.id,
                    ),
                    modifier = Modifier
                        .clickable { onTimetableItemClick(timetableItem) }
                        .padding(top = 10.dp),
                    highlightQuery = searchQuery,
                    chipContent = {
                        // Chips
                        val infoChip = mutableListOf<String>()

                        infoChip.add(timetableItem.day?.name.orEmpty())
                        infoChip.add(timetableItem.category.title.currentLangTitle)
                        infoChip.add(
                            timetableItem.language.langOfSpeaker.let {
                                when (it) {
                                    "MIXED" -> it
                                    else -> {
                                        it.take(2).uppercase(Locale.ENGLISH)
                                    }
                                }
                            },
                        )
                        if (timetableItem.language.isInterpretationTarget) {
                            infoChip.add(SessionsStrings.InterpretationTarget.asString())
                        }

                        val hallColor = hallColors()
                        val roomChipBackgroundColor = timetableItem.room.color
                        val roomChipLabelColor = hallColor.hallText
                        SessionTag(
                            label = timetableItem.room.name.currentLangTitle,
                            labelColor = roomChipLabelColor,
                            backgroundColor = roomChipBackgroundColor,
                        )
                        infoChip.forEach {
                            SessionTag(
                                label = it,
                                borderColor = MaterialTheme.colorScheme.outline,
                            )
                        }
                    },
                    onBookmarkClick = onBookmarkIconClick,
                )
            }
        }
    }
}
