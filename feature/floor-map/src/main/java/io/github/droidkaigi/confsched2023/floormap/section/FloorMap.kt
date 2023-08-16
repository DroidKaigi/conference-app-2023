package io.github.droidkaigi.confsched2023.floormap.section

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.feature.floormap.R
import io.github.droidkaigi.confsched2023.floormap.FloorMapStrings

sealed interface FloorMapUiState {
    val headline: String
    @get:DrawableRes val floorMapResId: Int

    data object Basement : FloorMapUiState {
        override val headline = FloorMapStrings.Basement.asString()
        override val floorMapResId = R.drawable.img_floormap_basement
    }

    data object Ground : FloorMapUiState {
        override val headline = FloorMapStrings.Ground.asString()
        override val floorMapResId = R.drawable.img_floormap_ground
    }
}

@Composable
fun FloorMap(
    uiState: FloorMapUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = uiState.headline,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = uiState.floorMapResId),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
