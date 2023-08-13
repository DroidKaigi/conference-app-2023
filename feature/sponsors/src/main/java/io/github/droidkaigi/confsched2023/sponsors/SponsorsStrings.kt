package io.github.droidkaigi.confsched2023.sponsors

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SponsorsStrings : Strings<SponsorsStrings>(Bindings) {

    private object Bindings : StringsBindings<SponsorsStrings>(
        Lang.Japanese to { item, _ ->
            TODO()
        },
        Lang.English to { item, bindings ->
            TODO()
        },
        default = Lang.Japanese,
    )
}
