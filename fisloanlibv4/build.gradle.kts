plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")

    id("kotlinx-serialization")
    //KTLINT
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}


android {
    namespace = "com.github.sugunasriram.fisloanlibv4"
    compileSdk = 34

    defaultConfig {

        minSdk = 26
        targetSdk = 34


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

//    flavorDimensions("version")
//    productFlavors {
//        create("PREPROD") {
//            dimension = "version"
//            buildConfigField("String", "BASE_URL", "\"https://stagingondcfs.jtechnoparks.in/jt-bap\"")
//            buildConfigField("String", "FLAVOR_NAME", "\"PREPROD\"")
////            buildConfigField("Boolean", "FEATURE_X_ENABLED", "true")
//        }
//        create("PROD") {
//            dimension = "version"
//            buildConfigField("String", "BASE_URL", "\"https://ondcfs.jtechnoparks.in/jt-bap\"")
//            buildConfigField("String", "FLAVOR_NAME", "\"PROD\"")
////            buildConfigField("Boolean", "FEATURE_X_ENABLED", "false")
//        }
//    }

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
                groupId = "com.github.sugunasriram"
                artifactId = "fisloanlibv4"
                version = "1.0.0"
            }
        }
    }
}

dependencies {

//    implementation("androidx.core:core-ktx:1.13.1")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
//    implementation("androidx.activity:activity-compose:1.9.1")
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//
//    implementation("androidx.compose.runtime:runtime:1.6.8")

// Basic Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.20")

    // Compose Core
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation("androidx.room:room-ktx:2.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Live Data
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")

    // Material Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    // Test Cases
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")

    // Compose Helper for : [View Pager and Indicator] [Status bar customize]
    implementation("com.google.accompanist:accompanist-pager:0.20.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("com.google.accompanist:accompanist-insets-ui:0.30.1")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")

    // Google Play Services
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Ktor
    implementation("io.ktor:ktor-client-android:1.6.4")
    implementation("io.ktor:ktor-client-serialization:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("io.ktor:ktor-client-logging-jvm:1.6.4")

    //SSE
    implementation("io.ktor:ktor-client-websockets:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // Android Permissions
    implementation("pub.devrel:easypermissions:3.0.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-core:1.1.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-dynamic-links-ktx")

    // Android Security
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")

    // Coil Image Loader
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")

    // Lottie Animation
    implementation("com.airbnb.android:lottie-compose:4.1.0")

    //Webview
    implementation("androidx.compose.material:material:1.6.8")
    implementation("androidx.compose.runtime:runtime:1.6.8")
    implementation("androidx.webkit:webkit:1.11.0")

    //phone verification lib
    implementation ("com.googlecode.libphonenumber:libphonenumber:9.0.1")

    implementation("com.google.android.gms:play-services-auth-api-phone:18.1.0")

    //In app auto update
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
}