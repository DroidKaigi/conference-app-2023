package io.github.droidkaigi.confsched2023

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
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
import io.github.droidkaigi.confsched2023.floormap.floorMapScreenRoute
import io.github.droidkaigi.confsched2023.floormap.navigateFloorMapScreen
import io.github.droidkaigi.confsched2023.floormap.nestedFloorMapScreen
import io.github.droidkaigi.confsched2023.main.MainNestedGraphStateHolder
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import io.github.droidkaigi.confsched2023.main.MainScreenTab.About
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Badges
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Contributor
import io.github.droidkaigi.confsched2023.main.MainScreenTab.FloorMap
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Timetable
import io.github.droidkaigi.confsched2023.main.mainScreen
import io.github.droidkaigi.confsched2023.main.mainScreenRoute
import io.github.droidkaigi.confsched2023.model.AboutItem.CodeOfConduct
import io.github.droidkaigi.confsched2023.model.AboutItem.Contributors
import io.github.droidkaigi.confsched2023.model.AboutItem.License
import io.github.droidkaigi.confsched2023.model.AboutItem.Medium
import io.github.droidkaigi.confsched2023.model.AboutItem.PrivacyPolicy
import io.github.droidkaigi.confsched2023.model.AboutItem.Sponsors
import io.github.droidkaigi.confsched2023.model.AboutItem.Staff
import io.github.droidkaigi.confsched2023.model.AboutItem.X
import io.github.droidkaigi.confsched2023.model.AboutItem.YouTube
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
import io.github.droidkaigi.confsched2023.stamps.navigateStampsScreen
import io.github.droidkaigi.confsched2023.stamps.nestedStampsScreen
import io.github.droidkaigi.confsched2023.stamps.stampsScreenRoute

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
    externalNavController: ExternalNavController = rememberExternalNavController(),
) {
    NavHostWithSharedAxisX(navController = navController, startDestination = mainScreenRoute) {
        mainScreen(navController, externalNavController)
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
            onTimetableItemClick = { timetableItem ->
                navController.navigateToTimetableItemDetailScreen(
                    timetableItem.id,
                )
            },
        )
        sponsorsScreen(
            onNavigationIconClick = {
                navController.popBackStack()
            },
            onSponsorClick = { sponsor ->
                TODO()
            },
        )
    }
}

private fun NavGraphBuilder.mainScreen(
    navController: NavHostController,
    externalNavController: ExternalNavController,
) {
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
                        CodeOfConduct -> TODO()
                        Contributors -> TODO()
                        License -> TODO()
                        Medium -> externalNavController.navigate(url = "https://medium.com/droidkaigi")
                        PrivacyPolicy -> TODO()
                        Staff -> TODO()
                        X -> externalNavController.navigate(url = "https://twitter.com/DroidKaigi")
                        YouTube -> externalNavController.navigate(url = "https://www.youtube.com/c/DroidKaigi")
                    }
                },
                onLinkClick = externalNavController::navigate,
            )
            nestedFloorMapScreen(
                onSideEventClick = {
                    TODO()
                },
            )
            nestedStampsScreen(
                onStampsClick = {
                    TODO()
                },
            )
            // For KMP, we are not using navigation abstraction for contributors screen
            composable(contributorsScreenRoute) {
                ContributorsScreen(
                    viewModel = hiltViewModel<ContributorsViewModel>(),
                    onNavigationIconClick = {
                        navController.popBackStack()
                    },
                    onContributorItemClick = externalNavController::navigate,
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
            floorMapScreenRoute -> FloorMap
            stampsScreenRoute -> Badges
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
            FloorMap -> mainNestedNavController.navigateFloorMapScreen()
            Contributor -> mainNestedNavController.navigate(contributorsScreenRoute) {
                launchSingleTop = true
                restoreState = true
            }
            Badges -> mainNestedNavController.navigateStampsScreen()
        }
    }
}

@Composable
private fun rememberExternalNavController(): ExternalNavController {
    val context = LocalContext.current
    return remember(context) {
        ExternalNavController(context = context)
    }
}

private class ExternalNavController(
    private val context: Context,
) {

    fun navigate(url: String) {
        val uri: Uri = url.toUri()
        val launched = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            navigateToNativeAppApi30(context = context, uri = uri)
        } else {
            navigateToNativeApp(context = context, uri = uri)
        }
        if (launched.not()) {
            navigateToCustomTab(context = context, uri = uri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun navigateToNativeAppApi30(context: Context, uri: Uri): Boolean {
        val nativeAppIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER)
        return try {
            context.startActivity(nativeAppIntent)
            true
        } catch (ex: ActivityNotFoundException) {
            false
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun navigateToNativeApp(context: Context, uri: Uri): Boolean {
        val pm = context.packageManager

        // Get all Apps that resolve a generic url
        val browserActivityIntent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.fromParts("http", "", null))
        val genericResolvedList: Set<String> =
            pm.queryIntentActivities(browserActivityIntent, 0)
                .map { it.activityInfo.packageName }
                .toSet()

        // Get all apps that resolve the specific Url
        val specializedActivityIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        val resolvedSpecializedList: MutableSet<String> =
            pm.queryIntentActivities(browserActivityIntent, 0)
                .map { it.activityInfo.packageName }
                .toMutableSet()

        // Keep only the Urls that resolve the specific, but not the generic urls.
        resolvedSpecializedList.removeAll(genericResolvedList)

        // If the list is empty, no native app handlers were found.
        if (resolvedSpecializedList.isEmpty()) {
            return false
        }

        // We found native handlers. Launch the Intent.
        specializedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(specializedActivityIntent)
        return true
    }

    private fun navigateToCustomTab(context: Context, uri: Uri) {
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
            .launchUrl(context, uri)
    }
}
