package io.github.droidkaigi.confsched2023.model

<<<<<<< HEAD:core/model/src/commonMain/kotlin/io/github/droidkaigi/confsched2023/model/Stamp.kt
data class Stamp(
    val id: AchievementsItemId,
=======
data class Achievement(
>>>>>>> origin/main:core/model/src/commonMain/kotlin/io/github/droidkaigi/confsched2023/model/Achievement.kt
    val hasDrawableResId: Int,
    val notHasDrawableResId: Int,
    val hasAchievement: Boolean = false,
    val contentDescription: String,
) {
    fun getDrawableResId() = if (hasAchievement) hasDrawableResId else notHasDrawableResId
}
