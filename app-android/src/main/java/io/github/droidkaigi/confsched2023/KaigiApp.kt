package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import io.github.droidkaigi.confsched2023.about.aboutScreenRoute
import io.github.droidkaigi.confsched2023.about.navigateAboutScreen
import io.github.droidkaigi.confsched2023.about.nestedAboutScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsViewModel
import io.github.droidkaigi.confsched2023.contributors.contributorsScreenRoute
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.main.MainNestedGraphStateHolder
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import io.github.droidkaigi.confsched2023.main.MainScreenTab.About
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Contributor
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Timetable
import io.github.droidkaigi.confsched2023.main.mainScreen
import io.github.droidkaigi.confsched2023.main.mainScreenRoute
import io.github.droidkaigi.confsched2023.model.AboutItem.Sponsors
import io.github.droidkaigi.confsched2023.sessions.navigateSearchScreen
import io.github.droidkaigi.confsched2023.sessions.navigateTimetableScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToBookmarkScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToTimetableItemDetailScreen
import io.github.droidkaigi.confsched2023.sessions.nestedSessionScreens
import io.github.droidkaigi.confsched2023.sessions.searchScreen
import io.github.droidkaigi.confsched2023.sessions.sessionScreens
import io.github.droidkaigi.confsched2023.sessions.timetableScreenRoute
import io.github.droidkaigi.confsched2023.sponsors.navigateSponsorsScreen
import io.github.droidkaigi.confsched2023.sponsors.sponsorsScreen

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
            onTimetableItemClick = { timetableItem ->
                navController.navigateToTimetableItemDetailScreen(
                    timetableItem.id,
                )
            },
        )
        searchScreen(
            onNavigationIconClick = {
                navController.popBackStack()
            },
        )
        sponsorsScreen(
            onSponsorClick = { sponsor ->
                TODO()
            }
        )
    }
}

private fun NavGraphBuilder.mainScreen(navController: NavHostController) {
    mainScreen(
        mainNestedGraphStateHolder = KaigiAppMainNestedGraphStateHolder(),
        mainNestedGraph = { mainNestedNavController, padding ->
            nestedSessionScreens(
                modifier = Modifier.padding(padding),
                onSearchClick = {
                    navController.navigateSearchScreen()
                },
                onTimetableItemClick = { timetableItem ->
                    navController.navigateToTimetableItemDetailScreen(
                        timetableItem.id,
                    )
                },
                onBookmarkIconClick = {
                    navController.navigateToBookmarkScreen()
                },
            )
            nestedAboutScreen(
                onAboutItemClick = { aboutItem ->
                    when (aboutItem) {
                        Sponsors -> navController.navigateSponsorsScreen()
                    }
                },
            )
            // For KMP, we are not using navigation abstraction for contributors screen
            composable(contributorsScreenRoute) {
                ContributorsScreen(
                    viewModel = hiltViewModel<ContributorsViewModel>(),
                    onNavigationIconClick = {
                        navController.popBackStack()
                    },
                )
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
            aboutScreenRoute -> About
            else -> null
        }
    }

    override fun onTabSelected(
        mainNestedNavController: NavController,
        tab: MainScreenTab,
    ) {
        when (tab) {
            Timetable -> mainNestedNavController.navigateTimetableScreen()
            About -> mainNestedNavController.navigateAboutScreen()
            Contributor -> mainNestedNavController.navigate(contributorsScreenRoute)
            else -> null
        }
    }
}
