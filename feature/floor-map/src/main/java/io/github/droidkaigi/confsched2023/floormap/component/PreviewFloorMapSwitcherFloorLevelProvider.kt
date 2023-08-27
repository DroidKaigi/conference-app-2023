package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.droidkaigi.confsched2023.model.FloorLevel

class PreviewFloorMapSwitcherFloorLevelProvider : PreviewParameterProvider<FloorLevel> {
    override val values: Sequence<FloorLevel>
        get() = FloorLevel.values().asSequence()
}
