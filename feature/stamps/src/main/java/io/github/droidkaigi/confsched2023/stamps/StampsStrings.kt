package io.github.droidkaigi.confsched2023.stamps

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class StampsStrings : Strings<StampsStrings>(Bindings) {

    private object Bindings : StringsBindings<StampsStrings>(
        Lang.Japanese to { item, _ ->
            TODO()
        },
        Lang.English to { item, bindings ->
            TODO()
        },
        default = Lang.Japanese,
    )
}
