package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.component.ClickableLinkText
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.TimetableAsset
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItem.Special
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.PersistentList

const val TimetableItemDetailReadMoreButtonTestTag = "TimetableItemDetailReadMoreButtonTestTag"

@Composable
fun TimetableItemDetailContent(
    uiState: TimetableItem,
    selectedLanguage: Lang?,
    modifier: Modifier = Modifier,
    onLinkClick: (url: String) -> Unit,
) {
    Column(modifier = modifier) {
        when (uiState) {
            is Session -> {
                val description = when (selectedLanguage) {
                    Lang.JAPANESE -> uiState.description.jaTitle
                    Lang.ENGLISH -> uiState.description.enTitle
                    Lang.MIXED,
                    null,
                    -> ""
                }
                DescriptionSection(
                    description = description,
                    onLinkClick = onLinkClick,
                )
                TargetAudienceSection(targetAudienceString = uiState.targetAudience)
                SpeakerSection(speakers = uiState.speakers)
                if (uiState.asset.isAvailable) {
                    ArchiveSection(
                        timeTableAsset = uiState.asset,
                        onViewDocumentClick = onLinkClick,
                        onWatchVideoClick = onLinkClick,
                    )
                }
            }

            is Special -> {
                val description = when (selectedLanguage) {
                    Lang.JAPANESE -> uiState.description.jaTitle
                    Lang.ENGLISH -> uiState.description.enTitle
                    Lang.MIXED -> uiState.description.jaTitle
                    null,
                    -> ""
                }
                DescriptionSection(
                    description = description,
                    onLinkClick = onLinkClick,
                )
            }
        }
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    modifier: Modifier = Modifier,
    onLinkClick: (url: String) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    SelectionContainer {
        Column(modifier = modifier.animateContentSize()) {
            ClickableLinkText(
                style = MaterialTheme.typography.bodyLarge
                    .copy(color = MaterialTheme.colorScheme.onSurface),
                content = description,
                onLinkClick = onLinkClick,
                regex = "(https)(://[\\w/:%#$&?()~.=+\\-]+)".toRegex(),
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            )
            if (!isExpanded) {
                ReadMoreOutlinedButton(
                    onClick = { isExpanded = true },
                    modifier = Modifier
                        .testTag(TimetableItemDetailReadMoreButtonTestTag)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                )
            }
            BorderLine(modifier = Modifier.padding(top = 24.dp))
        }
    }
}

@Composable
private fun TargetAudienceSection(targetAudienceString: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = SessionsStrings.TargetAudience.asString(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = targetAudienceString,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        )
        BorderLine(modifier = Modifier.padding(top = 24.dp))
    }
}

@Composable
private fun SpeakerSection(
    speakers: PersistentList<TimetableSpeaker>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = SessionsStrings.Speaker.asString(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        speakers.forEach { speaker ->
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = previewOverride(previewPainter = { rememberVectorPainter(image = Icons.Default.Person) }) {
                        rememberAsyncImagePainter(speaker.iconUrl)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            BorderStroke(1.dp, md_theme_light_outline),
                            RoundedCornerShape(12.dp),
                        ),
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 24.dp),
                ) {
                    Text(
                        text = speaker.name,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = speaker.tagLine,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        BorderLine(modifier = Modifier.padding(top = 24.dp))
    }
}

@Composable
private fun ArchiveSection(
    timeTableAsset: TimetableAsset,
    onViewDocumentClick: (slideUrl: String) -> Unit,
    onWatchVideoClick: (videoUrl: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
        Text(
            text = SessionsStrings.Archive.asString(),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            timeTableAsset.slideUrl?.let { slideUrl ->
                ArchiveSectionIconButton(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null,
                            modifier = Modifier.size(width = 18.dp, height = 18.dp),
                        )
                    },
                    label = {
                        Text(
                            text = SessionsStrings.ViewDocument.asString(),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                        )
                    },
                    onClick = { onViewDocumentClick(slideUrl) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1F),
                )
            }

            timeTableAsset.videoUrl?.let { videoUrl ->
                ArchiveSectionIconButton(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.PlayCircle,
                            contentDescription = null,
                            modifier = Modifier.size(width = 18.dp, height = 18.dp),
                        )
                    },
                    label = {
                        Text(
                            text = SessionsStrings.WatchVideo.asString(),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                        )
                    },
                    onClick = { onWatchVideoClick(videoUrl) },
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1F),
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ArchiveSectionIconButton(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSpacing: Dp = 8.dp,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(100.dp),
        onClick = { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = iconSpacing,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            icon()
            label()
        }
    }
}

@Composable
private fun BorderLine(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant),
    )
}

@Composable
private fun ReadMoreOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = SessionsStrings.ReadMore.asString(),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@MultiLanguagePreviews
@Composable
fun ReadMoreOutlinedButtonPreview() {
    KaigiTheme {
        ReadMoreOutlinedButton(onClick = {})
    }
}

@MultiThemePreviews
@Composable
fun DescriptionSectionPreview() {
    KaigiTheme {
        Surface {
            DescriptionSection(
                description = Session.fake().description.currentLangTitle,
                onLinkClick = {},
            )
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableItemDetailContentPreview() {
    KaigiTheme {
        Surface {
            TimetableItemDetailContent(
                uiState = Session.fake(),
                selectedLanguage = Lang.JAPANESE,
                onLinkClick = {},
            )
        }
    }
}
