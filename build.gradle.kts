// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
////    id("com.android.application") version "8.7.1" apply false
//    id("com.android.application") version "8.2.2" apply false
//    id("com.google.gms.google-services") version "4.4.2" apply false
//    id ("com.google.firebase.crashlytics") version "3.0.2" apply false

    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" apply false
//    id("com.android.application") version "8.8.2" apply false
    id("com.android.application") version "8.11.1" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false

    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    id("maven-publish") // ✅ needed for publishing
}