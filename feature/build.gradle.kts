plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.sopt.now.feature"
    compileSdk = 34

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
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
    implementation(project(":core-ui"))
    implementation(project(":domain"))

    // Google
    implementation(libs.material)

    // Ktx (by viewModels)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    // 기초 AndroidX ("core-ktx", "constraintlayout", "appcompat", "activity")
    implementation(libs.bundles.androidx)

    // Dagger hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.inject)

    // Third-Party
    implementation(libs.timber)
    implementation(libs.coil.core)
    implementation(libs.glide)
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    // Jetpack navi
    implementation(libs.bundles.jetpack.navi)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.android.test)
}