package io.github.droidkaigi.confsched2023

val PACKAGE_NAME = buildString {
    append("io.github.droidkaigi.confsched2023")
    append(if (BuildConfig.FLAVOR == "dev") ".dev" else "")
}
