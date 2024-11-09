val forgeVersion: String by project
val minecraftVersion: String by project

base {
    archivesName = "NorthernCompass-Forge"
}

architectury {
    platformSetupLoomIde()
    forge()
}

configurations {
    "developmentForge" {
        extendsFrom(common.get())
    }
}

repositories {
    maven {
        name = "minecraftforge"
        url = uri("https://maven.minecraftforge.net/")
    }
}

dependencies {
    common(project(path = ":northerncompass-common", configuration = "namedElements")) {
        isTransitive = false
    }
    compileJar(project(path = ":northerncompass-common", configuration = "transformProductionForge")) {
        isTransitive = false
    }
    forge("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
}

artifacts {
    signJar(tasks.remapJar)
}

tasks.build {
    finalizedBy(tasks.signJar)
}

tasks.compileJava {
    dependsOn(":northerncompass-common:build")
}

tasks.jar {
    enabled = false
    dependsOn(tasks.shadowJar)
    manifest {
        attributes(
            "Implementation-Title" to "NorthernCompass Forge"
        )
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from (sourceSets.main.get().resources.srcDirs) {
        include("META-INF/mods.toml")
        expand("version" to project.version.toString())
    }
}

tasks.remapJar {
    dependsOn(tasks.shadowJar)
    archiveClassifier = ""
    inputFile = tasks.shadowJar.get().archiveFile
}

tasks.shadowJar {
    archiveClassifier = "dev"
    configurations = listOf(project.configurations.compileJar.get())
    destinationDirectory = tasks.jar.get().destinationDirectory
}