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
                    embedBitcode(BitcodeEmbeddingMode.DISABLE)
                    binaryOption("bundleId", "io.github.droidkaigi.confsched2023.shared")
                    binaryOption("bundleVersion", version.toString())
                    xcf.add(this)
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
                implementation(projects.core.model)
                implementation(libs.kotlinxCoroutinesCore)
            }
        }
    }
}
