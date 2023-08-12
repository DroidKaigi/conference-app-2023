package io.github.droidkaigi.confsched2023.floormap

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.MultiLangText
import io.github.droidkaigi.confsched2023.model.SideEvent
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FloorMapScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val sideEvents = persistentListOf(
        SideEvent(
            title = MultiLangText(
                jaTitle = "アプリFiresideチャット(これは仮で後で消えます)",
                enTitle = "App Fireside chat(This is demo event and will be deleted later)",
            ),
            floorLevel = FloorLevel.Basement,
            description = MultiLangText(
                jaTitle = "地下一階でDroidKaigiアプリの開発について、開発者と一緒に語りましょう！(これは仮で後で消えます)",
                enTitle = "(Basement)Let's talk about the development of the DroidKaigi app with the developers!(This is demo event and will be deleted later)",
            ),
            timeText = MultiLangText(
                jaTitle = "DAY1-DAY2 10:00-11:00",
                enTitle = "DAY1-DAY2 10:00-11:00",
            ),
            mark = SideEvent.Mark.Mark1,
            link = "https://github.com/DroidKaigi/conference-app-2023",
        )
    )
    private val floorLevelStateFlow = MutableStateFlow(FloorLevel.Basement)
    private val floorMapSideEventListUiStateFlow = buildUiState(floorLevelStateFlow) { floorLevel ->
        FloorMapSideEventListUiState(
            sideEvents = sideEvents.filter { it.floorLevel == floorLevel }.toImmutableList()
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
