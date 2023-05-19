package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FilterUiState(val enabled: Boolean, val isChecked: Boolean)
sealed interface SessionListUiState {
    object Empty : SessionListUiState
    data class List(val timetable: Timetable) : SessionListUiState
}

data class SessionScreenUiState(
    val sessionListUiState: SessionListUiState,
    val filterUiState: FilterUiState,
)

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val sessionsStateFlow: StateFlow<Timetable> = sessionsRepository
        .getSessionsStream()
        .handleErrorAndRetry(
            // TODO: Decide how to write strings in ViewModel
            "Retry",
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Timetable()
        )
    private val filtersStateFlow = MutableStateFlow(Filters())

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

    fun onFavoriteClick(session: TimetableItem.Session) {
        viewModelScope.launch {
            sessionsRepository.toggleFavorite(session.id)
        }
    }
}
