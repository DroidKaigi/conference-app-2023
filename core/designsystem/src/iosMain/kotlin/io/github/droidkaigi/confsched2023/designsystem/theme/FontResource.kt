package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

val fontMap: HashMap<String, FontFamily?> = HashMap()

// https://ishroid.medium.com/custom-font-loading-in-kmp-compose-multiplatform-2eb19865f61b
// FIXME We initially planned to use this implementation on the iOS side.
//However, when shared.swift is output by XCFramework, methods with Compose annotations are not output.
//Therefore, this implementation cannot be referenced from the iOS side.
@ExperimentalResourceApi
@Composable
actual fun fontFamilyResource(fontResource: FontResource): FontFamily? {
    var fontFamily: FontFamily? by
        remember(fontResource.resName) { mutableStateOf(fontMap[fontResource.resName]) }
    if (fontFamily == null) {
        LaunchedEffect(fontResource.resName) {
            fontFamily = try {
                val font = Font(
                    fontResource.resName,
                    resource("font/${fontResource.resName}").readBytes(),
                )
                fontMap[fontResource.resName] = FontFamily(font)
                fontMap[fontResource.resName]
            } catch (e: Exception) {
                throw IllegalArgumentException(e)
            }
        }
    }
    return fontFamily
}
