package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
    Row(
        modifier
            .testTag(TimetableListItemTestTag)
            .clickable { onTimetableItemClick(session) },
    ) {
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
