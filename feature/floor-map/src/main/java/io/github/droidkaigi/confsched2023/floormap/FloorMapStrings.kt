package io.github.droidkaigi.confsched2023.floormap

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class FloorMapStrings : Strings<FloorMapStrings>(Bindings) {

    private object Bindings : StringsBindings<FloorMapStrings>(
        Lang.Japanese to { item, _ ->
            TODO()
        },
        Lang.English to { item, bindings ->
            TODO()
        },
        default = Lang.Japanese,
    )
}
