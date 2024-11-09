val fabricApiVersion: String by project
val fabricLoaderVersion: String by project

base {
    archivesName = "NorthernCompass-Fabric"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

configurations {
    "developmentFabric" {
        extendsFrom(configurations.common.get())
    }
}

repositories {
    maven {
        name = "fabricmc"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    common(project(path = ":northerncompass-common", configuration = "namedElements")) {
        isTransitive = false
    }
    compileJar(project(path = ":northerncompass-common", configuration = "transformProductionFabric")) {
        isTransitive = false
    }
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")

    listOf(
        "fabric-api-base"
    ).forEach {
        modImplementation(fabricApi.module(it, fabricApiVersion))
    }
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
            "Implementation-Title" to "NorthernCompass Fabric"
        )
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from (sourceSets.main.get().resources.srcDirs) {
        include("fabric.mod.json")
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