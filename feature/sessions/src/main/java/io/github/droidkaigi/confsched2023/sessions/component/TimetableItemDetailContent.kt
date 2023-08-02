package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItem.Special
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.ui.overridePreviewWith
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.PersistentList

@Composable
fun TimetableItemDetailContent(
    uiState: TimetableItem,
    onViewDocumentClick: () -> Unit,
    onWatchVideoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (uiState) {
            is Session -> {
                DescriptionSection(description = uiState.description)
                TargetAudienceSection(targetAudienceString = uiState.targetAudience)
                SpeakerSection(speakers = uiState.speakers)
                ArchiveSection(
                    onViewDocumentClick = onViewDocumentClick,
                    onWatchVideoClick = onWatchVideoClick,
                )
            }

            is Special -> {
                // do nothing
            }
        }
    }
}

@Composable
private fun DescriptionSection(description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        )
        BorderLine(modifier = Modifier.padding(top = 24.dp))
    }
}

@Composable
private fun TargetAudienceSection(targetAudienceString: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = SessionsStrings.TargetAudience.asString(),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
        )
        Text(
            text = targetAudienceString,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
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
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        speakers.forEach { speaker ->
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(speaker.iconUrl).overridePreviewWith {
                        rememberVectorPainter(image = Icons.Default.Person)
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
                        fontWeight = FontWeight.Bold
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
    onViewDocumentClick: () -> Unit,
    onWatchVideoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
        Text(
            text = SessionsStrings.Archive.asString(),
            fontSize = 14.sp,
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
        ) {
            Box(modifier = Modifier.weight(1F)) {
                AssistChip(
                    onClick = { onViewDocumentClick() },
                    label = {
                        Text(
                            text = SessionsStrings.ViewDocument.asString(),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(width = 18.dp, height = 18.dp),
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(100.dp),
                    border = AssistChipDefaults.assistChipBorder(
                        borderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Box(modifier = Modifier.weight(1F)) {
                AssistChip(
                    onClick = { onWatchVideoClick() },
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,

                            ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(width = 18.dp, height = 18.dp),
                            )
                            Text(
                                text = SessionsStrings.ViewDocument.asString(),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(100.dp),
                    border = AssistChipDefaults.assistChipBorder(
                        borderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BorderLine(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}
