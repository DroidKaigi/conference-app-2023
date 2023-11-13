package io.github.droidkaigi.confsched2023.model

public data class AchievementAnimation(
    val achievement: Achievement,
    val hasDrawableResId: Int,
    val notHasDrawableResId: Int,
    val hasAchievement: Boolean = false,
    val contentDescription: String,
) {
    public fun getDrawableResId(): Int = if (hasAchievement) hasDrawableResId else notHasDrawableResId
}
