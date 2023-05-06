package io.github.droidkaigi.confsched2023.model

import kotlinx.serialization.Serializable

@Serializable
public data class TimetableLanguage(
    val langOfSpeaker: String,
    val isInterpretationTarget: Boolean,
)
