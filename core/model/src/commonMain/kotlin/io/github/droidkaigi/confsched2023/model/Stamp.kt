package io.github.droidkaigi.confsched2023.model

data class Stamp(
    val hasImageResId: Int,
    val notHasImageResId: Int,
    val hasStamp: Boolean = false,
    val contentDescription: String,
)
