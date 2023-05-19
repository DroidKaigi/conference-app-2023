package io.github.droidkaigi.confsched2023.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T1, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    transform: (T1) -> R
): StateFlow<R> = flow.map(transform = transform)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = transform(
            flow.value
        )
    )

fun <T1, T2, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    flow2: StateFlow<T2>,
    transform: (T1, T2) -> R,
): StateFlow<R> = combine(
    flow = flow,
    flow2 = flow2,
    transform = transform
).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = transform(
        flow.value, flow2.value
    )
)

fun <T1, T2, T3, T4, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> = combine(
    flow = flow,
    flow2 = flow2,
    flow3 = flow3,
    flow4 = flow4,
    transform = transform
).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = transform(
        flow.value, flow2.value, flow3.value, flow4.value
    )
)
