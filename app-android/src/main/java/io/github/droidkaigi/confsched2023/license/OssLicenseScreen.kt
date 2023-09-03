package io.github.droidkaigi.confsched2023.license

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OssLicenseScreen(
    modifier: Modifier = Modifier,
    viewModel: OssLicenseViewModel = hiltViewModel<OssLicenseViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLicenseList()
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "OSS ライセンス")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            OssLicenseScreen(
                uiState = uiState,
                onLibraryClick = {},
            )
        }
    }
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
