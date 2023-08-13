package io.github.droidkaigi.confsched2023.data.sponsors

import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

class FakeSponsorsApiClient : SponsorsApiClient {

    sealed class Status : SponsorsApiClient {
        object Operational : Status() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                return Sponsor.fakes()
            }
        }

        object Error : Status() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun sponsors(): PersistentList<Sponsor> {
        return status.sponsors()
    }
}
