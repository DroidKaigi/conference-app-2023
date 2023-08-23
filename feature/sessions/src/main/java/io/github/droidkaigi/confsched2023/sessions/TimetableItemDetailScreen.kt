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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.component.LoadingText
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.TimetableItemDetailScreenUiState.Loaded
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailBottomAppBar
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailScreenTopAppBar
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemDetail
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemDetailSectionUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val timetableItemDetailScreenRouteItemIdParameterName = "timetableItemId"
const val timetableItemDetailScreenRoute =
    "timetableItemDetail/{$timetableItemDetailScreenRouteItemIdParameterName}"

fun NavGraphBuilder.sessionScreens(
    onNavigationIconClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    navigateToBookmarkScreen: () -> Unit,
) {
    composable(timetableItemDetailScreenRoute) {
        TimetableItemDetailScreen(
            onNavigationIconClick = onNavigationIconClick,
            onLinkClick = onLinkClick,
            onCalendarRegistrationClick = onCalendarRegistrationClick,
            navigateToBookmarkScreen = navigateToBookmarkScreen,
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
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    navigateToBookmarkScreen: () -> Unit,
    viewModel: TimetableItemDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    LaunchedEffect(viewModel, lifecycleOwner) {
        viewModel.event
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach { event ->
                when (event) {
                    is TimetableItemDetailEvent.ViewBookmarkList -> {
                        navigateToBookmarkScreen()
                    }
                }
            }
            .launchIn(this)
    }

    TimetableItemDetailScreen(
        uiState = uiState,
        onNavigationIconClick = onNavigationIconClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        onLinkClick = onLinkClick,
        onCalendarRegistrationClick = onCalendarRegistrationClick,
        snackbarHostState = snackbarHostState,
    )
}

sealed class TimetableItemDetailScreenUiState {
    data object Loading : TimetableItemDetailScreenUiState()
    data class Loaded(
        val timetableItem: TimetableItem,
        val timetableItemDetailSectionUiState: TimetableItemDetailSectionUiState,
        val isBookmarked: Boolean,
    ) : TimetableItemDetailScreenUiState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableItemDetailScreen(
    uiState: TimetableItemDetailScreenUiState,
    onNavigationIconClick: () -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
    onCalendarRegistrationClick: (TimetableItem) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (uiState is Loaded) {
                TimetableItemDetailScreenTopAppBar(
                    title = uiState.timetableItem.title,
                    onNavigationIconClick = onNavigationIconClick,
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
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            label = "TimetableItemDetailScreen",
        ) {
            when (it) {
                TimetableItemDetailScreenUiState.Loading -> {
                    LoadingText(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is Loaded -> {
                    TimetableItemDetail(
                        modifier = Modifier.fillMaxSize(),
                        uiState = it.timetableItemDetailSectionUiState,
                        onLinkClick = onLinkClick,
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
                    timetableItemDetailSectionUiState = TimetableItemDetailSectionUiState(fakeSession),
                    isBookmarked = isBookMarked,
                ),
                onNavigationIconClick = {},
                onBookmarkClick = {
                    isBookMarked = !isBookMarked
                },
                onLinkClick = {},
                onCalendarRegistrationClick = {},
                snackbarHostState = SnackbarHostState(),
            )
        }
    }
}
