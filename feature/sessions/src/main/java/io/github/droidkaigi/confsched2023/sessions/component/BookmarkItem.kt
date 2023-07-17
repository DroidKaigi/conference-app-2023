package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import kotlinx.collections.immutable.PersistentSet

@Composable
fun BookmarkItem(
    bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    timetableItem: TimetableItem,
    onClickBoomarkIcon: (TimetableItemId) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1F)) {
                AssistChip(
                    onClick = { /*Do Nothing*/ },
                    label = { Text(timetableItem.room.name.currentLangTitle) },
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
                    AsyncImage(
                        model = speaker.iconUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(BorderStroke(1.dp, Color.Black)),
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
