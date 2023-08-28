package io.github.droidkaigi.confsched2023.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.feature.achievements.R
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AchievementsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    stampRepository: StampRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val achievementDetailDescriptionStateFlow: StateFlow<String> =
        stampRepository.getStampDetailDescriptionStream()
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    private val achievementLottieRawResStateFlow: MutableStateFlow<Int?> =
        MutableStateFlow(null)

    private val achievementListState = buildUiState(
        achievementDetailDescriptionStateFlow,
    ) { detailDescription ->
        AchievementListUiState(
            achievements = persistentListOf(
                Achievement(
                    hasDrawableResId = R.drawable.img_achievement_a_on,
                    lottieRawId = R.raw.achievement_a_lottie,
                    notHasDrawableResId = R.drawable.img_achievement_a_off,
                    contentDescription = "AchievementA image",
                ),
                Achievement(
                    hasDrawableResId = R.drawable.img_achievement_b_on,
                    lottieRawId = R.raw.achievement_b_lottie,
                    notHasDrawableResId = R.drawable.img_achievement_b_off,
                    contentDescription = "AchievementB image",
                ),
                Achievement(
                    hasDrawableResId = R.drawable.img_achievement_c_on,
                    lottieRawId = R.raw.achievement_c_lottie,
                    notHasDrawableResId = R.drawable.img_achievement_c_off,
                    contentDescription = "AchievementC image",
                ),
                Achievement(
                    hasDrawableResId = R.drawable.img_achievement_d_on,
                    lottieRawId = R.raw.achievement_d_lottie,
                    notHasDrawableResId = R.drawable.img_achievement_d_off,
                    contentDescription = "AchievementD image",
                ),
                Achievement(
                    hasDrawableResId = R.drawable.img_achievement_e_on,
                    lottieRawId = R.raw.achievement_e_lottie,
                    notHasDrawableResId = R.drawable.img_achievement_e_off,
                    contentDescription = "AchievementE image",
                ),
            ),
            detailDescription = detailDescription,
        )
    }

    val uiState = buildUiState(
        achievementLottieRawResStateFlow,
        achievementListState,
    ) { rawRes, achievementListUiState ->
        AchievementsScreenUiState(
            lottieRawRes = rawRes,
            achievementListUiState = achievementListUiState,
        )
    }

    fun onAchievementClick(
        achievement: Achievement,
    ) {
        achievementLottieRawResStateFlow.value = achievement.lottieRawId
    }

    fun onReachAnimationEnd() {
        achievementLottieRawResStateFlow.value = null
    }
}
