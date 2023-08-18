package io.github.droidkaigi.confsched2023.stamps

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class StampsStrings : Strings<StampsStrings>(Bindings) {

    data object Title : StampsStrings()

    data object Description : StampsStrings()

    data object DescriptionNotes : StampsStrings()

    private object Bindings : StringsBindings<StampsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Stamps"
                Description -> "会場の各部屋に設置されたNFCタグにスマホをかざしてスタンプを集めてみましょう。"
                DescriptionNotes -> "※ この企画は変更または中止になる可能性があります"
            }
        },
        Lang.English to { item, _ ->
            when (item) {
                Title -> "Stamps"
                Description -> "Let's collect stamps by holding your smartphone over the NFC tags placed in each room of the venue."
                DescriptionNotes -> "※ This program is subject to change or cancellation."
            }
        },
        default = Lang.Japanese,
    )
}
