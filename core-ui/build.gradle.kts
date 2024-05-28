plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.sopt.now.core_ui"
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
    // 기초 AndroidX
    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    // Google
    implementation(libs.material)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.android.test)
}