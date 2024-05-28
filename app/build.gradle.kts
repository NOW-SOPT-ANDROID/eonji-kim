import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.sopt.now"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sopt.now"
        minSdk= 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "AUTH_BASE_URL", getApiKey("base.auth.url"))
        buildConfigField("String", "USER_BASE_URL", getApiKey("base.user.url"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":feature"))
    implementation(project(":core-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":data-local"))
    implementation(project(":data-remote"))

    // 기초 AndroidX 라이브러리 ("core-ktx", "constraintlayout", "appcompat", "activity")
    implementation(libs.bundles.androidx)
    implementation(libs.security.crypto)

    // Json
    implementation(libs.kotlinx.serialization.json)

    // Third Party
    implementation(libs.retrofit2)
    implementation(libs.retrofit.kotlin.serialization.converter)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.coil.core)
    implementation(libs.timber)

    // Dagger hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.android.test)
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}