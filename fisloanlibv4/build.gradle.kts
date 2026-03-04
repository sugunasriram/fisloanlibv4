plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

val libGroupId = "com.github.sugunasriram"
val libArtifactId = "fisloanlibv4"
val libVersion = "1.0.8"   // bump before each publish

android {
    namespace = "com.github.sugunasriram.fisloanlibv4"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        // targetSdk is optional for library, but keeping aligned is fine
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
        consumerProguardFiles("consumer-rules.pro")
    }

    lint {
        disable += setOf("Typos", "VectorPath", "VectorRaster")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/**/baseline-prof.txt"
        }
    }

    // ✅ Required so "components['release']" exists for publishing on recent AGP
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

// ✅ Maven Publish (AAR)
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = libGroupId
            artifactId = libArtifactId
            version = libVersion
            afterEvaluate { from(components["release"]) }
        }
    }
}

val lifecycleVersion = "2.8.4"
val corektxVersion = "1.13.1"
val composeVersion = "1.7.0"
val activityComposeVersion = "1.9.1"
val navComposeVersion = "2.7.7"
val roomVersion = "2.6.1"
val junitVersion = "4.13.2"
val junitTestVersion = "1.2.1"
val expressoCoreVersion = "3.6.1"

val accomPagerVersion = "0.20.0"
val accomVersion = "0.30.1"
val sysUiControllerVersion = "0.27.0"

val dataStoreVersion = "1.1.1"
val coilVersion = "2.2.2"
val webkitVersion = "1.14.0"
val lottieComposeVersion = "4.1.0"

val firebaseBomVersion = "33.1.2"
val gmsPlayVersion = "21.3.0"
val securityCryptoVersion = "1.1.0-alpha06"
val easyPermissionVersion = "3.0.0"

val ktorVersion = "2.3.12"
val coroutinesVersion = "1.8.1"
val serializationVersion = "1.6.3"

dependencies {

    // Basic Android
    implementation("androidx.core:core-ktx:$corektxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Compose Core
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material3:material3-android:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Compose Navigation + LiveData + Icons
    implementation("androidx.navigation:navigation-compose:$navComposeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // Room (if used inside lib)
    implementation("androidx.room:room-ktx:$roomVersion")

    // Compose Helper
    implementation("com.google.accompanist:accompanist-pager:$accomPagerVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accomPagerVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$sysUiControllerVersion")
    implementation("com.google.accompanist:accompanist-insets-ui:$accomVersion")
    implementation("com.google.accompanist:accompanist-permissions:$accomVersion")

    // Google Play Services
    implementation("com.google.android.gms:play-services-location:$gmsPlayVersion")

    // ✅ Ktor (aligned with app)
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")

    // ✅ Serialization + Coroutines (aligned with app)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")
    implementation("androidx.datastore:datastore-core:$dataStoreVersion")

    // Firebase (only keep if the LIB actually uses it)
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-dynamic-links-ktx")

    // Android Security
    implementation("androidx.security:security-crypto-ktx:$securityCryptoVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:$lottieComposeVersion")

    // Webview
    implementation("androidx.webkit:webkit:$webkitVersion")

    // Permissions
    implementation("pub.devrel:easypermissions:$easyPermissionVersion")

    // Phone verification + updates + gson
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.1")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.1.0")
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Tests
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$expressoCoreVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}
