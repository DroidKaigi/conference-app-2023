package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.SessionListUiState.Empty
import io.github.droidkaigi.confsched2023.SessionListUiState.List
import io.github.droidkaigi.confsched2023.designsystem.AppLocalizedStrings
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

@Composable
// TODO: Name screen level Composable function
fun TimetableScreen() {
    val timetableScreenViewModel: TimetableScreenViewModel =
        hiltViewModel<TimetableScreenViewModel>()
    val uiState by timetableScreenViewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()
    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = timetableScreenViewModel.userMessageStateHolder
    )
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            when (val listState = uiState.sessionListUiState) {
                Empty -> Text("empty")
                is List -> {
                    listState.timetable.timetableItems.forEach { session ->
                        Text(session.title.currentLangTitle)
                    }
                }
            }
        }
    }
}

// --
data class FilterUiState(val enabled: Boolean, val isChecked: Boolean)
sealed interface SessionListUiState {
    object Empty : SessionListUiState
    data class List(val timetable: Timetable) : SessionListUiState
}

data class SessionScreenUiState(
    val sessionListUiState: SessionListUiState,
    val filterUiState: FilterUiState,
)

class AppError(e: Throwable) : Exception(e)

// Test code
// class SessionsSc
@HiltViewModel
class TimetableScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val sessionsStateFlow: StateFlow<Timetable> = sessionsRepository
        .getSessionsStream()
        .handleErrorAndRetry(
            // TODO: Decide how to write strings in ViewModel
            AppLocalizedStrings.Retry,
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Timetable()
        )
    private val filtersStateFlow = MutableStateFlow(Filters())

    // ① Single source of truth of UiState
    private val sessionListUiState: StateFlow<SessionListUiState> = buildUiState(
        sessionsStateFlow,
        filtersStateFlow
    ) { sessionTimetable, filters ->
        if (sessionTimetable.timetableItems.isEmpty()) return@buildUiState SessionListUiState.Empty
        SessionListUiState.List(
            timetable = sessionTimetable.filtered(filters)
        )
    }

    private val filterUiState: StateFlow<FilterUiState> = buildUiState(
        sessionsStateFlow,
        filtersStateFlow
    ) { timetableItems, filters ->
        FilterUiState(
            enabled = timetableItems.timetableItems.isNotEmpty(),
            isChecked = filters.filterFavorite
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
            filterFavorite = !filtersStateFlow.value.filterFavorite
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

fun <T> Flow<T>.handleErrorAndRetry(
    actionLabel: AppLocalizedStrings,
    userMessageStateHolder: UserMessageStateHolder,
) = retry { throwable ->
    val messageResult = userMessageStateHolder.showMessage(
        message = throwable.toApplicationErrorMessage(),
        actionLabel = actionLabel.value(),
    )

    val retryPerformed = messageResult == UserMessageResult.ActionPerformed

    retryPerformed
}.catch { /* Do nothing if the user dose not retry. */ }

fun <T> Flow<T>.handleErrorAndRetryAction(
    actionLabel: String,
    userMessageStateHolder: UserMessageStateHolder,
    retryAction: suspend ((Throwable) -> Unit),
) = catch { throwable ->
    val messageResult = userMessageStateHolder.showMessage(
        message = throwable.toApplicationErrorMessage(),
        actionLabel = actionLabel,
    )

    if (messageResult == UserMessageResult.ActionPerformed) {
        retryAction(throwable)
    }
}.catch { /* Do nothing if the user dose not retry. */ }

// ① Single source of truth of UiState
// ② Application wide error handling
// ③ Capture Robolectric image(Robolectric Native Graphics)
// ④ Shared Testing Robot

// ⑤ Shared element transition with Jetpack Compose
// ⑥ ????
