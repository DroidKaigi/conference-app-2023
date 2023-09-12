package io.github.droidkaigi.confsched2023.achievements

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class AchievementsStrings : Strings<AchievementsStrings>(Bindings) {

    data object Title : AchievementsStrings()

    data object DescriptionNotes : AchievementsStrings()

    data object DialogTitle : AchievementsStrings()

    data object DialogConfirmButton : AchievementsStrings()

    data object DialogDescription1 : AchievementsStrings()

    data object DialogDescription2 : AchievementsStrings()

    data object DialogDescription3 : AchievementsStrings()

    private object Bindings : StringsBindings<AchievementsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Achievements"
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
                Title -> "Achievements"
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
