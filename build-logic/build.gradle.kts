plugins {
    `kotlin-dsl`
}

group = "io.github.droidkaigi.confsched2023.buildlogic"

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.spotlessGradlePlugin)
    implementation(libs.hiltGradlePlugin)
}

gradlePlugin {
    plugins {
        // Primitives
        register("androidApplication") {
            id = "com.example.primitive.androidapplication"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidApplicationPlugin"
        }
        register("android") {
            id = "com.example.primitive.android"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidPlugin"
        }
        register("androidCompose") {
            id = "com.example.primitive.android.compose"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "com.example.primitive.android.hilt"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.AndroidHiltPlugin"
        }
        register("spotless") {
            id = "com.example.primitive.spotless"
            implementationClass = "io.github.droidkaigi.confsched2023.primitive.SpotlessPlugin"
        }

        // Conventions
        register("androidFeature") {
            id = "com.example.convention.androidfeature"
            implementationClass = "io.github.droidkaigi.confsched2023.convention.AndroidFeature"
        }
    }
}