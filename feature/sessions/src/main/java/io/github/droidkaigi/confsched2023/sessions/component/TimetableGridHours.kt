package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSizes
import io.github.droidkaigi.confsched2023.sessions.section.TimetableState
import kotlin.math.roundToInt

@Composable
fun HoursItem(
    hour: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = hour,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimetableGridHours(
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit,
) {
    val itemProvider = itemProvider({ hoursList.size }) { index ->
        content(hoursList[index])
    }

}

private data class HoursLayout(
    val hours: List<String>,
    val density: Density,
    val verticalScale: Float,
) {
    var hoursHeight = 0
    var hoursWidth = 0
    val minutePx = with(density) { TimetableSizes.minuteHeight.times(verticalScale).toPx() }
    val hoursLayouts = List(hours.size) { index ->
        HoursItemLayout(
            density = density,
            minutePx = minutePx,
            index = index
        ).apply {
            hoursHeight = maxOf(hoursHeight, this.height)
            hoursWidth = maxOf(hoursWidth, this.width)
        }
    }

    fun visibleItemLayouts(
        screenHeight: Int,
        scrollY: Int
    ): List<IndexedValue<HoursItemLayout>> {
        return hoursLayouts.withIndex().filter { (_, layout) ->
            layout.isVisible(screenHeight, scrollY)
        }
    }
}

private data class HoursItemLayout(
    val density: Density,
    val minutePx: Float,
    val index: Int
) {
    val topOffset = with(density) { horizontalLineTopOffset.roundToPx() }
    val itemOffset = with(density) { hoursItemTopOffset.roundToPx() }
    val height = (minutePx * 60).roundToInt()
    val width = with(density) { hoursWidth.roundToPx() }
    val left = 0
    val top = index * height + topOffset - itemOffset
    val bottom = top + height

    fun isVisible(
        screenHeight: Int,
        scrollY: Int
    ): Boolean {
        val screenTop = -scrollY
        val screenBottom = -scrollY + screenHeight
        val yInside =
            top in screenTop..screenBottom || bottom in screenTop..screenBottom
        return yInside
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun itemProvider(
    itemCount: () -> Int,
    itemContent: @Composable (Int) -> Unit
): LazyLayoutItemProvider {
    return object : LazyLayoutItemProvider {
        @Composable
        override fun Item(index: Int, key: Any) {
            itemContent(index)
        }

        override val itemCount: Int get() = itemCount()
    }
}

private val lineStrokeSize = 1.dp
private val horizontalLineTopOffset = 48.dp
private val hoursWidth = 75.dp
private val hoursItemTopOffset = 11.dp
private val hoursList = listOf(
    "10:00",
    "11:00",
    "12:00",
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00",
    "19:00",
)