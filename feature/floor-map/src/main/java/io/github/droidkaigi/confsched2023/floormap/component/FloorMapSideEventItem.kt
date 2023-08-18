package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.component.ClickableLinkText
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.floormap.FloorMapStrings.EventDetail
import io.github.droidkaigi.confsched2023.floormap.FloorMapStrings.FavoriteIcon
import io.github.droidkaigi.confsched2023.model.SideEvent
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark.Favorite
import io.github.droidkaigi.confsched2023.model.SideEvent.MarkColor.Pink
import io.github.droidkaigi.confsched2023.model.SideEvents
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
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
                sideEvent.link?.let { link ->
                    val content = EventDetail(link)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Link,
                            contentDescription = FavoriteIcon.asString(),
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        ClickableLinkText(
                            style = MaterialTheme.typography.bodySmall,
                            content = content.asString(),
                            onLinkClick = onSideEventClick,
                            regex = content.asString().toRegex(),
                            url = content.url,
                        )
                    }
                }
            }
            sideEvent.imageLink?.let {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(16.dp),
                        ),
                    painter = previewOverride(
                        previewPainter = { rememberVectorPainter(image = Icons.Default.Person) },
                        painter = { rememberAsyncImagePainter(sideEvent.imageLink!!) },
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Side events image",
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
