package io.github.droidkaigi.confsched2023.sponsors.section

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Plan.GOLD
import io.github.droidkaigi.confsched2023.model.Plan.PLATINUM
import io.github.droidkaigi.confsched2023.model.Plan.SUPPORTER
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.sponsors.SponsorsStrings
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

data class SponsorListUiState(
    val title: SponsorsStrings,
    val gridSize: Int,
    val sponsorList: PersistentList<Sponsor>,
)

internal fun LazyListScope.sponsorList(
    uiState: SponsorListUiState,
    onSponsorClick: (Sponsor) ->Unit,
){
    val spacerSize = uiState.sponsorList.size % uiState.gridSize
    val spacerList = List(spacerSize){ Spacer }

    val gridItemList = mutableListOf<Any>().plus(uiState.sponsorList).plus(spacerList).toPersistentList()

    item {
        Text(
            text = uiState.title.asString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }

    items(gridItemList.chunked(uiState.gridSize)) {chunkedList ->
        Row {
            chunkedList.forEachIndexed{ index, item ->
                val padding = PaddingValues(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = if(index == 0) 0.dp else 6.dp,
                    end = if(index == chunkedList.lastIndex) 0.dp else 6.dp
                )
                when(item){
                    is Sponsor -> Image(
                        painter = previewOverride(
                            previewPainter = {
                                rememberVectorPainter(image = Icons.Default.Image)
                            }
                        ) {
                            rememberAsyncImagePainter(item.logo)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .padding(padding)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSponsorClick(item) }
                            .weight(1f),
                    )
                    is Spacer -> Spacer(
                        modifier = Modifier
                            .padding(padding)
                            .clip(RoundedCornerShape(8.dp))
                            .weight(1f)
                    )
                }
            }
        }
    }
}

private object Spacer

@Preview(locale = "ja", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(locale = "en", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SponsorListPreview(){
    KaigiTheme {
        Surface {
            LazyColumn{
                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == PLATINUM }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )

                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == GOLD }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )

                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == SUPPORTER }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )
            }
        }
    }
}

@Preview(locale = "ja", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(locale = "en", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SponsorListDarkModePreview(){
    KaigiTheme {
        Surface {
            LazyColumn{
                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == PLATINUM }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )

                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == GOLD }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )

                sponsorList(
                    uiState = SponsorListUiState(
                        title = SponsorsStrings.PlatinumSponsors,
                        gridSize = 1,
                        sponsorList = Sponsor.fakes().filter { it.plan == SUPPORTER }.toPersistentList()
                    ),
                    onSponsorClick = {},
                )
            }
        }
    }
}
