package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.Lang.MIXED
import io.github.droidkaigi.confsched2023.model.MultiLangText
import io.github.droidkaigi.confsched2023.ui.handleOnClickIfNotNavigating
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableItemDetailScreenTopAppBar(
    title: MultiLangText,
    isLangSelectable: Boolean,
    onNavigationIconClick: () -> Unit,
    onSelectedLanguage: (Lang) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LargeTopAppBar(
        title = {
            Box(
                contentAlignment = Alignment.CenterStart,
            ) {
                // If default animation of top app bar is used and Text and ResizeableText are shown/hidden with a flag,
                // the animation behaviour is little weird, so alpha value of composables are calculated independently for smoother animation.
                val textAlpha: Float by remember {
                    derivedStateOf {
                        val alphaBase = 0.65
                        if (scrollBehavior.state.collapsedFraction > alphaBase) {
                            // Calculate an alpha value when collapsedFraction is equal to
                            // ・fractionBase, the alpha value is 0
                            // ・1(headline is completely hidden), the alpha value is 1
                            ((scrollBehavior.state.collapsedFraction - alphaBase) / (1 - alphaBase)).toFloat()
                        } else {
                            0f
                        }
                    }
                }
                Text(
                    text = title.currentLangTitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.alpha(textAlpha),
                )

                val resizedTextAlpha: Float by remember {
                    derivedStateOf {
                        // The alphaBase value is different from Text to create a state
                        // where both the ResizedText and Text are hidden,
                        // so the composables does not overlap each other.
                        val alphaBase = 0.55
                        if (scrollBehavior.state.collapsedFraction < alphaBase) {
                            // Calculate an alpha value when collapsedFraction is equal to
                            // ・fractionBase, the alpha value is 0
                            // ・0(headline is completely shown), the alpha value is 1
                            (1 - (scrollBehavior.state.collapsedFraction / alphaBase)).toFloat()
                        } else {
                            0f
                        }
                    }
                }
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
                    alpha = (resizedTextAlpha),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                handleOnClickIfNotNavigating(lifecycleOwner, onNavigationIconClick)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        actions = {
            var expanded by remember { mutableStateOf(false) }

            val expandMenu = { expanded = true }
            val shrinkMenu = { expanded = false }

            if (isLangSelectable) {
                IconButton(onClick = expandMenu) {
                    Icon(
                        imageVector = Icons.Outlined.GTranslate,
                        contentDescription = null,
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = shrinkMenu,
            ) {
                Lang.entries.forEach { lang ->
                    if (lang != MIXED) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = lang.tagName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            onClick = {
                                onSelectedLanguage(lang)
                                shrinkMenu()
                            },
                            modifier = Modifier.testTag(DropdownFilterChipItemTestTag),
                        )
                    }
                }
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
    alpha: Float,
    styles: ImmutableList<TextStyle>,
    overflow: TextOverflow,
) {
    var styleIndex by remember(text) { mutableIntStateOf(0) }
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
        modifier = Modifier
            .alpha(alpha)
            .padding(end = 16.dp)
            // title heights of LargeTopAppBar will use `TopAppBarLargeTokens.ContainerHeight`, `TopAppBarSmallTokens.ContainerHeight` and `scroll offset`.
            // because of this, this height become taller than our expectation.
            // we want to fix max height, but `ContainerHeight`s are internal values in material3.
            // so set as constant dp. (Large - Small)
            .heightIn(max = 88.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun TimetableItemDetailScreenTopAppBarPreview() {
    TimetableItemDetailScreenTopAppBar(
        title = MultiLangText(jaTitle = "タイトル", enTitle = "title"),
        isLangSelectable = true,
        onNavigationIconClick = {},
        onSelectedLanguage = {},
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@MultiThemePreviews
@Composable
fun TimetableItemDetailScreenTopAppBarUnSelectableLangPreview() {
    TimetableItemDetailScreenTopAppBar(
        title = MultiLangText(jaTitle = "タイトル", enTitle = "title"),
        isLangSelectable = false,
        onNavigationIconClick = {},
        onSelectedLanguage = {},
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    )
}
