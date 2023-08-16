package io.github.droidkaigi.confsched2023.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.Approval
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2023.main.component.KaigiBottomBar
import io.github.droidkaigi.confsched2023.main.strings.MainStrings
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val mainScreenRoute = "main"
const val MainScreenTestTag = "MainScreen"

fun NavGraphBuilder.mainScreen(
    mainNestedGraphStateHolder: MainNestedGraphStateHolder,
    mainNestedGraph: NavGraphBuilder.(mainNestedNavController: NavController, PaddingValues) -> Unit,
) {
    composable(mainScreenRoute) {
        MainScreen(
            mainNestedGraphStateHolder = mainNestedGraphStateHolder,
            mainNestedNavGraph = mainNestedGraph,
        )
    }
}

interface MainNestedGraphStateHolder {
    val startDestination: String
    fun routeToTab(route: String): MainScreenTab?
    fun onTabSelected(mainNestedNavController: NavController, tab: MainScreenTab)
}

@Composable
fun MainScreen(
    mainNestedGraphStateHolder: MainNestedGraphStateHolder,
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
        routeToTab = mainNestedGraphStateHolder::routeToTab,
        onTabSelected = mainNestedGraphStateHolder::onTabSelected,
        mainNestedNavGraph = mainNestedNavGraph,
    )
}

enum class MainScreenTab(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
    val contentDescription: String,
    val testTag: String = "mainScreenTab:$label",
) {
    Timetable(
        icon = Icons.Outlined.CalendarMonth,
        selectedIcon = Icons.Filled.CalendarMonth,
        label = MainStrings.Timetable.asString(),
        contentDescription = MainStrings.Timetable.asString(),
    ),
    FloorMap(
        icon = Icons.Outlined.Map,
        selectedIcon = Icons.Filled.Map,
        label = MainStrings.FloorMap.asString(),
        contentDescription = MainStrings.FloorMap.asString(),
    ),
    Badges(
        icon = Icons.Outlined.Approval,
        selectedIcon = Icons.Filled.Approval,
        label = MainStrings.Badges.asString(),
        contentDescription = MainStrings.Badges.asString(),
    ),
    About(
        icon = Icons.Outlined.Info,
        selectedIcon = Icons.Filled.Info,
        label = MainStrings.About.asString(),
        contentDescription = MainStrings.About.asString(),
    ),
    Contributor(
        icon = Icons.Outlined.Group,
        selectedIcon = Icons.Filled.Group,
        label = MainStrings.Contributors.asString(),
        contentDescription = MainStrings.Contributors.asString(),
    ),
}

data class MainScreenUiState(
    val isStampsEnabled: Boolean = false,
)

@Composable
private fun MainScreen(
    uiState: MainScreenUiState,
    snackbarHostState: SnackbarHostState,
    routeToTab: String.() -> MainScreenTab?,
    onTabSelected: (NavController, MainScreenTab) -> Unit,
    mainNestedNavGraph: NavGraphBuilder.(NavController, PaddingValues) -> Unit,
) {
    val mainNestedNavController = rememberNavController()
    val navBackStackEntry by mainNestedNavController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.destination?.route?.routeToTab()
    Scaffold(
        bottomBar = {
            KaigiBottomBar(
                mainScreenTabs = MainScreenTab.values().toList(),
                onTabSelected = { tab ->
                    onTabSelected(mainNestedNavController, tab)
                },
                currentTab = currentTab ?: MainScreenTab.Timetable,
                isEnableStamps = uiState.isStampsEnabled,
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        NavHost(
            navController = mainNestedNavController,
            startDestination = "timetable",
            modifier = Modifier.padding(padding),
            enterTransition = { materialFadeThroughIn() },
            exitTransition = { materialFadeThroughOut() },
        ) {
            mainNestedNavGraph(mainNestedNavController, padding)
        }
    }
}

private fun materialFadeThroughIn(): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = 195,
        delayMillis = 105,
        easing = LinearOutSlowInEasing,
    ),
) + scaleIn(
    animationSpec = tween(
        durationMillis = 195,
        delayMillis = 105,
        easing = LinearOutSlowInEasing,
    ),
    initialScale = 0.92f,
)

private fun materialFadeThroughOut(): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = 105,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)
