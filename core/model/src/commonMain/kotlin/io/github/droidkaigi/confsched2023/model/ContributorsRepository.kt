package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

public interface ContributorsRepository {

    public fun contributors(): Flow<PersistentList<Contributor>>
    public suspend fun refresh()
}
