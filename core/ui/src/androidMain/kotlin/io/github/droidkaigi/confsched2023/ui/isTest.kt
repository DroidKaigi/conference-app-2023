package io.github.droidkaigi.confsched2023.ui

import android.os.Build

actual fun isTest(): Boolean {
    if ("robolectric" == Build.FINGERPRINT) return true
    return false
}