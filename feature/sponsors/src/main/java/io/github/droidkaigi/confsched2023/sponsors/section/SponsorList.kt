package io.github.droidkaigi.confsched2023.sponsors.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SponsorList(
    sponsors: ImmutableList<Sponsor>,
    modifier: Modifier = Modifier,
    onSponsorClick: (Sponsor) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = "PLATINUM SPONSORS",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        items(sponsors) { sponsor ->
            Text(
                text = sponsor.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun SponsorListPreview() {
    SponsorList(
        sponsors = Sponsor.fakes(),
    )
}
