package io.github.droidkaigi.confsched2023.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun handleOnClickIfNotNavigating(owner: LifecycleOwner, onClick: () -> Unit) {
    // https://stackoverflow.com/a/76386604/4339442
    val currentState = owner.lifecycle.currentState
    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        onClick()
    }
}
