package io.github.droidkaigi.confsched2023.data.session

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.fake

interface SessionsApi {
    suspend fun sessions(): Timetable
}

class FakeSessionsApi : SessionsApi {

    sealed class Strategy : SessionsApi {
        object Operational : Strategy() {
            override suspend fun sessions(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Strategy() {
            override suspend fun sessions(): Timetable {
                throw RuntimeException("Error")
            }
        }
    }

    private var strategy: Strategy = Strategy.Operational

    fun setup(strategy: Strategy) {
        this.strategy = strategy
    }

    override suspend fun sessions(): Timetable {
        return strategy.sessions()
    }
}
