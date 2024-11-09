val neoForgeVersion: String by project

base {
    archivesName = "NorthernCompass-NeoForge"
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

configurations {
    "developmentNeoForge" {
        extendsFrom(common.get())
    }
}

repositories {
    maven {
        name = "neoforged"
        url = uri("https://maven.neoforged.net/releases")
    }
}

dependencies {
    common(project(path = ":northerncompass-common", configuration = "namedElements")) {
        isTransitive = false
    }
    compileJar(project(path = ":northerncompass-common", configuration = "transformProductionNeoForge")) {
        isTransitive = false
    }
    neoForge("net.neoforged:neoforge:${neoForgeVersion}")
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
            "Implementation-Title" to "NorthernCompass NeoForge"
        )
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from (sourceSets.main.get().resources.srcDirs) {
        include("META-INF/neoforge.mods.toml")
        expand("version" to version)
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