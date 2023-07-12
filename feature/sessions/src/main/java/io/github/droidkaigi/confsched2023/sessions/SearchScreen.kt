package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.sessions.component.EmptySearchResultBody
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilter
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterUiState
import io.github.droidkaigi.confsched2023.sessions.component.SearchTextFieldAppBar

const val searchScreenRoute = "search"
const val SearchScreenTestTag = "SearchScreen"

fun NavGraphBuilder.searchScreens(onNavigationIconClick: () -> Unit) {
    composable(searchScreenRoute) {
        SearchScreen(
            onBackClick = onNavigationIconClick,
        )
    }
}

fun NavController.navigateSearchScreen() {
    navigate(searchScreenRoute)
}

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        searchQuery = uiState.searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        searchFilterUiState = uiState.searchFilterUiState,
        onDaySelected = viewModel::onDaySelected,
        onFilterCategoryChipClicked = viewModel::onFilterCategoryChipClicked,
        onCategoriesSelected = viewModel::onCategoriesSelected,
    )
}

data class SearchScreenUiState(
    val searchQuery: String,
    val searchFilterUiState: SearchFilterUiState,
)

@Composable
private fun SearchScreen(
    searchFilterUiState: SearchFilterUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit = { _, _ -> },
    onFilterCategoryChipClicked: () -> Unit = {},
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit = { _, _ -> },
) {
    Scaffold(
        modifier = modifier.testTag(SearchScreenTestTag),
        topBar = {
            SearchTextFieldAppBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onBackClick = onBackClick,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            SearchFilter(
                searchFilterUiState = searchFilterUiState,
                onDaySelected = onDaySelected,
                onFilterCategoryChipClicked = onFilterCategoryChipClicked,
                onCategoriesSelected = onCategoriesSelected,
            )
            EmptySearchResultBody()
        }
    }
}
