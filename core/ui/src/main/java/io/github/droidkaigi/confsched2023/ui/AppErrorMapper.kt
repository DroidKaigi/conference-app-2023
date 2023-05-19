package io.github.droidkaigi.confsched2023.ui

fun Throwable.toApplicationErrorMessage(): String {
    return message ?: ""
}
