package io.github.droidkaigi.confsched2023.model

data class AchievementAnimation(
    val achievement: Achievement,
    val hasDrawableResId: Int,
    val notHasDrawableResId: Int,
    val hasAchievement: Boolean = false,
    val contentDescription: String,
    val testTag: String,
) {
    fun getDrawableResId() = if (hasAchievement) hasDrawableResId else notHasDrawableResId
}
