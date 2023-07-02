package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
fun TimetableTab(
    day: Int,
    selected: Boolean,
    onClick: () -> Unit,
    scrollState: TimetableTabScrollState,
    modifier: Modifier = Modifier,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Text(
                    // TODO: Fix to reflect the date from the data
                    text = "Day$day",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    // TODO: Fix to reflect the date from the data
                    text = "${day + 13}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = (1 - scrollState.progress * 2).coerceAtLeast(0f)
                        }
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(
                                placeable.width,
                                placeable.height - (placeable.height * scrollState.progress).roundToInt(),
                            ) {
                                placeable.placeRelative(0, 0)
                            }
                        },
                )
            }
        },
        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.heightIn(min = minTabHeight),
    )
}

@Composable
fun TimetableTabIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .zIndex(-1f)
            .padding(horizontal = 8.dp)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50),
            ),
    )
}

@Composable
fun TimetableTabRow(
    scrollState: TimetableTabScrollState,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            TimetableTabIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
            )
        }
    },
    tabs: @Composable () -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.height(maxTabRowHeight - ((maxTabRowHeight - minTabRowHeight) * scrollState.progress)),
        divider = {},
        indicator = indicator,
        tabs = tabs,
    )
}

@Composable
fun rememberTimetableTabScrollState(): TimetableTabScrollState {
    val offsetLimit = LocalDensity.current.run {
        (maxTabRowHeight - minTabRowHeight).toPx()
    }
    return rememberSaveable(saver = TimetableTabScrollState.Saver) {
        TimetableTabScrollState(
            initialOffsetLimit = -offsetLimit,
        )
    }
}

@Stable
class TimetableTabScrollState(
    initialOffsetLimit: Float = 0f,
    initialScrollOffset: Float = 0f,
) {
    // This value will be like -418.0
    private val scrollOffsetLimit by mutableStateOf(initialOffsetLimit)

    /**
     * If progress is 0f, the tabs is fully expanded.
     * If progress is scrollOffsetLimit, the tabs is fully expanded.
     */
    val progress: Float
        get() = scrollOffset / scrollOffsetLimit

    private val _scrollOffset = mutableStateOf(initialScrollOffset)

    private var scrollOffset: Float
        get() = _scrollOffset.value
        set(newOffset) {
            _scrollOffset.value = newOffset.coerceIn(
                minimumValue = scrollOffsetLimit,
                maximumValue = 0f,
            )
        }

    private val isTabExpandable: Boolean
        get() = scrollOffset > scrollOffsetLimit

    private val isTabCollapsing: Boolean
        get() = scrollOffset != 0f

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            return onPreScrollScreen(available)
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            return onPostScrollScreen(available)
        }
    }

    /**
     * This function returns the consumed offset.
     */
    private fun onPreScrollScreen(availableScrollOffset: Offset): Offset {
        if (availableScrollOffset.y >= 0) return Offset.Zero
        // When scrolled upward
        return if (isTabExpandable) {
            val prevHeightOffset: Float = scrollOffset
            scrollOffset += availableScrollOffset.y
            availableScrollOffset.copy(x = 0f, y = scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    /**
     * This function returns the consumed offset.
     */
    private fun onPostScrollScreen(availableScrollOffset: Offset): Offset {
        if (availableScrollOffset.y < 0f) return Offset.Zero
        return if (isTabCollapsing && availableScrollOffset.y > 0) {
            // When scrolling downward and overscroll
            val prevHeightOffset = scrollOffset
            scrollOffset += availableScrollOffset.y
            availableScrollOffset.copy(x = 0f, y = scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    companion object {
        val Saver: Saver<TimetableTabScrollState, *> = listSaver(
            save = { listOf(it.scrollOffsetLimit, it.scrollOffset) },
            restore = {
                TimetableTabScrollState(
                    initialOffsetLimit = it[0],
                    initialScrollOffset = it[1],
                )
            },
        )
    }
}

private val minTabHeight = 32.dp
private val maxTabRowHeight = 84.dp
private val minTabRowHeight = 56.dp
