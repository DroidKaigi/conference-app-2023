package io.github.droidkaigi.confsched2023.staff

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.staff.section.StaffSheet
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState

const val staffScreenRoute = "staff"

fun NavGraphBuilder.staffScreen(onBackClick: () -> Unit, onStaffClick: (url: String) -> Unit) {
    composable(staffScreenRoute) {
        StaffScreen(onBackClick = onBackClick, onStaffClick = onStaffClick)
    }
}

fun NavController.navigateStaffScreen() {
    navigate(staffScreenRoute)
}

@Composable
fun StaffScreen(
    onBackClick: () -> Unit,
    onStaffClick: (url: String) -> Unit,
    viewModel: StaffScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    StaffScreen(
        uiState = uiState,
        onStaffClick = onStaffClick,
        onBackClick = onBackClick,
    )
}

internal data class StaffScreenUiState(
    val contentUiState: StaffSheetUiState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StaffScreen(
    uiState: StaffScreenUiState,
    onStaffClick: (url: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Staff") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { padding ->
        StaffSheet(
            uiState = uiState.contentUiState,
            onStaffClick = onStaffClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        )
    }
}
