import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "io.github.droidkaigi.confsched2023.buildlogic"

repositories {
    google()
    mavenCentral()
}

// If we use jvmToolchain, we need to install JDK 11
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "11"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.bundles.plugins)
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
        register("androidFirebase") {
            id = "droidkaigi.primitive.android.firebase"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidFirebasePlugin"
        }
        register("androidRoborazzi") {
            id = "droidkaigi.primitive.android.roborazzi"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidRoborazziPlugin"
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
        register("kotlinMppCompose") {
            id = "droidkaigi.primitive.kmp.compose"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.KmpComposePlugin"
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
            implementationClass = "io.github.droidkaigi.confsched2023.convention.AndroidFeaturePlugin"
        }
    }
}
