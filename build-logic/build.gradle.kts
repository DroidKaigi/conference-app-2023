plugins {
    `kotlin-dsl`
}

group = "com.example.project.template.buildlogic"

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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
            implementationClass = "com.example.project.template.primitive.AndroidApplicationPlugin"
        }
        register("android") {
            id = "com.example.primitive.android"
            implementationClass = "com.example.project.template.primitive.AndroidPlugin"
        }
        register("androidCompose") {
            id = "com.example.primitive.android.compose"
            implementationClass = "com.example.project.template.primitive.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "com.example.primitive.android.hilt"
            implementationClass = "com.example.project.template.primitive.AndroidHiltPlugin"
        }
        register("spotless") {
            id = "com.example.primitive.spotless"
            implementationClass = "com.example.project.template.primitive.SpotlessPlugin"
        }

        // Conventions
        register("androidFeature") {
            id = "com.example.convention.androidfeature"
            implementationClass = "com.example.project.template.convention.AndroidFeature"
        }
    }
}