plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.gms.google-services") // Apply Google Services plugin
}

android {
    namespace = "com.example.androiddevelopment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androiddevelopment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("com.google.android.gms:play-services-auth:21.1.1")


    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("io.insert-koin:koin-core:3.5.3")

    // Koin for Android
    implementation("io.insert-koin:koin-android:4.0.0")
    implementation("androidx.room:room-ktx:2.6.1")
    // Koin ViewModel support
   // implementation("io.insert-koin:koin-androidx-viewmodel:3.5.3")

    // Coroutines Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // Coroutines for Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    implementation("com.github.bumptech.glide:glide:4.15.0")  // Check for the latest version
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.0")  // F

}