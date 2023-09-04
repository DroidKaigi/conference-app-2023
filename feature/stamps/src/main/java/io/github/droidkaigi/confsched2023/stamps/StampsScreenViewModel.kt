package io.github.droidkaigi.confsched2023.stamps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.feature.stamps.R
import io.github.droidkaigi.confsched2023.model.Achievements
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.section.StampListUiState
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
class StampsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val stampRepository: StampRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val stampDetailDescriptionStateFlow: StateFlow<String> =
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

    private val achievementsStateFlow: StateFlow<PersistentSet<AchievementsItemId>> =
        stampRepository.getAchievementsStream()
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
        stampRepository.getResetAchievementsEnabledStream()
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    private val stampListState = buildUiState(
        stampDetailDescriptionStateFlow,
        achievementsStateFlow,
        resetAchievementsEnabledStateFlow,
    ) { detailDescription, achievements, isResetAchievementsEnable ->
        StampListUiState(
            stamps = persistentListOf(
                Stamp(
                    id = AchievementsItemId(Achievements.ArcticFox.id),
                    hasDrawableResId = R.drawable.img_stamp_a_on,
                    notHasDrawableResId = R.drawable.img_stamp_a_off,
                    hasStamp = achievements.contains(AchievementsItemId(Achievements.ArcticFox.id)),
                    contentDescription = "StampA image",
                ),
                Stamp(
                    id = AchievementsItemId(Achievements.Bumblebee.id),
                    hasDrawableResId = R.drawable.img_stamp_b_on,
                    notHasDrawableResId = R.drawable.img_stamp_b_off,
                    hasStamp = achievements.contains(AchievementsItemId(Achievements.Bumblebee.id)),
                    contentDescription = "StampB image",
                ),
                Stamp(
                    id = AchievementsItemId(Achievements.Chipmunk.id),
                    hasDrawableResId = R.drawable.img_stamp_c_on,
                    notHasDrawableResId = R.drawable.img_stamp_c_off,
                    hasStamp = achievements.contains(AchievementsItemId(Achievements.Chipmunk.id)),
                    contentDescription = "StampC image",
                ),
                Stamp(
                    id = AchievementsItemId(Achievements.Dolphin.id),
                    hasDrawableResId = R.drawable.img_stamp_d_on,
                    notHasDrawableResId = R.drawable.img_stamp_d_off,
                    hasStamp = achievements.contains(AchievementsItemId(Achievements.Dolphin.id)),
                    contentDescription = "StampD image",
                ),
                Stamp(
                    id = AchievementsItemId(Achievements.ElectricEel.id),
                    hasDrawableResId = R.drawable.img_stamp_e_on,
                    notHasDrawableResId = R.drawable.img_stamp_e_off,
                    hasStamp = achievements.contains(AchievementsItemId(Achievements.ElectricEel.id)),
                    contentDescription = "StampE image",
                ),
            ),
            detailDescription = detailDescription,
            isResetButtonEnabled = isResetAchievementsEnable,
        )
    }

    val uiState = buildUiState(
        stampListState,
    ) { stampListUiState ->
        StampsScreenUiState(
            stampListUiState = stampListUiState,
        )
    }

    fun onReset() {
        viewModelScope.launch {
            stampRepository.resetAchievements()
        }
    }
}
