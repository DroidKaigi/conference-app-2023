package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_a
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_b
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_c
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_d
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_e
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.ui.overridePreviewWith
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet

@Composable
fun BookmarkItem(
    bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    timetableItem: TimetableItem,
    onClickBoomarkIcon: (TimetableItemId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val roomChipBackgroundColor = when (timetableItem.room.type) {
        Room1 -> room_hall_a
        Room2 -> room_hall_b
        Room3 -> room_hall_c
        Room4 -> room_hall_d
        Room5 -> room_hall_e
        else -> Color.White
    }
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1F)) {
                AssistChip(
                    onClick = { /*Do Nothing*/ },
                    label = {
                        Text(
                            timetableItem.room.name.currentLangTitle,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = Color.White,
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = roomChipBackgroundColor,
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        borderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        borderWidth = 0.dp,
                    ),
                )
                Spacer(modifier = Modifier.size(5.dp))
                AssistChip(
                    onClick = { /*Do Nothing*/ },
                    label = { Text(timetableItem.day?.name.orEmpty()) },
                )
            }
            Icon(
                imageVector = if (bookmarkedTimetableItemIds.contains(timetableItem.id)) {
                    Filled.Bookmark
                } else {
                    Outlined.Bookmark
                },
                contentDescription = null,
                modifier = Modifier.clickable {
                    onClickBoomarkIcon(timetableItem.id)
                },
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = timetableItem.title.currentLangTitle,
            fontSize = 22.sp,
            lineHeight = 28.sp,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            timetableItem.speakers.forEachIndexed { index, speaker ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(url = speaker.iconUrl)
                            .overridePreviewWith {
                                rememberVectorPainter(image = Icons.Default.Person)
                            },
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                BorderStroke(1.dp, md_theme_light_outline),
                                RoundedCornerShape(12.dp),
                            ),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = speaker.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                if (timetableItem.speakers.lastIndex != index) {
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Divider()
    }
}

@Preview
@Composable
fun BookmarkItemPreview() {
    KaigiTheme {
        Surface {
            BookmarkItem(
                bookmarkedTimetableItemIds = emptyList<TimetableItemId>().toPersistentSet(),
                timetableItem = Session.fake(),
                onClickBoomarkIcon = {},
            )
        }
    }
}
