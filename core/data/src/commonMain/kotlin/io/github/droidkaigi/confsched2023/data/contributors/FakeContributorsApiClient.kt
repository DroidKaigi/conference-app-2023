package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

class FakeContributorsApiClient : ContributorsApiClient {

    sealed class Behavior : ContributorsApiClient {
        object Operational : Behavior() {
            override suspend fun contributors(): PersistentList<Contributor> {
                return Contributor.fakes()
            }
        }

        object Error : Behavior() {
            override suspend fun contributors(): PersistentList<Contributor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var behavor: Behavior = Behavior.Operational

    fun setup(behavior: Behavior) {
        this.behavor = behavior
    }

    override suspend fun contributors(): PersistentList<Contributor> {
        return behavor.contributors()
    }
}
