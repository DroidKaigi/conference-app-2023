package io.github.droidkaigi.confsched2023.sessions.strings

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SessionsStrings : Strings<SessionsStrings>(Bindings) {
    object Search : SessionsStrings()
    object Timetable : SessionsStrings()
    object Hoge : SessionsStrings()
    class Time(val hours: Int, val minutes: Int) : SessionsStrings()
    object ScheduleIcon : SessionsStrings()
    object UserIcon : SessionsStrings()
    object EventDay : SessionsStrings()
    object Category : SessionsStrings()

    private object Bindings : StringsBindings<SessionsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Search -> "検索"
                Timetable -> "タイムテーブル"
                Hoge -> "ホゲ"
                is Time -> "${item.hours}時${item.minutes}分"
                ScheduleIcon -> "スケジュールアイコン"
                UserIcon -> "ユーザーアイコン"
                EventDay -> "開催日"
                Category -> "カテゴリー"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Search -> "Search"
                Timetable -> "Timetable"
                Hoge -> bindings.defaultBinding(item, bindings)
                is Time -> "${item.hours}:${item.minutes}"
                ScheduleIcon -> "Schedule icon"
                UserIcon -> "User icon"
                EventDay -> "Day"
                Category -> "Category"
            }
        },
        default = Lang.Japanese,
    )
}
