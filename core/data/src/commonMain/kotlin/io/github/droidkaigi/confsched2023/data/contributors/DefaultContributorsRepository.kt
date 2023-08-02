package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

public class DefaultContributorsRepository(
    private val contributorsApi: ContributorsApiClient,
) : ContributorsRepository {
    private val contributorsStateFlow =
        MutableStateFlow<PersistentList<Contributor>>(persistentListOf())

    override fun contributors(): Flow<PersistentList<Contributor>> {
        return contributorsStateFlow.onStart {
            if (contributorsStateFlow.value.isEmpty()) {
                refresh()
            }
        }
    }

    override suspend fun refresh() {
        contributorsStateFlow.value = contributorsApi
            .contributors()
            .toPersistentList()
    }
}
