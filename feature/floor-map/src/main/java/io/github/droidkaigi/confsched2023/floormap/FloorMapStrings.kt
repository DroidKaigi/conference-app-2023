package io.github.droidkaigi.confsched2023.floormap

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class FloorMapStrings : Strings<FloorMapStrings>(Bindings) {
    data object Title : FloorMapStrings()
    data object FavoriteIcon : FloorMapStrings()
    data object EventDetail : FloorMapStrings()

    private object Bindings : StringsBindings<FloorMapStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Floor Map"
                FavoriteIcon -> "お気に入りアイコン"
                EventDetail -> "イベント詳細"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Title -> bindings.defaultBinding(item, bindings)
                FavoriteIcon -> "Favorite icon"
                EventDetail -> "Event details"
            }
        },
        default = Lang.Japanese,
    )
}
