package io.github.droidkaigi.confsched2023.model

public data class TimetableLanguage(
    val langOfSpeaker: String,
    val isInterpretationTarget: Boolean,
) {
    val labels = if (langOfSpeaker == Lang.MIXED.tagName) {
        listOf(Lang.MIXED.tagName)
    } else if (isInterpretationTarget) {
        listOf(Lang.ENGLISH.tagName, Lang.JAPANESE.tagName)
    } else {
        listOf(langOfSpeaker.take(2))
    }

    fun toLang() = if (isInterpretationTarget) {
        Lang.MIXED
    } else {
        Lang.entries.firstOrNull { it.tagName == langOfSpeaker.take(2) } ?: Lang.MIXED
    }
}
