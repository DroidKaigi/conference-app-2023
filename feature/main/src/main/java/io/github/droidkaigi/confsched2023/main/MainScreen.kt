package io.github.droidkaigi.confsched2023.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2023.main.strings.MainStrings
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val MainScreenTestTag = "MainScreen"

@Composable
fun MainScreen(
    timetableScreen: @Composable () -> Unit,
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
        timetableScreen = timetableScreen,
    )
}

enum class MainScreenTab(
    val icon: ImageVector,
    val contentDescription: String,
    val route: String,
) {
    Timetable(
        icon = Icons.Filled.DateRange,
        contentDescription = MainStrings.Timetable.asString(),
        route = "timetable",
    ),
    Play(
        icon = Icons.Filled.PlayArrow,
        contentDescription = MainStrings.Play.asString(),
        route = "play",
    ),
}

class MainScreenUiState()

@Composable
private fun MainScreen(
    uiState: MainScreenUiState,
    snackbarHostState: SnackbarHostState,
    timetableScreen: @Composable () -> Unit,
) {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                MainScreenTab.values().forEach { tab ->
                    IconButton(
                        onClick = {
                            bottomBarNavController.navigate(tab.route)
                        },
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.contentDescription,
                            tint = if (bottomBarNavController.currentBackStackEntry?.destination?.route == tab.route) {
                                Color.Blue
                            } else {
                                Color.Unspecified
                            },
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(navController = bottomBarNavController, startDestination = "timetable") {
            composable(MainScreenTab.Timetable.route) {
                Box(Modifier.padding(padding)) {
                    timetableScreen()
                }
            }
            composable(MainScreenTab.Play.route) {
                Text("play")
            }
        }
    }
}
