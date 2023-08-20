package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableScreenScrollState
import io.github.droidkaigi.confsched2023.sessions.component.TimetableTab
import io.github.droidkaigi.confsched2023.sessions.component.TimetableTabRow
import io.github.droidkaigi.confsched2023.sessions.component.TimetableTabState
import io.github.droidkaigi.confsched2023.sessions.component.rememberTimetableTabState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.GridTimetable
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.ListTimetable

sealed interface TimetableSheetUiState {
    data object Empty : TimetableSheetUiState
    data class ListTimetable(
        val timetableListUiStates: Map<DroidKaigi2023Day, TimetableListUiState>,
    ) : TimetableSheetUiState

    data class GridTimetable(
        val timetableGridUiState: Map<DroidKaigi2023Day, TimetableGridUiState>,
    ) : TimetableSheetUiState
}

@Composable
fun TimetableSheet(
    uiState: TimetableSheetUiState,
    timetableScreenScrollState: TimetableScreenScrollState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onFavoriteClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedDay by rememberSaveable { mutableStateOf(DroidKaigi2023Day.Day1) }
    val corner by animateIntAsState(
        if (timetableScreenScrollState.isScreenLayoutCalculating || timetableScreenScrollState.isSheetExpandable) 40 else 0,
        label = "Timetable corner state",
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
    ) {
        val timetableSheetContentScrollState = rememberTimetableSheetContentScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(timetableSheetContentScrollState.nestedScrollConnection),
        ) {
            TimetableTabRow(
                tabState = timetableSheetContentScrollState.tabScrollState,
                selectedTabIndex = DroidKaigi2023Day.entries.indexOf(selectedDay),
            ) {
                DroidKaigi2023Day.entries.forEach { day ->
                    TimetableTab(
                        day = day,
                        selected = day == selectedDay,
                        onClick = {
                            selectedDay = day
                        },
                        scrollState = timetableSheetContentScrollState.tabScrollState,
                    )
                }
            }
            when (uiState) {
                is Empty -> {
                    TimetableShimmerList(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                }

                is ListTimetable -> {
                    TimetableList(
                        uiState = requireNotNull(uiState.timetableListUiStates[selectedDay]),
                        onTimetableItemClick = onTimetableItemClick,
                        onBookmarkClick = onFavoriteClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                }

                is GridTimetable -> {
                    val nestedScrollDispatcher = remember { NestedScrollDispatcher() }
                    TimetableGrid(
                        uiState = requireNotNull(uiState.timetableGridUiState[selectedDay]),
                        nestedScrollDispatcher = nestedScrollDispatcher,
                        onTimetableItemClick = onTimetableItemClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(
                                timetableSheetContentScrollState.nestedScrollConnection,
                                nestedScrollDispatcher,
                            )
                            .weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
fun rememberTimetableSheetContentScrollState(
    tabScrollState: TimetableTabState = rememberTimetableTabState(),
): TimetableSheetContentScrollState {
    return remember { TimetableSheetContentScrollState(tabScrollState) }
}

@Stable
class TimetableSheetContentScrollState(
    val tabScrollState: TimetableTabState,
) {
    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            return onPreScrollSheetContent(available)
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            return onPostScrollSheetContent(available)
        }
    }

    /**
     * @return consumed offset
     */
    private fun onPreScrollSheetContent(availableScrollOffset: Offset): Offset {
        if (availableScrollOffset.y >= 0) return Offset.Zero
        // When scrolled upward
        return if (tabScrollState.isTabExpandable) {
            val prevHeightOffset: Float = tabScrollState.scrollOffset
            tabScrollState.onScroll(availableScrollOffset.y)
            availableScrollOffset.copy(x = 0f, y = tabScrollState.scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    /**
     * @return consumed offset
     */
    private fun onPostScrollSheetContent(availableScrollOffset: Offset): Offset {
        if (availableScrollOffset.y < 0f) return Offset.Zero
        return if (tabScrollState.isTabCollapsing && availableScrollOffset.y > 0) {
            // When scrolling downward and overscroll
            val prevHeightOffset = tabScrollState.scrollOffset
            tabScrollState.onScroll(availableScrollOffset.y)
            availableScrollOffset.copy(x = 0f, y = tabScrollState.scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }
}
