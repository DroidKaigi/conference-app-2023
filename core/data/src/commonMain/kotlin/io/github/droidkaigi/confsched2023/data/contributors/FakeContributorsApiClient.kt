package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

class FakeContributorsApiClient : ContributorsApiClient {

    sealed class Status : ContributorsApiClient {
        object Operational : Status() {
            override suspend fun contributors(): PersistentList<Contributor> {
                return Contributor.fakes()
            }
        }

        object Error : Status() {
            override suspend fun contributors(): PersistentList<Contributor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun contributors(): PersistentList<Contributor> {
        return status.contributors()
    }
}
