package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.component.LoadingText
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.TimetableItemDetailScreenUiState.Loaded
import io.github.droidkaigi.confsched2023.sessions.TimetableItemDetailScreenUiState.Loading
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailBottomAppBar
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailScreenTopAppBar
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemDetail
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemDetailSectionUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val timetableItemDetailScreenRouteItemIdParameterName = "timetableItemId"
const val timetableItemDetailScreenRoute =
    "timetableItemDetail/{$timetableItemDetailScreenRouteItemIdParameterName}"

fun NavGraphBuilder.sessionScreens(
    onNavigationIconClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onNavigateToBookmarkScreenRequested: () -> Unit,
    onLinkClick: (url: String) -> Unit,
    onRoomClick: () -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    onShareClick: (TimetableItem) -> Unit,
) {
    composable(timetableItemDetailScreenRoute) {
        TimetableItemDetailScreen(
            onNavigationIconClick = onNavigationIconClick,
            onLinkClick = onLinkClick,
            onRoomClick = onRoomClick,
            onCalendarRegistrationClick = onCalendarRegistrationClick,
            onNavigateToBookmarkScreenRequested = onNavigateToBookmarkScreenRequested,
            onShareClick = onShareClick,
        )
    }
    composable(bookmarkScreenRoute) {
        BookmarkScreen(
            onBackPressClick = onNavigationIconClick,
            onTimetableItemClick = onTimetableItemClick,
        )
    }
}

fun NavController.navigateToTimetableItemDetailScreen(
    timetableItem: TimetableItem,
) {
    navigate(
        timetableItemDetailScreenRoute.replace(
            "{$timetableItemDetailScreenRouteItemIdParameterName}",
            timetableItem.id.value,
        ),
    )
}

@Composable
fun TimetableItemDetailScreen(
    onNavigationIconClick: () -> Unit,
    onLinkClick: (url: String) -> Unit,
    onRoomClick: () -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    onNavigateToBookmarkScreenRequested: () -> Unit,
    onShareClick: (TimetableItem) -> Unit,
    viewModel: TimetableItemDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    LaunchedEffect(uiState.shouldNavigateToBookmarkList) {
        if (uiState.shouldNavigateToBookmarkList) {
            onNavigateToBookmarkScreenRequested()
            viewModel.onViewBookmarkListRequestCompleted()
        }
    }

    TimetableItemDetailScreen(
        uiState = uiState,
        onNavigationIconClick = onNavigationIconClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        onLinkClick = onLinkClick,
        onRoomClick = {
            viewModel.navigateTo("floorMap")
            onRoomClick()
        },
        onCalendarRegistrationClick = onCalendarRegistrationClick,
        onShareClick = onShareClick,
        onSelectedLanguage = viewModel::onSelectDescriptionLanguage,
        snackbarHostState = snackbarHostState,
    )
}

sealed class TimetableItemDetailScreenUiState {
    data object Loading : TimetableItemDetailScreenUiState()
    data class Loaded(
        val timetableItem: TimetableItem,
        val timetableItemDetailSectionUiState: TimetableItemDetailSectionUiState,
        val isBookmarked: Boolean,
        val isLangSelectable: Boolean,
        val viewBookmarkListRequestState: ViewBookmarkListRequestState,
        val currentLang: Lang?,
    ) : TimetableItemDetailScreenUiState()

    val shouldNavigateToBookmarkList: Boolean
        get() = this is Loaded && viewBookmarkListRequestState is ViewBookmarkListRequestState.Requested
}

sealed class ViewBookmarkListRequestState {
    data object NotRequested : ViewBookmarkListRequestState()
    data object Requested : ViewBookmarkListRequestState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableItemDetailScreen(
    uiState: TimetableItemDetailScreenUiState,
    onNavigationIconClick: () -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    onShareClick: (TimetableItem) -> Unit,
    onRoomClick: () -> Unit,
    onSelectedLanguage: (Lang) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (uiState is Loaded) {
                TimetableItemDetailScreenTopAppBar(
                    title = uiState.timetableItem.title,
                    isLangSelectable = uiState.isLangSelectable,
                    onNavigationIconClick = onNavigationIconClick,
                    onSelectedLanguage = onSelectedLanguage,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        bottomBar = {
            if (uiState is Loaded) {
                TimetableItemDetailBottomAppBar(
                    timetableItem = uiState.timetableItem,
                    isBookmarked = uiState.isBookmarked,
                    onBookmarkClick = onBookmarkClick,
                    onCalendarRegistrationClick = onCalendarRegistrationClick,
                    onShareClick = onShareClick,
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            contentKey = { uiState is Loaded },
            label = "TimetableItemDetailScreen",
        ) {
            when (it) {
                Loading -> {
                    LoadingText(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is Loaded -> {
                    TimetableItemDetail(
                        modifier = Modifier.fillMaxSize(),
                        uiState = it.timetableItemDetailSectionUiState,
                        selectedLanguage = it.currentLang,
                        onLinkClick = onLinkClick,
                        onRoomClick = onRoomClick,
                        contentPadding = innerPadding,
                    )
                }
            }
        }
    }
}

@Composable
@MultiThemePreviews
@MultiLanguagePreviews
fun TimetableItemDetailScreenPreview() {
    var isBookMarked by remember { mutableStateOf(false) }
    val fakeSession = Session.fake()

    KaigiTheme {
        Surface {
            TimetableItemDetailScreen(
                uiState = Loaded(
                    timetableItem = fakeSession,
                    timetableItemDetailSectionUiState = TimetableItemDetailSectionUiState(
                        fakeSession,
                    ),
                    isBookmarked = isBookMarked,
                    isLangSelectable = true,
                    viewBookmarkListRequestState = ViewBookmarkListRequestState.NotRequested,
                    currentLang = Lang.JAPANESE,
                ),
                onNavigationIconClick = {},
                onBookmarkClick = {
                    isBookMarked = !isBookMarked
                },
                onLinkClick = {},
                onRoomClick = {},
                onCalendarRegistrationClick = {},
                onShareClick = {},
                onSelectedLanguage = {},
                snackbarHostState = SnackbarHostState(),
            )
        }
    }
}
