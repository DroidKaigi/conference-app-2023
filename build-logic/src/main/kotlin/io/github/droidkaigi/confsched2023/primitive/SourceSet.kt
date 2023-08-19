package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Project
import java.util.Properties

enum class Arch(val arch: String?) {
    ARM("arm64"),
    X86("x86_64"),
    ALL(null),
    ;

    companion object {
        fun findByArch(arch: String?): Arch {
            return values().firstOrNull { it.arch == arch } ?: ALL
        }
    }
}

val Project.activeArch
    get() = Arch.findByArch(
        rootProject.layout.projectDirectory.file("local.properties").asFile.takeIf { it.exists() }
            ?.let {
                Properties().apply {
                    load(it.reader(Charsets.UTF_8))
                }.getProperty("arch")
            } ?: System.getenv("arch")
    )
