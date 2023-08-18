package io.github.droidkaigi.confsched2023.about.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.about.R

@Composable
fun AboutFooterLinksIcon(
    @DrawableRes iconRes: Int,
    testTag: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .testTag(testTag)
            .size(48.dp),
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
        )
    }
}

@MultiThemePreviews
@Composable
fun AboutFooterLinksIconPreview() {
    KaigiTheme {
        Surface {
            AboutFooterLinksIcon(
                iconRes = R.drawable.img_about_medium,
                testTag = "testTag",
                contentDescription = "YouTube",
                onClick = {},
            )
        }
    }
}
