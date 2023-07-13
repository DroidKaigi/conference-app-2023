package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
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
import io.github.droidkaigi.confsched2023.model.Timetable

@Composable
fun BookMarkList(
    timetable: Timetable,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(timetable.timetableItems.timetableItems.sortedBy { it.day }) { timetableItem ->
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Box(modifier = Modifier.size(42.dp))
                Column {
                    Row(
                        modifier = Modifier,
                    ) {
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
        }
    }
}
