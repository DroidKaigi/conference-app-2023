package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Immutable
public data class Timetable(
    val timetableItems: TimetableItemList = TimetableItemList(),
    val bookmarks: PersistentSet<TimetableItemId> = persistentSetOf(),
) {
    val contents: List<TimetableItemWithFavorite> by lazy {
        timetableItems.map {
            TimetableItemWithFavorite(it, bookmarks.contains(it.id))
        }
    }

    val rooms: List<TimetableRoom> by lazy {
        timetableItems.map { it.room }.toSet().sortedBy { it.sort }
    }

    val categories: List<TimetableCategory> by lazy {
        timetableItems.map { it.category }.toSet().sortedBy { it.id }
    }

    val sessionTypes: List<TimetableSessionType> by lazy {
        timetableItems.map { it.sessionType }.toSet().sorted()
    }

    val languages: List<TimetableLanguage> by lazy {
        timetableItems.map { it.language }.toSet().sortedBy { it.langOfSpeaker }
    }

    public fun dayTimetable(droidKaigi2023Day: DroidKaigi2023Day): Timetable {
        var timetableItems = timetableItems.toList()
        timetableItems = timetableItems.filter { timetableItem ->
            timetableItem.day == droidKaigi2023Day
        }
        return copy(timetableItems = TimetableItemList(timetableItems.toPersistentList()))
    }

    public fun filtered(filters: Filters): Timetable {
        var timetableItems = timetableItems.toList()
        if (filters.days.isNotEmpty()) {
            timetableItems = timetableItems.filter { timetableItem ->
                filters.days.contains(timetableItem.day)
            }
        }
        if (filters.categories.isNotEmpty()) {
            timetableItems = timetableItems.filter { timetableItem ->
                filters.categories.contains(timetableItem.category)
            }
        }
        if (filters.filterFavorite) {
            timetableItems = timetableItems.filter { timetableItem ->
                bookmarks.contains(timetableItem.id)
            }
        }
        if (filters.filterSession) {
            timetableItems = timetableItems.filterIsInstance<Session>()
        }
        if (filters.searchWord.isNotBlank()) {
            timetableItems = timetableItems.filter { timetableItem ->
                timetableItem.title.currentLangTitle.contains(
                    filters.searchWord,
                    ignoreCase = true,
                )
            }
        }
        return copy(timetableItems = TimetableItemList(timetableItems.toPersistentList()))
    }

    public fun isEmpty(): Boolean {
        return timetableItems.isEmpty()
    }

    public companion object
}

public fun Timetable?.orEmptyContents(): Timetable = this ?: Timetable()

public fun Timetable.Companion.fake(): Timetable {
    var rooms = listOf(
        TimetableRoom(1, MultiLangText("App Bar", "App Bar"), 0, 0),
        TimetableRoom(2, MultiLangText("Backdrop", "Backdrop"), 1, 1),
        TimetableRoom(3, MultiLangText("Cards", "Cards"), 2, 2),
        TimetableRoom(4, MultiLangText("Dialogs", "Dialogs"), 3, 3),
    )
    (0..10).forEach { _ ->
        rooms += rooms
    }
    val roomsIterator = rooms.iterator()
    val timetableItems = buildList {
        add(
            TimetableItem.Special(
                id = TimetableItemId("1"),
                title = MultiLangText("ウェルカムトーク", "Welcome Talk"),
                startsAt = LocalDateTime.parse("2023-09-15T10:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2023-09-15T10:20:00")
                    .toInstant(TimeZone.of("UTC+9")),
                category = TimetableCategory(
                    id = 28657,
                    title = MultiLangText("その他", "Other"),
                ),
                sessionType = TimetableSessionType.NORMAL,
                room = roomsIterator.next(),
                targetAudience = "TBW",
                language = TimetableLanguage(
                    langOfSpeaker = "JAPANESE",
                    isInterpretationTarget = true,
                ),
                asset = TimetableAsset(null, null),
                levels = persistentListOf(
                    "BEGINNER",
                    "INTERMEDIATE",
                    "ADVANCED",
                ),
                speakers = persistentListOf(
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
                ),
            ),
        )
        (-1..1).forEach { day ->
            (0..20).forEach { index ->
                val dayOffset = day * 24 * 60 * 60
                val start = Instant.fromEpochSeconds(
                    LocalDateTime.parse("2023-09-15T10:10:00")
                        .toInstant(TimeZone.of("UTC+9")).epochSeconds + index * 25 * 60 + dayOffset,
                ).toLocalDateTime(
                    TimeZone.of("UTC+9"),
                )
                val end = Instant.fromEpochSeconds(
                    LocalDateTime.parse("2023-09-15T10:50:00")
                        .toInstant(TimeZone.of("UTC+9")).epochSeconds + index * 25 * 60 + dayOffset,
                ).toLocalDateTime(
                    TimeZone.of("UTC+9"),
                )

                val fake = Session.fake()
                add(
                    fake
                        .copy(
                            id = TimetableItemId("$day$index"),
                            title = MultiLangText(
                                jaTitle = "${fake.title.jaTitle} $day $index",
                                enTitle = "${fake.title.enTitle} $day $index",
                            ),
                            room = roomsIterator.next(),
                            startsAt = start
                                .toInstant(TimeZone.of("UTC+9")),
                            endsAt = end
                                .toInstant(TimeZone.of("UTC+9")),
                        ),
                )
            }
        }
        add(
            TimetableItem.Special(
                id = TimetableItemId("3"),
                title = MultiLangText("Closing", "Closing"),
                startsAt = LocalDateTime.parse("2023-09-15T10:40:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2023-09-15T11:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                category = TimetableCategory(
                    id = 28657,
                    title = MultiLangText("その他", "Other"),
                ),
                sessionType = TimetableSessionType.NORMAL,
                room = roomsIterator.next(),
                targetAudience = "TBW",
                language = TimetableLanguage(
                    langOfSpeaker = "ENGLISH",
                    isInterpretationTarget = true,
                ),
                asset = TimetableAsset(null, null),
                levels = persistentListOf(
                    "BEGINNER",
                    "INTERMEDIATE",
                    "ADVANCED",
                ),
                speakers = persistentListOf(
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
                ),
            ),
        )
    }
    return Timetable(
        timetableItems = TimetableItemList(
            timetableItems.toPersistentList(),
        ),
        bookmarks = persistentSetOf(),
    )
}
