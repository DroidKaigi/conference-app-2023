package io.github.droidkaigi.confsched2023.sponsors.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Plan.GOLD
import io.github.droidkaigi.confsched2023.model.Plan.PLATINUM
import io.github.droidkaigi.confsched2023.model.Plan.SUPPORTER
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.sponsors.SponsorsStrings
import io.github.droidkaigi.confsched2023.sponsors.component.SponsorHeader
import io.github.droidkaigi.confsched2023.sponsors.component.SponsorItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private const val SponsorListColumns = 6
private const val SingleItemSpanCount = 6
private const val DoubleItemSpanCount = 6 / 2
private const val TripleItemSpanCount = 6 / 3

data class SponsorListUiState(
    val platinumSponsors: ImmutableList<Sponsor>,
    val goldSponsors: ImmutableList<Sponsor>,
    val supporters: ImmutableList<Sponsor>,
)

@Composable
fun SponsorList(
    uiState: SponsorListUiState,
    modifier: Modifier = Modifier,
    onSponsorClick: (Sponsor) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(SponsorListColumns),
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(
            key = "platinum_sponsor_header",
            span = { GridItemSpan(SingleItemSpanCount) },
        ) {
            SponsorHeader(title = SponsorsStrings.PlatinumSponsors.asString())
        }
        items(
            items = uiState.platinumSponsors,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(SingleItemSpanCount) },
        ) { sponsor ->
            SponsorItem(
                sponsor = sponsor,
                modifier = Modifier.height(110.dp),
                onSponsorClick = onSponsorClick,
            )
        }
        item(
            key = "spacer_under_platinum_sponsor",
        ) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item(
            key = "gold_sponsor_header",
            span = { GridItemSpan(SingleItemSpanCount) },
        ) {
            SponsorHeader(title = SponsorsStrings.GoldSponsors.asString())
        }
        items(
            items = uiState.goldSponsors,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(DoubleItemSpanCount) },
        ) { sponsor ->
            SponsorItem(
                sponsor = sponsor,
                modifier = Modifier.height(77.dp),
                onSponsorClick = onSponsorClick,
            )
        }
        item(
            key = "supporter_header",
            span = { GridItemSpan(SingleItemSpanCount) },
        ) {
            SponsorHeader(title = SponsorsStrings.Supporters.asString())
        }
        items(
            items = uiState.supporters,
            key = { sponsor -> sponsor.name },
            span = { GridItemSpan(TripleItemSpanCount) },
        ) { sponsor ->
            SponsorItem(
                sponsor = sponsor,
                modifier = Modifier.height(77.dp),
                onSponsorClick = onSponsorClick,
            )
        }
        item(
            key = "spacer_under_supporter",
        ) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun SponsorListPreview() {
    val uiState = SponsorListUiState(
        platinumSponsors = Sponsor.fakes().filter { it.plan == PLATINUM }.toImmutableList(),
        goldSponsors = Sponsor.fakes().filter { it.plan == GOLD }.toImmutableList(),
        supporters = Sponsor.fakes().filter { it.plan == SUPPORTER }.toImmutableList(),
    )
    KaigiTheme {
        Surface {
            SponsorList(
                uiState = uiState,
            )
        }
    }
}
