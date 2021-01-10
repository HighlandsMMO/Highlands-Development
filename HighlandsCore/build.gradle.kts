import kr.entree.spigradle.data.Load
import kr.entree.spigradle.kotlin.*

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.21"
    id("kr.entree.spigradle") version "2.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(kotlin("stdlib"))

    //Exposed
    implementation("org.jetbrains.exposed:exposed-core:${project.properties["exposed_version"]}")
    implementation("org.jetbrains.exposed:exposed-dao:${project.properties["exposed_version"]}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${project.properties["exposed_version"]}")

    //MySQL and SQLite Drivers
    implementation("mysql:mysql-connector-java:5.1.48")
    implementation("org.xerial:sqlite-jdbc:3.30.1")

    //Paper
    compileOnly(paper("1.16.4"))
}

spigot {
    description = "HighlandsCore providing the Api"
    apiVersion = "1.16"
    group = "com.highlands"
    version = "1.0-SNAPSHOT"
    //depends = listOf("Vault")
    load = Load.STARTUP
    debug {
        jvmArgs = listOf("-Xmx16G")
        buildVersion = "1.16.4"
    }
    commands {
        create("hlc") {
            aliases = listOf("hlchelp")
            description = "HLC command"
            permission = "hlc.help"
            permissionMessage = "You do not have permission!"
            usage = "/hlc"
        }
    }
    permissions {
        create("hlc.help") {
            description = "Allows hlc command"
            defaults = "op"
        }
    }
}

tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "com.highlands.highlandscore.core.HighlandsCore"
    }

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}