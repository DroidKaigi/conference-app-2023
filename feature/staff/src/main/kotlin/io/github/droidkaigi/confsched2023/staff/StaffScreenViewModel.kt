package io.github.droidkaigi.confsched2023.staff

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.StaffRepository
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StaffScreenViewModel @Inject constructor(
    staffRepository: StaffRepository,
) : ViewModel() {

    private val staffsStateFlow = staffRepository.staffs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    internal val uiState: StateFlow<StaffScreenUiState> = buildUiState(staffsStateFlow) { staffs ->
        when {
            staffs == null -> StaffSheetUiState.Loading
            staffs.isEmpty() -> StaffSheetUiState.Empty
            else -> StaffSheetUiState.StaffList(
                staffs = staffs,
            )
        }.run { StaffScreenUiState(this) }
    }
}
