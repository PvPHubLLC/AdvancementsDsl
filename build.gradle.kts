import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "co.pvphub"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.pvphub.me/releases")
    maven("https://nexus.frengor.com/repository/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("com.mattmx:ktgui:-SNAPSHOT")
    implementation("com.frengor:ultimateadvancementapi-shadeable:2.2.2")
}

sourceSets["main"].resources.srcDir("src/resources/")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("advancements-test")
    mergeServiceFiles()
}
tasks {
    build {
        dependsOn(shadowJar)
    }
}