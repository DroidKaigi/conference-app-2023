package io.github.droidkaigi.confsched2023.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2023.main.strings.MainStrings
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val mainScreenRoute = "main"
const val MainScreenTestTag = "MainScreen"


fun NavGraphBuilder.mainScreen(
    mainNestedGraphStateHolder: MainScreenStateHolder,
    mainNestedGraph: NavGraphBuilder.(mainNestedNavController: NavController, PaddingValues) -> Unit
) {
    composable(mainScreenRoute) {
        MainScreen(
            mainScreenStateHolder = mainNestedGraphStateHolder,
            mainNestedNavGraph = mainNestedGraph,
        )
    }
}

interface MainScreenStateHolder {
    val startDestination: String
    fun routeToTab(route: String): MainScreenTab?
    fun onTabSelected(mainNestedNavController: NavController, tab: MainScreenTab)
}

@Composable
fun MainScreen(
    mainScreenStateHolder: MainScreenStateHolder,
    viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModel>(),
    mainNestedNavGraph: NavGraphBuilder.(NavController, PaddingValues) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    MainScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        routeToTab = mainScreenStateHolder::routeToTab,
        onTabSelected = mainScreenStateHolder::onTabSelected,
        mainNestedNavGraph = mainNestedNavGraph,
    )
}

enum class MainScreenTab(
    val icon: ImageVector,
    val contentDescription: String,
) {
    Timetable(
        icon = Icons.Filled.DateRange,
        contentDescription = MainStrings.Timetable.asString(),
    ),
    Contributor(
        icon = Icons.Filled.List,
        contentDescription = MainStrings.Contributors.asString(),
    ),
}

class MainScreenUiState()

@Composable
private fun MainScreen(
    uiState: MainScreenUiState,
    snackbarHostState: SnackbarHostState,
    routeToTab: String.() -> MainScreenTab?,
    onTabSelected: (NavController, MainScreenTab) -> Unit,
    mainNestedNavGraph: NavGraphBuilder.(NavController, PaddingValues) -> Unit
) {
    val mainNestedNavController = rememberNavController()
    val navBackStackEntry by mainNestedNavController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.destination?.route?.routeToTab()
    Scaffold(
        bottomBar = {
            NavigationBar {
                MainScreenTab.values().forEach { tab ->
                    val selected = currentTab == tab
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            onTabSelected(mainNestedNavController, tab)
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.contentDescription,
                            )
                        },
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        NavHost(
            navController = mainNestedNavController,
            startDestination = "timetable"
        ) {
            mainNestedNavGraph(mainNestedNavController, padding)
        }
    }
}
