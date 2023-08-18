package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.about.R.drawable

const val AboutFooterLinksYouTubeItemTestTag = "AboutFooterLinksYouTubeItem"
const val AboutFooterLinksXItemTestTag = "AboutFooterLinksXItem"
const val AboutFooterLinksMediumItemTestTag = "AboutFooterLinksMediumItem"

@Composable
fun AboutFooterLinks(
    versionName: String?,
    onYouTubeClick: () -> Unit,
    onXClick: () -> Unit,
    onMediumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AboutFooterLinksIcon(
                iconRes = drawable.img_about_youtube,
                testTag = AboutFooterLinksYouTubeItemTestTag,
                contentDescription = "YouTube",
                onClick = onYouTubeClick,
            )
            AboutFooterLinksIcon(
                iconRes = drawable.img_about_x,
                testTag = AboutFooterLinksXItemTestTag,
                contentDescription = "X",
                onClick = onXClick,
            )
            AboutFooterLinksIcon(
                iconRes = drawable.img_about_medium,
                testTag = AboutFooterLinksMediumItemTestTag,
                contentDescription = "Medium",
                onClick = onMediumClick,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = AboutStrings.AppVersion.asString(),
            style = MaterialTheme.typography.labelLarge,
        )
        if (versionName != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = versionName,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun AboutFooterLinksPreview() {
    KaigiTheme {
        Surface {
            AboutFooterLinks(
                versionName = "1.2",
                onYouTubeClick = {},
                onXClick = {},
                onMediumClick = {},
            )
        }
    }
}
