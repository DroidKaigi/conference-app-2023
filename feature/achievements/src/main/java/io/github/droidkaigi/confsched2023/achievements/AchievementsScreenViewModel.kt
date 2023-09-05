package io.github.droidkaigi.confsched2023.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.feature.achievements.R
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.model.Achievements.ArcticFox
import io.github.droidkaigi.confsched2023.model.Achievements.Bumblebee
import io.github.droidkaigi.confsched2023.model.Achievements.Chipmunk
import io.github.droidkaigi.confsched2023.model.Achievements.Dolphin
import io.github.droidkaigi.confsched2023.model.Achievements.ElectricEel
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
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

    private val achievementsStateFlow: StateFlow<PersistentSet<AchievementsItemId>> =
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

    private val achievementListState = buildUiState(
        achievementDetailDescriptionStateFlow,
        achievementsStateFlow,
        resetAchievementsEnabledStateFlow,
    ) { detailDescription, achievements, isResetAchievementsEnable ->
        AchievementListUiState(
            achievements = persistentListOf(
                Achievement(
                    id = AchievementsItemId(ArcticFox.id),
                    hasDrawableResId = R.drawable.img_achievement_a_on,
                    notHasDrawableResId = R.drawable.img_achievement_a_off,
                    hasAchievement = achievements.contains(AchievementsItemId(ArcticFox.id)),
                    contentDescription = "AchievementA image",
                ),
                Achievement(
                    id = AchievementsItemId(Bumblebee.id),
                    hasDrawableResId = R.drawable.img_achievement_b_on,
                    notHasDrawableResId = R.drawable.img_achievement_b_off,
                    hasAchievement = achievements.contains(AchievementsItemId(Bumblebee.id)),
                    contentDescription = "AchievementB image",
                ),
                Achievement(
                    id = AchievementsItemId(Chipmunk.id),
                    hasDrawableResId = R.drawable.img_achievement_c_on,
                    notHasDrawableResId = R.drawable.img_achievement_c_off,
                    hasAchievement = achievements.contains(AchievementsItemId(Chipmunk.id)),
                    contentDescription = "AchievementC image",
                ),
                Achievement(
                    id = AchievementsItemId(Dolphin.id),
                    hasDrawableResId = R.drawable.img_achievement_d_on,
                    notHasDrawableResId = R.drawable.img_achievement_d_off,
                    hasAchievement = achievements.contains(AchievementsItemId(Dolphin.id)),
                    contentDescription = "AchievementD image",
                ),
                Achievement(
                    id = AchievementsItemId(ElectricEel.id),
                    hasDrawableResId = R.drawable.img_achievement_e_on,
                    notHasDrawableResId = R.drawable.img_achievement_e_off,
                    hasAchievement = achievements.contains(AchievementsItemId(ElectricEel.id)),
                    contentDescription = "AchievementE image",
                ),
            ),
            detailDescription = detailDescription,
            isResetButtonEnabled = isResetAchievementsEnable,
        )
    }

    val uiState = buildUiState(
        achievementListState,
    ) { achievementListUiState ->
        AchievementsScreenUiState(
            achievementListUiState = achievementListUiState,
        )
    }

    fun onReset() {
        viewModelScope.launch {
            achievementRepository.resetAchievements()
        }
    }
}
