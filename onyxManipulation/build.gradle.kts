plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":onyxModel"))
    implementation(project(":guiObserver"))
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.core:jackson-core:2.13.0-rc2") // release candidate
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0-rc2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0-rc2") //

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0-rc2") //
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.13.0-rc2") //
}

kotlin {
    //explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
