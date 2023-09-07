package io.github.droidkaigi.confsched2023

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import co.touchlab.kermit.Logger
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.github.droidkaigi.confsched2023.about.aboutScreenRoute
import io.github.droidkaigi.confsched2023.about.navigateAboutScreen
import io.github.droidkaigi.confsched2023.about.nestedAboutScreen
import io.github.droidkaigi.confsched2023.achievements.achievementsScreenRoute
import io.github.droidkaigi.confsched2023.achievements.navigateAchievementsScreen
import io.github.droidkaigi.confsched2023.achievements.nestedAchievementsScreen
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
import io.github.droidkaigi.confsched2023.main.MainScreenTab.Achievements
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
import io.github.droidkaigi.confsched2023.model.Lang.JAPANESE
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.defaultLang
import io.github.droidkaigi.confsched2023.sessions.navigateSearchScreen
import io.github.droidkaigi.confsched2023.sessions.navigateTimetableScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToBookmarkScreen
import io.github.droidkaigi.confsched2023.sessions.navigateToTimetableItemDetailScreen
import io.github.droidkaigi.confsched2023.sessions.nestedSessionScreens
import io.github.droidkaigi.confsched2023.sessions.searchScreen
import io.github.droidkaigi.confsched2023.sessions.sessionScreens
import io.github.droidkaigi.confsched2023.sessions.timetableScreenRoute
import io.github.droidkaigi.confsched2023.share.ShareNavigator
import io.github.droidkaigi.confsched2023.sponsors.navigateSponsorsScreen
import io.github.droidkaigi.confsched2023.sponsors.sponsorsScreen
import io.github.droidkaigi.confsched2023.staff.navigateStaffScreen
import io.github.droidkaigi.confsched2023.staff.staffScreen
import io.github.droidkaigi.confsched2023.ui.handleOnClickIfNotNavigating
import kotlinx.collections.immutable.PersistentList

@Composable
fun KaigiApp(
    windowSize: WindowSizeClass,
    displayFeatures: PersistentList<DisplayFeature>,
    modifier: Modifier = Modifier,
) {
    KaigiTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            KaigiNavHost(
                windowSize = windowSize,
                displayFeatures = displayFeatures,
            )
        }
    }
}

@Composable
private fun KaigiNavHost(
    windowSize: WindowSizeClass,
    displayFeatures: PersistentList<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
    externalNavController: ExternalNavController = rememberExternalNavController(),
) {
    NavHostWithSharedAxisX(navController = navController, startDestination = mainScreenRoute) {
        mainScreen(windowSize, displayFeatures, navController, externalNavController)
        sessionScreens(
            onNavigationIconClick = navController::popBackStack,
            onTimetableItemClick = navController::navigateToTimetableItemDetailScreen,
            onNavigateToBookmarkScreenRequested = navController::navigateToBookmarkScreen,
            onLinkClick = externalNavController::navigate,
            onCalendarRegistrationClick = externalNavController::navigateToCalendarRegistration,
            onShareClick = externalNavController::onShareClick,
        )
        searchScreen(
            onNavigationIconClick = navController::popBackStack,
            onTimetableItemClick = navController::navigateToTimetableItemDetailScreen,
        )
        sponsorsScreen(
            onNavigationIconClick = navController::popBackStack,
            onSponsorClick = externalNavController::navigate,
        )
        staffScreen(
            onBackClick = navController::popBackStack,
            onStaffClick = externalNavController::navigate,
        )
        // For KMP, we are not using navigation abstraction for contributors screen
        composable(contributorsScreenRoute) {
            val lifecycleOwner = LocalLifecycleOwner.current
            ContributorsScreen(
                viewModel = hiltViewModel<ContributorsViewModel>(),
                onNavigationIconClick = {
                    handleOnClickIfNotNavigating(
                        lifecycleOwner,
                        navController::popBackStack,
                    )
                },
                onContributorItemClick = externalNavController::navigate,
            )
        }
    }
}

