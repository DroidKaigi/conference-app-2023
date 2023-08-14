package io.github.droidkaigi.confsched2023.data.staff

import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.model.StaffRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultStaffRepository(
    private val staffApi: StaffApiClient,
) : StaffRepository {

    private val staffsStateFlow = MutableStateFlow<PersistentList<Staff>>(persistentListOf())

    override fun staffs(): Flow<PersistentList<Staff>> {
        return staffsStateFlow.onStart {
            if (staffsStateFlow.value.isEmpty()) {
                refresh()
            }
        }
    }

    override suspend fun refresh() {
        staffsStateFlow.value = staffApi
            .getStaff()
            .toPersistentList()
    }
}