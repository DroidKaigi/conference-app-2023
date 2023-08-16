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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
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
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.type
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.ScheduleIcon
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.UserIcon
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSizes
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlin.math.roundToInt

const val TimetableGridItemTestTag = "TimetableGridItem"

@Composable
fun TimetableGridItem(
    timetableItem: TimetableItem,
    onTimetableItemClick: (TimetableItem) -> Unit,
    gridItemHeightPx: Int,
    modifier: Modifier = Modifier,
) {
    val localDensity = LocalDensity.current

    val speaker = timetableItem.speakers.firstOrNull()

    val backgroundColor = when (timetableItem.room.type) {
        Room1 -> room_hall_a
        Room2 -> room_hall_b
        Room3 -> room_hall_c
        Room4 -> room_hall_d
        Room5 -> room_hall_e
        else -> Color.White
    }
    val height = with(localDensity) { gridItemHeightPx.toDp() }
    val titleTextStyle = MaterialTheme.typography.labelLarge.let {
        check(it.fontSize.isSp)
        val titleFontSize = calculateTitleFontSize(
            textStyle = it,
            localDensity = localDensity,
            gridItemHeightPx = gridItemHeightPx,
            speaker = speaker,
            titleLength = timetableItem.title.currentLangTitle.length,
        )
        it.copy(fontSize = titleFontSize, color = Color.White)
    }

    Box(modifier.testTag(TimetableGridItemTestTag)) {
        Box(
            modifier = Modifier
                .testTag(TimetableGridItemTestTag)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(4.dp),
                )
                .width(192.dp)
                .height(height)
                .clickable {
                    onTimetableItemClick(timetableItem)
                }
                .padding(12.dp),
        ) {
            Column {
                Text(
                    text = timetableItem.title.currentLangTitle,
                    style = titleTextStyle,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.height(16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
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
                            style = MaterialTheme.typography.labelMedium.copy(Color.White),
                        )
                    }
                }
            }
        }
    }
}

/**
 *
 * Calculate the font size of the title by the height of the displayed session grid.
 *
 * @param textStyle session title text style.
 * @param localDensity local density.
 * @param gridItemHeightPx session grid item height. (unit is px.)
 * @param speaker session speaker.
 * @param titleLength session title length.
 *
 */
private fun calculateTitleFontSize(
    textStyle: TextStyle,
    localDensity: Density,
    gridItemHeightPx: Int,
    speaker: TimetableSpeaker?,
    titleLength: Int,
): TextUnit {
    val titleToScheduleSpaceHeight = with(localDensity) { 4.dp.toPx() }
    val scheduleHeight = with(localDensity) { 16.dp.toPx() }
    val scheduleToSpeakerSpaceHeight = with(localDensity) { 16.dp.toPx() }
    val horizontalPadding = with(localDensity) { (12 * 2).dp.toPx() }

    // The height of the title that should be displayed.
    var displayTitleHeight =
        gridItemHeightPx - titleToScheduleSpaceHeight - scheduleHeight - scheduleToSpeakerSpaceHeight - horizontalPadding
    displayTitleHeight -= if (speaker != null) with(localDensity) { 32.dp.toPx() } else 0f

    // Actual height of displayed title.
    val boxWidthWithoutPadding = with(localDensity) { (192 - 12 * 2).dp.toPx() }
    val fontSizePx = with(localDensity) { textStyle.fontSize.toPx() }
    val textLengthInRow = (boxWidthWithoutPadding / fontSizePx).roundToInt()
    val rows = titleLength / textLengthInRow
    val actualTitleHeight = rows * fontSizePx

    val oneLine = 1
    val minFontSize = 9.sp
    return when {
        displayTitleHeight <= 0 -> minFontSize
        rows <= oneLine || displayTitleHeight > actualTitleHeight -> textStyle.fontSize
        else -> minFontSize
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
                gridItemHeightPx = 350,
            )
        }
    }
}

@Preview
@Composable
fun PreviewTimetableGridLongTitleItem() {
    val fake = Session.fake()

    val localDensity = LocalDensity.current
    val verticalScale = 1f

    val minutePx = with(localDensity) { TimetableSizes.minuteHeight.times(verticalScale).toPx() }
    val displayEndsAt = fake.endsAt.minus(1, DateTimeUnit.MINUTE)
    val height = ((displayEndsAt - fake.startsAt).inWholeMinutes * minutePx).roundToInt()

    KaigiTheme {
        Surface {
            TimetableGridItem(
                timetableItem = Session.fake().let {
                    val longTitle = it.title.copy(
                        jaTitle = it.title.jaTitle.repeat(2),
                        enTitle = it.title.enTitle.repeat(2),
                    )
                    it.copy(title = longTitle)
                },
                onTimetableItemClick = {},
                gridItemHeightPx = height,
            )
        }
    }
}
