package io.github.droidkaigi.confsched2023.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    achievementRepository: AchievementRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val isAchievementsEnabledStateFlow: StateFlow<Boolean> = achievementRepository.getAchievementEnabledStream()
        .handleErrorAndRetry(
            AppStrings.Retry,
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val uiState: StateFlow<MainScreenUiState> = buildUiState(
        isAchievementsEnabledStateFlow,
    ) { isAchievementsEnabled ->
        MainScreenUiState(
            isAchievementsEnabled = isAchievementsEnabled,
        )
    }
}
