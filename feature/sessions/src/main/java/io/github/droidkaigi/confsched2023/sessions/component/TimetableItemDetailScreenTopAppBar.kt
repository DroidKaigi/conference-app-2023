package io.github.droidkaigi.confsched2023.sessions.component

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.model.MultiLangText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
                    styles = persistentListOf(
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
    styles: ImmutableList<TextStyle>,
    overflow: TextOverflow,
) {
    var styleIndex by remember(text) { mutableStateOf(0) }
    Text(
        text = text,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.hasVisualOverflow) {
                // switch to the next text style
                val nextIndex = styleIndex.inc().coerceAtMost(styles.lastIndex)
                styleIndex = nextIndex
            }
        },
        style = styles[styleIndex],
        modifier = Modifier.padding(end = 16.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableItemDetailScreenTopAppBarPreview() {
    TimetableItemDetailScreenTopAppBar(
        title = MultiLangText(jaTitle = "タイトル", enTitle = "title"),
        onNavigationIconClick = {},
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    )
}
