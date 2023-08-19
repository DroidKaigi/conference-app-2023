package io.github.droidkaigi.confsched2023.sponsors.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter

@Composable
fun SponsorItem(
    sponsor: Sponsor,
    modifier: Modifier = Modifier,
    onSponsorClick: (Sponsor) -> Unit,
) {
    Image(
        painter = previewOverride(previewPainter = { rememberVectorPainter(image = Icons.Default.Image) }) {
            rememberAsyncImagePainter(sponsor.logo)
        },
        contentDescription = sponsor.name,
        modifier = modifier
            .padding(
                top = if (sponsor.plan.isPlatinum) 0.dp else 8.dp,
                bottom = if (sponsor.plan.isPlatinum) 12.dp else 8.dp,
            )
            .background(color = Color.White)
            .clip(RoundedCornerShape(if (sponsor.plan.isSupporter) 4.dp else 8.dp))
            .clickable { onSponsorClick(sponsor) }
            .fillMaxSize(),
    )
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun SponsorItemPreview() {
    KaigiTheme {
        Surface {
            SponsorItem(
                sponsor = Sponsor.fakes().first(),
                onSponsorClick = {},
            )
        }
    }
}
