plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.tuan5th2xulymangvagoiapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tuan5th2xulymangvagoiapi"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter (để chuyển đổi JSON sang Kotlin Object)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Coroutines cho Retrofit
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1") // hoặc phiên bản mới nhất
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")   // hoặc phiên bản mới nhất
    // Coroutines LifeCycle KTX
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // hoặc phiên bản mới nhất
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // nếu dùng Compose
    implementation("androidx.activity:activity-compose:1.8.2") // nếu dùng Compose
    implementation("androidx.compose.material3:material3:1.1.2") // nếu dùng Compose
    // Để tải hình ảnh từ URL (imageUrl)
    implementation("io.coil-kt:coil-compose:2.5.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}