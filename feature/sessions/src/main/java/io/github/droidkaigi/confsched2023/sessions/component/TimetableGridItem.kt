package io.github.droidkaigi.confsched2023.sessions.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.ScheduleIcon
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.UserIcon
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter

const val TimetableGridItemTestTag = "TimetableGridItem"

@Composable
fun TimetableGridItem(
    timetableItem: TimetableItem,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hallColor = hallColors()
    val backgroundColor = when (timetableItem.room.type) {
        Room1 -> hallColor.hallA
        Room2 -> hallColor.hallB
        Room3 -> hallColor.hallC
        Room4 -> hallColor.hallD
        Room5 -> hallColor.hallE
        else -> Color.White
    }
    Box(modifier.testTag(TimetableGridItemTestTag)) {
        val speaker = timetableItem.speakers.firstOrNull()

        Box(
            modifier = Modifier
                .testTag(TimetableGridItemTestTag)
                .background(
                    color = if (speaker != null) {
                        backgroundColor
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = RoundedCornerShape(4.dp),
                )
                .width(192.dp)
                .clickable {
                    onTimetableItemClick(timetableItem)
                }
                .padding(12.dp),
        ) {
            Column {
                val textColor = if (speaker != null) {
                    hallColor.hallText
                } else {
                    hallColor.hallTextWhenWithoutSpeakers
                }
                Text(
                    text = timetableItem.title.currentLangTitle,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.height(16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        tint = if (speaker != null) {
                            hallColor.hallText
                        } else {
                            hallColor.hallTextWhenWithoutSpeakers
                        },
                        contentDescription = ScheduleIcon.asString(),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${timetableItem.startsTimeString} - ${timetableItem.endsTimeString}",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TODO: Dealing with more than one speaker
                if (speaker != null) {
                    Row(
                        modifier = Modifier.height(32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = previewOverride(previewPainter = { rememberVectorPainter(image = Icons.Default.Person) }) {
                                rememberAsyncImagePainter(speaker.iconUrl)
                            },
                            contentDescription = UserIcon.asString(),
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = speaker.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = textColor,
                        )
                    }
                }
            }
        }
    }
}

@Preview(locale = "en")
@Preview(locale = "ja")
@Composable
fun PreviewTimetableGridItem() {
    KaigiTheme {
        Surface {
            TimetableGridItem(
                timetableItem = Session.fake(),
                onTimetableItemClick = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewTimetableGridItem(
    @PreviewParameter(PreviewTimeTableItemRoomProvider::class) timetableItem: TimetableItem,
) {
    KaigiTheme {
        Surface {
            TimetableGridItem(
                timetableItem = timetableItem,
                onTimetableItemClick = {},
            )
        }
    }
}
