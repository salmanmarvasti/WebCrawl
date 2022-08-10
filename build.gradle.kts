import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val ktor_version: String = "2.0.3"

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.admin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ( url="https://plugins.gradle.org/m2/" )
    maven ( url="https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    mavenLocal()
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}