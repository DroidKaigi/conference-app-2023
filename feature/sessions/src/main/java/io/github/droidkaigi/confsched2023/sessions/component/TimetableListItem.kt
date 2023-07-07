package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Locale
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableLanguage
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.model.fake
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.TimeZone.Companion
import kotlinx.datetime.toLocalDateTime

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
        SessionBookmark()

        Text(session.title.currentLangTitle)
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
}

@Composable
fun SessionBookmark() {
    // TODO
}

@Composable
fun SessionDescription(
    session: TimetableItem
) {

    Column {
        // For Chips
        Row {
            Text(session.room.name.currentLangTitle)
            Text(session.language.langOfSpeaker)
        }

        Text(session.title.currentLangTitle)

        if (session is Session) {
            Text(session.speakerString)
        }


    }
}

@Composable
fun Chips(
    room: TimetableRoom
) {



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
                isBookmarked = false,
                onFavoriteClick = {},
            )
        }
    }
}
