package io.github.droidkaigi.confsched2023.about

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class AboutStrings : Strings<AboutStrings>(Bindings) {
    object Title : AboutStrings()
    object Description : AboutStrings()
    object DateTitle : AboutStrings()
    object DateDescription : AboutStrings()
    object PlaceTitle : AboutStrings()
    object PlaceDescription : AboutStrings()
    object PlaceLink : AboutStrings()
    private object Bindings : StringsBindings<AboutStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "About"
                Description -> "DroidKaigiはAndroid技術情報の共有とコミュニケーションを目的に開催されるエンジニアが主役のAndroidカンファレンスです。"
                DateTitle -> "日時"
                DateDescription -> "2023.09.14(木) 〜 16(土) 3日間"
                PlaceTitle -> "場所"
                PlaceDescription -> "ベルサール渋谷ガーデン"
                PlaceLink -> "地図を見る"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Title -> "About"
                Description -> "DroidKaigi is an engineer-driven Android conference held to share and communicate Android technical information."
                DateTitle -> "Date & Time"
                DateDescription -> "2023.09.14(Thu) - 16(Sat) 3days"
                PlaceTitle -> "Location"
                PlaceDescription -> "Bellesalle Shibuya Garden"
                PlaceLink -> "View Map"
            }
        },
        default = Lang.Japanese,
    )
}
