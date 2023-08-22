package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.ScheduleIcon
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.UserIcon
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSizes
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlin.math.ceil
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
    val speakers = timetableItem.speakers

    val hallColor = hallColors()
    val backgroundColor = timetableItem.room.color
    val textColor = if (speaker != null) {
        hallColor.hallText
    } else {
        hallColor.hallTextWhenWithoutSpeakers
    }

    val height = with(localDensity) { gridItemHeightPx.toDp() }
    val titleTextStyle = MaterialTheme.typography.labelLarge.let {
        check(it.fontSize.isSp)
        val (titleFontSize, titleLineHeight) = calculateFontSizeAndLineHeight(
            textStyle = it,
            localDensity = localDensity,
            gridItemHeightPx = gridItemHeightPx,
            speaker = speaker,
            titleLength = timetableItem.title.currentLangTitle.length,
        )
        it.copy(fontSize = titleFontSize, lineHeight = titleLineHeight, color = textColor)
    }

    Box(
        modifier = modifier
            .background(
                color = if (speakers.isEmpty()) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    backgroundColor
                },
                shape = RoundedCornerShape(4.dp),
            )
            .width(TimetableGridItemSizes.width)
            .height(height)
            .clickable {
                onTimetableItemClick(timetableItem)
            }
            .padding(TimetableGridItemSizes.padding),
    ) {
        Column {
            Text(
                text = timetableItem.title.currentLangTitle,
                style = titleTextStyle,
            )
            Spacer(modifier = Modifier.height(TimetableGridItemSizes.titleToScheduleSpaceHeight))
            Row(modifier = Modifier.height(TimetableGridItemSizes.scheduleHeight)) {
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

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 8.dp),
            )

            when (speakers.size) {
                0 -> Unit
                1 -> SingleSpeaker(speaker = speakers.first(), textColor = textColor)
                else -> MultiSpeakers(speakers = speakers)
            }
        }
    }
}

@Composable
private fun SingleSpeaker(
    speaker: TimetableSpeaker,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(TimetableGridItemSizes.speakerHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpeakerIcon(iconUrl = speaker.iconUrl)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = speaker.name,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
        )
    }
}

@Composable
private fun MultiSpeakers(
    speakers: PersistentList<TimetableSpeaker>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(TimetableGridItemSizes.speakerHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        speakers.forEach { speaker ->
            SpeakerIcon(speaker.iconUrl)
        }
    }
}

@Composable
private fun SpeakerIcon(
    iconUrl: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = previewOverride(previewPainter = { rememberVectorPainter(image = Icons.Default.Person) }) {
            rememberAsyncImagePainter(iconUrl)
        },
        contentDescription = UserIcon.asString(),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, md_theme_light_outline),
                RoundedCornerShape(8.dp),
            ),
    )
}

/**
 *
 * Calculate the font size and line height of the title by the height of the session grid item.
 *
 * @param textStyle session title text style.
 * @param localDensity local density.
 * @param gridItemHeightPx session grid item height. (unit is px.)
 * @param speaker session speaker.
 * @param titleLength session title length.
 *
 * @return calculated font size and line height. (Both units are sp.)
 *
 */
