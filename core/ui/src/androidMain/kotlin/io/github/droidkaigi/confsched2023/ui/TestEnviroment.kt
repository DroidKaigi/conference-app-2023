package io.github.droidkaigi.confsched2023.ui

import android.os.Build

actual fun isTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
}
