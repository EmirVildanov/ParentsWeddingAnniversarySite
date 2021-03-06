import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("plugin.serialization") version "1.4.10"
    id("io.gitlab.arturbosch.detekt") version "1.15.0-RC1"
}

group = "com.example"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-websockets:1.3.1")
    implementation("io.ktor:ktor-client-websockets:1.3.1")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
    implementation("io.ktor:ktor-freemarker:1.4.2")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

defaultTasks = mutableListOf("shadowJar")

detekt {
    failFast = true
    buildUponDefaultConfig = true
    input = files("./src/")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

val serverJarLocation = "./build/libs/example-0.0.1-all.jar"

task("stage") {
    dependsOn(":shadowJar")
}

task("checkStage") {
    dependsOn("stage")
    doLast {
        val jarFile = file(serverJarLocation)
        if (!jarFile.exists()) {
            throw GradleException("File $serverJarLocation does not exist")
        }
    }
}