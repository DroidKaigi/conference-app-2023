package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.MultiLangText

const val RATE_TO_REDUCE_FONT_SIZE = 0.95f
const val LARGE_TOP_APP_BAR_HEIGHT_DP = 124f // maxTopAppBarHeight(152.dp) - bottomPadding(28.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableItemDetailScreenTopAppBar(
    title: MultiLangText,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction >= 0.5f
        }
    }
    LargeTopAppBar(
        title = {
            // TODO: Need some better way to switch these text styles
            if (isCollapsed) {
                Text(
                    text = title.currentLangTitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
            } else {
                ResizeableText(
                    text = title.currentLangTitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    styles = listOf(
                        MaterialTheme.typography.headlineMedium,
                        MaterialTheme.typography.titleLarge,
                        MaterialTheme.typography.titleMedium,
                        MaterialTheme.typography.titleSmall,
                    ),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Composable
private fun ResizeableText(
    text: String,
    maxLines: Int,
    styles: List<TextStyle>,
    overflow: TextOverflow,
    minFontSize: TextUnit = MaterialTheme.typography.labelSmall.fontSize,
) {
    var styleIndex by remember(text) { mutableIntStateOf(0) }
    val density = LocalDensity.current
    var fontSize by remember(text) { mutableFloatStateOf(styles.first().fontSize.value) }

    BoxWithConstraints {
        val calculateParagraph = @Composable {
            Paragraph(
                paragraphIntrinsics = ParagraphIntrinsics(
                    text = text,
                    style = styles[styleIndex].copy(fontSize = fontSize.sp),
                    density = density,
                    fontFamilyResolver = LocalFontFamilyResolver.current,
                ),
                constraints = constraints,
                maxLines = maxLines,
                ellipsis = true,
            )
        }

        // calculate before displaying to avoid broken character.
        var intrinsics = calculateParagraph()
        while (intrinsics.height > LARGE_TOP_APP_BAR_HEIGHT_DP && fontSize > minFontSize.value) {
            if (styleIndex == styles.lastIndex) {
                // if the size does not fit in any style, reduce the font size.
                fontSize *= RATE_TO_REDUCE_FONT_SIZE
            } else {
                // switch to the next text style
                val nextIndex = styleIndex.inc().coerceAtMost(styles.lastIndex)
                styleIndex = nextIndex
                fontSize = styles[nextIndex].fontSize.value
            }
            intrinsics = calculateParagraph()
        }
        Text(
            text = text,
            overflow = overflow,
            maxLines = maxLines,
            style = styles[styleIndex].copy(fontSize = fontSize.sp),
            modifier = Modifier.padding(end = 16.dp),
        )
    }
}
