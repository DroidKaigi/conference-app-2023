package io.github.droidkaigi.confsched2023.main

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.window.layout.DisplayFeature
import io.github.droidkaigi.confsched2023.feature.main.R
import io.github.droidkaigi.confsched2023.main.NavigationType.BOTTOM_NAVIGATION
import io.github.droidkaigi.confsched2023.main.NavigationType.NAVIGATION_RAIL
import io.github.droidkaigi.confsched2023.main.component.KaigiBottomBar
import io.github.droidkaigi.confsched2023.main.component.KaigiNavigationRail
import io.github.droidkaigi.confsched2023.main.strings.MainStrings
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

const val mainScreenRoute = "main"
const val MainScreenTestTag = "MainScreen"

fun NavGraphBuilder.mainScreen(
    windowSize: WindowSizeClass,
    displayFeatures: PersistentList<DisplayFeature>,
    mainNestedGraphStateHolder: MainNestedGraphStateHolder,
    mainNestedGraph: NavGraphBuilder.(mainNestedNavController: NavController, PaddingValues) -> Unit,
) {
    composable(mainScreenRoute) {
        MainScreen(
            windowSize = windowSize,
            displayFeatures = displayFeatures,
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

enum class NavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL
}

@Composable
fun MainScreen(
    windowSize: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    mainNestedGraphStateHolder: MainNestedGraphStateHolder,
    viewModel: MainScreenViewModel = hiltViewModel(),
    mainNestedNavGraph: NavGraphBuilder.(NavController, PaddingValues) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val navigationType: NavigationType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> NAVIGATION_RAIL
        else -> BOTTOM_NAVIGATION
    }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    MainScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        navigationType = navigationType,
        routeToTab = mainNestedGraphStateHolder::routeToTab,
        onTabSelected = mainNestedGraphStateHolder::onTabSelected,
        mainNestedNavGraph = mainNestedNavGraph,
    )
}

sealed class IconRepresentation {
    data class Vector(val imageVector: ImageVector) : IconRepresentation()
    data class Drawable(@DrawableRes val drawableId: Int) : IconRepresentation()
}

enum class MainScreenTab(
    val icon: IconRepresentation,
    val selectedIcon: IconRepresentation,
    val label: String,
    val contentDescription: String,
    val testTag: String = "mainScreenTab:$label",
) {
    Timetable(
        icon = IconRepresentation.Vector(Icons.Outlined.CalendarMonth),
        selectedIcon = IconRepresentation.Vector(Icons.Filled.CalendarMonth),
        label = MainStrings.Timetable.asString(),
        contentDescription = MainStrings.Timetable.asString(),
    ),
    FloorMap(
        icon = IconRepresentation.Vector(Icons.Outlined.Map),
        selectedIcon = IconRepresentation.Drawable(drawableId = R.drawable.icon_map_fill),
        label = MainStrings.FloorMap.asString(),
        contentDescription = MainStrings.FloorMap.asString(),
    ),
    Badges(
        icon = IconRepresentation.Drawable(drawableId = R.drawable.icon_stamp_outline),
        selectedIcon = IconRepresentation.Drawable(drawableId = R.drawable.icon_stamp_fill),
        label = MainStrings.Stamps.asString(),
        contentDescription = MainStrings.Stamps.asString(),
    ),
    About(
        icon = IconRepresentation.Vector(Icons.Outlined.Info),
        selectedIcon = IconRepresentation.Vector(Icons.Filled.Info),
        label = MainStrings.About.asString(),
        contentDescription = MainStrings.About.asString(),
    ),
    Contributor(
        icon = IconRepresentation.Vector(Icons.Outlined.Group),
        selectedIcon = IconRepresentation.Vector(Icons.Filled.Group),
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
    navigationType: NavigationType,
    routeToTab: String.() -> MainScreenTab?,
    onTabSelected: (NavController, MainScreenTab) -> Unit,
    mainNestedNavGraph: NavGraphBuilder.(NavController, PaddingValues) -> Unit,
) {
    val mainNestedNavController = rememberNavController()
    val navBackStackEntry by mainNestedNavController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.destination?.route?.routeToTab()
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NAVIGATION_RAIL) {
            KaigiNavigationRail(
                mainScreenTabs = MainScreenTab.entries.toPersistentList(),
                onTabSelected = { tab ->
                    onTabSelected(mainNestedNavController, tab)
                },
                currentTab = currentTab ?: MainScreenTab.Timetable,
                isEnableStamps = uiState.isStampsEnabled,
            )
        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = navigationType == BOTTOM_NAVIGATION) {
                    KaigiBottomBar(
                        mainScreenTabs = MainScreenTab.entries.toPersistentList(),
                        onTabSelected = { tab ->
                            onTabSelected(mainNestedNavController, tab)
                        },
                        currentTab = currentTab ?: MainScreenTab.Timetable,
                        isEnableStamps = uiState.isStampsEnabled,
                    )
                }
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
