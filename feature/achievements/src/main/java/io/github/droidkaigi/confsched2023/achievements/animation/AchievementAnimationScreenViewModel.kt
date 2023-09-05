package io.github.droidkaigi.confsched2023.achievements.animation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.feature.achievements.R
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AchievementAnimationScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val achievementRepository: AchievementRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val animationLottieRawResStateFlow: MutableStateFlow<Int?> =
        MutableStateFlow(null)

    val uiState = buildUiState(
        animationLottieRawResStateFlow,
    ) { rawRes ->
        AnimationScreenUiState(
            rawId = rawRes,
        )
    }

    fun onReadDeeplinkHash(deepLink: String, onReadFail: () -> Unit) {
        val achievementId = lastSegmentOfUrl(deepLink)
        val achievementHash = idToSha256(achievementId)
        val (achievement, animation) = when (achievementHash) {
            Achievement.ArcticFox.sha256 ->
                Achievement.ArcticFox to R.raw.achievement_a_lottie

            Achievement.Bumblebee.sha256 ->
                Achievement.Bumblebee to R.raw.achievement_b_lottie

            Achievement.Chipmunk.sha256 ->
                Achievement.Chipmunk to R.raw.achievement_c_lottie

            Achievement.Dolphin.sha256 ->
                Achievement.Dolphin to R.raw.achievement_d_lottie

            Achievement.ElectricEel.sha256 ->
                Achievement.ElectricEel to R.raw.achievement_e_lottie

            else -> {
                onReadFail()
                null to null
            }
        }
        animationLottieRawResStateFlow.value = animation

        viewModelScope.launch {
            achievement?.let {
                achievementRepository.saveAchievements(achievement)
            }
        }
    }

    fun onReachAnimationEnd() {
        animationLottieRawResStateFlow.value = null
    }
}

fun lastSegmentOfUrl(url: String): String? {
    return Uri.parse(url).path?.split("/")?.lastOrNull()?.takeIf { it.isNotEmpty() }
}

fun idToSha256(id: String?): String {
    if (id == null) return ""
    return MessageDigest.getInstance("SHA-256")
        .digest(id.toByteArray())
        .joinToString(separator = "") {
            "%02x".format(it)
        }
}
