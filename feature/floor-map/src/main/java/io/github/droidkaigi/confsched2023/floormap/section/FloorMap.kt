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
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.FloorLevel.Basement
import io.github.droidkaigi.confsched2023.model.FloorLevel.Ground

@Composable
fun FloorMap(
    floorLevel: FloorLevel,
    modifier: Modifier = Modifier,
) {
    val headline = when (floorLevel) {
        Basement -> FloorMapStrings.Basement.asString()
        Ground -> FloorMapStrings.Ground.asString()
    }
    val mapResId = when (floorLevel) {
        Basement -> R.drawable.img_floormap_basement
        Ground -> R.drawable.img_floormap_ground
    }

    FloorMap(
        headline = headline,
        floorMapResId = mapResId,
        modifier = modifier
    )
}

@Composable
fun FloorMap(
    headline: String,
    @DrawableRes floorMapResId: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = headline,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = floorMapResId),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
