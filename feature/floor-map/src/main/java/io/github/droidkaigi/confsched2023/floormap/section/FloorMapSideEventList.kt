package io.github.droidkaigi.confsched2023.floormap.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.floormap.component.FloorMapSideEventItem
import io.github.droidkaigi.confsched2023.model.SideEvent
import kotlinx.collections.immutable.ImmutableList

data class FloorMapSideEventListUiState(
    val sideEvents: ImmutableList<SideEvent>,
)

@Composable
fun FloorMapSideEventList(
    uiState: FloorMapSideEventListUiState,
    onSideEventClick: (SideEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(uiState.sideEvents) { sideEvent ->
            FloorMapSideEventItem(
                sideEvent = sideEvent,
                onSideEventClick = onSideEventClick,
            )
        }
    }
}

