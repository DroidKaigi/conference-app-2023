package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration.Companion
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.section.SearchQuery
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import java.lang.Integer.max

const val TimetableListItemTestTag = "TimetableListItem"
const val TimetableListItemBookmarkIconTestTag = "TimetableListItemBookmarkIconTestTag"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimetableListItem(
    timetableItem: TimetableItem,
    isBookmarked: Boolean,
    onClick: (TimetableItem) -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    chipContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    highlightQuery: SearchQuery = SearchQuery.Empty,
) {
    Column(
        modifier
            .testTag(TimetableListItemTestTag)
            .clickable { onClick(timetableItem) },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            FlowRow(
                modifier = Modifier.weight(1F),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                chipContent()
            }
            BookmarkIcon(
                modifier = Modifier
                    .size(56.dp)
                    .testTag(TimetableListItemBookmarkIconTestTag),
                isBookmarked = isBookmarked,
                contentDescription = if (isBookmarked) {
                    SessionsStrings.RemoveFromFavorites.asString()
                } else {
                    SessionsStrings.AddToFavorites.asString()
                },
                onClick = { onBookmarkClick(timetableItem) }
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = buildAnnotatedString {
                timetableItem.title.currentLangTitle.let { title ->
                    val highlightRange = with(highlightQuery) {
                        title.getMatchIndexRange()
                    }
                    append(title.take(highlightRange.first))
                    withStyle(
                        SpanStyle(
                            background = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                            textDecoration = Companion.Underline,
                        ),
                    ) {
                        append(title.substring(highlightRange))
                    }
                    append(title.takeLast(max((title.lastIndex - highlightRange.last), 0)))
                }
            },
            fontSize = 22.sp,
            lineHeight = 28.sp,
        )

        Spacer(modifier = Modifier.size(8.dp))

        if (timetableItem is Session) {
            timetableItem.message?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = SessionsStrings.ErrorIcon.asString(),
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it.currentLangTitle,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(12.dp))
        Column {
            timetableItem.speakers.forEachIndexed { index, speaker ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = previewOverride(previewPainter = {
                            rememberVectorPainter(image = Icons.Default.Person)
                        }) {
                            rememberAsyncImagePainter(url = speaker.iconUrl)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                BorderStroke(1.dp, md_theme_light_outline),
                                RoundedCornerShape(12.dp),
                            ),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = speaker.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                if (timetableItem.speakers.lastIndex != index) {
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Divider()
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableListItemPreview() {
    KaigiTheme {
        Surface {
            TimetableListItem(
                timetableItem = Session.fake(),
                isBookmarked = false,
                highlightQuery = SearchQuery.Empty,
                onClick = {},
                onBookmarkClick = {},
                chipContent = {
                },
            )
        }
    }
}
