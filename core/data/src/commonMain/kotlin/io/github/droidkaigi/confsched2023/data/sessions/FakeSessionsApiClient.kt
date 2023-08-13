package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.fake
import okio.IOException

class FakeSessionsApiClient : SessionsApiClient {

    sealed class Status : SessionsApiClient {
        object Operational : Status() {
            override suspend fun timetable(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Status() {
            override suspend fun timetable(): Timetable {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun timetable(): Timetable {
        return status.timetable()
    }
}
