plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.compose")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.spotless")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.ui"

kotlin{
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:data"))
                implementation(libs.kermit)
                api(project(":core:common"))
                api(libs.composeImageLoader)
            }
        }
    }
}
dependencies {
}