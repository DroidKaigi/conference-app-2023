package io.github.droidkaigi.confsched2023.designsystem

enum class SessionsLocalizedStrings : LocalizedString {
    Timetable {

        override fun japanese(vararg args: String): String {
            return "タイムテーブル"
        }

        override fun english(vararg args: String): String {
            return "Timetable"
        }
    }
}