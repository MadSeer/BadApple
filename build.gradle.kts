plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.openpnp:opencv:4.6.0-0")
    implementation("org.apache.commons:commons-math3:3.6.1")
}



tasks.test {
    useJUnitPlatform()
}


kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}