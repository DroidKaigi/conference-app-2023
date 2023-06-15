package io.github.droidkaigi.confsched2023.ui

import kotlinx.coroutines.CoroutineScope

expect abstract class KmpViewModel()

expect val KmpViewModel.viewModelScope: CoroutineScope
