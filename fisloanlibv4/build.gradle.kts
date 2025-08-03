plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")

    id("kotlinx-serialization")
    // KTLINT - Sugu11
    //id("org.jll  /eitschuh.gradle.ktlint") version "11.6.0"
}

android {
    namespace = "com.github.sugunasriram.fisloanlibv4"
    compileSdk = 34

    defaultConfig {

//        minSdk = 26
        minSdk = 24
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
// //            buildConfigField("Boolean", "FEATURE_X_ENABLED", "true")
//        }
//        create("PROD") {
//            dimension = "version"
//            buildConfigField("String", "BASE_URL", "\"https://ondcfs.jtechnoparks.in/jt-bap\"")
//            buildConfigField("String", "FLAVOR_NAME", "\"PROD\"")
// //            buildConfigField("Boolean", "FEATURE_X_ENABLED", "false")
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
                version = "1.0.7"
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

    val lifecycleVersion = "2.8.4"
    val corektxVersion = "1.13.1"
    val composeVersion = "1.6.8"
    val jetbrainKotlinVersion = "1.9.20"
    val activityComposeVersion = "1.9.1"
    val navComposeVersion = "2.7.7"
    val roomVersion = "2.6.1"
    val junitVersion = "4.13.2"
    val junitTestVersion = "1.2.1"
    val accomPagerVersion = "0.20.0"
    val accomVersion = "0.30.1"
    val ktorVersion = "1.6.4"
    val kotlinSerializationVersion = "1.3.2"
    val dataStoreVersion = "1.1.1"
    val coilVersion = "2.2.2"
    val webkitVersion = "1.11.0"
    val lottieComposeVersion = "4.1.0"
    val firebaseBomVersion = "33.1.2"
    val coroutineAndroidVersion = "1.7.3"
    val gmsPlayVersion = "21.3.0"
    val expressoCoreVersion = "3.6.1"
    val sysUiControllerVersion = "0.27.0"
    val securityCryptoVersion = "1.1.0-alpha06"
    val easyPermissionVersion = "3.0.0"

// Basic Android
    // Basic Android
    implementation("androidx.core:core-ktx:$corektxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$jetbrainKotlinVersion")

    // Compose Core
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material3:material3-android:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.compose.material3:material3-android:1.3.2")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:$navComposeVersion")

    // Live Data
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    // Material Icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // Test Cases
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$expressoCoreVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

    // Compose Helper for : [View Pager and Indicator] [Status bar customize]
    implementation("com.google.accompanist:accompanist-pager:$accomPagerVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accomPagerVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$sysUiControllerVersion")
    implementation("com.google.accompanist:accompanist-insets-ui:$accomVersion")
    implementation("com.google.accompanist:accompanist-permissions:$accomVersion")

    // Google Play Services
    implementation("com.google.android.gms:play-services-location:$gmsPlayVersion")

    // Ktor
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")

    // SSE
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineAndroidVersion")

    // Android Permissions
    implementation("pub.devrel:easypermissions:$easyPermissionVersion")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")
    implementation("androidx.datastore:datastore-core:$dataStoreVersion")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-dynamic-links-ktx")

    // Android Security
    implementation("androidx.security:security-crypto-ktx:$securityCryptoVersion")

    // Coil Image Loader
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")

    // Lottie Animation
    implementation("com.airbnb.android:lottie-compose:$lottieComposeVersion")

    // Webview
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.webkit:webkit:$webkitVersion")

    //phone verification lib
    implementation ("com.googlecode.libphonenumber:libphonenumber:9.0.1")

    implementation("com.google.android.gms:play-services-auth-api-phone:18.1.0")

    //In app auto update
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")

    implementation("com.google.code.gson:gson:2.10.1")

}
