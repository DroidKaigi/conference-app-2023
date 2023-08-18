package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.floormap.FloorMapStrings
import io.github.droidkaigi.confsched2023.floormap.FloorMapStrings.FavoriteIcon
import io.github.droidkaigi.confsched2023.model.SideEvent
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark.Favorite
import io.github.droidkaigi.confsched2023.model.SideEvent.MarkColor.Pink
import io.github.droidkaigi.confsched2023.model.SideEvents

@Composable
fun FloorMapSideEventItem(
    sideEvent: SideEvent,
    onSideEventClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .clickable(enabled = !sideEvent.link.isNullOrEmpty()) {
                sideEvent.link?.let(onSideEventClick)
            }
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val (iconVector, iconColor) = sideEvent.mark.iconResAndColor()
            Icon(
                imageVector = iconVector,
                contentDescription = FavoriteIcon.asString(),
                tint = iconColor,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sideEvent.title.currentLangTitle,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = sideEvent.description.currentLangTitle,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = FavoriteIcon.asString(),
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sideEvent.timeText.currentLangTitle,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        if (sideEvent.link != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Link,
                    contentDescription = FavoriteIcon.asString(),
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = createAnnotatedEventDetailString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        // todo open link
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}

@Composable
private fun Mark.iconResAndColor(): Pair<ImageVector, Color> {
    val icon = when (this) {
        Favorite -> Icons.Filled.Favorite
    }
    val colorLong = when (color) {
        Pink -> 0xFFDC369A
    }
    return icon to Color(colorLong)
}

@Composable
private fun createAnnotatedEventDetailString(): AnnotatedString {
    return buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = FloorMapStrings.EventDetail.asString(),
        )
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
            ),
        ) {
            append(FloorMapStrings.EventDetail.asString())
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun PreviewFloorMapSideEventItem() {
    KaigiTheme {
        Surface {
            FloorMapSideEventItem(
                sideEvent = SideEvents.first(),
                onSideEventClick = {},
            )
        }
    }
}
