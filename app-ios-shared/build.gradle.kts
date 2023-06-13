import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.ios")
}

kotlin {
    val frameworkName = "shared"
    val xcf = XCFramework(frameworkName)

    targets.filterIsInstance<KotlinNativeTarget>()
        .forEach {
            it.binaries {
                framework {
                    baseName = frameworkName
                    // compose for iOS(skiko) needs to be static library
                    isStatic = true
                    embedBitcode(BitcodeEmbeddingMode.DISABLE)
                    binaryOption("bundleId", "io.github.droidkaigi.confsched2023.shared")
                    binaryOption("bundleVersion", version.toString())
                    binaryOption("bundleShortVersionString", version.toString())
                    xcf.add(this)

                    export(projects.feature.contributors)
                    export(projects.core.model)
                    export(projects.core.data)
                }
            }
        }

    // workaround https://youtrack.jetbrains.com/issue/KT-55751
    // when apply arch filter, need to use this attribute
    val archAttribute = Attribute.of("arch-filter-attribute", String::class.java)
    configurations.named("releaseFrameworkIosFat").configure {
        attributes {
            attribute(archAttribute, "release-all")
        }
    }

    configurations.named("debugFrameworkIosFat").configure {
        attributes {
            attribute(archAttribute, "debug-all")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.model)
                api(projects.core.data)
                api(projects.feature.contributors)
                implementation(libs.kotlinxCoroutinesCore)
            }
        }
    }
}
