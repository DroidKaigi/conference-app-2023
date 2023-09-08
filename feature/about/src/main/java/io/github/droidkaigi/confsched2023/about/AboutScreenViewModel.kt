package io.github.droidkaigi.confsched2023.about

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AboutScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    buildConfigProvider: BuildConfigProvider,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val versionNameStateFlow = MutableStateFlow(buildConfigProvider.versionName)

    val uiState: StateFlow<AboutScreenUiState> = buildUiState(versionNameStateFlow) {
        AboutScreenUiState(buildConfigProvider.versionName)
    }
}
