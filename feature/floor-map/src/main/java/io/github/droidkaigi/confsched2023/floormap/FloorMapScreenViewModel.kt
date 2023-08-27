package io.github.droidkaigi.confsched2023.floormap

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.floormap.FloorMapContentUiState.LargeFloorMapContentUiState
import io.github.droidkaigi.confsched2023.floormap.FloorMapContentUiState.SmallFloorMapContentUiState
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapUiState
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.SideEvents
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FloorMapScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(), UserMessageStateHolder by userMessageStateHolder {
    private val floorLevelStateFlow = MutableStateFlow(FloorLevel.Basement)
    private val floorMapSideEventListUiState = FloorMapSideEventListUiState(
        sideEvents = SideEvents,
    )
    private val smallFloorMapSideEventListUiStateFlow =
        buildUiState(floorLevelStateFlow) { floorLevel ->
            FloorMapSideEventListUiState(
                floorMapSideEventListUiState.sideEvents.filter { it.floorLevel == floorLevel }
                    .toImmutableList(),
            )
        }
    private val floorMapUiStateFlow = buildUiState(floorLevelStateFlow) { floorLevel ->
        FloorMapUiState.of(floorLevel)
    }

    val uiState = buildUiState(
        floorLevelStateFlow,
        floorMapUiStateFlow,
        smallFloorMapSideEventListUiStateFlow,
    ) { floorLevel, floorMapUiState, floorMapSideEventListUiState ->
        FloorMapScreenUiState(
            floorLevel = floorLevel,
            smallFloorMapContentUiState = SmallFloorMapContentUiState(
                sideEventListUiState = floorMapSideEventListUiState,
                floorMapUiState = floorMapUiState,
            ),
            largeFloorMapContentUiState = LargeFloorMapContentUiState(
                groundSideEventListUiState = FloorMapSideEventListUiState(
                    this.floorMapSideEventListUiState.sideEvents.filter { it.floorLevel == FloorLevel.Basement }
                        .toImmutableList(),
                ),
                baseSideEventListUiState = FloorMapSideEventListUiState(
                    this.floorMapSideEventListUiState.sideEvents.filter { it.floorLevel == FloorLevel.Ground }
                        .toImmutableList(),
                ),
            ),
        )
    }

    fun onClickFloorLevelSwitcher(floorLevel: FloorLevel) {
        floorLevelStateFlow.value = floorLevel
    }
}
