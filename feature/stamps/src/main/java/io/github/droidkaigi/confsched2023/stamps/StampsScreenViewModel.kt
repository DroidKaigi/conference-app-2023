package io.github.droidkaigi.confsched2023.stamps

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.stamps.section.StampsSheetUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StampsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    val uiState = MutableStateFlow(
        StampsScreenUiState(
            StampsSheetUiState(
                stamps = persistentListOf(),
            )
        ),
    )
}
