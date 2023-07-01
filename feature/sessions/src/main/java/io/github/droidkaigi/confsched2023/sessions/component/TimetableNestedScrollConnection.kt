package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.abs

@Composable
fun rememberTimetableScrollState(): TimetableScrollState {
    return rememberSaveable(saver = TimetableScrollState.Saver) {
        TimetableScrollState()
    }
}

@Stable
class TimetableScrollState(
    initialOffsetLimit: Float = 0f,
    initialScrollOffset: Float = 0f,
) {
    var scrollOffsetLimit by mutableStateOf(initialOffsetLimit)
        private set

    private val _scrollOffset = mutableStateOf(initialScrollOffset)
    var scrollOffset: Float
        get() = _scrollOffset.value
        internal set(newOffset) {
            _scrollOffset.value = newOffset.coerceIn(
                minimumValue = scrollOffsetLimit,
                maximumValue = 0f,
            )
        }

    val enableExpandTimetable: Boolean
        get() = scrollOffset > scrollOffsetLimit

    val isExpandedTimetable: Boolean
        get() = scrollOffset != 0f

    fun updateScrollOffsetLimit(offsetLimit: Float) {
        scrollOffsetLimit = 0f - abs(offsetLimit)
    }

    companion object {
        val Saver: Saver<TimetableScrollState, *> = listSaver(
            save = { listOf(it.scrollOffsetLimit, it.scrollOffset) },
            restore = {
                TimetableScrollState(
                    initialOffsetLimit = it[0],
                    initialScrollOffset = it[1],
                )
            },
        )
    }
}

class TimetableNestedScrollConnection(
    private val state: TimetableScrollState,
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (available.y >= 0) return Offset.Zero
        // When scrolled upward
        return if (state.enableExpandTimetable && state.scrollOffsetLimit != 0f) {
            // Add offset up to the height of TopAppBar and consume all
            val prevHeightOffset: Float = state.scrollOffset
            state.scrollOffset += available.y
            available.copy(x = 0f, y = state.scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset {
        if (available.y < 0f) return Offset.Zero
        return if (state.isExpandedTimetable && available.y > 0) {
            // When scrolling downward and overscroll
            val prevHeightOffset = state.scrollOffset
            state.scrollOffset += available.y
            available.copy(x = 0f, y = state.scrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }
}
