package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.ui.KmpHiltViewModel
import io.github.droidkaigi.confsched2023.ui.KmpInject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@KmpHiltViewModel
class ContributorsViewModel @KmpInject constructor(
) : KmpViewModel() {

    // TODO: Pass data from repository
    val uiState: StateFlow<ContributorsUiState> =
        MutableStateFlow(ContributorsUiState(Contributor.fakes())).asStateFlow()
}