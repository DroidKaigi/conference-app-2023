package io.github.droidkaigi.confsched2023.data.staff

import io.github.droidkaigi.confsched2023.data.staff.response.StaffsResponse
import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

class FakeStaffApiClient : StaffApiClient {
    sealed class Status : StaffApiClient {
        data object Operational : Status() {
            override suspend fun getStaff(): PersistentList<Staff> {
                return Staff.fakes()
            }
        }

        data object Error : Status() {
            override suspend fun getStaff(): PersistentList<Staff> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun getStaff(): PersistentList<Staff> {
        return status.getStaff()
    }
}