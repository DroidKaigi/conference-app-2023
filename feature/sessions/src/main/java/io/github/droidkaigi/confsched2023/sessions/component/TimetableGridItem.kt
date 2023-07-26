package io.github.droidkaigi.confsched2023.sessions.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_a
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_b
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_c
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_d
import io.github.droidkaigi.confsched2023.designsystem.theme.room_hall_e
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.model.ConferenceRoom.RoomHallA
import io.github.droidkaigi.confsched2023.model.ConferenceRoom.RoomHallB
import io.github.droidkaigi.confsched2023.model.ConferenceRoom.RoomHallC
import io.github.droidkaigi.confsched2023.model.ConferenceRoom.RoomHallD
import io.github.droidkaigi.confsched2023.model.ConferenceRoom.RoomHallE
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.sessions.strings.SessionsStrings.ScheduleIcon
import io.github.droidkaigi.confsched2023.sessions.strings.SessionsStrings.UserIcon
import io.github.droidkaigi.confsched2023.ui.overridePreviewWith
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter

const val TimetableGridItemTestTag = "TimetableGridItem"

@Composable
fun TimetableGridItem(
    timetableItem: TimetableItem,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (timetableItem.room.type) {
        RoomHallA -> room_hall_a
        RoomHallB -> room_hall_b
        RoomHallC -> room_hall_c
        RoomHallD -> room_hall_d
        RoomHallE -> room_hall_e
        else -> Color.White
    }
    // TODO: Dealing with more than one speaker
    val speaker = timetableItem.speakers[0]
    Box(modifier.testTag(TimetableGridItemTestTag)) {
        Box(
            modifier = Modifier
                .testTag(TimetableGridItemTestTag)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(4.dp),
                )
                .width(192.dp)
                .clickable {
                    onTimetableItemClick(timetableItem)
                }
                .padding(12.dp),
        ) {
            Column {
                Text(
                    text = timetableItem.title.currentLangTitle,
                    style = MaterialTheme.typography.labelLarge.copy(Color.White),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.height(16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_schedule),
                        tint = Color.White,
                        contentDescription = ScheduleIcon.asString(),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${timetableItem.startsTimeString} - ${timetableItem.endsTimeString}",
                        style = MaterialTheme.typography.bodySmall.copy(Color.White),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.height(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(speaker.iconUrl)
                            .overridePreviewWith {
                                rememberVectorPainter(image = Icons.Default.Person)
                            },
                        contentDescription = UserIcon.asString(),
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = speaker.name,
                        style = MaterialTheme.typography.labelMedium.copy(Color.White),
                    )
                }
            }
        }
    }
}

@Preview
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
