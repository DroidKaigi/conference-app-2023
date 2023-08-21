package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

val fontMap: HashMap<String, FontFamily?> = HashMap()

// https://ishroid.medium.com/custom-font-loading-in-kmp-compose-multiplatform-2eb19865f61b
@ExperimentalResourceApi
@Composable
actual fun fontFamilyResource(fontResource: FontResource): MutableState<FontFamily?> {
    val state: MutableState<FontFamily?> =
        remember(fontResource.resName) { mutableStateOf(fontMap[fontResource.resName]) }
    if (state.value == null) {
        LaunchedEffect(fontResource.resName) {
            state.value = try {
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
    return state
}
