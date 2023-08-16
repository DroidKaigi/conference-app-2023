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
import io.github.droidkaigi.confsched2023.model.FloorLevel

data class FloorMapUiState(
    private val floorLevel: FloorLevel,
    @DrawableRes val floorMapResId: Int,
) {
    val floorName: String = floorLevel.name

    companion object {
        fun of(floorLevel: FloorLevel): FloorMapUiState {
            return when (floorLevel) {
                FloorLevel.Basement -> FloorMapUiState(
                    floorLevel = floorLevel,
                    floorMapResId = R.drawable.img_floormap_basement,
                )

                FloorLevel.Ground -> FloorMapUiState(
                    floorLevel = floorLevel,
                    floorMapResId = R.drawable.img_floormap_ground,
                )
            }
        }
    }
}

@Composable
fun FloorMap(
    uiState: FloorMapUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = uiState.floorName,
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
