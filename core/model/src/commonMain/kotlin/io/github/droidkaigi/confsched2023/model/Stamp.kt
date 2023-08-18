package io.github.droidkaigi.confsched2023.model

data class Stamp(
    val hasDrawableResId: Int,
    val notHasDrawableResId: Int,
    val hasStamp: Boolean = false,
    val contentDescription: String,
) {
    fun getDrawableResId() = if (hasStamp) hasDrawableResId else notHasDrawableResId
}
