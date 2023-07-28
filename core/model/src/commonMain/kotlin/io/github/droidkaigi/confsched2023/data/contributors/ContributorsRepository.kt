package io.github.droidkaigi.confsched2023.data.contributors

import io.github.droidkaigi.confsched2023.model.Contributor
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

interface ContributorsRepository {

    fun contributors(): Flow<PersistentList<Contributor>>
    suspend fun refresh()
}
