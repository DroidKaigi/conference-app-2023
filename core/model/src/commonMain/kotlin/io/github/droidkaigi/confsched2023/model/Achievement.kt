package io.github.droidkaigi.confsched2023.model

data class Achievement(
    val id: AchievementsItemId,
    val hasDrawableResId: Int,
    val notHasDrawableResId: Int,
    val hasAchievement: Boolean = false,
    val contentDescription: String,
) {
    fun getDrawableResId() = if (hasAchievement) hasDrawableResId else notHasDrawableResId
}
