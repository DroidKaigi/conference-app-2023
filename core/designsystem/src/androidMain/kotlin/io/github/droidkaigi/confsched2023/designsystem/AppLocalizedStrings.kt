package io.github.droidkaigi.confsched2023.designsystem

enum class AppLocalizedStrings : LocalizedString {
    Retry {
        override fun japanese(vararg args: String): String {
            return "リトライ"
        }

        override fun english(vararg args: String): String {
            return "Retry"
        }
    },
}
