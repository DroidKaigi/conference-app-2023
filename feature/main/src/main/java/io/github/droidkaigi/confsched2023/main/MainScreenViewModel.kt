package io.github.droidkaigi.confsched2023.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.NavigationRequester
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
    private val navigationRequester: NavigationRequester,
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

    private val navigationRouteStateFlow: StateFlow<String> = navigationRequester.getNavigationRouteFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "",
        )

    val uiState: StateFlow<MainScreenUiState> = buildUiState(
        isAchievementsEnabledStateFlow,
        navigationRouteStateFlow,
    ) { isAchievementsEnabled, navigationRoute ->
        MainScreenUiState(
            isAchievementsEnabled = isAchievementsEnabled,
            navigationRoute = navigationRoute,
        )
    }

    fun onNavigated() {
        navigationRequester.navigated()
    }
}
