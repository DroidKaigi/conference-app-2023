package io.github.droidkaigi.confsched2023.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageATestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageBTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageCTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageDTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageETestTag
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.feature.achievements.R
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.model.AchievementAnimation
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val achievementRepository: AchievementRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val achievementDetailDescriptionStateFlow: StateFlow<String> =
        achievementRepository.getAchievementDetailDescriptionStream()
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    private val achievementsStateFlow: StateFlow<PersistentSet<Achievement>> =
        achievementRepository.getAchievementsStream()
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentSetOf(),
            )

    private val isInitialDialogDisplayFlow: StateFlow<Boolean?> =
        achievementRepository.getIsInitialDialogDisplayStateStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null,
            )

    private val resetAchievementsEnabledStateFlow: StateFlow<Boolean> =
        achievementRepository.getResetAchievementsEnabledStream()
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    private val achievementAnimationListState = buildUiState(
        achievementDetailDescriptionStateFlow,
        achievementsStateFlow,
        resetAchievementsEnabledStateFlow,
    ) { detailDescription, achievements, isResetAchievementsEnable ->
        AchievementListUiState(
            achievements = persistentListOf(
                AchievementAnimation(
                    achievement = Achievement.ArcticFox,
                    hasDrawableResId = R.drawable.img_achievement_a_on,
                    notHasDrawableResId = R.drawable.img_achievement_a_off,
                    hasAchievement = achievements.contains(Achievement.ArcticFox),
                    contentDescription = "AchievementA image",
                    testTag = AchievementImageATestTag,
                ),
                AchievementAnimation(
                    achievement = Achievement.Bumblebee,
                    hasDrawableResId = R.drawable.img_achievement_b_on,
                    notHasDrawableResId = R.drawable.img_achievement_b_off,
                    hasAchievement = achievements.contains(Achievement.Bumblebee),
                    contentDescription = "AchievementB image",
                    testTag = AchievementImageBTestTag,
                ),
                AchievementAnimation(
                    achievement = Achievement.Chipmunk,
                    hasDrawableResId = R.drawable.img_achievement_c_on,
                    notHasDrawableResId = R.drawable.img_achievement_c_off,
                    hasAchievement = achievements.contains(Achievement.Chipmunk),
                    contentDescription = "AchievementC image",
                    testTag = AchievementImageCTestTag,
                ),
                AchievementAnimation(
                    achievement = Achievement.Dolphin,
                    hasDrawableResId = R.drawable.img_achievement_d_on,
                    notHasDrawableResId = R.drawable.img_achievement_d_off,
                    hasAchievement = achievements.contains(Achievement.Dolphin),
                    contentDescription = "AchievementD image",
                    testTag = AchievementImageDTestTag,
                ),
                AchievementAnimation(
                    achievement = Achievement.ElectricEel,
                    hasDrawableResId = R.drawable.img_achievement_e_on,
                    notHasDrawableResId = R.drawable.img_achievement_e_off,
                    hasAchievement = achievements.contains(Achievement.ElectricEel),
                    contentDescription = "AchievementE image",
                    testTag = AchievementImageETestTag,
                ),
            ),
            detailDescription = detailDescription,
            isResetButtonEnabled = isResetAchievementsEnable,
        )
    }

    private val achievementAnimationState =
        MutableStateFlow<AchievementAnimationState>(AchievementAnimationState.NotAnimating)

    val uiState = buildUiState(
        achievementAnimationListState,
        isInitialDialogDisplayFlow,
        achievementAnimationState,
    ) { achievementListUiState, isDisplayedInitialDialog, achievementAnimationState ->
        AchievementsScreenUiState(
            achievementListUiState = achievementListUiState,
            isShowInitialDialog = isDisplayedInitialDialog?.not() ?: false,
            achievementAnimationState = achievementAnimationState,
        )
    }

    fun onReset() {
        viewModelScope.launch {
            achievementRepository.resetAchievements()
        }
    }

    fun onDisplayedInitialDialog() {
        viewModelScope.launch {
            achievementRepository.displayedInitialDialog()
        }
    }

    fun onAchievementClick(clickedAchievement: Achievement) {
        val animationRawId: Int = when (clickedAchievement) {
            Achievement.ArcticFox -> R.raw.achievement_a_lottie
            Achievement.Bumblebee -> R.raw.achievement_b_lottie
            Achievement.Chipmunk -> R.raw.achievement_c_lottie
            Achievement.Dolphin -> R.raw.achievement_d_lottie
            Achievement.ElectricEel -> R.raw.achievement_e_lottie
        }
        this.achievementAnimationState.value = AchievementAnimationState.Animating(
            achievement = clickedAchievement,
            animationRawId = animationRawId,
        )
    }

    fun onAnimationFinish() {
        this.achievementAnimationState.value = AchievementAnimationState.NotAnimating
    }
}
