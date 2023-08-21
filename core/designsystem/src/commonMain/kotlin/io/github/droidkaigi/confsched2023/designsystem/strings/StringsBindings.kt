package io.github.droidkaigi.confsched2023.designsystem.strings

import androidx.compose.ui.text.intl.Locale

abstract class StringsBindings<T : Strings<T>>(
    vararg mapPairs: Pair<String, (T, StringsBindings<T>) -> String>,
    val default: String,
) {
    private val localeMap: Map<String, (T, StringsBindings<T>) -> String> = mapOf(*mapPairs)
    val defaultBinding: (T, StringsBindings<T>) -> String = requireNotNull(localeMap[default])

    private fun findBinding(): (T, StringsBindings<T>) -> String {
        val lang = lang()
        return localeMap[lang] ?: localeMap[default]!!
    }

    fun getString(item: Strings<T>): String {
        val binding = findBinding()
        return binding(item as T, this)
    }
}

private fun lang(): String = Locale.current.language

abstract class Strings<T : Strings<T>>(
    private val bindings: StringsBindings<T>,
) {
    fun asString(): String {
        return bindings.getString(this)
    }
}

object Lang {
    const val Japanese = "ja"
    const val English = "en"
}
