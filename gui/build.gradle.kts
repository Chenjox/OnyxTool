plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "org.example"
version = "1.2-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":onyxManipulation"))
    implementation(project(":guiObserver"))
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta2")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha5")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")

}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        manifest {
            attributes(mapOf("Main-Class" to "MordantStarterKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

kotlin {
    //explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
