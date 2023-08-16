package io.github.droidkaigi.confsched2023.stamps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.feature.stamps.R
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList

const val stampsScreenRoute = "stamps"
fun NavGraphBuilder.nestedStampsScreen(
    onStampsClick: () -> Unit,
) {
    composable(stampsScreenRoute) {
        StampsScreen(
            onStampsClick = onStampsClick,
        )
    }
}

fun NavController.navigateStampsScreen() {
    navigate(stampsScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val StampsScreenTestTag = "StampsScreen"

@Composable
fun StampsScreen(
    onStampsClick: () -> Unit,
    viewModel: StampsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    StampsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onStampsClick = onStampsClick,
    )
}

data class StampsScreenUiState(
    val stamps: ImmutableList<String>,
)

@Composable
private fun StampsScreen(
    uiState: StampsScreenUiState,
    snackbarHostState: SnackbarHostState,
    onStampsClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(StampsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            val layoutDirection = LocalLayoutDirection.current

            LazyColumn(
                Modifier.padding(
                    PaddingValues(
                        top = padding.calculateTopPadding() + 20.dp,
                        start = padding.calculateStartPadding(layoutDirection) + 16.dp,
                        end = padding.calculateEndPadding(layoutDirection) + 16.dp
                    )
                ),
            ) {
                item {
                    StampsDetail()
                }
                item {
                    Stamps(stamps = uiState.stamps)
                }
            }
        },
    )
}

@Composable
private fun StampsDetail(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = StampsStrings.Title.asString(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = StampsStrings.Description.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = StampsStrings.DescriptionNotes.asString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Stamps(
    stamps: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    // TODO: Switching the display of stamps image according stamps variable
    Column(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.img_stamp_a_off),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 21.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.img_stamp_b_off),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 21.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Image(
                painter = painterResource(id = R.drawable.img_stamp_c_off),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 21.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.img_stamp_d_off),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 21.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.img_stamp_e_off),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}
