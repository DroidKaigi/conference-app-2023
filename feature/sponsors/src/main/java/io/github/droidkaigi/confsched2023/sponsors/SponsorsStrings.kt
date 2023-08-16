package io.github.droidkaigi.confsched2023.sponsors

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SponsorsStrings : Strings<SponsorsStrings>(Bindings) {

    data object Sponsor : SponsorsStrings()
    data object PlatinumSponsors : SponsorsStrings()
    data object GoldSponsors : SponsorsStrings()
    data object Supporters : SponsorsStrings()

    private object Bindings : StringsBindings<SponsorsStrings>(
        Lang.Japanese to { item, _ ->
            when(item){
                Sponsor -> "スポンサー"
                PlatinumSponsors -> "プラチナスポンサー"
                GoldSponsors -> "ゴールドスポンサー"
                Supporters -> "サポーター"
            }
        },
        Lang.English to { item, _ ->
            when(item){
                Sponsor -> "Sponsor"
                PlatinumSponsors -> "PLATINUM SPONSORS"
                GoldSponsors -> "GOLD SPONSORS"
                Supporters -> "SUPPORTERS"
            }
        },
        default = Lang.Japanese,
    )
}
