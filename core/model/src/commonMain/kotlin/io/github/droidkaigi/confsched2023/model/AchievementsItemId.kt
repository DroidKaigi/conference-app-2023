package io.github.droidkaigi.confsched2023.model

class AchievementsItemId(val value: String?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AchievementsItemId) return false
        if (value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        const val ACHIEVEMENT_A = "Arctic Fox"
        const val ACHIEVEMENT_B = "Bumblebee"
        const val ACHIEVEMENT_C = "Chipmunk"
        const val ACHIEVEMENT_D = "Dolphin"
        const val ACHIEVEMENT_E = "Electric Eel"
    }
}
