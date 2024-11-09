plugins {
    id("net.kyori.blossom")
}

val fabricLoaderVersion: String by project
val platforms: String by project

base {
    archivesName = "NorthernCompass-Common"
}

architectury {
    common(platforms.split(","))
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}

tasks.processResources {
    from("../LICENSE")
    rename("LICENSE", "LICENSE-NorthernCompass")
}

tasks.remapJar {
    enabled = false
}