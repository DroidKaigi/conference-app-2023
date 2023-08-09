package io.github.droidkaigi.confsched2023.designsystem.strings

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun lang(): String {
    return NSLocale.currentLocale.languageCode
}
