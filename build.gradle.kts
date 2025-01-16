plugins {
    kotlin("jvm") version "2.0.21"
}

group = "dev.moyar.beenroaster"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation("com.google.code.gson:gson:2.9.0")

    val koinVersion = "4.0.1"
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:$koinVersion"))
    implementation("io.insert-koin:koin-core")

    implementation("org.json:json:20090211")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}