package io.github.droidkaigi.confsched2023.completeachievement

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.feature.completeachievement.R
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AnimationScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val animationLottieRawResStateFlow: MutableStateFlow<Int?> =
        MutableStateFlow(null)

    fun onReadDeeplinkHash(deepLink: String) {
        val achievementHash = lastSegmentOfUrl(deepLink)
        animationLottieRawResStateFlow.value = when (achievementHash) {
            idToSha256("Arctic Fox") -> R.raw.stamp_a_lottie
            idToSha256("Bumblebee") -> R.raw.stamp_b_lottie
            idToSha256("Chipmunk") -> R.raw.stamp_c_lottie
            idToSha256("Dolphin") -> R.raw.stamp_d_lottie
            idToSha256("Electric Eel") -> R.raw.stamp_e_lottie

            else -> null
        }
    }

    fun onReachAnimationEnd() {
        animationLottieRawResStateFlow.value = null
    }

    val uiState = buildUiState(
        animationLottieRawResStateFlow,
    ) { rawRes ->
        AnimationScreenUiState(
            rawId = rawRes
        )
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
