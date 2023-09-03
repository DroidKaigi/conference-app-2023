package io.github.droidkaigi.confsched2023.stamps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.feature.stamps.R
import io.github.droidkaigi.confsched2023.model.AchievementsItemId
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_A
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_B
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_C
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_D
import io.github.droidkaigi.confsched2023.model.AchievementsItemId.Companion.ACHIEVEMENT_E
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.section.StampListUiState
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
import javax.inject.Inject

@HiltViewModel
class StampsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    stampRepository: StampRepository,
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

    private val stampLottieRawResStateFlow: MutableStateFlow<Int?> =
        MutableStateFlow(null)

    private val stampListState = buildUiState(
        stampDetailDescriptionStateFlow,
        achievementsStateFlow,
    ) { detailDescription, achievements ->
        StampListUiState(
            stamps = persistentListOf(
                Stamp(
                    id = AchievementsItemId(ACHIEVEMENT_A),
                    hasDrawableResId = R.drawable.img_stamp_a_on,
                    notHasDrawableResId = R.drawable.img_stamp_a_off,
                    hasStamp = achievements.contains(AchievementsItemId(ACHIEVEMENT_A)),
                    contentDescription = "StampA image",
                ),
                Stamp(
                    id = AchievementsItemId(ACHIEVEMENT_B),
                    hasDrawableResId = R.drawable.img_stamp_b_on,
                    notHasDrawableResId = R.drawable.img_stamp_b_off,
                    hasStamp = achievements.contains(AchievementsItemId(ACHIEVEMENT_B)),
                    contentDescription = "StampB image",
                ),
                Stamp(
                    id = AchievementsItemId(ACHIEVEMENT_C),
                    hasDrawableResId = R.drawable.img_stamp_c_on,
                    notHasDrawableResId = R.drawable.img_stamp_c_off,
                    hasStamp = achievements.contains(AchievementsItemId(ACHIEVEMENT_C)),
                    contentDescription = "StampC image",
                ),
                Stamp(
                    id = AchievementsItemId(ACHIEVEMENT_D),
                    hasDrawableResId = R.drawable.img_stamp_d_on,
                    notHasDrawableResId = R.drawable.img_stamp_d_off,
                    hasStamp = achievements.contains(AchievementsItemId(ACHIEVEMENT_D)),
                    contentDescription = "StampD image",
                ),
                Stamp(
                    id = AchievementsItemId(ACHIEVEMENT_E),
                    hasDrawableResId = R.drawable.img_stamp_e_on,
                    notHasDrawableResId = R.drawable.img_stamp_e_off,
                    hasStamp = achievements.contains(AchievementsItemId(ACHIEVEMENT_E)),
                    contentDescription = "StampE image",
                ),
            ),
            detailDescription = detailDescription,
        )
    }

    val uiState = buildUiState(
        stampLottieRawResStateFlow,
        stampListState,
    ) { rawRes, stampListUiState ->
        StampsScreenUiState(
            lottieRawRes = rawRes,
            stampListUiState = stampListUiState,
        )
    }

    fun onReachAnimationEnd() {
        stampLottieRawResStateFlow.value = null
    }
}
