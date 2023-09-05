package io.github.droidkaigi.confsched2023.achievements.animation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.feature.achievements.R
import io.github.droidkaigi.confsched2023.model.Achievements
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AnimationScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val achievementRepository: AchievementRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val animationLottieRawResStateFlow: MutableStateFlow<Pair<AchievementsItemId, Int?>> =
        MutableStateFlow(AchievementsItemId(null) to null)

    val uiState = buildUiState(
        animationLottieRawResStateFlow,
    ) { rawRes ->
        AnimationScreenUiState(
            rawId = rawRes.second,
        )
    }

    fun onReadDeeplinkHash(deepLink: String) {
        val achievementId = lastSegmentOfUrl(deepLink)
        val achievementHash = idToSha256(achievementId)
        animationLottieRawResStateFlow.value = when (achievementHash) {
            Achievements.ArcticFox.sha256 ->
                AchievementsItemId(Achievements.ArcticFox.id) to R.raw.achievement_a_lottie

            Achievements.Bumblebee.sha256 ->
                AchievementsItemId(Achievements.Bumblebee.id) to R.raw.achievement_b_lottie

            Achievements.Chipmunk.sha256 ->
                AchievementsItemId(Achievements.Chipmunk.id) to R.raw.achievement_c_lottie

            Achievements.Dolphin.sha256 ->
                AchievementsItemId(Achievements.Dolphin.id) to R.raw.achievement_d_lottie

            Achievements.ElectricEel.sha256 ->
                AchievementsItemId(Achievements.ElectricEel.id) to R.raw.achievement_e_lottie

            else -> AchievementsItemId(null) to null
        }
    }

    fun onReachAnimationEnd() {
        viewModelScope.launch {
            achievementRepository.saveAchievements(animationLottieRawResStateFlow.value.first)
            animationLottieRawResStateFlow.value = AchievementsItemId(null) to null
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
