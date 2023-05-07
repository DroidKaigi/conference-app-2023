package io.github.droidkaigi.confsched2023.designsystem

import io.github.droidkaigi.confsched2023.model.Locale
import io.github.droidkaigi.confsched2023.model.getDefaultLocale

interface LocalizedString {

    fun japanese(vararg args: String): String
    fun english(vararg args: String): String

    fun value(vararg args: String): String = getDefaultLocale().let { locale ->
        when (locale) {
            Locale.JAPAN -> japanese(*args)
            Locale.OTHER -> english(*args)
        }
    }
}
