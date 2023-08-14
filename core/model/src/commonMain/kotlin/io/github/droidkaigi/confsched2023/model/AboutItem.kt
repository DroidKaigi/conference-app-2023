package io.github.droidkaigi.confsched2023.model

sealed class AboutItem {
    data object Sponsors : AboutItem()
    data object Contributors : AboutItem()
    data object Staff : AboutItem()
    data object CodeOfConduct : AboutItem()
    data object License : AboutItem()
    data object PrivacyPolicy : AboutItem()
    data object YouTube : AboutItem()
    data object X : AboutItem()
    data object Medium : AboutItem()
}
