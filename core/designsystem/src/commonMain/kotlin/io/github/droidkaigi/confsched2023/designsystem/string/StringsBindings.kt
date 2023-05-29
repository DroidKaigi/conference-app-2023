package io.github.droidkaigi.confsched2023.designsystem.string

open class StringsBindings<T : Strings<T>>(
    private val localeMap: Map<String, (T, StringsBindings<T>) -> String>,
    val default: String
) {
    val defaultBinding = requireNotNull(localeMap[default])

    private fun findBinding(): (T, StringsBindings<T>) -> String {
        val lang = "ja"
        return localeMap[lang] ?: localeMap[default]!!
    }

    fun getString(item: Strings<T>): String {
        val binding = findBinding()
        return binding(item as T, this)
    }
}

abstract class Strings<T : Strings<T>>(
    private val bindings: StringsBindings<T>
) {
    fun asString(): String {
        return bindings.getString(this)
    }
}

object Lang {
    const val Japanese = "ja"
    const val English = "en"
}