private fun NavGraphBuilder.mainScreen(
    windowSize: WindowSizeClass,
    displayFeatures: PersistentList<DisplayFeature>,
    navController: NavHostController,
    externalNavController: ExternalNavController,
) {
    mainScreen(
        windowSize = windowSize,
        displayFeatures = displayFeatures,
        mainNestedGraphStateHolder = KaigiAppMainNestedGraphStateHolder(),
        mainNestedGraph = { mainNestedNavController, contentPadding ->
            nestedSessionScreens(
                modifier = Modifier,
                onSearchClick = navController::navigateSearchScreen,
                onTimetableItemClick = navController::navigateToTimetableItemDetailScreen,
                onBookmarkIconClick = navController::navigateToBookmarkScreen,
                contentPadding = contentPadding,
            )
            nestedAboutScreen(
                onAboutItemClick = { aboutItem ->
                    val portalBaseUrl = if (defaultLang() == JAPANESE) {
                        "https://portal.droidkaigi.jp"
                    } else {
                        "https://portal.droidkaigi.jp/en"
                    }
                    when (aboutItem) {
                        Sponsors -> navController.navigateSponsorsScreen()
                        CodeOfConduct -> { externalNavController.navigate(url = "$portalBaseUrl/about/code-of-conduct") }
                        Contributors -> navController.navigate(contributorsScreenRoute)
                        License -> externalNavController.navigateToLicenseScreen()
                        Medium -> externalNavController.navigate(url = "https://medium.com/droidkaigi")
                        PrivacyPolicy -> {
                            externalNavController.navigate(url = "$portalBaseUrl/about/privacy")
                        }

                        Staff -> navController.navigateStaffScreen()
                        X -> externalNavController.navigate(url = "https://twitter.com/DroidKaigi")
                        YouTube -> externalNavController.navigate(url = "https://www.youtube.com/c/DroidKaigi")
                    }
                },
                onLinkClick = externalNavController::navigate,
                contentPadding = contentPadding,
            )
            nestedFloorMapScreen(
                windowSize = windowSize,
                onSideEventClick = externalNavController::navigate,
                contentPadding = contentPadding,
            )
            nestedAchievementsScreen(
                contentPadding = contentPadding,
            )
        },
    )
}

class KaigiAppMainNestedGraphStateHolder : MainNestedGraphStateHolder {
    override val startDestination: String = timetableScreenRoute

    override fun routeToTab(route: String): MainScreenTab? {
        return when (route) {
            timetableScreenRoute -> Timetable
            aboutScreenRoute -> About
            floorMapScreenRoute -> FloorMap
            achievementsScreenRoute -> Achievements
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
            Achievements -> mainNestedNavController.navigateAchievementsScreen()
        }
    }
}

@Composable
private fun rememberExternalNavController(): ExternalNavController {
    val context = LocalContext.current
    val shareNavigator = ShareNavigator(context)

    return remember(context) {
        ExternalNavController(
            context = context,
            shareNavigator = shareNavigator,
        )
    }
}

private class ExternalNavController(
    private val context: Context,
    private val shareNavigator: ShareNavigator,
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

    /**
     * Navigate to Calendar Registration
     * @param timeTableItem カレンダー登録に必要なタイムラインアイテムの情報
     */
    fun navigateToCalendarRegistration(timeTableItem: TimetableItem) {
        val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtras(
                bundleOf(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME to timeTableItem.startsAt.toEpochMilliseconds(),
                    CalendarContract.EXTRA_EVENT_END_TIME to timeTableItem.endsAt.toEpochMilliseconds(),
                    CalendarContract.Events.TITLE to "[${timeTableItem.room.name.currentLangTitle}] ${timeTableItem.title.currentLangTitle}",
                    CalendarContract.Events.DESCRIPTION to timeTableItem.url,
                    CalendarContract.Events.EVENT_LOCATION to timeTableItem.room.name.currentLangTitle,
                ),
            )
        }

        runCatching {
            context.startActivity(calendarIntent)
        }.onFailure {
            Logger.e("Fail startActivity in navigateToCalendarRegistration", it)
        }
    }

    fun navigateToLicenseScreen() {
        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
    }

    fun onShareClick(timeTableItem: TimetableItem) {
        shareNavigator.share(
            "[${timeTableItem.room.name.currentLangTitle}] ${timeTableItem.startsTimeString} - ${timeTableItem.endsTimeString}\n" +
                "${timeTableItem.title.currentLangTitle}\n" +
                timeTableItem.url,
        )
    }

    @Suppress("SwallowedException")
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
