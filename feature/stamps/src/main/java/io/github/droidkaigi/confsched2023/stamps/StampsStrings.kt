package io.github.droidkaigi.confsched2023.stamps

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class StampsStrings : Strings<StampsStrings>(Bindings) {

    data object Title : StampsStrings()

    data object DescriptionNotes : StampsStrings()

    private object Bindings : StringsBindings<StampsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Achievements"
                DescriptionNotes -> "※ この企画は変更または中止になる可能性があります"
            }
        },
        Lang.English to { item, _ ->
            when (item) {
                Title -> "Stamps"
                DescriptionNotes -> "※ This program is subject to change or cancellation."
            }
        },
        default = Lang.Japanese,
    )
}
