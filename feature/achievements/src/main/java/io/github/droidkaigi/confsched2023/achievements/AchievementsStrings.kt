package io.github.droidkaigi.confsched2023.achievements

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class AchievementsStrings : Strings<AchievementsStrings>(Bindings) {

    data object Title : AchievementsStrings()

    data object DescriptionNotes : AchievementsStrings()

    private object Bindings : StringsBindings<AchievementsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "Achievements"
                DescriptionNotes -> "※ この企画は変更または中止になる可能性があります"
            }
        },
        Lang.English to { item, _ ->
            when (item) {
                Title -> "Achievements"
                DescriptionNotes -> "※ This program is subject to change or cancellation."
            }
        },
        default = Lang.Japanese,
    )
}
