package io.github.droidkaigi.confsched2023.about

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.AppVersion
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AboutScreenViewModel @Inject constructor(
    val appVersion: AppVersion,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    val uiState = MutableStateFlow(
        AboutScreenUiState(
            appVersion = appVersion,
        ),
    ).asStateFlow()
}
