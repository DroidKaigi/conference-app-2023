package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import io.github.droidkaigi.confsched2023.sessions.component.color
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import java.util.Locale

data class SearchListUiState(
    val bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    val timetableItems: PersistentList<TimetableItem>,
)

@JvmInline
value class SearchQuery(val queryText: String) {
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
        val Empty = SearchQuery("")
    }
}

@Composable
fun SearchList(
    contentPaddingValues: PaddingValues,
    searchListUiState: SearchListUiState,
    searchQuery: SearchQuery,
    scrollState: LazyListState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = scrollState,
        contentPadding = contentPaddingValues,
        modifier = modifier
            .imePadding()
            .padding(end = 16.dp),
    ) {
        itemsIndexed(searchListUiState.timetableItems) { index, timetableItem ->
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Column(
                    modifier = Modifier.width(58.dp),
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
                        SuggestionChip(
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = roomChipBackgroundColor,
                                labelColor = roomChipLabelColor,
                            ),
                            onClick = { /* Do nothing */ },
                            label = {
                                Text(
                                    text = timetableItem.room.name.currentLangTitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            },
                        )
                        infoChip.forEach {
                            SuggestionChip(
                                modifier = Modifier.padding(start = 4.dp),
                                onClick = { /* Do nothing */ },
                                label = {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                },
                            )
                        }
                    },
                    onClick = onTimetableItemClick,
                    onBookmarkClick = onBookmarkIconClick,
                )
            }
        }
    }
}
