package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    onFavoriteClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.testTag(TimetableListItemTestTag),
    ) {
        SessionTime(session.startsTimeString, session.endsTimeString)
        SessionDescription(session)
        SessionBookmark(session, isBookmarked, onFavoriteClick)
    }
}

@Composable
fun SessionBookmark(
    session: TimetableItem,
    isBookmarked: Boolean,
    onFavoriteClick: (Session) -> Unit,
    ) {
    if (session is Session) {
        if (isBookmarked) {
            Text(
                text = "★",
                modifier = Modifier.clickable {
                    onFavoriteClick(session)
                },
            )
        } else {
            Text(
                text = "☆",
                modifier = Modifier.clickable {
                    onFavoriteClick(session)
                },
            )
        }
    }
}

@Composable
fun SessionDescription(
    session: TimetableItem
) {

    Column {
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
                maxLines = 2
            )
        }
    }
}

@Composable
fun SessionTime(
    startAt: String,
    endAt: String,
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
                isBookmarked = true,
                onFavoriteClick = {},
            )
        }
    }
}
