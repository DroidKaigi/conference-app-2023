package io.github.droidkaigi.confsched2023.ui

import java.util.UUID

actual fun randomUUIDHash(): Int = UUID.randomUUID().hashCode()
