package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.ui.HiltViewModel
import io.github.droidkaigi.confsched2023.ui.Inject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ContributorsViewModel @Inject constructor(
    contributorsRepository: ContributorsRepository
) : KmpViewModel() {
    private val contributors = contributorsRepository
        .contributors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = persistentListOf()
        )

    val uiState: StateFlow<ContributorsUiState> = buildUiState(contributors) {
        ContributorsUiState(
            contributors = it,
        )
    }
}