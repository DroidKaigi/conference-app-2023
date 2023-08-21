package io.github.droidkaigi.confsched2023.floormap.section

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.floormap.R
import io.github.droidkaigi.confsched2023.model.FloorLevel

enum class FloorMapUiState(
    val floorLevel: FloorLevel,
    val floorName: String,
    @DrawableRes val floorMapResId: Int,
) {
    Basement(FloorLevel.Basement, FloorLevel.Basement.floorName, R.drawable.img_floormap_basement),
    Ground(FloorLevel.Ground, FloorLevel.Ground.floorName, R.drawable.img_floormap_ground),
    ;

    companion object {
        fun of(floorLevel: FloorLevel) = values().find { it.floorLevel == floorLevel }
            ?: throw IllegalStateException("There's no such floorLevel $floorLevel")
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
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@MultiThemePreviews
@Composable
fun BasementFloorMapPreview() {
    KaigiTheme {
        Surface {
            FloorMap(uiState = FloorMapUiState.of(FloorLevel.Basement))
        }
    }
}

@MultiThemePreviews
@Composable
fun GroundFloorMapPreview() {
    KaigiTheme {
        Surface {
            FloorMap(uiState = FloorMapUiState.of(FloorLevel.Ground))
        }
    }
}
