package io.github.droidkaigi.confsched2023.data.sessions

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.github.droidkaigi.confsched2023.data.NetworkService
import io.github.droidkaigi.confsched2023.data.sessions.response.LocaledResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionAssetResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionMessageResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse
import io.github.droidkaigi.confsched2023.model.MultiLangText
import io.github.droidkaigi.confsched2023.model.RoomType.RoomA
import io.github.droidkaigi.confsched2023.model.RoomType.RoomB
import io.github.droidkaigi.confsched2023.model.RoomType.RoomC
import io.github.droidkaigi.confsched2023.model.RoomType.RoomD
import io.github.droidkaigi.confsched2023.model.RoomType.RoomE
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableAsset
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItem.Special
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.model.TimetableItemList
import io.github.droidkaigi.confsched2023.model.TimetableLanguage
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.model.TimetableSessionType.Companion
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

internal interface SessionApi {
    @GET("events/droidkaigi2023/timetable")
    suspend fun getTimetable(): SessionsAllResponse
}

class DefaultSessionsApiClient internal constructor(
    private val networkService: NetworkService,
    ktorfit: Ktorfit,
) : SessionsApiClient {

    private val sessionApi = ktorfit.create<SessionApi>()

    override suspend fun sessionsAllResponse(): SessionsAllResponse {
        return networkService {
            sessionApi.getTimetable()
        }
    }
}

internal fun SessionsAllResponse.toTimetable(): Timetable {
    val timetableContents = this
    val speakerIdToSpeaker: Map<String, TimetableSpeaker> = timetableContents.speakers
        .groupBy { it.id }
        .mapValues { (_, apiSpeakers) ->
            apiSpeakers.map { apiSpeaker ->
                TimetableSpeaker(
                    id = apiSpeaker.id,
                    name = apiSpeaker.fullName,
                    bio = apiSpeaker.bio ?: "",
                    iconUrl = apiSpeaker.profilePicture.orEmpty(),
                    tagLine = apiSpeaker.tagLine ?: "",
                )
            }.first()
        }
    val categoryIdToCategory: Map<Int, TimetableCategory> = timetableContents.categories
        .flatMap { it.items }
        .groupBy { it.id }
        .mapValues { (_, apiCategories) ->
            apiCategories.map { apiCategory ->
                TimetableCategory(
                    id = apiCategory.id,
                    title = apiCategory.name.toMultiLangText(),
                )
            }.first()
        }
    val roomIdToRoom: Map<Int, TimetableRoom> = timetableContents.rooms
        .associateBy(
            keySelector = { room -> room.id },
            valueTransform = { room ->
                TimetableRoom(
                    id = room.id,
                    name = room.name.toMultiLangText(),
                    type = room.name.toRoomType(),
                )
            },
        )

    return Timetable(
        TimetableItemList(
            timetableContents.sessions.map { apiSession ->
                if (!apiSession.isServiceSession) {
                    Session(
                        id = TimetableItemId(apiSession.id),
                        title = apiSession.title.toMultiLangText(),
                        startsAt = apiSession.startsAt.toInstantAsJST(),
                        endsAt = apiSession.endsAt.toInstantAsJST(),
                        category = categoryIdToCategory[apiSession.sessionCategoryItemId]!!,
                        sessionType = TimetableSessionType.ofOrNull(apiSession.sessionType)!!,
                        room = roomIdToRoom[apiSession.roomId]!!,
                        targetAudience = apiSession.targetAudience,
                        language = TimetableLanguage(
                            langOfSpeaker = apiSession.language,
                            isInterpretationTarget = apiSession.interpretationTarget,
                        ),
                        asset = apiSession.asset.toTimetableAsset(),
                        description = apiSession.description ?: "",
                        speakers = apiSession.speakers
                            .map { speakerIdToSpeaker[it]!! }
                            .toPersistentList(),
                        message = apiSession.message?.toMultiLangText(),
                        levels = apiSession.levels.toPersistentList(),
                    )
                } else {
                    Special(
                        id = TimetableItemId(apiSession.id),
                        title = apiSession.title.toMultiLangText(),
                        startsAt = apiSession.startsAt.toInstantAsJST(),
                        endsAt = apiSession.endsAt.toInstantAsJST(),
                        category = categoryIdToCategory[apiSession.sessionCategoryItemId]!!,
                        sessionType = Companion.ofOrNull(apiSession.sessionType)!!,
                        room = roomIdToRoom[apiSession.roomId]!!,
                        targetAudience = apiSession.targetAudience,
                        language = TimetableLanguage(
                            langOfSpeaker = apiSession.language,
                            isInterpretationTarget = apiSession.interpretationTarget,
                        ),
                        asset = apiSession.asset.toTimetableAsset(),
                        speakers = apiSession.speakers
                            .map { speakerIdToSpeaker[it]!! }
                            .toPersistentList(),
                        levels = apiSession.levels.toPersistentList(),
                    )
                }
            }
                .sortedWith(
                    compareBy<TimetableItem> { it.startsAt }
                        .thenBy { it.room.name.currentLangTitle },
                )
                .toPersistentList(),
        ),
    )
}

private fun LocaledResponse.toMultiLangText() = MultiLangText(jaTitle = ja, enTitle = en)
private fun SessionMessageResponse.toMultiLangText() = MultiLangText(jaTitle = ja, enTitle = en)
private fun SessionAssetResponse.toTimetableAsset() = TimetableAsset(videoUrl, slideUrl)
private fun LocaledResponse.toRoomType() = when (en.lowercase()) {
    "arctic fox" -> RoomA
    "bumblebee" -> RoomB
    "chipmunk" -> RoomC
    "dolphin" -> RoomD
    "electric eel" -> RoomE
    else -> RoomA
}

internal fun String.toInstantAsJST(): Instant {
    val (date, _) = split("+")
    return LocalDateTime.parse(date).toInstant(TimeZone.of("UTC+9"))
}
