package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi

enum class FontResource(val resName: String) {
    Montserrat("montserrat_medium.ttf"),
}

// https://ishroid.medium.com/custom-font-loading-in-kmp-compose-multiplatform-2eb19865f61b
@ExperimentalResourceApi
@Composable
public expect fun fontFamilyResource(fontResource: FontResource): MutableState<FontFamily?>
