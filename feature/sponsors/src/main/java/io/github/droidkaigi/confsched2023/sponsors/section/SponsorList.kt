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
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter
import kotlinx.collections.immutable.toPersistentList

internal fun LazyListScope.sponsorList(
    title: String,
    gridSize: Int = 1,
    onSponsorClick: (Sponsor) ->Unit,
    sponsorList: List<Sponsor>
){
    val spacerSize = sponsorList.size % gridSize
    val spacerList = List(spacerSize){ Spacer }

    val gridItemList = mutableListOf<Any>().plus(sponsorList).plus(spacerList).toPersistentList()

    item {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }

    items(gridItemList.chunked(gridSize)) {chunkedList ->
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


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SponsorListPreview(){
    KaigiTheme {
        Surface {
            LazyColumn{
                sponsorList(
                    gridSize = 1,
                    title = "PLATINUM SPONSORS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == PLATINUM }
                )

                sponsorList(
                    gridSize = 2,
                    title = "GOLD SPONSORS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == GOLD }
                )

                sponsorList(
                    gridSize = 3,
                    title = "SUPPORTERS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == SUPPORTER }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SponsorListDarkModPreview(){
    KaigiTheme {
        Surface {
            LazyColumn{
                sponsorList(
                    gridSize = 1,
                    title = "PLATINUM SPONSORS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == PLATINUM }
                )

                sponsorList(
                    gridSize = 2,
                    title = "GOLD SPONSORS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == GOLD }
                )

                sponsorList(
                    gridSize = 3,
                    title = "SUPPORTERS",
                    onSponsorClick = {},
                    sponsorList = Sponsor.fakes().filter { it.plan == SUPPORTER }
                )
            }
        }
    }
}