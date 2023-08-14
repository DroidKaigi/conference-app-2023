package io.github.droidkaigi.confsched2023.staff

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val staffScreenRoute = "staff"

fun NavGraphBuilder.staffScreen() {
    composable(staffScreenRoute) {
        StaffScreen()
    }
}

fun NavController.navigateStaffScreen() {
    navigate(staffScreenRoute)
}

@Composable
fun StaffScreen(viewModel: StaffScreenViewModel = hiltViewModel()) {

}