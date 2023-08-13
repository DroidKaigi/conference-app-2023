package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import java.util.Locale

@Composable
fun TimetableItemDetailSummaryCard(
    timetableItem: TimetableItem,
    modifier: Modifier = Modifier,
) {
    val isJapaneseLocale = Locale.getDefault().language == Locale("ja").language

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
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
                leadingIcon = Icons.Filled.Schedule,
                label = SessionsStrings.Date.asString(),
                content = timetableItem.formattedDateTimeString,
            )
            TimetableItemDetailSummaryCardRow(
                leadingIcon = Icons.Filled.Place,
                label = SessionsStrings.Place.asString(),
                content = timetableItem.room.name.currentLangTitle,
            )
            TimetableItemDetailSummaryCardRow(
                leadingIcon = Icons.Filled.Language,
                label = SessionsStrings.SupportedLanguages.asString(),
                content = timetableItem.getSupportedLangString(isJapaneseLocale),
            )
            TimetableItemDetailSummaryCardRow(
                leadingIcon = Icons.Filled.Category,
                label = SessionsStrings.Category.asString(),
                content = timetableItem.category.title.currentLangTitle,
            )
        }
    }
}

@Preview
@Composable
fun PreviewTimetableItemDetailSummary() {
    KaigiTheme {
        Surface {
            TimetableItemDetailSummaryCard(timetableItem = Session.fake())
        }
    }
}
