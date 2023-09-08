import java.io.FileInputStream
import java.util.Properties

plugins {
    id("droidkaigi.primitive.androidapplication")
    id("droidkaigi.primitive.android.kotlin")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.android.hilt")
    id("droidkaigi.primitive.android.firebase")
    id("droidkaigi.primitive.android.crashlytics")
    id("droidkaigi.primitive.detekt")
    id("droidkaigi.primitive.android.roborazzi")
    id("droidkaigi.primitive.kover.entrypoint")
    id("droidkaigi.primitive.android.osslicenses")
}

val keystorePropertiesFile = file("keystore.properties")
val keystoreExits = keystorePropertiesFile.exists()

android {
    namespace = "io.github.droidkaigi.confsched2023"

    flavorDimensions += "network"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        versionCode = 5
        versionName = "1.1.0"
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
            buildConfigField(
                type = "String",
                name = "SERVER_URL",
                value = "\"https://ssot-api-staging.an.r.appspot.com/\"",
            )
        }
        create("prod") {
            dimension = "network"
            signingConfig = if (keystoreExits) {
                signingConfigs.getByName("prod")
            } else {
                signingConfigs.getByName("dev")
            }
            buildConfigField(
                type = "String",
                name = "SERVER_URL",
                value = "\"https://ssot-api.droidkaigi.jp/\"",
            )
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
    implementation(projects.feature.achievements)
    implementation(projects.feature.staff)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(libs.composeNavigation)
    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialWindowSize)
    implementation(libs.androidxBrowser)
    implementation(libs.androidxWindow)
    implementation(libs.kermit)
    implementation(libs.firebaseDynamicLinks)
    testImplementation(projects.core.testing)
}
