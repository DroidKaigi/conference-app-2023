package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.FloorLevel.Basement
import io.github.droidkaigi.confsched2023.model.SideEvent.Mark.Favorite
import io.github.droidkaigi.confsched2023.model.SideEvent.MarkColor.Pink
import kotlinx.collections.immutable.persistentListOf

public data class SideEvent(
    val title: MultiLangText,
    val description: MultiLangText,
    val timeText: MultiLangText,
    val floorLevel: FloorLevel,
    val mark: Mark,
    val link: String?,
    val imageLink: String?,
) {

    enum class Mark(val color: MarkColor) {
        Favorite(Pink),
    }

    enum class MarkColor {
        Pink,
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
        mark = Favorite,
        link = "https://github.com/DroidKaigi/conference-app-2023",
        imageLink = "https://2023.droidkaigi.jp/static/12059b53c8c9813a85c1c44f8692a2c0/img_04.jpg",
    ),
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
        mark = Favorite,
        link = null,
        imageLink = null,
    ),
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
        mark = Favorite,
        link = "https://github.com/DroidKaigi/conference-app-2023",
        imageLink = "https://2023.droidkaigi.jp/static/12059b53c8c9813a85c1c44f8692a2c0/img_04.jpg",
    ),
)
