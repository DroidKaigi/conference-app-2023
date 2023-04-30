package io.github.droidkaigi.confsched2023.data

import io.github.droidkaigi.confsched2023.model.Session
import io.github.droidkaigi.confsched2023.model.SessionTimetable

interface SessionsApi {
    suspend fun sessions(): SessionTimetable
}

class FakeSessionsApi : SessionsApi {

    sealed class Strategy : SessionsApi {
        object Operational : Strategy() {
            override suspend fun sessions(): SessionTimetable {
                return SessionTimetable(
                    listOf(
                        Session(
                            id = "1",
                        ),
                        Session(
                            id = "2",
                        ),
                    )
                )
            }
        }

        object Error : Strategy() {
            override suspend fun sessions(): SessionTimetable {
                throw RuntimeException("Error")
            }
        }
    }

    private var strategy: Strategy = Strategy.Operational

    fun setup(strategy: Strategy) {
        this.strategy = strategy
    }

    override suspend fun sessions(): SessionTimetable {
        return strategy.sessions()
    }
}