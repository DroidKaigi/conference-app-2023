import io.github.droidkaigi.confsched2023.primitive.Arch
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties

plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.ios")
}

kotlin {
    val frameworkName = "shared"
    val xcf = XCFramework(frameworkName)

    val activeArch = Arch.findByArch(
        rootProject.layout.projectDirectory.file("local.properties").asFile.takeIf { it.exists() }
            ?.let {
                Properties().apply {
                    load(it.reader(Charsets.UTF_8))
                }.getProperty("arch")
            } ?: System.getenv("arch")
    )

    when (activeArch) {
        Arch.ARM -> listOf(iosSimulatorArm64())
        Arch.X86 -> listOf(iosX64())
        Arch.ALL -> {
            listOf(
                iosArm64(),
                iosX64(),
                iosSimulatorArm64(),
            )
        }
    }.forEach {
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

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
                implementation(libs.kotlinxCoroutinesCore)
            }
        }
    }
}
