package io.github.droidkaigi.confsched2023.achievements.animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

data class AnimationScreenUiState(
    @androidx.annotation.RawRes
    val rawId: Int?,
)

@Composable
fun AchievementAnimationScreen(
    deepLink: String,
    onFinished: () -> Unit,
    viewModel: AchievementAnimationScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    LaunchedEffect(Unit) {
        viewModel.onReadDeeplinkHash(
            deepLink = deepLink,
            onReadFail = onFinished,
        )
    }
    AchievementAnimationScreen(
        uiState = uiState,
        onFinished = onFinished,
        onReachAnimationEnd = viewModel::onReachAnimationEnd,
    )
}

@Composable
fun AchievementAnimationScreen(
    uiState: AnimationScreenUiState,
    onFinished: () -> Unit,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.rawId != null) {
            val lottieComposition by rememberLottieComposition(RawRes(uiState.rawId))
            val progress by animateLottieCompositionAsState(
                composition = lottieComposition,
                isPlaying = true,
                restartOnPlay = true,
            )
            if (progress == 1f) {
                onReachAnimationEnd()
                onFinished()
            }
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
            )
        }
    }
}
