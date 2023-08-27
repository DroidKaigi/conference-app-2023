package io.github.droidkaigi.confsched2023.staff

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.StaffRepository
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StaffScreenViewModel @Inject constructor(
    staffRepository: StaffRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(), UserMessageStateHolder by userMessageStateHolder {

    private val staffsStateFlow = staffRepository.staffs()
        .handleErrorAndRetry(
            AppStrings.Retry,
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    internal val uiState: StateFlow<StaffScreenUiState> = buildUiState(staffsStateFlow) { staffs ->
        when (staffs) {
            null -> StaffSheetUiState.Loading
            else -> StaffSheetUiState.StaffList(
                staffs = staffs,
            )
        }.run { StaffScreenUiState(this) }
    }
}
