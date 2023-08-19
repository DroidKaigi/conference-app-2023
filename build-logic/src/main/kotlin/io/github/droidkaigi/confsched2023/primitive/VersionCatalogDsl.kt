package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(name: String): String {
    return findVersion(name).get().requiredVersion
}

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency {
    return findLibrary(name).get().get()
}

internal fun VersionCatalog.plugin(name: String): PluginDependency {
    return findPlugin(name).get().get()
}

internal fun VersionCatalog.bundle(name: String): ExternalModuleDependencyBundle {
    return findBundle(name).get().get()
}
