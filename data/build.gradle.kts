plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.sopt.now.data"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        targetSdk = 34
        minSdk = 28
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))

    // Json
    implementation(libs.kotlinx.serialization.json)

    // Third-Party
    implementation(libs.retrofit2)
    implementation(libs.retrofit.kotlin.serialization.converter)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Dagger hilt
    implementation(libs.inject)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.android.test)
}