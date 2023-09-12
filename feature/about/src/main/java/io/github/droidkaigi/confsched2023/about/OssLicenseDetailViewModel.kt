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
        ossLicenseRepository.licenseMetaData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentListOf(),
            )

    private val licenseDetailStateFlow: StateFlow<List<String>> =
        ossLicenseRepository.licenseDetailData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    internal val uiState: StateFlow<OssLicenseDetailScreenUiState> =
        buildUiState(licenseStateFlow, licenseDetailStateFlow) { metadata, detail ->
            val license = metadata.firstOrNull { it.name.replace(' ', '-') == licenseName.value }
                ?.let { license ->
                    val detail = kotlin.runCatching {
                        val start = license.offset
                        val end = license.offset + license.length
                        detail.subList(
                            fromIndex = start,
                            toIndex = end,
                        ).joinToString("\n")
                    }.getOrElse { "" }
                    license.copy(detail = detail)
                }

            OssLicenseDetailScreenUiState(license)
        }
}
