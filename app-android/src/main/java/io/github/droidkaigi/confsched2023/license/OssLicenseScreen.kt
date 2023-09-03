package io.github.droidkaigi.confsched2023.license

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ossLicenseScreenRoute = "osslicense"
fun NavGraphBuilder.ossLicenseScreen() {
    composable(ossLicenseScreenRoute) {
        OssLicenseScreen()
    }
}

fun NavController.navigateOssLicenseScreen() {
    navigate(ossLicenseScreenRoute)
}

data class OssLicenseScreenUiState(
    val licenseList: MutableList<OssLicense> = mutableListOf(),
)

@Composable
fun OssLicenseScreen(
    viewModel: OssLicenseViewModel = hiltViewModel<OssLicenseViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLicenseList()
    }

    OssLicenseScreen(
        uiState = uiState,
        onLibraryClick = {},
    )
}

@Composable
private fun OssLicenseScreen(
    uiState: OssLicenseScreenUiState,
    onLibraryClick: (OssLicense) -> Unit,
) {
    LazyColumn {
        items(items = uiState.licenseList) { item ->
            TextButton(onClick = { onLibraryClick(item) }) {
                Text(text = item.name)
            }
        }
    }
}
