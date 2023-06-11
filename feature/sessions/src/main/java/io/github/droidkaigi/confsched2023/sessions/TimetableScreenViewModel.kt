package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableListUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TimetableScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val sessionsStateFlow: StateFlow<Timetable> = sessionsRepository
        .getSessionsStream()
        .handleErrorAndRetry(
            AppStrings.Retry,
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Timetable()
        )
    private val filtersStateFlow = MutableStateFlow(Filters())

    private val timetableContentUiState: StateFlow<TimetableContentUiState> = buildUiState(
        sessionsStateFlow,
        filtersStateFlow
    ) { sessionTimetable, filters ->
        if (sessionTimetable.timetableItems.isEmpty()) {
            return@buildUiState TimetableContentUiState.Empty
        }
        TimetableContentUiState.ListTimetable(
            TimetableListUiState(
                timetable = sessionTimetable.filtered(filters)
            )
        )
    }

    val uiState: StateFlow<TimetableScreenUiState> = buildUiState(
        filtersStateFlow,
        timetableContentUiState
    ) { filterUiState, sessionListUiState ->
        TimetableScreenUiState(
            contentUiState = sessionListUiState,
            filterEnabled = sessionListUiState is TimetableContentUiState.ListTimetable,
            filterIsChecked = filterUiState.filterFavorite
        )
    }

    fun onFavoriteFilterClick() {
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
