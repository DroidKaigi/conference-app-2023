package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.Lang.ENGLISH
import io.github.droidkaigi.confsched2023.model.Lang.JAPANESE
import io.github.droidkaigi.confsched2023.model.Lang.MIXED

public enum class Lang(
    public val tagName: String,
    public val backgroundColor: Long,
) {
    MIXED("MIXED", backgroundColor = 0xFF7056BB),
    JAPANESE("JA", backgroundColor = 0xFF48A8DA),
    ENGLISH("EN", backgroundColor = 0xFF6ACA8F);
}

public fun defaultLang(): Lang = if (getDefaultLocale() == Locale.JAPAN) JAPANESE else ENGLISH

public fun Lang.secondLang(): Lang? = when (this) {
    MIXED -> null
    JAPANESE -> ENGLISH
    ENGLISH -> JAPANESE
}
