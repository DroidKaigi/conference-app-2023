package io.github.droidkaigi.confsched2023.sessions.strings

import io.github.droidkaigi.confsched2023.designsystem.string.Lang
import io.github.droidkaigi.confsched2023.designsystem.string.Strings
import io.github.droidkaigi.confsched2023.designsystem.string.StringsBindings

sealed class SessionsStrings : Strings<SessionsStrings>(Binding) {
    object Timetable : SessionsStrings()
    object Hoge : SessionsStrings()
    class Time(val hour: Int, val minutes: Int) : SessionsStrings()
    object Binding : StringsBindings<SessionsStrings>(
        mapOf(
            Lang.Japanese to { item, _ ->
                when (item) {
                    Timetable -> "タイムテーブル"
                    Hoge -> "ホゲ"
                    is Time -> "${item.hour}時${item.minutes}分"
                }
            },
            Lang.English to ({ item, bindings ->
                when (item) {
                    Timetable -> "Timetable"
                    Hoge -> bindings.defaultBinding(item, bindings)
                    is Time -> "${item.hour}:${item.minutes}"
                }
            })
        ),
        default = Lang.Japanese
    )
}