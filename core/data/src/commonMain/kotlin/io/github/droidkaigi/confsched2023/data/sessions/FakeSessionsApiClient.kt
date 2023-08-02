package io.github.droidkaigi.confsched2023.data.sessions

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.fake
import okio.IOException

class FakeSessionsApiClient : SessionsApiClient {

    sealed class Behavior : SessionsApiClient {
        object Operational : Behavior() {
            override suspend fun timetable(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Behavior() {
            override suspend fun timetable(): Timetable {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var behavor: Behavior = Behavior.Operational

    fun setup(behavior: Behavior) {
        this.behavor = behavior
    }

    override suspend fun timetable(): Timetable {
        return behavor.timetable()
    }
}
