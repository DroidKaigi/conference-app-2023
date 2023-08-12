package io.github.droidkaigi.confsched2023.data.sponsors

import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

public class DefaultSponsorsRepository(
    private val sponsorsApi: SponsorsApiClient,
) : SponsorsRepository {
    private val sponsorsStateFlow =
        MutableStateFlow<PersistentList<Sponsor>>(persistentListOf())

    override fun sponsors(): Flow<PersistentList<Sponsor>> {
        return sponsorsStateFlow.onStart {
            if (sponsorsStateFlow.value.isEmpty()) {
                refresh()
            }
        }
    }

    override suspend fun refresh() {
        sponsorsStateFlow.value = sponsorsApi
            .sponsors()
            .toPersistentList()
    }
}
