package io.github.droidkaigi.confsched2023.completeachievement

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.completeachievement.R
import java.security.MessageDigest

data class AnimationScreenUiState(
    @androidx.annotation.RawRes
    val rawId: Int?,
)

@Composable
fun AnimationScreen(
    deepLink: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var uiState by remember {
        mutableStateOf(AnimationScreenUiState(null))
    }

    LaunchedEffect(Unit) {
        val achievementHash = lastSegmentOfUrl(deepLink)
        uiState = uiState.copy(
            rawId = when (achievementHash) {
                idToSha256("Arctic Fox") -> R.raw.stamp_a_lottie
                idToSha256("Bumblebee") -> R.raw.stamp_b_lottie
                idToSha256("Chipmunk") -> R.raw.stamp_c_lottie
                idToSha256("Dolphin") -> R.raw.stamp_d_lottie
                idToSha256("Electric Eel") -> R.raw.stamp_e_lottie

                else -> null
            }
        )
    }
    KaigiTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (uiState.rawId != null) {
                val lottieComposition by rememberLottieComposition(RawRes(uiState.rawId!!))
                val progress by animateLottieCompositionAsState(
                    composition = lottieComposition,
                    isPlaying = true,
                    restartOnPlay = true,
                )
                if (progress == 1f) {
                    uiState = uiState.copy(rawId = null)
                    onFinished()
                }
                LottieAnimation(
                    composition = lottieComposition,
                    progress = { progress },
                )
            }
        }
    }
}

fun lastSegmentOfUrl(url: String): String? {
    return url.trim().split("/").lastOrNull()?.takeIf { it.isNotEmpty() }
}

fun idToSha256(id: String?): String {
    if (id == null) return ""
    return MessageDigest.getInstance("SHA-256")
        .digest(id.toByteArray())
        .joinToString(separator = "") {
            "%02x".format(it)
        }
}
