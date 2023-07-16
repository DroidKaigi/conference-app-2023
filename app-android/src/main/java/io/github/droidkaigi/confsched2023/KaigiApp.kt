package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.droidkaigi.confsched2023.contributors.ContributorsScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsViewModel
import io.github.droidkaigi.confsched2023.contributors.contributorsScreenRoute
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.main.MainNestedGraphStateHolder
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Contributor
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Timetable
import io.github.droidkaigi.confsched2023.main.mainScreen
import io.github.droidkaigi.confsched2023.main.mainScreenRoute
import io.github.droidkaigi.confsched2023.sessions.navigateSearchScreen
import io.github.droidkaigi.confsched2023.sessions.navigateTimetableScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToBookMarkScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToTimetableItemDetailScreen
import io.github.droidkaigi.confsched2023.sessions.nestedSessionScreens
import io.github.droidkaigi.confsched2023.sessions.searchScreen
import io.github.droidkaigi.confsched2023.sessions.sessionScreens
import io.github.droidkaigi.confsched2023.sessions.timetableScreenRoute

@Composable
fun KaigiApp(modifier: Modifier = Modifier) {
    KaigiTheme {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
            onDispose {}
        }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            KaigiNavHost()
        }
    }
}

@Composable
private fun KaigiNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = mainScreenRoute) {
        mainScreen(navController)
        sessionScreens(
            onNavigationIconClick = {
                navController.popBackStack()
            },
        )
        searchScreen(
            onNavigationIconClick = {
                navController.popBackStack()
            },
        )
    }
}

private fun NavGraphBuilder.mainScreen(navController: NavHostController) {
    mainScreen(
        mainNestedGraphStateHolder = KaigiAppMainNestedGraphStateHolder(),
        mainNestedGraph = { mainNestedNavController, padding ->
            nestedSessionScreens(
                onSearchClick = {
                    navController.navigateSearchScreen()
                },
                onTimetableItemClick = { timetableitem ->
                    navController.navigateToTimetableItemDetailScreen(
                        timetableitem.id,
                    )
                },
                onClickBookMarkIcon = {
                    navController.navigateToBookMarkScreen()
                },
            )
            composable(contributorsScreenRoute) {
                ContributorsScreen(hiltViewModel<ContributorsViewModel>())
            }
        },
    )
}

class KaigiAppMainNestedGraphStateHolder : MainNestedGraphStateHolder {
    override val startDestination: String = timetableScreenRoute

    override fun routeToTab(route: String): MainScreenTab? {
        return when (route) {
            timetableScreenRoute -> Timetable
            contributorsScreenRoute -> Contributor
            else -> null
        }
    }

    override fun onTabSelected(
        mainNestedNavController: NavController,
        tab: MainScreenTab,
    ) {
        when (tab) {
            Timetable -> mainNestedNavController.navigateTimetableScreen()
            Contributor -> mainNestedNavController.navigate(contributorsScreenRoute)
        }
    }
}
