package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.stamps.component.Stamps
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail

data class StampsSheetUiState(
    val isStampA: Boolean,
    val isStampB: Boolean,
    val isStampC: Boolean,
    val isStampD: Boolean,
    val isStampE: Boolean,
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
            Stamps(
                isStampA = uiState.isStampA,
                isStampB = uiState.isStampB,
                isStampC = uiState.isStampC,
                isStampD = uiState.isStampD,
                isStampE = uiState.isStampE,
                onStampsClick = onStampsClick,
            )
        }
    }
}
