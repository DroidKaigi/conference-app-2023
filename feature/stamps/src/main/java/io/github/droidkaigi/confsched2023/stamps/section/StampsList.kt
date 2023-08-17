package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail
import kotlinx.collections.immutable.ImmutableList

private const val STAMP_LIST_COLUMNS = 2
private const val SINGLE_ITEM_SPAN_COUNT = 2
private const val DOUBLE_ITEM_SPAN_COUNT = 2 / 2

@Composable
fun StampsList(
    stamps: ImmutableList<Stamp>,
    onStampsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = Fixed(STAMP_LIST_COLUMNS),
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            key = "stamps_header",
            span = { GridItemSpan(SINGLE_ITEM_SPAN_COUNT) }
        ) {
            StampsDetail()
        }
        items(
            items = stamps,
            key = { stamp -> stamp.hasDrawableResId },
            span = { stamp ->
                GridItemSpan(
                    if (stamp == stamps.last() && stamps.size % STAMP_LIST_COLUMNS != 0) {
                        SINGLE_ITEM_SPAN_COUNT
                    } else {
                        DOUBLE_ITEM_SPAN_COUNT
                    }
                )
            }
        ) { stamp ->
            Image(
                painter = painterResource(id = stamp.getDrawableResId()),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onStampsClick() }
                    .padding(horizontal = 21.dp),
            )
        }
    }
}
