package io.github.droidkaigi.confsched2023.sponsors

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SponsorsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    sponsorsRepository: SponsorsRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val sponsorsStateFlow = sponsorsRepository.sponsors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf(),
        )

    val uiState: StateFlow<SponsorsScreenUiState> = buildUiState(sponsorsStateFlow) { sponsors ->
        SponsorsScreenUiState(
            sponsors = sponsors,
        )
    }
}
