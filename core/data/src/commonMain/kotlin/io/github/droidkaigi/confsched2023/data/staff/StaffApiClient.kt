package io.github.droidkaigi.confsched2023.data.staff

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.github.droidkaigi.confsched2023.data.NetworkService
import io.github.droidkaigi.confsched2023.data.staff.response.StaffResponse
import io.github.droidkaigi.confsched2023.data.staff.response.StaffsResponse
import io.github.droidkaigi.confsched2023.model.Staff
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

internal interface StaffApi {
    @GET("/events/droidkaigi2023/staff")
    suspend fun getStaff(): StaffsResponse
}

public class DefaultStaffApiClient (
    val networkService: NetworkService,
    val ktorfit: Ktorfit
) : StaffApiClient {

    private val staffApi = ktorfit.create<StaffApi>()

    public override suspend fun getStaff(): PersistentList<Staff> {
        return networkService {
            staffApi.getStaff()
        }.toStaffList()
    }
}

interface StaffApiClient {
    suspend fun getStaff(): PersistentList<Staff>
}

private fun StaffsResponse.toStaffList(): PersistentList<Staff> {
    return staff.map { it.toStaff() }.toPersistentList()
}

private fun StaffResponse.toStaff(): Staff {
    return Staff(
        username = username,
        iconUrl = iconUrl,
    )
}
