package io.github.droidkaigi.confsched2023.stamps

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class StampsStrings : Strings<StampsStrings>(Bindings) {

    data object Title : StampsStrings()

    data object Description : StampsStrings()

    data object DescriptionNotes : StampsStrings()

    data object DialogTitle : StampsStrings()

    data object DialogConfirmButton: StampsStrings()

    data object DialogDescription1: StampsStrings()

    data object DialogDescription2: StampsStrings()

    data object DialogDescription3: StampsStrings()

    private object Bindings : StringsBindings<StampsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Stamps"
                Description -> "会場の各部屋に設置されたNFCタグにスマホをかざしてスタンプを集めてみましょう。"
                DescriptionNotes -> "※ この企画は変更または中止になる可能性があります"
                DialogTitle -> "アチーブメントに関する\n注意事項"
                DialogConfirmButton -> "OK"
                DialogDescription1 -> "アプリを削除してしまうと、データは復元できません。"
                DialogDescription2 -> "機種やバージョンによって、お手持ちの端末でうまく動作しない場合があるかもしれません。ご理解いただけますと幸いです。"
                DialogDescription3 -> "この企画は変更される可能性があります。"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Title -> "Stamps"
                Description -> "Let's collect stamps by holding your smartphone over the NFC tags placed in each room of the venue."
                DescriptionNotes -> "※ This program is subject to change or cancellation."
                DialogTitle -> "Notes on Achievements"
                DialogConfirmButton -> bindings.defaultBinding(item, bindings)
                DialogDescription1 -> "If you delete the application, data cannot be recovered."
                DialogDescription2 -> "Depending on the model and version, the application may not work well on your device. We appreciate your understanding."
                DialogDescription3 -> "This project is subject to change."
            }
        },
        default = Lang.Japanese,
    )
}
