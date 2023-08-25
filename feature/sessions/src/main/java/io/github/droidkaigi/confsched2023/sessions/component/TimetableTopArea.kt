package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewTimeline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.model.TimetableUiType
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.Bookmark
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.Search
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.Timetable

const val SearchButtonTestTag = "SearchButton"
const val TimetableUiTypeChangeButtonTestTag = "TimetableUiTypeChangeButton"
const val TimetableBookmarksIconTestTag = "TimetableBookmarksIconTestTag"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimetableTopArea(
    timetableUiType: TimetableUiType,
    onTimetableUiChangeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTopAreaBookmarkIconClick: () -> Unit,
    onReachAnimationEnd: () -> Unit,
    onBookmarkClickStatus: Boolean?,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(id = R.drawable.icon_droidkaigi_logo),
                contentDescription = null,
            )
        },
        modifier = modifier,
        actions = {
            IconButton(
                modifier = Modifier.testTag(SearchButtonTestTag),
                onClick = { onSearchClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = Search.asString(),
                )
            }
            BookmarkIcon(
                modifier = Modifier
                    .testTag(TimetableBookmarksIconTestTag)
                    .clickable { onTopAreaBookmarkIconClick() },
                contentDescription = Bookmark.asString(),
                onBookmarkClickStatus = onBookmarkClickStatus,
                onReachAnimationEnd = onReachAnimationEnd,
            )
            IconButton(
                modifier = Modifier.testTag(TimetableUiTypeChangeButtonTestTag),
                onClick = { onTimetableUiChangeClick() },
            ) {
                Icon(
                    imageVector = if (timetableUiType != TimetableUiType.Grid) {
                        Icons.Outlined.GridView
                    } else {
                        Icons.Outlined.ViewTimeline
                    },
                    contentDescription = Timetable.asString(),
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableTopAreaPreview() {
    KaigiTheme {
        Surface {
            TimetableTopArea(
                timetableUiType = TimetableUiType.Grid,
                onTimetableUiChangeClick = {},
                onSearchClick = {},
                onTopAreaBookmarkIconClick = {},
                onReachAnimationEnd = {},
                onBookmarkClickStatus = null,
            )
        }
    }
}
