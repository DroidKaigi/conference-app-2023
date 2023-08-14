package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.FloorLevel.Basement
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark.Mark1
import kotlinx.collections.immutable.persistentListOf

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
    }

    public companion object
}

val SideEvents = persistentListOf(
    SideEvent(
        title = MultiLangText(
            jaTitle = "アプリFiresideチャット(これは仮で後で消えます)",
            enTitle = "App Fireside chat(This is demo event and will be deleted later)",
        ),
        floorLevel = Basement,
        description = MultiLangText(
            jaTitle = "地下一階でDroidKaigiアプリの開発について、開発者と一緒に語りましょう！(これは仮で後で消えます)",
            enTitle = "(Basement)Let's talk about the development of the DroidKaigi app with the developers!(This is demo event and will be deleted later)",
        ),
        timeText = MultiLangText(
            jaTitle = "DAY1-DAY2 10:00-11:00",
            enTitle = "DAY1-DAY2 10:00-11:00",
        ),
        mark = Mark1,
        link = "https://github.com/DroidKaigi/conference-app-2023",
    ),
)
