package io.github.droidkaigi.confsched2023.sessions.strings

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SessionsStrings : Strings<SessionsStrings>(Bindings) {
    object Timetable : SessionsStrings()
    object Hoge : SessionsStrings()
    class Time(val hours: Int, val minutes: Int) : SessionsStrings()
    object ScheduleIcon : SessionsStrings()
    object UserIcon : SessionsStrings()

    private object Bindings : StringsBindings<SessionsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Timetable -> "タイムテーブル"
                Hoge -> "ホゲ"
                is Time -> "${item.hours}時${item.minutes}分"
                ScheduleIcon -> "スケジュールアイコン"
                UserIcon -> "ユーザーアイコン"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Timetable -> "Timetable"
                Hoge -> bindings.defaultBinding(item, bindings)
                is Time -> "${item.hours}:${item.minutes}"
                ScheduleIcon -> "Schedule icon"
                UserIcon -> "User icon"
            }
        },
        default = Lang.Japanese,
    )
}
