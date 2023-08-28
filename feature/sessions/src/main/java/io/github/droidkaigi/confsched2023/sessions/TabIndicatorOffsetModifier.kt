package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrain
import kotlinx.coroutines.launch

fun Modifier.tabIndicatorOffsetModifierNode(currentTabPosition: TabPosition)
    = this
        .fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .then(TabIndicatorOffsetElement(currentTabPosition = currentTabPosition))

private class TabIndicatorOffsetElement(private val currentTabPosition: TabPosition)
    : ModifierNodeElement<TabIndicatorOffsetNode>() {
    override fun create(): TabIndicatorOffsetNode = TabIndicatorOffsetNode(currentTabPosition)

    override fun update(node: TabIndicatorOffsetNode) {
        node.update(currentTabPosition = currentTabPosition)
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? TabIndicatorOffsetElement ?: return false
        return otherModifier.currentTabPosition == currentTabPosition
    }

    override fun hashCode(): Int {
        var result = currentTabPosition.hashCode()
        return result
    }

    override fun InspectorInfo.inspectableProperties() {
        debugInspectorInfo {
            name = "tabIndicatorOffsetModifierNode"
            value = currentTabPosition
        }
    }
}

private class TabIndicatorOffsetNode(
    currentTabPosition: TabPosition,
): LayoutModifierNode, Modifier.Node() {
    //
    private var targetTabWidth by mutableStateOf(currentTabPosition.width)
    private var targetIndicatorOffset by mutableStateOf(currentTabPosition.left)

    private var tabWidthAnimatable: Animatable<Dp, AnimationVector1D>? = null
    private var indicatorOffsetAnimatable: Animatable<Dp, AnimationVector1D>? = null

    fun update(currentTabPosition: TabPosition) {
        this.targetTabWidth = currentTabPosition.width
        this.targetIndicatorOffset = currentTabPosition.left
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val tabWidthAnim = tabWidthAnimatable?.also {
            if (targetTabWidth != it.targetValue) {
                coroutineScope.launch {
                    // animation spec defined in Modifier.tabIndicatorOffset() for currentTabWidth
                    // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/src/commonMain/kotlin/androidx/compose/material/TabRow.kt%3Bl=401?q=tabIndicatorOffset&sq=
                    it.animateTo(
                        targetTabWidth,
                        tween(durationMillis = 250, easing = FastOutSlowInEasing)
                    )
                }
            }
        } ?: Animatable(targetTabWidth, Dp.VectorConverter).also {
            tabWidthAnimatable = it
        }

        // Since, Modifier.tabIndicatorOffset() has Modifier.width(currentTabWidth) in chain,
        // calculation of placeable implementation copied from SizeNode.
        // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation-layout/src/commonMain/kotlin/androidx/compose/foundation/layout/Size.kt%3Bl=761?q=SizeNode
        val maxWidth = tabWidthAnim.value.roundToPx().coerceAtLeast(0)
        val minWidth = tabWidthAnim.value.roundToPx().coerceAtMost(maxWidth).coerceAtLeast(0).let {
            if (it != Constraints.Infinity) it else 0
        }
        val wrappedConstraints = constraints.constrain(
            Constraints(
                minWidth = minWidth,
                maxWidth = maxWidth
            )
        )
        val placeable = measurable.measure(wrappedConstraints)

        return layout(placeable.width, placeable.height) {
            val offsetWidthAnim = indicatorOffsetAnimatable?.also {
                if (targetIndicatorOffset != it.targetValue) {
                    coroutineScope.launch {
                        // animation spec defined in Modifier.tabIndicatorOffset() for indicatorOffset
                        // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/src/commonMain/kotlin/androidx/compose/material/TabRow.kt%3Bl=401?q=tabIndicatorOffset&sq=
                        it.animateTo(
                            targetValue = targetIndicatorOffset,
                            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                        )
                    }
                }
            } ?: Animatable(targetIndicatorOffset, Dp.VectorConverter).also {
                indicatorOffsetAnimatable = it
            }

            // Since, Modifier.tabIndicatorOffset() has Modifier.offset(x = indicatorOffset) in chain,
            // calculation of placeable implementation copied from OffsetNode.
            // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation-layout/src/commonMain/kotlin/androidx/compose/foundation/layout/Offset.kt%3Bl=181?q=OffsetNode
            placeable.place(offsetWidthAnim.value.roundToPx(), 0)
        }
    }
}
