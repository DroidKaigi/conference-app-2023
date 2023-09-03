package io.github.droidkaigi.confsched2023.completeachievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.feature.completeachievement.R
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_A
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_B
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_C
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_D
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_E
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AnimationScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val stampRepository: StampRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val animationLottieRawResStateFlow: MutableStateFlow<Pair<AchievementsItemId, Int?>> =
        MutableStateFlow(AchievementsItemId(null) to null)

    fun onReadDeeplinkHash(deepLink: String) {
        val achievementHash = lastSegmentOfUrl(deepLink)
        animationLottieRawResStateFlow.value = when (achievementHash) {
            idToSha256(ACHIEVEMENT_A) ->
                AchievementsItemId(ACHIEVEMENT_A) to R.raw.stamp_a_lottie

            idToSha256(ACHIEVEMENT_B) ->
                AchievementsItemId(ACHIEVEMENT_B) to R.raw.stamp_b_lottie

            idToSha256(ACHIEVEMENT_C) ->
                AchievementsItemId(ACHIEVEMENT_C) to R.raw.stamp_c_lottie

            idToSha256(ACHIEVEMENT_D) ->
                AchievementsItemId(ACHIEVEMENT_D) to R.raw.stamp_d_lottie

            idToSha256(ACHIEVEMENT_E) ->
                AchievementsItemId(ACHIEVEMENT_E) to R.raw.stamp_e_lottie

            else -> AchievementsItemId(null) to null
        }
    }

    fun onReachAnimationEnd() {
        viewModelScope.launch {
            stampRepository.saveAchievements(animationLottieRawResStateFlow.value.first)
            animationLottieRawResStateFlow.value = AchievementsItemId(null) to null
        }
    }

    val uiState = buildUiState(
        animationLottieRawResStateFlow,
    ) { rawRes ->
        AnimationScreenUiState(
            rawId = rawRes.second
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
