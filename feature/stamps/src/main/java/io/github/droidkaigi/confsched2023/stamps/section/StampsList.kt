package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.component.StampImage
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail
import kotlinx.collections.immutable.ImmutableList

private const val StampListColumns = 2
private const val SingleItemSpanCount = 2
private const val DoubleItemSpanCount = 2 / 2

const val StampsListLazyVerticalGridTestTag = "StampsListLazyVerticalGridTestTag"

@Composable
fun StampsList(
    stamps: ImmutableList<Stamp>,
    onStampsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = Fixed(StampListColumns),
        modifier = modifier.testTag(StampsListLazyVerticalGridTestTag),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(
            key = "stamps_header",
            span = { GridItemSpan(SingleItemSpanCount) },
        ) {
            StampsDetail()
        }
        items(
            items = stamps,
            key = { stamp -> stamp.hasDrawableResId },
            span = { stamp ->
                GridItemSpan(
                    if (stamp == stamps.last() && stamps.size % StampListColumns != 0) {
                        SingleItemSpanCount
                    } else {
                        DoubleItemSpanCount
                    },
                )
            },
        ) { stamp ->
            StampImage(stamp = stamp, onStampClick = onStampsClick)
        }
    }
}
