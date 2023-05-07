package io.github.droidkaigi.confsched2023.designsystem

enum class SessionsLocalizedStrings : LocalizedString {
    Sessions {

        override fun japanese(vararg args: String): String {
            return "セッション"
        }

        override fun english(vararg args: String): String {
            return "Sessions"
        }
    }
}