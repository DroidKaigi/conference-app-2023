package io.github.droidkaigi.confsched2023.model

sealed class AboutItem {
    object Sponsors : AboutItem()
    object Contributors : AboutItem()
    object Staff : AboutItem()
    object CodeOfConduct : AboutItem()
    object License : AboutItem()
    object PrivacyPolicy : AboutItem()
    object YouTube : AboutItem()
    object X : AboutItem()
    object Medium : AboutItem()
}
