package io.github.droidkaigi.confsched2023.staff.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.designsystem.component.LoadingText
import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState.Loading
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState.StaffList
import kotlinx.collections.immutable.ImmutableList

@Stable
internal sealed interface StaffSheetUiState {
    data object Loading : StaffSheetUiState
    data class StaffList(
        val staffs: ImmutableList<Staff>,
    ) : StaffSheetUiState
}

@Composable
internal fun StaffSheet(
    uiState: StaffSheetUiState,
    modifier: Modifier = Modifier,
    onStaffClick: (url: String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            Loading -> LoadingText(modifier = Modifier.fillMaxSize())
            is StaffList -> StaffList(
                staffs = uiState.staffs,
                onStaffClick = onStaffClick,
            )
        }
    }
}
