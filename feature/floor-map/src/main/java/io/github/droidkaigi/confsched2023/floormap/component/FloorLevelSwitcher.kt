package io.github.droidkaigi.confsched2023.floormap.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.FloorLevel

@Composable
fun FloorLevelSwitcher(
    selectingFloorLevel: FloorLevel,
    onClickFloorLevelSwitcher: (FloorLevel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier,
        Arrangement.spacedBy(-1.dp)
    ) {
        FloorLevelSwitcherButton(
            selectingFloorLevel = selectingFloorLevel,
            targetFloorLevel = FloorLevel.Ground,
            shape = RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp),
            onClick = onClickFloorLevelSwitcher
        )
        FloorLevelSwitcherButton(
            selectingFloorLevel = selectingFloorLevel,
            targetFloorLevel = FloorLevel.Basement,
            shape = RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp),
            onClick = onClickFloorLevelSwitcher
        )
    }
}

@Composable
private fun FloorLevelSwitcherButton(
    selectingFloorLevel: FloorLevel,
    targetFloorLevel: FloorLevel,
    shape: RoundedCornerShape,
    onClick: (FloorLevel) -> Unit,
) {
    OutlinedButton(
        shape = shape,
        enabled = targetFloorLevel != selectingFloorLevel,
        onClick = { onClick(targetFloorLevel) },
        contentPadding = PaddingValues(vertical = 10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White, // FIXME The color that can handle the theme switch between light and dark is not defined in the Theme.
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        modifier = Modifier
            .width(104.dp),
    ) {
        if (targetFloorLevel == selectingFloorLevel) Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null, //FIXME Not defined on Figma.
            modifier = Modifier
                .padding(end = 8.dp)
                .size(18.dp)
        )
        Text(
            text = targetFloorLevel.floorName,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
    }
}