package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.component.StampImage
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail
import kotlinx.collections.immutable.ImmutableList

private const val StampListColumns = 2
private const val SingleItemSpanCount = 2
private const val DoubleItemSpanCount = 2 / 2

data class StampListUiState(
    val stamps: ImmutableList<Stamp>,
    val detailDescription: String,
    val isResetButtonEnabled: Boolean,
)

@Composable
fun StampList(
    uiState: StampListUiState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyVerticalGrid(
            columns = Fixed(StampListColumns),
            modifier = modifier,
            contentPadding = PaddingValues(
                start = 16.dp + contentPadding.calculateStartPadding(layoutDirection),
                end = 16.dp + contentPadding.calculateEndPadding(layoutDirection),
                top = 20.dp + contentPadding.calculateTopPadding(),
                bottom = 20.dp + contentPadding.calculateBottomPadding(),
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                key = "stamps_header",
                span = { GridItemSpan(SingleItemSpanCount) },
            ) {
                StampsDetail(uiState.detailDescription)
            }
            items(
                items = uiState.stamps,
                key = { stamp -> stamp.hasDrawableResId },
                span = { stamp ->
                    GridItemSpan(
                        if (stamp == uiState.stamps.last() && uiState.stamps.size % StampListColumns != 0) {
                            SingleItemSpanCount
                        } else {
                            DoubleItemSpanCount
                        },
                    )
                },
            ) { stamp ->
                StampImage(
                    stamp = stamp,
                )
            }
            if (uiState.isResetButtonEnabled) {
                item(
                    key = "reset_button",
                    span = { GridItemSpan(SingleItemSpanCount) },
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onReset,
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }
    }
}
