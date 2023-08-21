package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings

const val TimetableItemDetailBookmarkIconTestTag = "TimetableItemDetailBookmarkIcon"

@Composable
fun TimetableItemDetailBottomAppBar(
    timetableItem: TimetableItem,
    isBookmarked: Boolean,
    onBookmarkClick: (TimetableItem) -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = SessionsStrings.Share.asString(),
                )
            }
            IconButton(onClick = { onCalendarRegistrationClick(timetableItem) }) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_add_on),
                    contentDescription = SessionsStrings.AddToCalendar.asString(),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onBookmarkClick(timetableItem) },
                modifier = Modifier.testTag(TimetableItemDetailBookmarkIconTestTag),
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                if (isBookmarked) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = SessionsStrings.RemoveFromFavorites.asString(),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = SessionsStrings.AddToFavorites.asString(),
                    )
                }
            }
        },
    )
}
