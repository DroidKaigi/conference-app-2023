package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Min
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItem.Special
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.component.SummaryInfoType.Category
import io.github.droidkaigi.confsched2023.sessions.component.SummaryInfoType.Language
import io.github.droidkaigi.confsched2023.sessions.component.SummaryInfoType.Place
import io.github.droidkaigi.confsched2023.sessions.component.SummaryInfoType.Schedule

@Composable
fun TimetableItemDetailSummary(
    uiState: TimetableItem,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is Session -> {
            SummaryInfo(
                date = uiState.sessionDateDetailString.currentLangTitle,
                place = uiState.roomString.currentLangTitle,
                language = uiState.languageString.currentLangTitle,
                category = uiState.category.title.currentLangTitle,
                modifier = modifier,
            )
        }

        is Special -> {
            // do nothing
        }
    }
}

@Composable
private fun SummaryInfo(
    date: String,
    place: String,
    language: String,
    category: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryInfoType.values().forEach { type ->
                when (type) {
                    Schedule -> {
                        SummaryInfoItem(
                            icon = type.icon,
                            title = type.title,
                            description = date,
                        )
                    }

                    Place -> {
                        SummaryInfoItem(
                            icon = type.icon,
                            title = type.title,
                            description = place,
                        )
                    }

                    Language -> {
                        SummaryInfoItem(
                            icon = type.icon,
                            title = type.title,
                            description = language,
                        )
                    }

                    Category -> {
                        SummaryInfoItem(
                            icon = type.icon,
                            title = type.title,
                            description = category,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryInfoItem(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp),
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 12.dp),
        )
    }
}

private enum class SummaryInfoType(val icon: ImageVector, val title: String) {
    Schedule(icon = Icons.Default.Schedule, title = SessionsStrings.Date.asString()),
    Place(icon = Icons.Default.LocationOn, title = SessionsStrings.Place.asString()),
    Language(icon = Icons.Default.Language, title = SessionsStrings.SupportLanguage.asString()),
    Category(icon = Icons.Default.Category, title = SessionsStrings.Category.asString()),
    ;
}
