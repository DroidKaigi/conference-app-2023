plugins {
    `kotlin-dsl`
}

group = "io.github.droidkaigi.confsched2023.buildlogic"

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.spotlessGradlePlugin)
    implementation(libs.hiltGradlePlugin)
    // https://github.com/google/dagger/issues/3068#issuecomment-1470534930
    implementation(libs.javaPoet)
}

gradlePlugin {
    plugins {
        // Primitives
        register("androidApplication") {
            id = "droidkaigi.primitive.androidapplication"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidApplicationPlugin"
        }
        register("android") {
            id = "droidkaigi.primitive.android"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidPlugin"
        }
        register("androidKotlin") {
            id = "droidkaigi.primitive.android.kotlin"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidKotlinPlugin"
        }
        register("androidCompose") {
            id = "droidkaigi.primitive.android.compose"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "droidkaigi.primitive.android.hilt"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidHiltPlugin"
        }
        register("kotlinMpp") {
            id = "droidkaigi.primitive.kmp"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.KmpPlugin"
        }
        register("kotlinMppIos") {
            id = "droidkaigi.primitive.kmp.ios"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.KmpIosPlugin"
        }
        register("kotlinMppAndroid") {
            id = "droidkaigi.primitive.kmp.android"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.KmpAndroidPlugin"
        }
        register("kotlinMppAndroidHilt") {
            id = "droidkaigi.primitive.kmp.android.hilt"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.KmpAndroidHiltPlugin"
        }
        register("spotless") {
            id = "droidkaigi.primitive.spotless"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.SpotlessPlugin"
        }

        // Conventions
        register("androidFeature") {
            id = "droidkaigi.convention.androidfeature"
            implementationClass = "io.github.droidkaigi.confsched2023.convention.AndroidFeature"
        }
    }
}