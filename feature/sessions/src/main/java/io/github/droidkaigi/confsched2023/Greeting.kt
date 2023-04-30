package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.SessionListUiState.Empty
import io.github.droidkaigi.confsched2023.SessionListUiState.List
import io.github.droidkaigi.confsched2023.model.Filter
import io.github.droidkaigi.confsched2023.model.SessionTimetable
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@Composable
fun Greeting(name: String) {
    val sessionScreenViewModel: SessionScreenViewModel = hiltViewModel<SessionScreenViewModel>()
    val uiState by sessionScreenViewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()
    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = sessionScreenViewModel.userMessageStateHolder
    )
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column {
            when (val listState = uiState.sessionListUiState) {
                Empty -> Text("empty")
                is List -> {
                    listState.sessionTimetable.sessions.forEach { session ->
                        Text(session.id)
                    }
                }
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//    Androidprojecttemplate2022Theme {
//        Greeting("Android")
//    }
// }

// --
data class FilterUiState(val enabled: Boolean, val isChecked: Boolean)
sealed interface SessionListUiState {
    object Empty : SessionListUiState
    data class List(val sessionTimetable: SessionTimetable) : SessionListUiState
}

data class SessionScreenUiState(
    val sessionListUiState: SessionListUiState,
    val filterUiState: FilterUiState,
)

class AppError(e: Throwable) : Exception(e)

// Test code
// class SessionsScreenSnapshotTest {
//    val composeTestRule = composeTestRule()
//    @Inject lateinit var sessionScreenRobot: SessionScreenRobot
//
//    @Test
//    fun favoriteScreenShot() {
//        sessionScreenRobot(composeTestRule) {
//            filterFavorite()
//            capture()
//        }
//    }
// }
//
// class SessionsScreenTest {
//    val composeTestRule = composeTestRule()
//    @Inject lateinit var sessionScreenRobot: SessionScreenRobot
//
//    @Test
//    fun shouldBeFilteredWhenFavoriteFilterIsEnabled() {
//        sessionScreenRobot(composeTestRule) {
//            filterFavorite()
//            isSessionListNotEmpty()
//            areAllSessionsFavorite()
//        }
//    }
// }
//
// // ④ Shared Testing Robot
// class SessionScreenRobot @Inject constructor() {
//    lateinit var composeTestRule: ComposeTestRule
//    fun invoke(composeTestRule: ComposeTestRule, block: SessionScreenRobot.() -> Unit) {
//        this.composeTestRule = composeTestRule
//    }
//
//    fun filterFavorite() {
//        composeTestRule
//            .onNodeWithTestTag("favorite")
//            .click()
//    }
//
//    fun areAllSessionsFavorite() {
//        composeTestRule
//            .onNodeWithText("All sessions are favorite")
//            .assertExists()
//    }
//
//    fun isSessionListNotEmpty() {
//        composeTestRule
//            .onNodeWithText("Session 1")
//            .assertExists()
//    }
//
//    fun capture() {
//        // ③ Capture Robolectric image
//        composeTestRule
//            .onNode(isRoot())
//            .captureRoboImage()
//    }
// }

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val sessionsStateFlow: StateFlow<SessionTimetable> = sessionsRepository
        .getSessionsStream()
        .catch {
            // ② Application wide error handling
            val applicationErrorMessage = it.toApplicationErrorMessage()

            // Shared snackbar message logic
            val messageResult = showMessage(
                message = applicationErrorMessage,
                // TODO: Decide how to write strings in ViewModel
                actionLabel = "Retry"
            )

            // Retry
            if (messageResult == UserMessageResult.ActionPerformed) {
                emitAll(sessionsRepository.getSessionsStream())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SessionTimetable()
        )
    private val filtersStateFlow = MutableStateFlow(Filter(false))

    // ① Single source of truth of UiState
    private val sessionListUiState: StateFlow<SessionListUiState> = buildUiState(
        sessionsStateFlow,
        filtersStateFlow
    ) { sessionTimetable, filters ->
        if (sessionTimetable.sessions.isEmpty()) return@buildUiState SessionListUiState.Empty
        SessionListUiState.List(
            sessionTimetable = sessionTimetable.filtered(filters)
        )
    }

    private val filterUiState: StateFlow<FilterUiState> = buildUiState(
        sessionsStateFlow,
        filtersStateFlow
    ) { sessions, filters ->
        FilterUiState(
            enabled = sessions.sessions.isNotEmpty(),
            isChecked = filters.filterFavorites
        )
    }

    val uiState: StateFlow<SessionScreenUiState> = buildUiState(
        filterUiState,
        sessionListUiState
    ) { filterUiState, sessionListUiState ->
        SessionScreenUiState(
            sessionListUiState = sessionListUiState,
            filterUiState = filterUiState
        )
    }

    fun onFavoriteFilterClicked() {
        filtersStateFlow.value = filtersStateFlow.value.copy(
            filterFavorites = !filtersStateFlow.value.filterFavorites
        )
    }
}

private fun Throwable.toApplicationErrorMessage(): String {
    return message ?: ""
}

fun <T1, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    transform: (T1) -> R
): StateFlow<R> = flow.map(transform = transform)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = transform(
            flow.value
        )
    )

fun <T1, T2, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    flow2: StateFlow<T2>,
    transform: (T1, T2) -> R,
): StateFlow<R> = combine(
    flow = flow,
    flow2 = flow2,
    transform = transform
).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = transform(
        flow.value, flow2.value
    )
)

fun <T1, T2, T3, T4, R> ViewModel.buildUiState(
    flow: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> = combine(
    flow = flow,
    flow2 = flow2,
    flow3 = flow3,
    flow4 = flow4,
    transform = transform
).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = transform(
        flow.value, flow2.value, flow3.value, flow4.value
    )
)

// ① Single source of truth of UiState
// ② Application wide error handling
// ③ Capture Robolectric image(Robolectric Native Graphics)
// ④ Shared Testing Robot

// ⑤ Shared element transition with Jetpack Compose
// ⑥ ????
