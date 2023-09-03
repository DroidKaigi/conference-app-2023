package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.component.StampImage
import io.github.droidkaigi.confsched2023.stamps.component.StampsDetail
import kotlinx.collections.immutable.ImmutableList

private const val StampListColumns = 2
private const val SingleItemSpanCount = 2
private const val DoubleItemSpanCount = 2 / 2

data class StampListUiState(
    val stamps: ImmutableList<Stamp>,
    val detailDescription: String,
)

@Composable
fun StampList(
    uiState: StampListUiState,
    @androidx.annotation.RawRes
    stampLottieRawId: Int?,
    onStampsClick: (Stamp) -> Unit,
    contentPadding: PaddingValues,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    val bgColor: Color by animateColorAsState(
        if (stampLottieRawId != null) {
            Color(0xFF37383D)
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            delayMillis = 2,
            durationMillis = 400,
            easing = EaseInOut,
        ),
        label = "",
    )
    Box(
        modifier = Modifier
            .background(bgColor)
            .fillMaxSize(),
    ) {
        if (stampLottieRawId != null) {
            val lottieComposition by rememberLottieComposition(RawRes(stampLottieRawId))
            val progress by animateLottieCompositionAsState(
                composition = lottieComposition,
                isPlaying = true,
                restartOnPlay = true,
            )
            if (progress == 1f) {
                onReachAnimationEnd()
            }
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
            )
        }
        LazyVerticalGrid(
            columns = Fixed(StampListColumns),
            modifier = modifier,
            contentPadding = PaddingValues(
                start = 16.dp + contentPadding.calculateStartPadding(layoutDirection),
                end = 16.dp + contentPadding.calculateEndPadding(layoutDirection),
                top = 20.dp + contentPadding.calculateTopPadding(),
                bottom = 20.dp + contentPadding.calculateBottomPadding(),
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                key = "stamps_header",
                span = { GridItemSpan(SingleItemSpanCount) },
            ) {
                StampsDetail(uiState.detailDescription)
            }
            items(
                items = uiState.stamps,
                key = { stamp -> stamp.hasDrawableResId },
                span = { stamp ->
                    GridItemSpan(
                        if (stamp == uiState.stamps.last() && uiState.stamps.size % StampListColumns != 0) {
                            SingleItemSpanCount
                        } else {
                            DoubleItemSpanCount
                        },
                    )
                },
            ) { stamp ->
                val onStampClick = if (stampLottieRawId != null) {
                    {} // Prevents clicks during animation playback.
                } else {
                    onStampsClick
                }
                StampImage(
                    stamp = stamp,
                    onStampClick = onStampClick,
                )
            }
        }
    }
}
