package io.github.droidkaigi.confsched2023.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val MainScreenTestTag = "MainScreen"

@Composable
fun MainScreen(
    timetableScreen: @Composable () -> Unit
) {
    val viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    MainScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        timetableScreen = timetableScreen
    )
}

class MainScreenUiState(
)

@Composable
private fun MainScreen(
    uiState: MainScreenUiState,
    snackbarHostState: SnackbarHostState,
    timetableScreen: @Composable () -> Unit
) {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = {
                    bottomBarNavController.navigate("timetable")
                }) {
                    Icon(
                        Icons.Filled.DateRange, contentDescription = "", tint = Color.White
                    )
                }
                IconButton(onClick = {
                    bottomBarNavController.navigate("play")
                }) {
                    Icon(
                        Icons.Filled.PlayArrow, contentDescription = "", tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        padding
        NavHost(navController = bottomBarNavController, startDestination = "timetable") {
            composable("timetable") {
                timetableScreen()
            }
            composable("play") {
                Text("play")
            }
        }
    }
}
