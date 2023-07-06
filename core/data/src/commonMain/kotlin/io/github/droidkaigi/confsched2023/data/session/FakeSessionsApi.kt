package io.github.droidkaigi.confsched2023.data.session

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.fake

interface SessionsApi {
    suspend fun timetable(): Timetable
}

class FakeSessionsApi : SessionsApi {

    sealed class Strategy : SessionsApi {
        object Operational : Strategy() {
            override suspend fun timetable(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Strategy() {
            override suspend fun timetable(): Timetable {
                throw RuntimeException("Error")
            }
        }
    }

    private var strategy: Strategy = Strategy.Operational

    fun setup(strategy: Strategy) {
        this.strategy = strategy
    }

    override suspend fun timetable(): Timetable {
        return strategy.timetable()
    }
}
