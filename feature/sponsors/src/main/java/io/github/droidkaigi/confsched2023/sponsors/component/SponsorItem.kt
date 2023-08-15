package io.github.droidkaigi.confsched2023.sponsors.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes

@Composable
fun SponsorItem(
    sponsor: Sponsor,
    modifier: Modifier = Modifier,
    onSponsorClick: (Sponsor) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = if (sponsor.plan.isPlatinum) 0.dp else 8.dp,
                bottom = if (sponsor.plan.isPlatinum) 12.dp else 8.dp
            )
            .clickable { onSponsorClick(sponsor) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(if (sponsor.plan.isSupporter) 4.dp else 8.dp)
                )
        ) {
            // TODO Implement Sponsor UI
        }

    }
}

@Preview
@Composable
fun SponsorItemPreview() {
    SponsorItem(
        sponsor = Sponsor.fakes().first(),
        onSponsorClick = {},
    )
}
