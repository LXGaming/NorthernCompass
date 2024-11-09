import dev.architectury.plugin.ArchitectPluginExtension
import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("java")
    id("signing")
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "8.3.5" apply false
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("net.kyori.blossom") version "2.1.0" apply false
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.9" apply false
}

subprojects {
    apply(plugin = "architectury-plugin")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "org.jetbrains.gradle.plugin.idea-ext")
    apply(plugin = "signing")

    val minecraftVersion: String by project

    group = "io.github.lxgaming"
    version = "${minecraftVersion}-${project.version}"

    configure<ArchitectPluginExtension> {
        addCommonMarker = false
        injectInjectables = false
        minecraft = minecraftVersion
    }

    val loom = the<LoomGradleExtensionAPI>()

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()

        @Suppress("UnstableApiUsage")
        mixin {
            useLegacyMixinAp = true
            defaultRefmapName = "mixins.northerncompass.refmap.json"
        }
    }

    val common: Configuration by configurations.creating
    val compileJar: Configuration by configurations.creating
    val signJar: Configuration by configurations.creating

    configurations {
        compileClasspath {
            extendsFrom(common)
        }

        runtimeClasspath {
            extendsFrom(common)
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:${minecraftVersion}")
        "mappings"(loom.officialMojangMappings())
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.jar {
        manifest {
            attributes(
                "Implementation-Title" to "NorthernCompass",
                "Implementation-Vendor" to "LX_Gaming",
                "Implementation-Version" to project.version.toString(),
                "Specification-Title" to "NorthernCompass",
                "Specification-Vendor" to "LX_Gaming",
                "Specification-Version" to "1"
            )
        }
    }

    tasks.register("signJar") {
        doFirst {
            if (!project.hasProperty("signing.keyStorePath") || !project.hasProperty("signing.secretKeyRingFile")) {
                project.logger.warn("========== [WARNING] ==========")
                project.logger.warn("")
                project.logger.warn("   This build is not signed!   ")
                project.logger.warn("")
                project.logger.warn("========== [WARNING] ==========")
                throw StopExecutionException()
            }
        }

        doLast {
            signJar.allArtifacts.files.forEach {
                ant.withGroovyBuilder {
                    "signjar"(
                        "jar" to it,
                        "alias" to project.property("signing.alias"),
                        "storepass" to project.property("signing.keyStorePassword"),
                        "keystore" to project.property("signing.keyStorePath"),
                        "preservelastmodified" to project.property("signing.preserveLastModified"),
                        "tsaurl" to project.property("signing.timestampAuthority"),
                        "digestalg" to project.property("signing.digestAlgorithm"))
                }
                project.logger.lifecycle("JAR Signed: ${it.name}")

                signing.sign(it)
                project.logger.lifecycle("PGP Signed: ${it.name}")
            }
        }
    }
}

tasks.jar {
    enabled = false
}