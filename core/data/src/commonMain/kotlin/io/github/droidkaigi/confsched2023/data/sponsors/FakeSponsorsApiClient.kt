package io.github.droidkaigi.confsched2023.data.sponsors

import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

class FakeSponsorsApiClient : SponsorsApiClient {

    sealed class Behavior : SponsorsApiClient {
        object Operational : Behavior() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                return Sponsor.fakes()
            }
        }

        object Error : Behavior() {
            override suspend fun sponsors(): PersistentList<Sponsor> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var behavor: Behavior = Behavior.Operational

    fun setup(behavior: Behavior) {
        this.behavor = behavior
    }

    override suspend fun sponsors(): PersistentList<Sponsor> {
        return behavor.sponsors()
    }
}
