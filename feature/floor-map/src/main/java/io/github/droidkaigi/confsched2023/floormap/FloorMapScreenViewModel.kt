package io.github.droidkaigi.confsched2023.floormap

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FloorMapScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    val uiState = MutableStateFlow(
        FloorMapScreenUiState(
            sideEvents = emptyList(),
        ),
    ).asStateFlow()
}
