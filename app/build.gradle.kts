// plugins {
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
//    id("kotlinx-serialization")
//
//    id("com.google.firebase.crashlytics")
//
//    // KTLINT - Sugu11
//    //id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
// }
//
// android {
//    namespace = "com.example.fisloanone"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.fisloanone"
// //        minSdk = 26
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        compose = true
//        buildConfig = true
//    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.3"
//    }
// }
//
// dependencies {
//
//    implementation(project(":fisloanlibv4")) // Sugu1
// //    implementation("com.github.sugunasriram:fisloanlibv4:v1.0.3")
//
//    implementation("androidx.activity:activity-compose:1.9.1")
// //    implementation("androidx.room:room-ktx:2.6.1")
// //    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
// //    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
// //
// //    // Compose Navigation
// //    implementation("androidx.navigation:navigation-compose:2.7.7")
// //
// //    // Live Data
// //    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
// //
// //    // Material Icons
// //    implementation("androidx.compose.material:material-icons-extended:1.6.8")
// //
// //    // Test Cases
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
//
// //
// //    // Compose Helper for : [View Pager and Indicator] [Status bar customize]
// //    implementation("com.google.accompanist:accompanist-pager:0.20.0")
// //    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.0")
// //    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
// //    implementation("com.google.accompanist:accompanist-insets-ui:0.30.1")
// //    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
// //
// //    // Google Play Services
// //    implementation("com.google.android.gms:play-services-location:21.3.0")
// //
// //    // Ktor
// //    implementation("io.ktor:ktor-client-android:1.6.4")
// //    implementation("io.ktor:ktor-client-serialization:1.6.4")
// //    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
// //    implementation("io.ktor:ktor-client-logging-jvm:1.6.4")
// //
// //    //SSE
// //    implementation("io.ktor:ktor-client-websockets:1.6.4")
// //    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
// //    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
// //
// //
// //    // Android Permissions
// //    implementation("pub.devrel:easypermissions:3.0.0")
// //
// //    // DataStore
// //    implementation("androidx.datastore:datastore-preferences:1.1.1")
// //    implementation("androidx.datastore:datastore-core:1.1.1")
// //
// //    // Firebase
// //    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
// //    implementation("com.google.firebase:firebase-analytics-ktx")
// //    implementation("com.google.firebase:firebase-crashlytics")
// //    implementation("com.google.firebase:firebase-dynamic-links-ktx")
// //
// //    // Android Security
// //    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")
// //
// //    // Coil Image Loader
// //    implementation("io.coil-kt:coil-compose:2.2.2")
// //    implementation("io.coil-kt:coil-gif:2.2.2")
// //    implementation("io.coil-kt:coil-svg:2.2.2")
// //
// //    // Lottie Animation
// //    implementation("com.airbnb.android:lottie-compose:4.1.0")
// //
// //    //Webview
// //    implementation("androidx.compose.material:material:1.6.8")
// //    implementation("androidx.compose.runtime:runtime:1.6.8")
// //    implementation("androidx.webkit:webkit:1.11.0")
// }

import com.android.tools.analytics.AnalyticsSettings.disable

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose") // ✅ required since Kotlin 2.0
    id("maven-publish") // ✅ needed for publishing

    // Sonarqube
//    id("org.sonarqube") version "6.0.1.5171"
//    id("org.sonarqube") version "5.0.0.4638"

    // KTLINT
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

android {
    namespace = "com.example.fisloanone"
    compileSdk = 36

//    signingConfigs {
//        create("config") {
//            keyAlias = "key0"
//            keyPassword = "jtpl123"
//            storeFile = file("release/fs-app-key.jks")
//            storePassword = "jtpl123"
//        }
//    }

    defaultConfig {
        applicationId = "com.example.fisloanone"
        minSdk = 26
        targetSdk = 36
        // For V2 Release
//        versionCode = 25
//        versionName = "1.0.25"

        // For V4 Release
        versionCode = 37
        versionName = "2.0.27"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            signingConfig = signingConfigs.getByName("config")
//        }
//        debug {
//            isMinifyEnabled = false
//            isShrinkResources = false
//        }
//    }

    lint {
        lintOptions.disable("Typos", "VectorPath", "VectorRaster")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.getByName("config")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
//             proguardFiles(
//                 getDefaultProguardFile("proguard-android-optimize.txt"),
//                 "proguard-rules.pro"
//             )
        }
    }
    splits {
        abi {
            isEnable = false
        }
    }

    flavorDimensions("version")
    productFlavors {
        create("PREPROD") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://stagingondcfs.jtechnoparks.in/jt-bap\"")
            buildConfigField("String", "FLAVOR_NAME", "\"PREPROD\"")
            buildConfigField(
                "String",
                "CONSENT_CALLBACK_REDIRECT_URL",
                "\"https://stagingondcfs.jtechnoparks.in/jt-bap/api/v1/finvu/consent-callback/\""
            )
//            buildConfigField("Boolean", "FEATURE_X_ENABLED", "true")
        }
        create("PROD") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://ondcfs.jtechnoparks.in/jt-bap\"")
            buildConfigField(
                "String",
                "CONSENT_CALLBACK_REDIRECT_URL",
                "\"https://ondcfs.jtechnoparks.in/jt-bap/api/v1/finvu/consent-callback/\""
            )
            buildConfigField("String", "FLAVOR_NAME", "\"PROD\"")
//            buildConfigField("Boolean", "FEATURE_X_ENABLED", "false")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=/tmp/compose-metrics",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=/tmp/compose-reports"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.3"
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/**/baseline-prof.txt"
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }
}

// Sonarqube
// sonarqube {
//    properties {
//        property("sonar.projectKey", "your-project-key")
//        property("sonar.host.url", "http://localhost:9000") // Replace with your server URL
//        property("sonar.login", "your-sonarqube-token")     // Generate a token in the SonarQube dashboard
//        property("sonar.language", "kotlin")
//        property("sonar.sourceEncoding", "UTF-8")
//        property("sonar.sources", "src/main/kotlin")        // Adjust as needed
//    }
// }

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
val webkitVersion = "1.14.0"
val lottieComposeVersion = "4.1.0"
val firebaseBomVersion = "33.1.2"
val coroutineAndroidVersion = "1.7.3"
val gmsPlayVersion = "21.3.0"
val expressoCoreVersion = "3.6.1"
val sysUiControllerVersion = "0.27.0"
val securityCryptoVersion = "1.1.0-alpha06"
val easyPermissionVersion = "3.0.0"

dependencies {
    implementation(project(":fisloanlibv4")) // Sugu1
//    implementation("com.github.sugunasriram:fisloanlibv4:v1.0.60")

    implementation("org.bouncycastle:bcprov-jdk15to18:1.79")
    implementation("org.bouncycastle:bctls-jdk15to18:1.79")

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
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
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
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineAndroidVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$ktorVersion")

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

    // phone verification lib
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.1")

    implementation("com.google.android.gms:play-services-auth-api-phone:18.1.0")

    // In app auto update
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")

    implementation("com.tom-roush:pdfbox-android:2.0.27.0")
}
