package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings
import io.github.droidkaigi.confsched2023.designsystem.component.ClickableLinkText
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme

@Composable
fun AboutDroidKaigiDetailSummaryCardRow(
    leadingIcon: ImageVector,
    label: String,
    content: String,
    modifier: Modifier = Modifier,
    leadingIconContentDescription: String? = null,
    onLinkClick: (url: String) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = leadingIconContentDescription,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.width(12.dp))
        ClickableLinkText(
            style = MaterialTheme.typography.bodyMedium,
            content = content,
            onLinkClick = onLinkClick,
            regex = AboutStrings.PlaceLink().asString().toRegex(),
            url = AboutStrings.PlaceLink().url,
        )
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun AboutDroidKaigiDetailSummaryCardRowPreview() {
    KaigiTheme {
        Surface {
            AboutDroidKaigiDetailSummaryCardRow(
                leadingIcon = Icons.Filled.Schedule,
                label = "label".repeat(5),
                content = "content".repeat(5),
            )
        }
    }
}
