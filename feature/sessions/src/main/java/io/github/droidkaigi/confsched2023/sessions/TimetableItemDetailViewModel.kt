package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.strings.TimetableItemDetailStrings
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableItemDetailViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val timetableItemIdFlow = savedStateHandle.getStateFlow<String>(
        timetableItemDetailScreenRouteItemIdParameterName,
        "",
    )
    private val timetableItemStateFlow: StateFlow<Pair<TimetableItem, Boolean>?> =
        timetableItemIdFlow.flatMapLatest { timetableItemId: String ->
            sessionsRepository
                .getTimetableItemWithBookmarkStream(TimetableItemId(timetableItemId))
        }
            .handleErrorAndRetry(
                AppStrings.Retry,
                userMessageStateHolder,
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null,
            )

    val uiState: StateFlow<TimetableItemDetailScreenUiState> =
        buildUiState(timetableItemStateFlow) { timetableItemAndBookmark ->
            if (timetableItemAndBookmark == null) {
                return@buildUiState TimetableItemDetailScreenUiState.Loading
            }
            val (timetableItem, bookmarked) = timetableItemAndBookmark
            TimetableItemDetailScreenUiState.Loaded(
                timetableItem,
                bookmarked,
            )
        }

    fun onBookmarkClick(timetableItem: TimetableItem) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(timetableItem.id)
            val bookmarked = timetableItemStateFlow.value?.second ?: return@launch
            if (bookmarked) {
                userMessageStateHolder.showMessage(
                    TimetableItemDetailStrings.BookmarkedSuccessfully.asString(),
                    TimetableItemDetailStrings.ViewBookmarkList.asString(),
                )
            }
        }
    }
}
