package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.ui.HiltViewModel
import io.github.droidkaigi.confsched2023.ui.Inject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ContributorsViewModel @Inject constructor(
    contributorsRepository: ContributorsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : KmpViewModel(), UserMessageStateHolder by userMessageStateHolder {
    private val contributors = contributorsRepository
        .contributors()
        .handleErrorAndRetry(
            AppStrings.Retry,
            userMessageStateHolder,
        )
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
