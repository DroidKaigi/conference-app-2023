package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake

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
        modifier
    ) {
        // Chips
        Row {
            Text(session.room.name.currentLangTitle)
            Text(session.language.langOfSpeaker)
        }

        // Title
        Text(session.title.currentLangTitle)

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
    Column {
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
