import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.kooper"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.skriptlang.org/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.github.SkriptLang:Skript:2.7.3")

    // Since this is a system dependency, we'll assume it's present
    // at build time. You'll need to adjust paths and manage this
    // dependency appropriately for your build
    compileOnly(files("${System.getProperty("user.home")}/Desktop/Coding/GhostCore/build/libs/GhostCore-1.0-SNAPSHOT-all.jar"))

    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "me.kooper.GhostCoreKt" // Adjust for your main class
    }
}

tasks.create("buildAndCopy", Copy::class) {
    group = "server"
    dependsOn("shadowJar")
    from(tasks.getByName("shadowJar").outputs)
    into("C:\\Users\\riley\\AppData\\Roaming\\.feather\\player-server\\servers\\cb8c898a-5ed9-47ed-996d-b7f378555200\\plugins")
}