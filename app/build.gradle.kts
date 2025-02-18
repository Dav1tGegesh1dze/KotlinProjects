plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.devtools.ksp")

    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
    namespace = "com.example.task23"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.task23"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://reqres.in/api/\"")
            isMinifyEnabled = false
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.production.example.com/\"")
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.leanback.paging)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.glide)
    implementation(libs.androidx.datastore.preferences)
    implementation(kotlin("script-runtime"))
    implementation(libs.androidx.datastore.preferences.rxjava2)
    implementation(libs.glide)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit)
    implementation(libs.okhttp)

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
kapt {
    correctErrorTypes = true
}