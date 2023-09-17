package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AchievementHighlightAnimation(
    animationRawId: Int,
    onAnimationFinish: () -> Unit,
) {
    val lottieComposition by rememberLottieComposition(RawRes(animationRawId))
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = true,
        restartOnPlay = true,
    )
    if (progress == 1f) {
        onAnimationFinish()
    }
    LottieAnimation(
        composition = lottieComposition,
        progress = { progress },
    )
}
