package io.github.droidkaigi.confsched2023.model

import kotlinx.serialization.Serializable

@Serializable
public data class SideEvent(
    val title: MultiLangText,
    val description: MultiLangText,
    val link: String,
) {
    public companion object
}
