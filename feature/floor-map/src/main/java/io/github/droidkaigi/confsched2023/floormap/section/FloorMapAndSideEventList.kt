package io.github.droidkaigi.confsched2023.floormap.section

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.floormap.component.FloorMapSideEventItem
import io.github.droidkaigi.confsched2023.model.SideEvent
import kotlinx.collections.immutable.ImmutableList

data class FloorMapSideEventListUiState(
    val sideEvents: ImmutableList<SideEvent>,
)

@Composable
fun FloorMapAndSideEventList(
    floorMapUiState: FloorMapUiState,
    sideEventListUiState: FloorMapSideEventListUiState,
    onSideEventClick: (url: String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        item {
            FloorMap(uiState = floorMapUiState)
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(sideEventListUiState.sideEvents) { sideEvent ->
            FloorMapSideEventItem(
                sideEvent = sideEvent,
                onSideEventClick = onSideEventClick,
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

fun Modifier.fadingEdge(
    brush: Brush,
) = graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
