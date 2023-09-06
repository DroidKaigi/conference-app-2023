package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi

val fontMap: HashMap<String, FontFamily?> = HashMap()

// https://ishroid.medium.com/custom-font-loading-in-kmp-compose-multiplatform-2eb19865f61b
@ExperimentalResourceApi
@Composable
actual fun fontFamilyResource(fontResource: FontResource): FontFamily? {
    // FIXME: Load the custom font in resources directory
    return FontFamily.Default
//    var fontFamily: FontFamily? by
//        remember(fontResource.resName) { mutableStateOf(fontMap[fontResource.resName]) }
//    if (fontFamily == null) {
//        LaunchedEffect(fontResource.resName) {
//            fontFamily = try {
//                val font = Font(
//                    fontResource.resName,
//                    resource("font/${fontResource.resName}").readBytes(),
//                )
//                fontMap[fontResource.resName] = FontFamily(font)
//                fontMap[fontResource.resName]
//            } catch (e: Exception) {
//                throw IllegalArgumentException(e)
//            }
//        }
//    }
//    return fontFamily
}
