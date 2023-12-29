plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("realm-android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.kirdevelopment.testrickmasters"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kirdevelopment.testrickmasters"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeVersion = "1.5.4"

    // basic
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.wear.compose:compose-material:1.2.1")

    // images
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0-rc02")
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation ("com.google.accompanist:accompanist-flowlayout:0.30.1")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")
    implementation ("androidx.compose.foundation:foundation:1.5.4")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // dagger
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}