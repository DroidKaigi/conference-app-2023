package io.github.droidkaigi.confsched2023.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual abstract class KmpViewModel
actual val KmpViewModel.viewModelScope: CoroutineScope
    // TODO: Cancel scope
    get() = MainScope()
