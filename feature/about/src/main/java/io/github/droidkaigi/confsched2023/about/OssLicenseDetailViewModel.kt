package io.github.droidkaigi.confsched2023.about

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OssLicenseDetailViewModel @Inject constructor(
    private val ossLicenseRepository: OssLicenseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val licenseName = savedStateHandle.getStateFlow<String>(
        ossLicenseDetailScreenRouteNameArgument,
        "",
    )

    private val licenseStateFlow: StateFlow<PersistentList<License>> =
        ossLicenseRepository.licenseData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentListOf(),
            )

    internal val uiState: StateFlow<OssLicenseDetailScreenUiState> =
        buildUiState(licenseStateFlow) { licenses ->
            val license = licenses.firstOrNull { it.id == licenseName.value }
            OssLicenseDetailScreenUiState(license)
        }
}
