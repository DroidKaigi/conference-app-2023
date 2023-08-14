package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.Search
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.Timetable

const val SearchButtonTestTag = "SearchButton"
const val TimetableUiTypeChangeButtonTestTag = "TimetableUiTypeChangeButton"
const val TimetableBookmarksIconTestTag = "TimetableBookmarksIconTestTag"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimetableTopArea(
    onTimetableUiChangeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTopAreaBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Image(
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
            IconButton(
                modifier = Modifier.testTag(TimetableBookmarksIconTestTag),
                onClick = { onTopAreaBookmarkIconClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = Timetable.asString(),
                )
            }
            IconButton(
                modifier = Modifier.testTag(TimetableUiTypeChangeButtonTestTag),
                onClick = { onTimetableUiChangeClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = Timetable.asString(),
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    )
}
