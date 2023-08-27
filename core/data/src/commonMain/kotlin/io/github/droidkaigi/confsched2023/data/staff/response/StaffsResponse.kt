package io.github.droidkaigi.confsched2023.data.staff.response

import kotlinx.serialization.Serializable

@Serializable
internal data class StaffsResponse(
    val staff: List<StaffResponse>,
)
