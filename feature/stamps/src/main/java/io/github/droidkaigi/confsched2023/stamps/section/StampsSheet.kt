package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.stamps.component.Stamps
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail
import kotlinx.collections.immutable.ImmutableList

data class StampsSheetUiState(
    val stamps: ImmutableList<String>,
)

@Composable
fun StampsSheet(
    uiState: StampsSheetUiState,
    onStampsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            StampsDetail()
        }
        item {
            Stamps(stamps = uiState.stamps, onStampsClick = onStampsClick)
        }
    }
}