private fun calculateFontSizeAndLineHeight(
    textStyle: TextStyle,
    localDensity: Density,
    gridItemHeightPx: Int,
    speaker: TimetableSpeaker?,
    titleLength: Int,
): Pair<TextUnit, TextUnit> {
    // The height of the title that should be displayed.
    val titleToScheduleSpaceHeightPx = with(localDensity) {
        TimetableGridItemSizes.titleToScheduleSpaceHeight.toPx()
    }
    val scheduleHeightPx = with(localDensity) {
        TimetableGridItemSizes.scheduleHeight.toPx()
    }
    val scheduleToSpeakerSpaceHeightPx = with(localDensity) {
        TimetableGridItemSizes.scheduleToSpeakerSpaceMinHeight.toPx()
    }
    val horizontalPaddingPx = with(localDensity) {
        (TimetableGridItemSizes.padding * 2).toPx()
    }
    var displayTitleHeight =
        gridItemHeightPx - titleToScheduleSpaceHeightPx - scheduleHeightPx - scheduleToSpeakerSpaceHeightPx - horizontalPaddingPx
    displayTitleHeight -= if (speaker != null) {
        with(localDensity) { TimetableGridItemSizes.speakerHeight.toPx() }
    } else {
        0f
    }

    // Actual height of displayed title.
    val boxWidthWithoutPadding = with(localDensity) {
        (TimetableGridItemSizes.width - TimetableGridItemSizes.padding * 2).toPx()
    }
    val fontSizePx = with(localDensity) { textStyle.fontSize.toPx() }
    val lineHeightPx = with(localDensity) { textStyle.lineHeight.toPx() }
    var actualTitleHeight = calculateTitleHeight(
        fontSizePx = fontSizePx,
        lineHeightPx = lineHeightPx,
        titleLength = titleLength,
        maxWidth = boxWidthWithoutPadding,
    )

    return when {
        displayTitleHeight <= 0 ->
            Pair(TimetableGridItemSizes.minTitleFontSize, TimetableGridItemSizes.minTitleLineHeight)

        displayTitleHeight > actualTitleHeight ->
            Pair(textStyle.fontSize, textStyle.lineHeight)

        else -> {
            // Change the font size until it fits in the height of the title box.
            var fontResizePx = fontSizePx
            var lineHeightResizePx = lineHeightPx

            val minFontSizePx = with(localDensity) {
                TimetableGridItemSizes.minTitleFontSize.toPx()
            }
            val middleLineHeightPx = with(localDensity) {
                TimetableGridItemSizes.middleTitleLineHeight.toPx()
            }
            val minLineHeightPx = with(localDensity) {
                TimetableGridItemSizes.minTitleLineHeight.toPx()
            }

            while (displayTitleHeight <= actualTitleHeight) {
                if (fontResizePx <= minFontSizePx) {
                    fontResizePx = minFontSizePx
                    lineHeightResizePx = minLineHeightPx
                    break
                }

                fontResizePx -= with(localDensity) { 1.sp.toPx() }
                val fontResize = with(localDensity) { fontResizePx.toSp() }
                if (fontResize <= 12.sp && fontResize > 10.sp) {
                    lineHeightResizePx = middleLineHeightPx
                } else if (fontResize <= 10.sp) {
                    lineHeightResizePx = minLineHeightPx
                }
                actualTitleHeight = calculateTitleHeight(
                    fontSizePx = fontResizePx,
                    lineHeightPx = lineHeightResizePx,
                    titleLength = titleLength,
                    maxWidth = boxWidthWithoutPadding,
                )
            }

            Pair(
                with(localDensity) { fontResizePx.toSp() },
                with(localDensity) { lineHeightResizePx.toSp() },
            )
        }
    }
}

/**
 *
 * Calculate the title height.
 *
 * @param fontSizePx font size. (unit is px.)
 * @param lineHeightPx line height. (unit is px.)
 * @param titleLength session title length.
 * @param maxWidth max width of session grid item.
 *
 * @return calculated title height. (unit is px.)
 *
 */
private fun calculateTitleHeight(
    fontSizePx: Float,
    lineHeightPx: Float,
    titleLength: Int,
    maxWidth: Float,
): Float {
    val rows = ceil(titleLength * fontSizePx / maxWidth)
    return fontSizePx + (lineHeightPx * (rows - 1f))
}

object TimetableGridItemSizes {
    val width = 192.dp
    val padding = 12.dp
    val titleToScheduleSpaceHeight = 4.dp
    val scheduleHeight = 16.dp
    val scheduleToSpeakerSpaceMinHeight = 8.dp
    val speakerHeight = 32.dp
    val minTitleFontSize = 10.sp
    val middleTitleLineHeight = 16.sp // base on MaterialTheme.typography.labelSmall.lineHeight
    val minTitleLineHeight = 12.sp
}

@MultiLanguagePreviews
@Composable
fun PreviewTimetableGridItem() {
    KaigiTheme {
        Surface {
            TimetableGridItem(
                timetableItem = Session.fake()
                    .copy(speakers = persistentListOf(Session.fake().speakers.first())),
                onTimetableItemClick = {},
                gridItemHeightPx = 350,
            )
        }
    }
}

@MultiLanguagePreviews
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
                    it.copy(title = longTitle, speakers = persistentListOf(it.speakers.first()))
                },
                onTimetableItemClick = {},
                gridItemHeightPx = height,
            )
        }
    }
}

@MultiThemePreviews
@Composable
fun PreviewTimetableGridMultiSpeakersItem() {
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

@MultiThemePreviews
@Composable
internal fun PreviewTimetableGridItem(
    @PreviewParameter(PreviewTimeTableItemRoomProvider::class) timetableItem: TimetableItem,
) {
    KaigiTheme {
        Surface {
            TimetableGridItem(
                timetableItem = timetableItem,
                onTimetableItemClick = {},
                gridItemHeightPx = 350,
            )
        }
    }
}
