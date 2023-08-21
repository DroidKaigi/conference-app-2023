package io.github.droidkaigi.confsched2023.staff.section

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.staff.component.StaffListItem
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun StaffList(
    staffs: ImmutableList<Staff>,
    onStaffClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(staffs) { staff ->
            StaffListItem(
                staff = staff,
                onStaffClick = onStaffClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
