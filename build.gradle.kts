plugins {
    java
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.runPaper)
}

group = "de.diruptio"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.diruptio.de/repository/maven-private") {
        credentials {
            username = (System.getenv("DIRUPTIO_REPO_USERNAME") ?: project.findProperty("maven_username") ?: "").toString()
            password = (System.getenv("DIRUPTIO_REPO_PASSWORD") ?: project.findProperty("maven_password") ?: "").toString()
        }
    }
}

dependencies {
    paperweight.devBundle(libs.lodestone)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

val minecraftVersion = libs.versions.lodestone.get().split(".build.")[0]
tasks {
    compileJava {
        options.release = 25
        options.encoding = "UTF-8"
    }

    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to version, "minecraft" to minecraftVersion)
        }
    }

    runServer {
        minecraftVersion(minecraftVersion)
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }
}
