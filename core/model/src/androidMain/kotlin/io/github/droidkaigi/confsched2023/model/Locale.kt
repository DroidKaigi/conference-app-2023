package io.github.droidkaigi.confsched2023.model

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

public actual fun getDefaultLocale(): Locale {
    val applicationLocales = AppCompatDelegate.getApplicationLocales()
    if (!applicationLocales.isEmpty) {
        return if (applicationLocales == LocaleListCompat.forLanguageTags("ja")) {
            Locale.JAPAN
        } else {
            Locale.OTHER
        }
    }

    return if (java.util.Locale.getDefault() == java.util.Locale.JAPAN) {
        Locale.JAPAN
    } else {
        Locale.OTHER
    }
}
