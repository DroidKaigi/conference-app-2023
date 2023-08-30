package io.github.droidkaigi.confsched2023.model

data class Achievement(
    val hasDrawableResId: Int,
    val lottieRawId: Int,
    val notHasDrawableResId: Int,
    val hasAchievement: Boolean = false,
    val contentDescription: String,
) {
    fun getDrawableResId() = if (hasAchievement) hasDrawableResId else notHasDrawableResId
}
