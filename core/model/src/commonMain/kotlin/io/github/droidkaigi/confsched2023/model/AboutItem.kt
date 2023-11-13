package io.github.droidkaigi.confsched2023.model

public sealed class AboutItem {
    public data object Sponsors : AboutItem()
    public data object Contributors : AboutItem()
    public data object Staff : AboutItem()
    public data object CodeOfConduct : AboutItem()
    public data object License : AboutItem()
    public data object PrivacyPolicy : AboutItem()
    public data object YouTube : AboutItem()
    public data object X : AboutItem()
    public data object Medium : AboutItem()
}
