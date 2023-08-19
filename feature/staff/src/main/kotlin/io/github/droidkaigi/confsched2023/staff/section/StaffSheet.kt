package io.github.droidkaigi.confsched2023.staff.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState.Empty
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState.Loading
import io.github.droidkaigi.confsched2023.staff.section.StaffSheetUiState.StaffList
import kotlinx.collections.immutable.ImmutableList

internal sealed interface StaffSheetUiState {
    data object Loading : StaffSheetUiState
    data object Empty : StaffSheetUiState
    data class StaffList(
        val staffs: ImmutableList<Staff>,
    ) : StaffSheetUiState
}

@Composable
internal fun StaffSheet(
    uiState: StaffSheetUiState,
    modifier: Modifier = Modifier,
    onStaffClick: (url: String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            Loading -> LoadingContent(modifier = Modifier.fillMaxSize())
            Empty -> {
                Text(
                    text = "empty",
                    modifier = Modifier.testTag("empty"),
                )
            }

            is StaffList -> StaffList(
                staffs = uiState.staffs,
                onStaffClick = onStaffClick,
            )
        }
    }
}


// FIXME: It might be a good idea to move this Composable function to a common module somewhere. The same applies to TimetableLoadingContent as well.
@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        CircularProgressIndicator()

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Loading...",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

