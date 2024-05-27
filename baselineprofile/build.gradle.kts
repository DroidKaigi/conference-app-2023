import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(libs.plugins.androidTest)
    alias(libs.plugins.kotlinGradlePlugin)
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = "io.github.droidkaigi.confsched2023.baselineprofile"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        minSdk = 28
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }

    targetProjectPath = ":app-android"

    flavorDimensions += listOf("network")
    productFlavors {
        create("dev") {
            dimension = "network"
            buildConfigField(
                "String",
                "APP_FLAVOR_SUFFIX",
                "\".dev\""
            )
        }
        create("prod") {
            dimension = "network"
            buildConfigField(
                "String",
                "APP_FLAVOR_SUFFIX",
                "\"\""
            )
        }
    }

    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "google"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api34"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidxTestExtJunit)
    implementation(libs.androidxTestEspressoEspressoCore)
    implementation(libs.uiautomator)
    implementation(libs.benchmark.macro.junit4)
}
