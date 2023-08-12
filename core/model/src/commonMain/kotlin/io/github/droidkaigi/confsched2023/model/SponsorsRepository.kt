package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

public interface SponsorsRepository {
    public fun sponsors(): Flow<PersistentList<Sponsor>>

    @Throws(CancellationException::class)
    public suspend fun refresh()
}
