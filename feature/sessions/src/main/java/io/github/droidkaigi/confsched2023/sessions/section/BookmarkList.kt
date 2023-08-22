package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import io.github.droidkaigi.confsched2023.sessions.component.color
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet

@Composable
fun BookmarkList(
    scrollState: LazyListState,
    bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    timetableItemMap: PersistentMap<String, List<TimetableItem>>,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    LazyColumn(
        state = scrollState,
        modifier = modifier.padding(end = 16.dp),
    ) {
        itemsIndexed(timetableItemMap.toList(), key = { _, (key, _) -> key }) { index, (_, values) ->
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
                modifier = Modifier.padding(start = 16.dp, top = 10.dp).onGloballyPositioned {
                    rowHeight = it.size.height
                },
            ) {
                Column(
                    modifier = Modifier
                        .width(58.dp).onGloballyPositioned {
                            timeTextHeight = it.size.height
                        }
                        .offset { IntOffset(0, timeTextOffset) },
                    verticalArrangement = Arrangement.Center,
                ) {
                    val timetableItem = values[0]
                    Spacer(modifier = Modifier.size(6.dp))
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
                Column {
                    values.forEachIndexed { k, timetableItem ->
                        TimetableListItem(
                            modifier = Modifier.let { if (k >= 1) it.padding(top = 10.dp) else it },
                            timetableItem = timetableItem,
                            isBookmarked = bookmarkedTimetableItemIds.contains(timetableItem.id),
                            chipContent = {
                                val hallColor = hallColors()
                                val roomChipBackgroundColor = timetableItem.room.color
                                val roomChipLabelColor = hallColor.hallText
                                AssistChip(
                                    onClick = { /*Do Nothing*/ },
                                    label = {
                                        Text(
                                            timetableItem.room.name.currentLangTitle,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = roomChipBackgroundColor,
                                        labelColor = roomChipLabelColor,
                                    ),
                                    shape = RoundedCornerShape(50.dp),
                                    border = null,
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                AssistChip(
                                    onClick = { /*Do Nothing*/ },
                                    label = { Text(timetableItem.day?.name.orEmpty()) },
                                    shape = RoundedCornerShape(50.dp),
                                )
                            },
                            onClick = onTimetableItemClick,
                            onBookmarkClick = onBookmarkIconClick,
                        )
                    }
                }
            }
        }
    }
}
