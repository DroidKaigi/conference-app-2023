package io.github.droidkaigi.confsched2023.sessions.component

import android.os.Build
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings

const val TimetableItemDetailBookmarkIconTestTag = "TimetableItemDetailBookmarkIcon"

@Composable
fun TimetableItemDetailBottomAppBar(
    timetableItem: TimetableItem,
    isBookmarked: Boolean,
    onBookmarkClick: (TimetableItem) -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    onShareClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { onShareClick(timetableItem) }) {
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
                onClick = {
                    // NOOP ,
                },
                modifier = Modifier.testTag(TimetableItemDetailBookmarkIconTestTag),
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    AnimatedBookmarkIcon(
                        isBookmarked = isBookmarked,
                        timetableItem = timetableItem,
                        onClick = onBookmarkClick,
                    )
                } else {
                    BookmarkIcon(
                        isBookmarked = isBookmarked,
                        timetableItem = timetableItem,
                        onClick = onBookmarkClick,
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun AnimatedBookmarkIcon(
    isBookmarked: Boolean,
    timetableItem: TimetableItem,
    onClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var atEnd by remember { mutableStateOf(false) }
    val animatedBookmarkIcon = AnimatedImageVector.animatedVectorResource(
        id = if (isBookmarked) {
            R.drawable.animated_bookmark_icon_reverse
        } else {
            R.drawable.animated_bookmark_icon
        },
    )
    Icon(
        painter = rememberAnimatedVectorPainter(animatedBookmarkIcon, atEnd),
        contentDescription = if (isBookmarked) {
            SessionsStrings.RemoveFromFavorites.asString()
        } else {
            SessionsStrings.AddToFavorites.asString()
        },
        modifier = modifier
            .clickable {
                atEnd = atEnd.not()
                onClick(timetableItem)
            },
    )
}

@Composable
fun BookmarkIcon(
    isBookmarked: Boolean,
    timetableItem: TimetableItem,
    onClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = if (isBookmarked) {
            Icons.Filled.Bookmark
        } else {
            Icons.Outlined.BookmarkBorder
        },
        contentDescription = if (isBookmarked) {
            SessionsStrings.RemoveFromFavorites.asString()
        } else {
            SessionsStrings.AddToFavorites.asString()
        },
        modifier = modifier
            .clickable { onClick(timetableItem) },
    )
}

@MultiThemePreviews
@MultiLanguagePreviews
@Preview(apiLevel = 30)
@Composable
fun TimetableItemDetailBottomAppBarPreview() {
    KaigiTheme {
        Surface {
            TimetableItemDetailBottomAppBar(
                timetableItem = Session.fake(),
                isBookmarked = true,
                onBookmarkClick = {},
                onCalendarRegistrationClick = {},
                onShareClick = {},
            )
        }
    }
}
