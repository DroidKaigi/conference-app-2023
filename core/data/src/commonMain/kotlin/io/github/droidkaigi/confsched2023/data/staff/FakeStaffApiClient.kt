package io.github.droidkaigi.confsched2023.data.staff

import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.model.fakes
import kotlinx.collections.immutable.PersistentList
import okio.IOException

public class FakeStaffApiClient : StaffApiClient {
    public sealed class Status : StaffApiClient {
        public data object Operational : Status() {
            override suspend fun getStaff(): PersistentList<Staff> {
                return Staff.fakes()
            }
        }

        public data object Error : Status() {
            override suspend fun getStaff(): PersistentList<Staff> {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    public fun setup(status: Status) {
        this.status = status
    }

    override suspend fun getStaff(): PersistentList<Staff> {
        return status.getStaff()
    }
}
