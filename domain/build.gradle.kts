plugins {
    id("java-library")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Third-Party
    implementation(libs.kotlin.coroutines)

    // Test
    testImplementation(libs.junit)
}