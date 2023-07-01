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
fun rememberTimetableScreenScrollState(): TimetableScreenScrollState {
    return rememberSaveable(saver = TimetableScreenScrollState.Saver) {
        TimetableScreenScrollState()
    }
}

@Stable
class TimetableScreenScrollState(
    initialSheetOffsetLimit: Float = 0f,
    initialSheetScrollOffset: Float = 0f,
) {
    // This value will be like -418.0
    var sheetScrollOffsetLimit by mutableStateOf(initialSheetOffsetLimit)

    val isScreenLayoutCalculating get() = sheetScrollOffsetLimit == 0f

    private val _sheetScrollOffset = mutableStateOf(initialSheetScrollOffset)

    /**
     * If sheetScrollOffset is 0f, the sheet is fully collapsed.
     * If sheetScrollOffset is sheetScrollOffsetLimit, the sheet is fully expanded.
     */
    var sheetScrollOffset: Float
        get() = _sheetScrollOffset.value
        internal set(newOffset) {
            _sheetScrollOffset.value = newOffset.coerceIn(
                minimumValue = sheetScrollOffsetLimit,
                maximumValue = 0f,
            )
        }

    val isSheetExpandable: Boolean
        get() = sheetScrollOffset > sheetScrollOffsetLimit

    private val isSheetScrolled: Boolean
        get() = sheetScrollOffset != 0f

    val screenNestedScrollConnection: NestedScrollConnection
        get() = object : NestedScrollConnection {
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
        return if (isSheetExpandable && !isScreenLayoutCalculating) {
            // Add offset up to the height of TopAppBar and consume all
            val prevHeightOffset: Float = sheetScrollOffset
            sheetScrollOffset += availableScrollOffset.y
            availableScrollOffset.copy(x = 0f, y = sheetScrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    /**
     * This function returns the consumed offset.
     */
    private fun onPostScrollScreen(availableScrollOffset: Offset): Offset {
        if (availableScrollOffset.y < 0f) return Offset.Zero
        return if (isSheetScrolled && availableScrollOffset.y > 0) {
            // When scrolling downward and overscroll
            val prevHeightOffset = sheetScrollOffset
            sheetScrollOffset += availableScrollOffset.y
            availableScrollOffset.copy(x = 0f, y = sheetScrollOffset - prevHeightOffset)
        } else {
            Offset.Zero
        }
    }

    fun onLargeTopBarPositioned(largeAppBarHeight: Float, statusBarHeight: Float) {
        sheetScrollOffsetLimit = 0f - abs(largeAppBarHeight - statusBarHeight)
    }

    companion object {
        val Saver: Saver<TimetableScreenScrollState, *> = listSaver(
            save = { listOf(it.sheetScrollOffsetLimit, it.sheetScrollOffset) },
            restore = {
                TimetableScreenScrollState(
                    initialSheetOffsetLimit = it[0],
                    initialSheetScrollOffset = it[1],
                )
            },
        )
    }
}
