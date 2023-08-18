package io.github.droidkaigi.confsched2023.main.strings

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class MainStrings : Strings<MainStrings>(Bindings) {
    data object Timetable : MainStrings()
    data object FloorMap : MainStrings()
    data object Stamps : MainStrings()
    data object About : MainStrings()
    data object Contributors : MainStrings()
    class Time(val hours: Int, val minutes: Int) : MainStrings()

    private object Bindings : StringsBindings<MainStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Timetable -> "Timetable"
                FloorMap -> "Floor Map"
                Stamps -> "Stamps"
                About -> "About"
                Contributors -> "Contributors"
                is Time -> "${item.hours}時${item.minutes}分"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Timetable -> "Timetable"
                FloorMap -> "Floor Map"
                Stamps -> "Stamps"
                About -> "About"
                Contributors -> "Contributors"
                is Time -> "${item.hours}:${item.minutes}"
            }
        },
        default = Lang.English,
    )
}
