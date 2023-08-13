package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val AboutFooterLinksYouTubeItemTestTag = "AboutFooterLinksYouTubeItem"
const val AboutFooterLinksXItemTestTag = "AboutFooterLinksXItem"
const val AboutFooterLinksMediumItemTestTag = "AboutFooterLinksMediumItem"

@Composable
fun AboutFooterLinks(
    onYouTubeClick: () -> Unit,
    onXClick: () -> Unit,
    onMediumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text("This is AboutFooterLinks")
        Text(
            text = "Go to YouTube",
            modifier = Modifier
                .testTag(AboutFooterLinksYouTubeItemTestTag)
                .clickable { onYouTubeClick() },
        )
        Text(
            text = "Go to X",
            modifier = Modifier
                .testTag(AboutFooterLinksXItemTestTag)
                .clickable { onXClick() },
        )
        Text(
            text = "Go to Medium",
            modifier = Modifier
                .testTag(AboutFooterLinksMediumItemTestTag)
                .clickable { onMediumClick() },
        )
    }
}
