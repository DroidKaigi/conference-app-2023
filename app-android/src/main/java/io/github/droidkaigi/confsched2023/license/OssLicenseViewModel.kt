package io.github.droidkaigi.confsched2023.license

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OssLicenseViewModel @Inject constructor(
    private val ossLicenseRepository: OssLicenseRepository,
) : ViewModel() {

    private val ossLicenseStateFlow: StateFlow<PersistentList<OssLicense>> =
        ossLicenseRepository.sponsors()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentListOf(),
            )

    internal val uiState: StateFlow<OssLicenseScreenUiState> =
        buildUiState(ossLicenseStateFlow) { licenses ->
            OssLicenseScreenUiState(licenseList = licenses.toMutableList())
        }

    fun loadLicenseList() {
        ossLicenseRepository.refresh()
    }
}
