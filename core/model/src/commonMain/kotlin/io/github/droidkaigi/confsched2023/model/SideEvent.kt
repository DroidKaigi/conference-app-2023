package io.github.droidkaigi.confsched2023.model

import kotlinx.serialization.Serializable

@Serializable
public data class SideEvent(
    val title: MultiLangText,
    val description: MultiLangText,
    val timeText: MultiLangText,
    val floorLevel: FloorLevel,
    val mark: Mark,
    val link: String?,
) {

    enum class Mark {
        Mark1,
        ;
    }

    public companion object
}
