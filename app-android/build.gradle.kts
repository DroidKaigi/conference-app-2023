import java.io.FileInputStream
import java.util.Properties

plugins {
    id("droidkaigi.primitive.androidapplication")
    id("droidkaigi.primitive.android.kotlin")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.android.hilt")
    id("droidkaigi.primitive.android.firebase")
    id("droidkaigi.primitive.detekt")
    id("droidkaigi.primitive.android.roborazzi")
    id("droidkaigi.primitive.kover")
}

val keystorePropertiesFile = file("keystore.properties")
val keystoreExits = keystorePropertiesFile.exists()

android {
    namespace = "io.github.droidkaigi.confsched2023"

    flavorDimensions += "network"
    defaultConfig {
        versionCode = 1
        versionName = "0.0.1"
    }
    signingConfigs {
        create("dev") {
            storeFile = project.file("dev.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        if (keystoreExits) {
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
            create("prod") {
                keyAlias = keystoreProperties["keyAlias"] as String?
                keyPassword = keystoreProperties["keyPassword"] as String?
                storeFile = keystoreProperties["storeFile"]?.let { file(it) }
                storePassword = keystoreProperties["storePassword"] as String?
            }
        }
    }
    productFlavors {
        create("dev") {
            signingConfig = signingConfigs.getByName("dev")
            isDefault = true
            applicationIdSuffix = ".dev"
            dimension = "network"
        }
        create("prod") {
            dimension = "network"
            signingConfig = if (keystoreExits) {
                signingConfigs.getByName("prod")
            } else {
                signingConfigs.getByName("dev")
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            signingConfig = null
        }
        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            proguardFiles("benchmark-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.feature.contributors)
    implementation(projects.feature.sessions)
    implementation(projects.feature.about)
    implementation(projects.feature.sponsors)
    implementation(projects.feature.floorMap)
    implementation(projects.feature.stamps)
    implementation(projects.feature.staff)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(libs.composeNavigation)
    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialWindowSize)
    implementation(libs.androidxBrowser)
    implementation(libs.androidxWindow)
    implementation(libs.kermit)
    testImplementation(projects.core.testing)
}

// Dependency configuration to aggregate Kover coverage reports
// TODO: extract report aggregation to build-logic
dependencies {
    kover(projects.feature.about)
    kover(projects.feature.contributors)
    kover(projects.feature.floorMap)
    kover(projects.feature.main)
    kover(projects.feature.sessions)
    kover(projects.feature.sponsors)
    kover(projects.feature.staff)
    kover(projects.feature.stamps)

    kover(projects.core.common)
    kover(projects.core.data)
    kover(projects.core.designsystem)
    kover(projects.core.model)
    kover(projects.core.testing)
    kover(projects.core.ui)
}
