package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.ScrollAxisRange
import androidx.compose.ui.semantics.horizontalScrollAxisRange
import androidx.compose.ui.semantics.scrollBy
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.verticalScrollAxisRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.component.TimetableGridItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus
import kotlin.math.roundToInt

data class TimetableGridUiState(val timetable: Timetable)

@Composable
fun TimetableGrid(
    uiState: TimetableGridUiState,
    nestedScrollDispatcher: NestedScrollDispatcher,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val timetableGridState = rememberTimetableGridState()
    TimetableGrid(
        timetable = uiState.timetable,
        timetableState = timetableGridState,
        nestedScrollDispatcher = nestedScrollDispatcher,
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
    ) { timetableItem ->
        TimetableGridItem(
            timetableItem = timetableItem,
            onTimetableItemClick = onTimetableItemClick,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimetableGrid(
    timetable: Timetable,
    timetableState: TimetableState,
    nestedScrollDispatcher: NestedScrollDispatcher,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable (TimetableItem) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val itemProvider = itemProvider({ timetable.timetableItems.size }) { index ->
        val timetableItemWithFavorite = timetable.contents[index]
        content(timetableItemWithFavorite.timetableItem)
    }
    val density = timetableState.density
    val verticalScale = timetableState.screenScaleState.verticalScale
    val timetableLayout = remember(timetable, verticalScale) {
        TimetableLayout(timetable = timetable, density = density, verticalScale = verticalScale)
    }
    val scrollState = timetableState.screenScrollState
    val timetableScreen = remember(timetableLayout, density) {
        TimetableScreen(
            timetableLayout,
            scrollState,
            density,
        )
    }
    val visibleItemLayouts by remember(timetableScreen) { timetableScreen.visibleItemLayouts }
    val lineColor = MaterialTheme.colorScheme.surfaceVariant
    val linePxSize = with(timetableState.density) { TimetableSizes.lineStrokeSize.toPx() }
    val layoutDirection = LocalLayoutDirection.current

    LazyLayout(
        modifier = modifier
            .padding(
                top = contentPadding.calculateTopPadding(),
                start = contentPadding.calculateStartPadding(layoutDirection),
                end = contentPadding.calculateEndPadding(layoutDirection),
            )
            .focusGroup()
            .clipToBounds()
            .drawBehind {
                timetableScreen.timeHorizontalLines.value.forEach {
                    drawLine(
                        lineColor,
                        Offset(0F, it),
                        Offset(timetableScreen.width.toFloat(), it),
                        linePxSize,
                    )
                }
                timetableScreen.roomVerticalLines.value.forEach {
                    drawLine(
                        lineColor,
                        Offset(it, 0f),
                        Offset(it, timetableScreen.height.toFloat()),
                        linePxSize,
                    )
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        if (timetableScreen.enableHorizontalScroll(dragAmount.x)) {
                            if (change.positionChange() != Offset.Zero) change.consume()
                        }
                        coroutineScope.launch {
                            timetableScreen.scroll(
                                dragAmount,
                                change.uptimeMillis,
                                change.position,
                                nestedScrollDispatcher,
                            )
                        }
                    },
                    onDragCancel = {
                        scrollState.resetTracking()
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            scrollState.flingIfPossible()
                        }
                    },
                )
            }
            // FIXME: This disables timetable scroll
//            .transformable(
//                rememberTransformableStateForScreenScale(timetableState.screenScaleState),
//            )
            .semantics {
                horizontalScrollAxisRange = ScrollAxisRange(
                    value = { -scrollState.scrollX },
                    maxValue = { -scrollState.maxX },
                )
                verticalScrollAxisRange = ScrollAxisRange(
                    value = { -scrollState.scrollY },
                    maxValue = { -scrollState.maxY },
                )
                scrollBy(
                    action = { x: Float, y: Float ->
                        coroutineScope.launch {
                            scrollState.scroll(
                                scrollX = x,
                                scrollY = y,
                                timeMillis = 0,
                                position = Offset.Zero,
                            )
                        }
                        return@scrollBy true
                    },
                )
            },
        itemProvider = itemProvider,
    ) { constraint ->

        data class ItemData(val placeable: Placeable, val timetableItem: TimetableItemLayout)
        if (timetableScreen.width != constraint.maxWidth ||
            timetableScreen.height != constraint.maxHeight
        ) {
            timetableScreen.updateBounds(
                width = constraint.maxWidth,
                height = constraint.maxHeight,
                bottomPadding = contentPadding.calculateBottomPadding(),
            )
            val originalContentHeight = timetableScreen.timetableLayout.timetableHeight *
                timetableState.screenScaleState.verticalScale
            val layoutHeight = constraint.maxHeight
            timetableState.screenScaleState.updateVerticalScaleLowerBound(
                layoutHeight.toFloat() / originalContentHeight,
            )
        }

        val items = visibleItemLayouts.map { (index, timetableLayout) ->
            ItemData(
                placeable = measure(
                    index,
                    Constraints.fixed(
                        width = timetableLayout.width,
                        height = timetableLayout.height,
                    ),
                )[0],
                timetableItem = timetableLayout,
            )
        }
        layout(constraint.maxWidth, constraint.maxHeight) {
            items.forEach { (placable, timetableLayout) ->
                placable.place(
                    timetableLayout.left + timetableScreen.scrollState.scrollX.toInt(),
                    timetableLayout.top + timetableScreen.scrollState.scrollY.toInt(),
                )
            }
        }
    }
}

@Preview
@Composable
fun TimetablePreview() {
    val timetableState = rememberTimetableGridState()
    TimetableGrid(
        modifier = Modifier.fillMaxSize(),
        timetable = Timetable.fake(),
        timetableState = timetableState,
        nestedScrollDispatcher = remember { NestedScrollDispatcher() },
    ) { timetableItem ->
        TimetableGridItem(
            timetableItem = timetableItem,
            onTimetableItemClick = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun itemProvider(
    itemCount: () -> Int,
    itemContent: @Composable (Int) -> Unit,
): LazyLayoutItemProvider {
    return object : LazyLayoutItemProvider {
        @Composable
        override fun Item(index: Int, key: Any) {
            itemContent(index)
        }

        override val itemCount: Int get() = itemCount()
    }
}

private data class TimetableItemLayout(
    val timetableItem: TimetableItem,
    val rooms: List<TimetableRoom>,
    val dayStartTime: Instant,
    val density: Density,
    val minutePx: Float,
    val dayToStartTime: MutableMap<DroidKaigi2023Day, Instant>,
) {
    val dayStart = dayToStartTime[timetableItem.day] ?: dayStartTime
    private val displayEndsAt = timetableItem.endsAt.minus(1, DateTimeUnit.MINUTE)
    val height =
        ((displayEndsAt - timetableItem.startsAt).inWholeMinutes * minutePx).roundToInt()
    val width = with(density) { TimetableSizes.columnWidth.roundToPx() }
    val left = rooms.indexOf(timetableItem.room) * width
    val top = ((timetableItem.startsAt - dayStart).inWholeMinutes * minutePx).toInt()
    val right = left + width
    val bottom = top + height

    fun isVisible(
        screenWidth: Int,
        screenHeight: Int,
        scrollX: Int,
        scrollY: Int,
    ): Boolean {
        val screenLeft = -scrollX
        val screenRight = -scrollX + screenWidth
        val screenTop = -scrollY
        val screenBottom = -scrollY + screenHeight
        val xInside =
            left in screenLeft..screenRight || right in screenLeft..screenRight
        val yInside =
            top in screenTop..screenBottom || bottom in screenTop..screenBottom ||
                (top <= screenTop && screenBottom <= bottom)
        return xInside && yInside
    }
}

private data class TimetableLayout(
    val timetable: Timetable,
    val density: Density,
    val verticalScale: Float,
) {
    val rooms = timetable.rooms
    val dayStartTime = timetable.timetableItems.minOfOrNull { it.startsAt }
    var timetableHeight = 0
    var timetableWidth = 0
    val minutePx = with(density) { TimetableSizes.minuteHeight.times(verticalScale).toPx() }
    val dayToStartTime = run {
        val dayToStartTime = mutableMapOf<DroidKaigi2023Day, Instant>()
        timetable.timetableItems.forEach { timetableItem ->
            timetableItem.day?.let {
                dayToStartTime[it] = minOf(
                    dayToStartTime[it] ?: Instant.DISTANT_FUTURE,
                    timetableItem.startsAt,
                )
            }
        }
        dayToStartTime
    }
    val timetableLayouts = timetable.timetableItems.map {
        val timetableItemLayout = TimetableItemLayout(
            timetableItem = it,
            rooms = rooms,
            dayToStartTime = dayToStartTime,
            dayStartTime = dayStartTime!!,
            density = density,
            minutePx = minutePx,
        )
        timetableHeight =
            maxOf(timetableHeight, timetableItemLayout.bottom)
        timetableWidth =
            maxOf(timetableWidth, timetableItemLayout.right)
        timetableItemLayout
    }

    fun visibleItemLayouts(
        screenWidth: Int,
        screenHeight: Int,
        scrollX: Int,
        scrollY: Int,
    ): List<IndexedValue<TimetableItemLayout>> {
        return timetableLayouts.withIndex().filter { (_, layout) ->
            layout.isVisible(screenWidth, screenHeight, scrollX, scrollY)
        }
    }
}

@Composable
fun rememberTimetableGridState(
    screenScrollState: ScreenScrollState = rememberScreenScrollState(),
    screenScaleState: ScreenScaleState = rememberScreenScaleState(),
    density: Density = LocalDensity.current,
): TimetableState = remember {
    TimetableState(
        screenScrollState,
        screenScaleState,
        density,
    )
}

data class TimetableState(
    val screenScrollState: ScreenScrollState,
    val screenScaleState: ScreenScaleState,
    val density: Density,
)

@Composable
fun rememberScreenScrollState(): ScreenScrollState = rememberSaveable(
    saver = ScreenScrollState.Saver,
) {
    ScreenScrollState()
}

@Stable
class ScreenScrollState(
    initialScrollX: Float = 0f,
    initialScrollY: Float = 0f,
) {
    private val velocityTracker = VelocityTracker()
    private val _scrollX = Animatable(initialScrollX)
    private val _scrollY = Animatable(initialScrollY)

    val scrollX: Float
        get() = _scrollX.value
    val scrollY: Float
        get() = _scrollY.value

    val maxX: Float
        get() = _scrollX.lowerBound ?: 0f
    val maxY: Float
        get() = _scrollY.lowerBound ?: 0f

    suspend fun scroll(
        scrollX: Float,
        scrollY: Float,
        timeMillis: Long,
        position: Offset,
    ) {
        if (scrollX.isNaN().not() && scrollY.isNaN().not()) {
            coroutineScope {
                velocityTracker.addPosition(timeMillis = timeMillis, position = position)
                launch {
                    _scrollX.snapTo(scrollX)
                }
                launch {
                    _scrollY.snapTo(scrollY)
                }
            }
        }
    }

    suspend fun flingIfPossible() = coroutineScope {
        val velocity = velocityTracker.calculateVelocity()
        launch {
            _scrollX.animateDecay(
                velocity.x / 2f,
                exponentialDecay(),
            )
        }
        launch {
            _scrollY.animateDecay(
                velocity.y / 2f,
                exponentialDecay(),
            )
        }
    }

    fun updateBounds(maxX: Float, maxY: Float) {
        _scrollX.updateBounds(maxX, 0f)
        _scrollY.updateBounds(maxY, 0f)
    }

    fun resetTracking() {
        velocityTracker.resetTracking()
    }

    companion object {
        val Saver: Saver<ScreenScrollState, *> = listSaver(
            save = {
                listOf(
                    it.scrollX,
                    it.scrollY,
                )
            },
            restore = {
                ScreenScrollState(
                    initialScrollX = it[0],
                    initialScrollY = it[1],
                )
            },
        )
    }
}

@Composable
fun rememberScreenScaleState(): ScreenScaleState = rememberSaveable(
    saver = ScreenScaleState.Saver,
) {
    ScreenScaleState()
}

@Composable
fun rememberTransformableStateForScreenScale(screenScaleState: ScreenScaleState) =
    rememberTransformableState { zoomChange, _, _ ->
        screenScaleState.updateVerticalScale(screenScaleState.verticalScale * zoomChange)
    }

@Stable
class ScreenScaleState(
    initialVerticalScale: Float = 1f,
    initialVerticalScaleLowerBound: Float = 1f,
) {
    private var verticalScaleLowerBound = initialVerticalScaleLowerBound
    private val verticalScaleUpperBound = 1f
    private val verticalScaleState = mutableStateOf(
        initialVerticalScale.coerceIn(verticalScaleLowerBound, verticalScaleUpperBound),
    )

    val verticalScale: Float
        get() = verticalScaleState.value

    fun updateVerticalScale(newScale: Float) {
        verticalScaleState.value =
            newScale.coerceIn(verticalScaleLowerBound, verticalScaleUpperBound)
    }

    fun updateVerticalScaleLowerBound(newLowerBound: Float) {
        if (newLowerBound < verticalScaleLowerBound) {
            verticalScaleLowerBound = newLowerBound
            updateVerticalScale(verticalScale)
        }
    }

    companion object {
        val Saver: Saver<ScreenScaleState, *> = listSaver(
            save = {
                listOf(
                    it.verticalScale,
                    it.verticalScaleLowerBound,
                )
            },
            restore = {
                ScreenScaleState(
                    initialVerticalScale = it[0],
                    initialVerticalScaleLowerBound = it[1],
                )
            },
        )
    }
}

private class TimetableScreen(
    val timetableLayout: TimetableLayout,
    val scrollState: ScreenScrollState,
    private val density: Density,
) {
    var width = 0
        private set
    var height = 0
        private set

    val visibleItemLayouts: State<List<IndexedValue<TimetableItemLayout>>> =
        derivedStateOf {
            timetableLayout.visibleItemLayouts(
                width,
                height,
                scrollState.scrollX.toInt(),
                scrollState.scrollY.toInt(),
            )
        }
    val topOffset = with(density) { 0.dp.roundToPx() }
    val timeHorizontalLines = derivedStateOf {
        (0..10).map {
            scrollState.scrollY + timetableLayout.minutePx * 60 * it + topOffset
        }
    }
    val roomVerticalLines = derivedStateOf {
        val width = with(density) { TimetableSizes.columnWidth.toPx() }
        val rooms = timetableLayout.rooms
        (0..rooms.lastIndex).map {
            scrollState.scrollX + width * it
        }
    }

    override fun toString(): String {
        return "Screen(" +
            "width=$width, " +
            "height=$height, " +
            "scroll=$scrollState, " +
            "visibleItemLayouts=$visibleItemLayouts" +
            ")"
    }

    suspend fun scroll(
        dragAmount: Offset,
        timeMillis: Long,
        position: Offset,
        nestedScrollDispatcher: NestedScrollDispatcher,
    ) {
        val parentConsumed = nestedScrollDispatcher.dispatchPreScroll(
            available = dragAmount,
            source = NestedScrollSource.Drag,
        )
        val nextPossibleX = calculatePossibleScrollX(dragAmount.x - parentConsumed.x)
        val nextPossibleY = calculatePossibleScrollY(dragAmount.y - parentConsumed.y)
        val weConsumed = Offset(
            nextPossibleX - scrollState.scrollX,
            nextPossibleY - scrollState.scrollY,
        )
        scrollState.scroll(
            scrollX = nextPossibleX,
            scrollY = nextPossibleY,
            timeMillis = timeMillis,
            position = position,
        )
        nestedScrollDispatcher.dispatchPostScroll(
            consumed = parentConsumed + weConsumed,
            available = dragAmount - weConsumed - parentConsumed,
            source = NestedScrollSource.Drag,
        )
    }

    fun enableHorizontalScroll(dragX: Float): Boolean {
        val nextPossibleX = calculatePossibleScrollX(dragX)
        return (scrollState.maxX < nextPossibleX && nextPossibleX < 0f)
    }

    fun enableVerticalScroll(dragY: Float): Boolean {
        val nextPossibleY = calculatePossibleScrollY(dragY)
        return (scrollState.maxY < nextPossibleY && nextPossibleY < 0f)
    }

    fun updateBounds(width: Int, height: Int, bottomPadding: Dp) {
        val bottomPaddingPx = with(density) {
            bottomPadding.toPx()
        }
        this.width = width
        this.height = height
        scrollState.updateBounds(
            maxX = if (width < timetableLayout.timetableWidth) {
                -(timetableLayout.timetableWidth - width).toFloat()
            } else {
                0f
            },
            maxY = if (height < timetableLayout.timetableHeight - bottomPaddingPx) {
                // Allow additional scrolling by bottomPadding (navigation bar height).
                -(timetableLayout.timetableHeight - height).toFloat() - bottomPaddingPx
            } else {
                0f
            },
        )
    }

    private fun calculatePossibleScrollX(scrollX: Float): Float {
        val currentValue = scrollState.scrollX
        val nextValue = currentValue + scrollX
        val maxScroll = scrollState.maxX
        return maxOf(minOf(nextValue, 0f), maxScroll)
    }

    private fun calculatePossibleScrollY(scrollY: Float): Float {
        val currentValue = scrollState.scrollY
        val nextValue = currentValue + scrollY
        val maxScroll = scrollState.maxY
        return maxOf(minOf(nextValue, 0f), maxScroll)
    }
}

/**
 * Workaround to prevent detectDragGestures from consuming events by default and disabling parent scrolling.
 *
 * ref: https://stackoverflow.com/a/72935823
 */
internal suspend fun PointerInputScope.detectDragGestures(
    onDragStart: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    awaitEachGesture {
        val down = awaitFirstDown(requireUnconsumed = false)
        var drag: PointerInputChange?
        val overSlop = Offset.Zero
        do {
            drag = awaitTouchSlopOrCancellation(down.id, onDrag)
            // ! EVERY Default movable GESTURE HAS THIS CHECK
        } while (drag != null && !drag.isConsumed)
        if (drag != null) {
            onDragStart.invoke(drag.position)
            onDrag(drag, overSlop)
            if (
                !drag(drag.id) {
                    onDrag(it, it.positionChange())
                    it.consume()
                }
            ) {
                onDragCancel()
            } else {
                onDragEnd()
            }
        }
    }
}

object TimetableSizes {
    val columnWidth = 192.dp
    val lineStrokeSize = 1.dp
    val currentTimeCircleRadius = 6.dp
    val minuteHeight = 4.dp
}
