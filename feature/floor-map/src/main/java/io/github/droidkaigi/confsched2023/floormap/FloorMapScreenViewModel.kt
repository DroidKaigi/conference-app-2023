package io.github.droidkaigi.confsched2023.floormap

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
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
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val floorLevelStateFlow = MutableStateFlow(FloorLevel.Basement)
    private val floorMapSideEventListUiStateFlow = buildUiState(floorLevelStateFlow) { floorLevel ->
        FloorMapSideEventListUiState(
            sideEvents = SideEvents.filter { it.floorLevel == floorLevel }.toImmutableList(),
        )
    }
    val uiState = buildUiState(
        floorLevelStateFlow,
        floorMapSideEventListUiStateFlow,
    ) { floorLevel, floorMapSideEventListUiState ->
        FloorMapScreenUiState(
            floorMapSideEventListUiState = floorMapSideEventListUiState,
            floorLevel = floorLevel,
        )
    }
}
