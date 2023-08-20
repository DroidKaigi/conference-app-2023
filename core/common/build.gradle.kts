plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.spotless")
    id("droidkaigi.primitive.kover")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.common"

kotlin{
    sourceSets {
        commonMain {
            dependencies {
            }
        }
    }
}
dependencies {
}