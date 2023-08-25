package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import kotlin.math.min

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookmarkTopArea(
    scrollState: LazyListState,
    onBackPressClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bigText = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
    )
    val normalText = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal,
    )

    val fraction by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0) {
                min(scrollState.firstVisibleItemScrollOffset / 520F, 1F)
            } else {
                1F
            }
        }
    }

    val rowNum by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset / 520F < 1F) {
                1
            } else {
                2
            }
        }
    }

    val titleTextStyle = lerp(
        bigText,
        normalText,
        fraction,
    )

    val backgroundColor = lerp(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        fraction,
    )

    val topBarHeight = lerp(
        132.dp,
        72.dp,
        fraction,
    )

    val titlePaddingTop = lerp(
        16.dp,
        0.dp,
        fraction,
    )

    val titlePaddingStart = lerp(
        0.dp,
        16.dp,
        fraction,
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = modifier
            .background(backgroundColor)
            .windowInsetsPadding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
            )
            .height(topBarHeight),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = rowNum,
            modifier = Modifier
                .padding(start = 16.dp, top = 22.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        // Ignore click events when you've started navigating to another screen
                        // https://stackoverflow.com/a/76386604/4339442
                        val currentState = lifecycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBackPressClick()
                        }
                    },
            )
            Text(
                text = SessionsStrings.Bookmark.asString(),
                style = titleTextStyle,
                modifier = Modifier.padding(
                    start = titlePaddingStart,
                    top = titlePaddingTop,
                ),
            )
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun BookmarkTopAreaPreview() {
    KaigiTheme {
        Surface {
            BookmarkTopArea(
                scrollState = rememberLazyListState(),
                onBackPressClick = {},
            )
        }
    }
}
