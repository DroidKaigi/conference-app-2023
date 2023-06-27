package io.github.droidkaigi.confsched2023.main.strings

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class MainStrings : Strings<MainStrings>(Bindings) {
    object Main : MainStrings()
    object Hoge : MainStrings()
    class Time(val hours: Int, val minutes: Int) : MainStrings()

    private object Bindings : StringsBindings<MainStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Main -> "タイムテーブル"
                Hoge -> "ホゲ"
                is Time -> "${item.hours}時${item.minutes}分"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Main -> "Main"
                Hoge -> bindings.defaultBinding(item, bindings)
                is Time -> "${item.hours}:${item.minutes}"
            }
        },
        default = Lang.Japanese,
    )
}
