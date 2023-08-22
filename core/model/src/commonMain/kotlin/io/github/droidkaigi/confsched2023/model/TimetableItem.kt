package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.RoomType.RoomA
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableSessionType.NORMAL
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

sealed class TimetableItem {
    abstract val id: TimetableItemId
    abstract val title: MultiLangText
    abstract val startsAt: Instant
    abstract val endsAt: Instant
    abstract val category: TimetableCategory
    abstract val sessionType: TimetableSessionType
    abstract val room: TimetableRoom
    abstract val targetAudience: String
    abstract val language: TimetableLanguage
    abstract val asset: TimetableAsset
    abstract val levels: PersistentList<String>
    abstract val speakers: PersistentList<TimetableSpeaker>
    val day: DroidKaigi2023Day? get() = DroidKaigi2023Day.ofOrNull(startsAt)

    @Serializable
    data class Session(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val sessionType: TimetableSessionType,
        override val room: TimetableRoom,
        override val targetAudience: String,
        override val language: TimetableLanguage,
        override val asset: TimetableAsset,
        override val levels: PersistentList<String>,
        override val speakers: PersistentList<TimetableSpeaker>,
        val description: String,
        val message: MultiLangText?,
    ) : TimetableItem() {
        companion object
    }

    @Serializable
    data class Special(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val sessionType: TimetableSessionType,
        override val room: TimetableRoom,
        override val targetAudience: String,
        override val language: TimetableLanguage,
        override val asset: TimetableAsset,
        override val levels: PersistentList<String>,
        override val speakers: PersistentList<TimetableSpeaker>,
    ) : TimetableItem()

    private val startsDateString: String by lazy {
        val localDate = startsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.year}" + "." + "${localDate.monthNumber}".padStart(
            2,
            '0',
        ) + "." + "${localDate.dayOfMonth}".padStart(2, '0')
    }

    val startsTimeString: String by lazy {
        val localDate = startsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.hour}".padStart(2, '0') + ":" + "${localDate.minute}".padStart(2, '0')
    }

    val endsTimeString: String by lazy {
        val localDate = endsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.hour}".padStart(2, '0') + ":" + "${localDate.minute}".padStart(2, '0')
    }

    private val minutesString: String by lazy {
        val minutes = (endsAt - startsAt)
            .toComponents { minutes, _, _ -> minutes }
        "$minutes" + MultiLangText(jaTitle = "分", enTitle = "min").currentLangTitle
    }

    val formattedDateTimeString: String by lazy {
        "$startsDateString / $startsTimeString ~ $endsTimeString ($minutesString)"
    }

    val speakerString: String by lazy {
        speakers.joinToString(", ") { it.name }
    }

    fun getSupportedLangString(isJapaneseLocale: Boolean): String {
        val japanese = if (isJapaneseLocale) "日本語" else "Japanese"
        val english = if (isJapaneseLocale) "英語" else "English"
        val japaneseWithInterpretation =
            if (isJapaneseLocale) "日本語 (英語通訳あり)" else "Japanese (with English Interpretation)"
        val englishWithInterpretation =
            if (isJapaneseLocale) "英語 (日本語通訳あり)" else "English (with Japanese Interpretation)"

        return when (language.langOfSpeaker) {
            "JAPANESE" -> if (language.isInterpretationTarget) japaneseWithInterpretation else japanese
            "ENGLISH" -> if (language.isInterpretationTarget) englishWithInterpretation else english
            else -> language.langOfSpeaker
        }
    }
}

fun Session.Companion.fake(): Session {
    return Session(
        id = TimetableItemId("2"),
        title = MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
        startsAt = LocalDateTime.parse("2023-09-15T10:30:00")
            .toInstant(TimeZone.of("UTC+9")),
        endsAt = LocalDateTime.parse("2023-09-15T10:50:00")
            .toInstant(TimeZone.of("UTC+9")),
        category = TimetableCategory(
            id = 28654,
            title = MultiLangText(
                "Android FrameworkとJetpack",
                "Android Framework and Jetpack",
            ),
        ),
        sessionType = NORMAL,
        room = TimetableRoom(
            id = 1,
            name = MultiLangText("Room1", "Room2"),
            sort = 1,
            sortIndex = 0,
            type = RoomA,
        ),
        targetAudience = "For App developer アプリ開発者向け",
        language = TimetableLanguage(
            langOfSpeaker = "JAPANESE",
            isInterpretationTarget = true,
        ),
        asset = TimetableAsset(
            videoUrl = "https://www.youtube.com/watch?v=hFdKCyJ-Z9A",
            slideUrl = "https://droidkaigi.jp/2021/",
        ),
        speakers = listOf(
            TimetableSpeaker(
                id = "1",
                name = "taka",
                iconUrl = "https://github.com/takahirom.png",
                bio = "Likes Android",
                tagLine = "Android Engineer",
            ),
            TimetableSpeaker(
                id = "2",
                name = "ry",
                iconUrl = "https://github.com/ry-itto.png",
                bio = "Likes iOS",
                tagLine = "iOS Engineer",
            ),
        ).toPersistentList(),
        description = "これはディスクリプションです。\nこれはディスクリプションです。\nhttps://github.com/DroidKaigi/conference-app-2023 これはURLです。\nこれはディスクリプションです。",
        message = MultiLangText(
            jaTitle = "このセッションは事情により中止となりました",
            enTitle = "This session has been cancelled due to circumstances.",
        ),
        levels = listOf(
            "INTERMEDIATE",
        ).toPersistentList(),
    )
}
