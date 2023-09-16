package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

public class FakeContributorsApiClient : ContributorsApiClient {

    public sealed class Status : ContributorsApiClient {
        public data object Operational : Status() {
            override suspend fun contributors(): PersistentList<Contributor> {
                return Contributor.fakes()
            }
        }

        public data object Error : Status() {
            override suspend fun contributors(): PersistentList<Contributor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    public fun setup(status: Status) {
        this.status = status
    }

    override suspend fun contributors(): PersistentList<Contributor> {
        return status.contributors()
    }
}
