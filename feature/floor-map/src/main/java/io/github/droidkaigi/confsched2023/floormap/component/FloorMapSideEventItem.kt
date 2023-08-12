package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.SideEvent

@Composable
fun FloorMapSideEventItem(
    sideEvent: SideEvent,
    onSideEventClick: (SideEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(sideEvent.toString())
    }
}
