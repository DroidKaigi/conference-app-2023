package io.github.droidkaigi.confsched2023.sessions.component

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.MultiParagraph
import androidx.compose.ui.text.MultiParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.MultiLangText

const val LARGE_TOP_APP_BAR_HEIGHT_DP = 72f
const val RATE_TO_REDUCE_FONT_SIZE = 0.95f

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
                        MaterialTheme.typography.headlineSmall,
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
    var styleIndex by remember(text) { mutableStateOf(0) }
    val density = LocalDensity.current
    var fontSize by remember(text) { mutableFloatStateOf(styles.first().fontSize.value) }

    val calculateMultiParagraph = @Composable {
        MultiParagraph(
            intrinsics = MultiParagraphIntrinsics(
                annotatedString = AnnotatedString(text = text),
                style = styles[styleIndex].copy(fontSize = fontSize.sp),
                placeholders = emptyList(),
                density = density,
                fontFamilyResolver = LocalFontFamilyResolver.current,
            ),
            constraints = Constraints(),
            maxLines = maxLines,
            ellipsis = true,
        )
    }

    var intrinsics = calculateMultiParagraph()
    while (intrinsics.height > LARGE_TOP_APP_BAR_HEIGHT_DP && fontSize > minFontSize.value) {
        if (styleIndex == styles.lastIndex) {
            // change font size
            fontSize *= RATE_TO_REDUCE_FONT_SIZE
        } else {
            // switch to the next text style
            val nextIndex = styleIndex.inc().coerceAtMost(styles.lastIndex)
            styleIndex = nextIndex
            fontSize = styles[nextIndex].fontSize.value
        }
        intrinsics = calculateMultiParagraph()
    }
    Text(
        text = text,
        fontSize = fontSize.sp,
        overflow = overflow,
        maxLines = maxLines,
        style = styles[styleIndex],
    )
}