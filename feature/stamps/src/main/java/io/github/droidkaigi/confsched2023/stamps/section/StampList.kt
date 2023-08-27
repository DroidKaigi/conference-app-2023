package io.github.droidkaigi.confsched2023.stamps.section

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun StampList(
    stamps: ImmutableList<Stamp>,
    @androidx.annotation.RawRes
    stampLottieRawId: Int?,
    onStampsClick: (Stamp) -> Unit,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor: Color by animateColorAsState(
        if (stampLottieRawId != null) {
            Color(0xFF37383D)
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            delayMillis = 2,
            durationMillis = 400, easing = EaseInOut
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
                horizontal = 16.dp,
                vertical = 20.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                key = "stamps_header",
                span = { GridItemSpan(SingleItemSpanCount) },
            ) {
                StampsDetail()
            }
            items(
                items = stamps,
                key = { stamp -> stamp.hasDrawableResId },
                span = { stamp ->
                    GridItemSpan(
                        if (stamp == stamps.last() && stamps.size % StampListColumns != 0) {
                            SingleItemSpanCount
                        } else {
                            DoubleItemSpanCount
                        },
                    )
                },
            ) { stamp ->
                StampImage(stamp = stamp, onStampClick = onStampsClick)
            }
        }
    }
}
