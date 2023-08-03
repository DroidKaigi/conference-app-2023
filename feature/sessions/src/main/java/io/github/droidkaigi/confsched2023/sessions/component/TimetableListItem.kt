package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.type

const val TimetableListItemTestTag = "TimetableListItem"

@Composable
fun TimetableListItem(
    session: TimetableItem,
    isBookmarked: Boolean,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onFavoriteClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .testTag(TimetableListItemTestTag)
            .clickable { onTimetableItemClick(session) },
    ) {
        Row {
            SessionTime(
                startAt = session.startsTimeString,
                endAt = session.endsTimeString,
            )
            SessionDescription(session = session)
            SessionBookmark(
                session = session,
                isBookmarked = isBookmarked,
                onFavoriteClick = onFavoriteClick,
            )
        }

        Divider()
    }
}

@Composable
fun SessionBookmark(
    session: TimetableItem,
    isBookmarked: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Session) -> Unit,
) {
    if (session is Session) {
        if (isBookmarked) {
            Text(
                text = "★",
                modifier = modifier.clickable {
                    onFavoriteClick(session)
                },
            )
        } else {
            Text(
                text = "☆",
                modifier = modifier.clickable {
                    onFavoriteClick(session)
                },
            )
        }
    }
}

@Composable
fun SessionDescription(
    session: TimetableItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(start = 16.dp),
    ) {
        Row {
            // Chips
            val infoChip = mutableListOf<String>()

            infoChip.add(session.language.langOfSpeaker.substring(0, 2))
            if (session.language.isInterpretationTarget) {
                if (session.language.langOfSpeaker == "ENGLISH") {
                    infoChip.add("JA")
                } else if (session.language.langOfSpeaker == "JAPANESE") {
                    infoChip.add("EN")
                }
            }

            SuggestionChip(
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = when (session.room.type) {
                        Room1 -> room_hall_a
                        Room2 -> room_hall_b
                        Room3 -> room_hall_c
                        Room4 -> room_hall_d
                        Room5 -> room_hall_e
                        else -> Color.White
                    },
                ),
                onClick = { /* Do nothing */ },
                label = {
                    Text(
                        text = session.room.name.currentLangTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                },
            )

            infoChip.forEach {
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
        }

        // Title
        Text(
            text = session.title.currentLangTitle,

        )

        // Message
        if (session is Session) {
            session.message?.let {
                Text(it.currentLangTitle)
            }
        }

        // Speaker
        Row {
            // TODO: Use Compose-image-loader to show image
            // https://github.com/DroidKaigi/conference-app-2023/issues/239

            Text(
                text = session.speakerString,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}

@Composable
fun SessionTime(
    startAt: String,
    endAt: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
    ) {
        Text(startAt)
        Text("    |")
        Text(endAt)
    }
}

@Preview
@Composable
fun PreviewTimetableListItem() {
    KaigiTheme {
        Surface {
            TimetableListItem(
                session = Session.fake(),
                isBookmarked = false,
                onTimetableItemClick = {},
                onFavoriteClick = {},
            )
        }
    }
}
