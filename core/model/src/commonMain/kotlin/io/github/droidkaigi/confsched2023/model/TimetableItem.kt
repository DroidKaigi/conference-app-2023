@file:UseSerializers(
    PersistentListSerializer::class,
)

package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
public sealed class TimetableItem {
    public abstract val id: TimetableItemId
    public abstract val title: MultiLangText
    public abstract val startsAt: Instant
    public abstract val endsAt: Instant
    public abstract val category: TimetableCategory
    public abstract val room: TimetableRoom
    public abstract val targetAudience: String
    public abstract val language: TimetableLanguage
    public abstract val asset: TimetableAsset
    public abstract val levels: PersistentList<String>
    public abstract val speakers: PersistentList<TimetableSpeaker>
    public val day: DroidKaigi2023Day? get() = DroidKaigi2023Day.ofOrNull(startsAt)

    @Serializable
    public data class Session(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val room: TimetableRoom,
        override val targetAudience: String,
        override val language: TimetableLanguage,
        override val asset: TimetableAsset,
        override val levels: PersistentList<String>,
        override val speakers: PersistentList<TimetableSpeaker>,
        val description: String,
        val message: MultiLangText?,
    ) : TimetableItem() {
        public companion object
    }

    @Serializable
    public data class Special(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val room: TimetableRoom,
        override val targetAudience: String,
        override val language: TimetableLanguage,
        override val asset: TimetableAsset,
        override val levels: PersistentList<String>,
        override val speakers: PersistentList<TimetableSpeaker>,
    ) : TimetableItem()

    private val startsDateString: String by lazy {
        val localDate = startsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.year}" + "." + "${localDate.monthNumber}".padStart(2, '0') + "." + "${localDate.dayOfMonth}".padStart(2, '0')
    }

    public val startsTimeString: String by lazy {
        val localDate = startsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.hour}".padStart(2, '0') + ":" + "${localDate.minute}".padStart(2, '0')
    }

    public val endsTimeString: String by lazy {
        val localDate = endsAt.toLocalDateTime(TimeZone.currentSystemDefault())
        "${localDate.hour}".padStart(2, '0') + ":" + "${localDate.minute}".padStart(2, '0')
    }

    private val minutesString: String by lazy {
        val minutes = (endsAt - startsAt)
            .toComponents { minutes, _, _ -> minutes }
        "${minutes}min"
    }

    public val formattedDateTimeString: String by lazy {
        "$startsDateString / $startsTimeString ~ $endsTimeString ($minutesString)"
    }

    public val speakerString: String by lazy {
        speakers.joinToString(", ") { it.name }
    }

    fun getSupportedLangString(isJapaneseLocale: Boolean): String {
        val japanese = if (isJapaneseLocale) "日本語" else "Japanese"
        val english = if (isJapaneseLocale) "英語" else "English"
        val japaneseWithInterpretation =
            if (isJapaneseLocale) "日本語 (英語通訳あり)" else "Japanese (with Japanese Interpretation)"
        val englishWithInterpretation =
            if (isJapaneseLocale) "英語 (日本語通訳あり)" else "English (with Japanese Interpretation)"

        return when (language.langOfSpeaker) {
            "JAPANESE" -> if (language.isInterpretationTarget) japaneseWithInterpretation else japanese
            "ENGLISH" -> if (language.isInterpretationTarget) englishWithInterpretation else english
            else -> language.langOfSpeaker
        }
    }
}

public fun Session.Companion.fake(): Session {
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
        room = TimetableRoom(
            id = 1,
            name = MultiLangText("Room1", "Room2"),
            sort = 1,
            sortIndex = 1,
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
        description = "これはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。",
        message = MultiLangText(
            jaTitle = "このセッションは事情により中止となりました",
            enTitle = "This session has been cancelled due to circumstances.",
        ),
        levels = listOf(
            "INTERMEDIATE",
        ).toPersistentList(),
    )
}
