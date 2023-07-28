package io.github.droidkaigi.confsched2023.data.sessions

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.github.droidkaigi.confsched2023.data.NetworkService
import io.github.droidkaigi.confsched2023.data.sessions.response.LocaledResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionAllResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionAssetResponse
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionMessageResponse
import io.github.droidkaigi.confsched2023.model.MultiLangText
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
import io.github.droidkaigi.confsched2023.model.TimetableSpeaker
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

internal interface SessionApi {
    @GET("events/droidkaigi2022/timetable")
    suspend fun getTimetable(): SessionAllResponse
}

class DefaultSessionsApiClient(
    val networkService: NetworkService,
    ktorfit: Ktorfit,
) : SessionsApiClient {

    private val sessionApi = ktorfit.create<SessionApi>()

    override suspend fun timetable(): Timetable {
        return networkService {
            sessionApi.getTimetable().toTimetable()
        }
    }
}

internal fun SessionAllResponse.toTimetable(): Timetable {
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
    val roomIdToRoom = timetableContents.rooms
        .groupBy { it.id }
        .mapValues { (_, apiRooms) ->
            apiRooms.map { apiRoom ->
                val roomSorts = apiRooms.map { it.sort }.sorted()
                TimetableRoom(
                    id = apiRoom.id,
                    name = apiRoom.name.toMultiLangText(),
                    sort = apiRoom.sort,
                    sortIndex = roomSorts.indexOf(apiRoom.sort),
                )
            }.first()
        }

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
                        .thenBy { it.room.sort },
                )
                .toPersistentList(),
        ),
    )
}

private fun LocaledResponse.toMultiLangText() = MultiLangText(jaTitle = ja, enTitle = en)
private fun SessionMessageResponse.toMultiLangText() = MultiLangText(jaTitle = ja, enTitle = en)
private fun SessionAssetResponse.toTimetableAsset() = TimetableAsset(videoUrl, slideUrl)

internal fun String.toInstantAsJST(): Instant {
    val (date, _) = split("+")
    return LocalDateTime.parse(date).toInstant(TimeZone.of("UTC+9"))
}
