package io.github.droidkaigi.confsched2023.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    stampRepository: StampRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val isStampsEnabledStateFlow: StateFlow<Boolean> = stampRepository.getStampEnabledStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false,
    )

    val uiState: StateFlow<MainScreenUiState> = buildUiState(
        isStampsEnabledStateFlow,
    ) { isStampsEnabled ->
        MainScreenUiState(
            isStampsEnabled = isStampsEnabled,
        )
    }
}
