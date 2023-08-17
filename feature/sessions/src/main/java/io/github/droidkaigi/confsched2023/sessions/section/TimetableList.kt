package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItem
import kotlinx.collections.immutable.PersistentMap

const val TimetableListTestTag = "TimetableList"

data class TimetableListUiState(
    val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
    val timetable: Timetable,
)

@Composable
fun TimetableList(
    uiState: TimetableListUiState,
    onBookmarkClick: (TimetableItem) -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag(TimetableListTestTag),
    ) {
        uiState.timetableItemMap.forEach { (_, timetableItems) ->
            itemsIndexed(timetableItems) { index, timetableItem ->
                Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
                    Column(
                        modifier = Modifier.width(58.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (index == 0) {
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
                    }
                    val isBookmarked = uiState.timetable.bookmarks.contains(timetableItem.id)
                    TimetableListItem(
                        timetableItem,
                        isBookmarked = isBookmarked,
                        onClick = onTimetableItemClick,
                        onBookmarkClick = onBookmarkClick,
                        chipContent = {
                            // Chips

                            val hallColor = hallColors()
                            val containerColor = when (timetableItem.room.type) {
                                Room1 -> hallColor.hallA
                                Room2 -> hallColor.hallB
                                Room3 -> hallColor.hallC
                                Room4 -> hallColor.hallD
                                Room5 -> hallColor.hallE
                                else -> Color.White
                            }

                            SuggestionChip(
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = containerColor,
                                ),
                                onClick = { /* Do nothing */ },
                                label = {
                                    Text(
                                        text = timetableItem.room.name.currentLangTitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White,
                                    )
                                },
                            )
                            timetableItem.language.labels.forEach {
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
                    )
                }
            }
        }
    }
}
