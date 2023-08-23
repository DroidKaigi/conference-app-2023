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
import io.github.droidkaigi.confsched2023.sessions.component.SearchTextFieldAppBar
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilter
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState
import io.github.droidkaigi.confsched2023.sessions.section.SearchList
import io.github.droidkaigi.confsched2023.sessions.section.SearchListUiState
import io.github.droidkaigi.confsched2023.sessions.section.SearchQuery

const val searchScreenRoute = "search"
const val SearchScreenTestTag = "SearchScreen"
const val SearchScreenSearchTextFiledTestTag = "SearchTextFiled"
const val SearchScreenEmptyBodyTestTag = "SearchScreenEmptySearchResultBody"

sealed interface SearchScreenUiState {
    val searchQuery: SearchQuery
    val searchFilterDayUiState: SearchFilterUiState<DroidKaigi2023Day>
    val searchFilterCategoryUiState: SearchFilterUiState<TimetableCategory>
    val searchFilterSessionTypeUiState: SearchFilterUiState<TimetableSessionType>
    val searchFilterLanguageUiState: SearchFilterUiState<Lang>

    data class Empty(
        override val searchQuery: SearchQuery,
        override val searchFilterDayUiState: SearchFilterUiState<DroidKaigi2023Day>,
        override val searchFilterCategoryUiState: SearchFilterUiState<TimetableCategory>,
        override val searchFilterSessionTypeUiState: SearchFilterUiState<TimetableSessionType>,
        override val searchFilterLanguageUiState: SearchFilterUiState<Lang>,
    ) : SearchScreenUiState

    data class SearchList(
        override val searchQuery: SearchQuery,
        override val searchFilterDayUiState: SearchFilterUiState<DroidKaigi2023Day>,
        override val searchFilterCategoryUiState: SearchFilterUiState<TimetableCategory>,
        override val searchFilterSessionTypeUiState: SearchFilterUiState<TimetableSessionType>,
        override val searchFilterLanguageUiState: SearchFilterUiState<Lang>,
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
                searchQuery = uiState.searchQuery.queryText,
                onSearchQueryChanged = onSearchQueryChanged,
                onBackClick = onBackClick,
                testTag = SearchScreenSearchTextFiledTestTag,
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
                searchFilterDayUiState = uiState.searchFilterDayUiState,
                searchFilterCategoryUiState = uiState.searchFilterCategoryUiState,
                searchFilterSessionTypeUiState = uiState.searchFilterSessionTypeUiState,
                searchFilterLanguageUiState = uiState.searchFilterLanguageUiState,
                onDaySelected = onDaySelected,
                onCategoriesSelected = onCategoriesSelected,
                onSessionTypesSelected = onSessionTypesSelected,
                onLanguagesSelected = onLanguagesSelected,
            )
            when (uiState) {
                is Empty -> EmptySearchResultBody(
                    missedQuery = uiState.searchQuery.queryText,
                    modifier = Modifier
                        .testTag(SearchScreenEmptyBodyTestTag),
                )
                is SearchList -> SearchList(
                    contentPaddingValues = PaddingValues(
                        bottom = innerPadding.calculateBottomPadding(),
                    ),
                    scrollState = scrollState,
                    searchListUiState = uiState.searchListUiState,
                    searchQuery = uiState.searchQuery,
                    onTimetableItemClick = onTimetableItemClick,
                    onBookmarkIconClick = onBookmarkClick,
                )
            }
        }
    }
}
