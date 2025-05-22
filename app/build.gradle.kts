plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")

    id("com.google.firebase.crashlytics")

    // KTLINT
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

android {
    namespace = "com.example.fisloanone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fisloanone"
        minSdk = 26
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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    implementation(project(":fisloanlibv4")) //Sugu1
//    implementation("com.github.sugunasriram:fisloanlibv4:1.0.1")

    implementation("androidx.activity:activity-compose:1.9.1")
//    implementation("androidx.room:room-ktx:2.6.1")
//    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
//
//    // Compose Navigation
//    implementation("androidx.navigation:navigation-compose:2.7.7")
//
//    // Live Data
//    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
//
//    // Material Icons
//    implementation("androidx.compose.material:material-icons-extended:1.6.8")
//
//    // Test Cases
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
//
//    // Compose Helper for : [View Pager and Indicator] [Status bar customize]
//    implementation("com.google.accompanist:accompanist-pager:0.20.0")
//    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.0")
//    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
//    implementation("com.google.accompanist:accompanist-insets-ui:0.30.1")
//    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
//
//    // Google Play Services
//    implementation("com.google.android.gms:play-services-location:21.3.0")
//
//    // Ktor
//    implementation("io.ktor:ktor-client-android:1.6.4")
//    implementation("io.ktor:ktor-client-serialization:1.6.4")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
//    implementation("io.ktor:ktor-client-logging-jvm:1.6.4")
//
//    //SSE
//    implementation("io.ktor:ktor-client-websockets:1.6.4")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//
//
//    // Android Permissions
//    implementation("pub.devrel:easypermissions:3.0.0")
//
//    // DataStore
//    implementation("androidx.datastore:datastore-preferences:1.1.1")
//    implementation("androidx.datastore:datastore-core:1.1.1")
//
//    // Firebase
//    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics")
//    implementation("com.google.firebase:firebase-dynamic-links-ktx")
//
//    // Android Security
//    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")
//
//    // Coil Image Loader
//    implementation("io.coil-kt:coil-compose:2.2.2")
//    implementation("io.coil-kt:coil-gif:2.2.2")
//    implementation("io.coil-kt:coil-svg:2.2.2")
//
//    // Lottie Animation
//    implementation("com.airbnb.android:lottie-compose:4.1.0")
//
//    //Webview
//    implementation("androidx.compose.material:material:1.6.8")
//    implementation("androidx.compose.runtime:runtime:1.6.8")
//    implementation("androidx.webkit:webkit:1.11.0")
}
