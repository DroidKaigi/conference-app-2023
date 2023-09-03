package io.github.droidkaigi.confsched2023.ui

import platform.Foundation.NSUUID

actual fun randomUUIDHash(): Int = NSUUID().hash.toInt()
