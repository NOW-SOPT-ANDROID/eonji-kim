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

    // jetpack navi
    implementation(libs.bundles.jetpack.navi)

    // google
    implementation(libs.material)

    // ktx (by viewModels)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    // 기초 androidx 라이브러리 ("core-ktx", "constraintlayout", "appcompat", "activity")
    implementation(libs.bundles.androidx)

    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.android.test)

    implementation(libs.inject)

    // dagger hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.glide)
    implementation(libs.coil.core)

    implementation("androidx.recyclerview:recyclerview:1.2.0")
}