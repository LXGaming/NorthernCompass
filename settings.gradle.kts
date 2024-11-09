pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "architectury"
            url = uri("https://maven.architectury.dev/")
        }
        maven {
            name = "fabricmc"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "minecraftforge"
            url = uri("https://maven.minecraftforge.net/")
        }
        maven {
            name = "neoforged"
            url = uri("https://maven.neoforged.net/releases")
        }
    }
}

listOf(
    "common",
    "fabric",
    "forge",
    "neoforge"
).forEach {
    include(it)
    findProject(":${it}")?.name = "northerncompass-${it}"
}