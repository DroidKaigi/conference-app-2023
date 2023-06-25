package io.github.droidkaigi.confsched2023.model

public enum class Locale {
    JAPAN,
    OTHER,
}

public expect fun getDefaultLocale(): Locale
