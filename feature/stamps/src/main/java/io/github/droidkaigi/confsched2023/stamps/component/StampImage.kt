package io.github.droidkaigi.confsched2023.stamps.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Stamp

@Composable
fun StampImage(
    stamp: Stamp,
    onStampClick: (Stamp) -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = stamp.getDrawableResId()),
        contentDescription = stamp.contentDescription,
        modifier = modifier
            .clickable { onStampClick(stamp) }
            .padding(horizontal = 21.dp),
    )
}
