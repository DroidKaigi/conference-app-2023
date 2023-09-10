package io.github.droidkaigi.confsched2023.sessions.section

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.SessionTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import io.github.droidkaigi.confsched2023.sessions.component.color
import kotlinx.collections.immutable.PersistentMap

const val TimetableListTestTag = "TimetableList"

data class TimetableListUiState(
    val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
    val timetable: Timetable,
)

@Composable
fun TimetableList(
    uiState: TimetableListUiState,
    scrollState: LazyListState,
    onBookmarkClick: (TimetableItem, Boolean) -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    LazyColumn(
        modifier = modifier.testTag(TimetableListTestTag),
        state = scrollState,
        contentPadding = contentPadding,
    ) {
        itemsIndexed(uiState.timetableItemMap.toList(), key = { _, (key, _) -> key }) { index, (_, timetableItems) ->
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
                modifier = Modifier
                    .padding(start = 16.dp)
                    .onGloballyPositioned {
                        rowHeight = it.size.height
                    },
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(58.dp)
                        .onGloballyPositioned {
                            timeTextHeight = it.size.height
                        }
                        .offset { IntOffset(0, timeTextOffset) },
                    verticalArrangement = Arrangement.Center,
                ) {
                    val timetableItem = timetableItems[0]
                    Spacer(modifier = Modifier.size(6.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 4.dp,
                            alignment = Alignment.CenterVertically,
                        ),
                        modifier = Modifier.semantics(mergeDescendants = true) {
                            contentDescription = timetableItem.formattedTimeString
                        },
                    ) {
                        Text(
                            text = timetableItem.startsTimeString,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clearAndSetSemantics {},
                        )
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(2.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant),
                        )
                        Text(
                            text = timetableItem.endsTimeString,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clearAndSetSemantics {},
                        )
                    }
                }
                Column {
                    timetableItems.forEach { timetableItem ->
                        val isBookmarked = uiState.timetable.bookmarks.contains(timetableItem.id)
                        val haptic = LocalHapticFeedback.current
                        TimetableListItem(
                            timetableItem,
                            modifier = Modifier
                                .clickable { onTimetableItemClick(timetableItem) }
                                .padding(top = 10.dp),
                            isBookmarked = isBookmarked,
                            onBookmarkClick = { timetableItem, isBookmarked ->
                                if (isBookmarked) {
                                    haptic.performHapticFeedback(
                                        HapticFeedbackType(
                                            HapticFeedbackConstants.LONG_PRESS,
                                        ),
                                    )
                                }
                                onBookmarkClick(timetableItem, isBookmarked)
                            },
                            chipContent = {
                                // Chips

                                val hallColor = hallColors()
                                val containerColor = timetableItem.room.color
                                val labelColor = hallColor.hallText

                                SessionTag(
                                    label = timetableItem.room.name.currentLangTitle,
                                    labelColor = labelColor,
                                    backgroundColor = containerColor,
                                )
                                timetableItem.language.labels.forEach {
                                    SessionTag(
                                        label = it,
                                        borderColor = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
