plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.johnrengelman.shadow")
}

group = "me.timschneeberger"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "me.timschneeberger.MainKt"
    }
}