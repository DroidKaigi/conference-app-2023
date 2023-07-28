package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.data.contributors.ContributorsRepository
import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.ui.KmpHiltViewModel
import io.github.droidkaigi.confsched2023.ui.KmpInject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@KmpHiltViewModel
class ContributorsViewModel @KmpInject constructor(
    contributorsRepository: ContributorsRepository
) : KmpViewModel() {
    private val contributors = contributorsRepository
        .contributors()
        .onStart {
            viewModelScope.launch {
                contributorsRepository.refresh()
            }
        }
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