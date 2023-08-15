package io.github.droidkaigi.confsched2023.sponsors.section

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.sponsors.component.SponsorHeader
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private const val SPONSOR_LIST_COLUMNS = 6
private const val SINGLE_ITEM_SPAN_COUNT = 6
private const val DOUBLE_ITEM_SPAN_COUNT = 6 / 2
private const val TRIPLE_ITEM_SPAN_COUNT = 6 / 3

@Composable
fun SponsorList(
    platinumSponsors: ImmutableList<Sponsor>,
    goldSponsors: ImmutableList<Sponsor>,
    supporters: ImmutableList<Sponsor>,
    modifier: Modifier = Modifier,
    onSponsorClick: (Sponsor) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(SPONSOR_LIST_COLUMNS),
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        item(
            span = { GridItemSpan(SINGLE_ITEM_SPAN_COUNT) }
        ) {
            SponsorHeader(title = "PLATINUM SPONSORS")
        }
        items(
            items = platinumSponsors,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(SINGLE_ITEM_SPAN_COUNT) }
        ) { sponsor ->
            Text(
                text = sponsor.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item(
            span = { GridItemSpan(SINGLE_ITEM_SPAN_COUNT) }
        ) {
            SponsorHeader(title = "GOLD SPONSORS")
        }
        items(
            items = goldSponsors,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(DOUBLE_ITEM_SPAN_COUNT) }
        ) { sponsor ->
            Text(
                text = sponsor.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item(
            span = { GridItemSpan(SINGLE_ITEM_SPAN_COUNT) }
        ) {
            SponsorHeader(title = "SUPPORTERS")
        }
        items(
            items = supporters,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(TRIPLE_ITEM_SPAN_COUNT) }
        ) { sponsor ->
            Text(
                text = sponsor.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun SponsorListPreview() {
    SponsorList(
        platinumSponsors = Sponsor.fakes().take(5).toImmutableList(),
        goldSponsors = Sponsor.fakes().take(5).toImmutableList(),
        supporters = Sponsor.fakes().take(5).toImmutableList(),
    )
}
