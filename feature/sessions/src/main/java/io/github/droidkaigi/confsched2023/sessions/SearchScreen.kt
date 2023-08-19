package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.sessions.SearchScreenUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.SearchScreenUiState.SearchList
import io.github.droidkaigi.confsched2023.sessions.component.EmptySearchResultBody
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilter
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterUiState
import io.github.droidkaigi.confsched2023.sessions.component.SearchTextFieldAppBar
import io.github.droidkaigi.confsched2023.sessions.section.SearchList
import io.github.droidkaigi.confsched2023.sessions.section.SearchListUiState
import io.github.droidkaigi.confsched2023.sessions.section.SearchQueryUiState

const val searchScreenRoute = "search"
const val SearchScreenTestTag = "SearchScreen"

sealed interface SearchScreenUiState {
    val searchQueryUiState: SearchQueryUiState
    val searchFilterUiState: SearchFilterUiState

    data class Empty(
        override val searchQueryUiState: SearchQueryUiState,
        override val searchFilterUiState: SearchFilterUiState,
    ) : SearchScreenUiState

    data class SearchList(
        override val searchQueryUiState: SearchQueryUiState,
        override val searchFilterUiState: SearchFilterUiState,
        val searchListUiState: SearchListUiState,
    ) : SearchScreenUiState
}

fun NavGraphBuilder.searchScreen(
    onNavigationIconClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
) {
    composable(searchScreenRoute) {
        SearchScreen(
            onBackClick = onNavigationIconClick,
            onTimetableItemClick = onTimetableItemClick,
        )
    }
}

fun NavController.navigateSearchScreen() {
    navigate(searchScreenRoute)
}

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onDaySelected = viewModel::onDaySelected,
        onCategoriesSelected = viewModel::onCategoriesSelected,
        onSessionTypesSelected = viewModel::onSessionTypesSelected,
        onLanguagesSelected = viewModel::onLanguagesSelected,
        onTimetableItemClick = onTimetableItemClick,
        onBookmarkClick = viewModel::onClickTimetableItemBookmark,
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchScreenUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit = { _, _ -> },
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit = { _, _ -> },
    onSessionTypesSelected: (TimetableSessionType, Boolean) -> Unit = { _, _ -> },
    onLanguagesSelected: (Lang, Boolean) -> Unit = { _, _ -> },
    onTimetableItemClick: (TimetableItem) -> Unit = {},
    onBookmarkClick: (TimetableItem) -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = modifier.testTag(SearchScreenTestTag),
        topBar = {
            SearchTextFieldAppBar(
                searchQuery = uiState.searchQueryUiState.queryText,
                onSearchQueryChanged = onSearchQueryChanged,
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
        ) {
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            SearchFilter(
                searchFilterUiState = uiState.searchFilterUiState,
                onDaySelected = onDaySelected,
                onCategoriesSelected = onCategoriesSelected,
                onSessionTypesSelected = onSessionTypesSelected,
                onLanguagesSelected = onLanguagesSelected,
            )
            when (uiState) {
                is Empty -> EmptySearchResultBody(missedQuery = uiState.searchQueryUiState.queryText)
                is SearchList -> SearchList(
                    contentPaddingValues = PaddingValues(
                        bottom = innerPadding.calculateBottomPadding(),
                    ),
                    scrollState = scrollState,
                    searchListUiState = uiState.searchListUiState,
                    searchQueryUiState = uiState.searchQueryUiState,
                    onTimetableItemClick = onTimetableItemClick,
                    onBookmarkIconClick = onBookmarkClick,
                )
            }
        }
    }
}
