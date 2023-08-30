package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.material3.SnackbarDuration.Short
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemDetailSectionUiState
import io.github.droidkaigi.confsched2023.sessions.strings.TimetableItemDetailStrings
import io.github.droidkaigi.confsched2023.ui.UserMessageResult
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val viewBookmarkListRequestStateFlow =
        MutableStateFlow<ViewBookmarkListRequestState>(ViewBookmarkListRequestState.NotRequested)
    private val selectedDescriptionLanguageStateFlow: MutableStateFlow<Lang?> =
        MutableStateFlow(null)

    val uiState: StateFlow<TimetableItemDetailScreenUiState> =
        buildUiState(
            timetableItemStateFlow,
            viewBookmarkListRequestStateFlow,
            selectedDescriptionLanguageStateFlow,
        ) { timetableItemAndBookmark, viewBookmarkListRequestState, selectedLang ->
            if (timetableItemAndBookmark == null) {
                return@buildUiState TimetableItemDetailScreenUiState.Loading
            }
            if (selectedLang == null) {
                onSelectDescriptionLanguage(Lang.valueOf(timetableItemAndBookmark.first.language.langOfSpeaker))
            }
            val (timetableItem, bookmarked) = timetableItemAndBookmark
            TimetableItemDetailScreenUiState.Loaded(
                timetableItem = timetableItem,
                timetableItemDetailSectionUiState = TimetableItemDetailSectionUiState(timetableItem),
                isBookmarked = bookmarked,
                viewBookmarkListRequestState = viewBookmarkListRequestState,
                currentLang = selectedLang,
            )
        }

    fun onBookmarkClick(timetableItem: TimetableItem) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(timetableItem.id)
            val bookmarked = timetableItemStateFlow.value?.second ?: return@launch
            if (bookmarked) {
                val result = userMessageStateHolder.showMessage(
                    message = TimetableItemDetailStrings.BookmarkedSuccessfully.asString(),
                    actionLabel = TimetableItemDetailStrings.ViewBookmarkList.asString(),
                    duration = Short,
                )
                if (result == UserMessageResult.ActionPerformed) {
                    viewBookmarkListRequestStateFlow.update { ViewBookmarkListRequestState.Requested }
                }
            }
        }
    }

    fun onViewBookmarkListRequestCompleted() {
        viewModelScope.launch {
            viewBookmarkListRequestStateFlow.update { ViewBookmarkListRequestState.NotRequested }
        }
    }

    fun onSelectDescriptionLanguage(
        language: Lang,
    ) {
        selectedDescriptionLanguageStateFlow.value = language
    }
}
