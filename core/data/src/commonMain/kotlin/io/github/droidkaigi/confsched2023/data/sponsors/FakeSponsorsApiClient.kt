package io.github.droidkaigi.confsched2023.data.sponsors

import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

public class FakeSponsorsApiClient : SponsorsApiClient {

    public sealed class Status : SponsorsApiClient {
        public data object Operational : Status() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                return Sponsor.fakes()
            }
        }

        public data object Error : Status() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    public fun setup(status: Status) {
        this.status = status
    }

    override suspend fun sponsors(): PersistentList<Sponsor> {
        return status.sponsors()
    }
}
