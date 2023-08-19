package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

interface StaffRepository {

    public fun staffs(): Flow<PersistentList<Staff>>

    @Throws(CancellationException::class)
    public suspend fun refresh()
}
