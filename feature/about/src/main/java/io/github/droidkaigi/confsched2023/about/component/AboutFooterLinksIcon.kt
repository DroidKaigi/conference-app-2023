package io.github.droidkaigi.confsched2023.about.component

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .size(48.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutFooterLinksIconDarkModePreview() {
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
