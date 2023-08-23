package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.data.sessions.response.CategoryItemResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.CategoryResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.LocaledResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.RoomResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionAssetResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SpeakerResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import okio.IOException

class FakeSessionsApiClient : SessionsApiClient {

    sealed class Status : SessionsApiClient {
        data object Operational : Status() {
            override suspend fun sessionsAllResponse(): SessionsAllResponse {
                return SessionsAllResponse.fake()
            }
        }

        data object Error : Status() {
            override suspend fun sessionsAllResponse(): SessionsAllResponse {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun sessionsAllResponse(): SessionsAllResponse {
        return status.sessionsAllResponse()
    }
}

fun SessionsAllResponse.Companion.fake(): SessionsAllResponse {
    val sessions = mutableListOf<SessionResponse>()
    val speakers = listOf(
        SpeakerResponse(fullName = "taka", id = "1", isTopSpeaker = true),
        SpeakerResponse(fullName = "ry", id = "2", isTopSpeaker = true),
    )
    val rooms = listOf(
        RoomResponse(name = LocaledResponse(ja = "Chipmunk ja", en = "Chipmunk"), id = 1, sort = 1),
        RoomResponse(name = LocaledResponse(ja = "Arctic Fox ja", en = "Arctic Fox"), id = 2, sort = 2),
        RoomResponse(name = LocaledResponse(ja = "Bumblebee ja", en = "Bumblebee"), id = 3, sort = 3),
        RoomResponse(name = LocaledResponse(ja = "Dolphin ja", en = "Dolphin"), id = 4, sort = 4),
    )
    val categories = listOf(
        CategoryResponse(
            id = 1,
            sort = 1,
            title = LocaledResponse(ja = "Category1 ja", en = "Category1 en"),
            items = listOf(
                CategoryItemResponse(
                    id = 1,
                    name = LocaledResponse(ja = "CategoryItem1 ja", en = "CategoryItem1 en"),
                    sort = 1,
                ),
                CategoryItemResponse(
                    id = 2,
                    name = LocaledResponse(ja = "CategoryItem2 ja", en = "CategoryItem2 en"),
                    sort = 2,
                ),
            ),
        ),
    )

    sessions.add(
        SessionResponse(
            id = "0570556a-8a53-49d6-916c-26ff85635d86",
            title = LocaledResponse(ja = "Welcome Talk", en = "Welcome Talk"),
            description = null,
            startsAt = "2023-09-14T10:30:00+09:00",
            endsAt = "2023-09-14T11:00:00+09:00",
            isServiceSession = true,
            isPlenumSession = false,
            speakers = emptyList(),
            roomId = 1,
            targetAudience = "TBW",
            language = "JAPANESE",
            sessionCategoryItemId = 1,
            interpretationTarget = false,
            asset = SessionAssetResponse(videoUrl = null, slideUrl = null),
            message = null,
            sessionType = "WELCOME_TALK",
            levels = listOf("UNSPECIFIED"),
        ),
    )

    for (day in 0 until 3) {
        val dayOffset = day * 24 * 60 * 60
        for (room in rooms) {
            for (index in 0 until 4) {
                val start = Instant.fromEpochSeconds(
                    LocalDateTime.parse("2023-09-14T10:10:00")
                        .toInstant(TimeZone.of("UTC+9")).epochSeconds + index * 25 * 60 + dayOffset,
                ).toLocalDateTime(TimeZone.of("UTC+9"))
                val end = Instant.fromEpochSeconds(
                    LocalDateTime.parse("2023-09-14T10:50:00")
                        .toInstant(TimeZone.of("UTC+9")).epochSeconds + index * 25 * 60 + dayOffset,
                ).toLocalDateTime(TimeZone.of("UTC+9"))

                val session = SessionResponse(
                    id = "$day$room$index",
                    isServiceSession = false,
                    title = LocaledResponse(
                        ja = "DroidKaigiのアプリのアーキテクチャ day$day room${room.name.ja} index$index",
                        en = "DroidKaigi App Architecture day$day room${room.name.en} index$index",
                    ),
                    speakers = listOf("1", "2"),
                    description = "これはディスクリプションです。\nこれはディスクリプションです。",
                    startsAt = start.toString(),
                    endsAt = end.toString(),
                    language = "JAPANESE",
                    roomId = room.id,
                    sessionCategoryItemId = 1,
                    sessionType = "NORMAL",
                    message = null,
                    isPlenumSession = false,
                    targetAudience = "For App developer アプリ開発者向け",
                    interpretationTarget = false,
                    asset = SessionAssetResponse(
                        videoUrl = "https://www.youtube.com/watch?v=hFdKCyJ-Z9A",
                        slideUrl = "https://droidkaigi.jp/2021/",
                    ),
                    levels = listOf("INTERMEDIATE"),
                )
                sessions.add(session)
            }
        }
    }

    return SessionsAllResponse(
        sessions = sessions,
        rooms = rooms,
        speakers = speakers,
        categories = categories,
    )
}
