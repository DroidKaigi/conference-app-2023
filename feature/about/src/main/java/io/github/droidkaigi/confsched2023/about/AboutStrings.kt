package io.github.droidkaigi.confsched2023.about

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class AboutStrings : Strings<AboutStrings>(Bindings) {

    private object Bindings : StringsBindings<AboutStrings>(
        Lang.Japanese to { item, _ ->
            TODO()
        },
        Lang.English to { item, bindings ->
            TODO()
        },
        default = Lang.Japanese,
    )
}
