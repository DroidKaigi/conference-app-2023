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

const val TimetableGridItemTestTag = "TimetableGridItem"

@Composable
fun TimetableGridItem(
    timetableItem: TimetableItem,
    onTimetableItemClick: (TimetableItem) -> Unit,
    isBookmarked: Boolean,
    onBookmarkClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.testTag(TimetableGridItemTestTag),
    ) {
        Text(timetableItem.title.currentLangTitle)
        if (timetableItem is Session) {
            if (isBookmarked) {
                Text(
                    text = "★",
                    modifier = Modifier.clickable {
                        onBookmarkClick(timetableItem)
                    },
                )
            } else {
                Text(
                    text = "☆",
                    modifier = Modifier.clickable {
                        onBookmarkClick(timetableItem)
                    },
                )
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
                isBookmarked = false,
                onTimetableItemClick = {},
                onBookmarkClick = {},
            )
        }
    }
}
