package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.FloorLevel

@Composable
fun FloorMap(
    floorLevel: FloorLevel,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(floorLevel.toString())
    }
}
