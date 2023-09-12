package io.github.droidkaigi.confsched2023.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.License

const val ossLicenseDetailScreenRouteNameArgument = "name"
const val ossLicenseDetailScreenRoute = "osslicense"
fun NavGraphBuilder.nestedOssLicenseDetailScreen(onUpClick: () -> Unit) {
    composable("$ossLicenseDetailScreenRoute/{$ossLicenseDetailScreenRouteNameArgument}") {
        OssLicenseDetailScreen(
            onUpClick = onUpClick,
        )
    }
}

fun NavController.navigateOssLicenseDetailScreen(
    license: License,
) {
    val licenseName = license.name.replace(' ', '-')
    navigate("$ossLicenseDetailScreenRoute/$licenseName")
}

data class OssLicenseDetailScreenUiState(
    val ossLicense: License? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OssLicenseDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: OssLicenseDetailViewModel = hiltViewModel<OssLicenseDetailViewModel>(),
    onUpClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "OSS ライセンス")
                },
                navigationIcon = {
                    IconButton(onClick = { onUpClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back button",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
            ) {
                uiState.ossLicense?.run {
                    Text(
                        text = this.detail,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
