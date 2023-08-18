package io.github.droidkaigi.confsched2023.sponsors.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme

@Composable
fun SponsorHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        modifier = modifier.padding(vertical = 16.dp),
        style = MaterialTheme.typography.titleMedium,
    )
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun SponsorHeaderPreview() {
    KaigiTheme {
        Surface {
            SponsorHeader(
                title = "PLATINUM SPONSORS",
            )
        }
    }
}
