package io.github.droidkaigi.confsched2023.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual abstract class KmpViewModel {
    // TODO: Cancel scope
    internal val internalViewModelScope: CoroutineScope = MainScope()
}

actual val KmpViewModel.viewModelScope: CoroutineScope
    get() = internalViewModelScope
