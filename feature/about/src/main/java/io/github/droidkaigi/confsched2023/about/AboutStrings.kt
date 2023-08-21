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
    class PlaceLink(
        val url: String = "https://goo.gl/maps/vv9sE19JvRjYKtSP9",
    ) : AboutStrings()
    object CreditsTitle : AboutStrings()
    object Staff : AboutStrings()
    object Contributor : AboutStrings()
    object Sponsor : AboutStrings()
    object OthersTitle : AboutStrings()
    object CodeOfConduct : AboutStrings()
    object License : AboutStrings()
    object PrivacyPolicy : AboutStrings()
    object AppVersion : AboutStrings()

    private object Bindings : StringsBindings<AboutStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Title -> "About"
                Description -> "DroidKaigiはAndroid技術情報の共有とコミュニケーションを目的に開催されるエンジニアが主役のAndroidカンファレンスです。"
                DateTitle -> "日時"
                DateDescription -> "2023.09.14(木) 〜 16(土) 3日間"
                PlaceTitle -> "場所"
                PlaceDescription -> "ベルサール渋谷ガーデン"
                is PlaceLink -> "地図を見る"
                CreditsTitle -> "Credits"
                Staff -> "スタッフ"
                Contributor -> "コントリビューター"
                Sponsor -> "スポンサー"
                OthersTitle -> "Others"
                CodeOfConduct -> "行動規範"
                License -> "ライセンス"
                PrivacyPolicy -> "プライバシーポリシー"
                AppVersion -> "アプリバージョン"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Title -> bindings.defaultBinding(item, bindings)
                Description -> "DroidKaigi is a conference tailored for Android developers."
                DateTitle -> "Date"
                DateDescription -> "2023.09.14(Thu) - 16(Sat) 3days"
                PlaceTitle -> "Place"
                PlaceDescription -> "Bellesalle Shibuya Garden"
                is PlaceLink -> "View Map"
                CreditsTitle -> bindings.defaultBinding(item, bindings)
                Staff -> "Staff"
                Contributor -> "Contributor"
                Sponsor -> "Sponsor"
                OthersTitle -> bindings.defaultBinding(item, bindings)
                CodeOfConduct -> "Code Of Conduct"
                License -> "License"
                PrivacyPolicy -> "Privacy Policy"
                AppVersion -> "App Version"
            }
        },
        default = Lang.Japanese,
    )
}
