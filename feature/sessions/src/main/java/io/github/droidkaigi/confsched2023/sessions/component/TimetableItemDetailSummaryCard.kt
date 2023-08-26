package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.model.nameAndFloor
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.ErrorIcon
import java.util.Locale

@Composable
fun TimetableItemDetailSummaryCard(
    timetableItem: TimetableItem,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (timetableItem is Session) {
            timetableItem.message?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = ErrorIcon.asString(),
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = it.currentLangTitle,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        val isJapaneseLocale = Locale.getDefault().language == Locale("ja").language

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    1.dp,
                ),
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            ) {
                TimetableItemDetailSummaryCardRow(
                    leadingIcon = Icons.Outlined.Schedule,
                    label = SessionsStrings.Date.asString(),
                    content = timetableItem.formattedDateTimeString,
                )
                TimetableItemDetailSummaryCardRow(
                    leadingIcon = Icons.Outlined.Place,
                    label = SessionsStrings.Place.asString(),
                    content = timetableItem.room.nameAndFloor,
                )
                TimetableItemDetailSummaryCardRow(
                    leadingIcon = Icons.Outlined.Language,
                    label = SessionsStrings.SupportedLanguages.asString(),
                    content = timetableItem.getSupportedLangString(isJapaneseLocale),
                )
                TimetableItemDetailSummaryCardRow(
                    leadingIcon = Icons.Outlined.Category,
                    label = SessionsStrings.Category.asString(),
                    content = timetableItem.category.title.currentLangTitle,
                )
            }
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableItemDetailSummaryPreview() {
    KaigiTheme {
        Surface {
            TimetableItemDetailSummaryCard(timetableItem = Session.fake())
        }
    }
}
