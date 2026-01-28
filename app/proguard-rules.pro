# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
-dontwarn org.slf4j.impl.StaticLoggerBinder

# Keep the LogIn and Token classes and their properties
-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.LogIn { *; }
-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Token { *; }

-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.core.* { *; }
-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.core.*.* { *; }

-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.model.* { *; }
-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.model.*.* { *; }

-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.* { *; }
-keep class com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.*.* { *; }

-keepclassmembers class * extends android.webkit.WebViewClient {
    public *;
}


# Keep all Serializable annotations (optional)
-keepattributes *Annotation*

# PDF support
-dontwarn com.gemalto.jp2.**
# Prevent warnings for optional SSL/TLS providers
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-dontwarn org.slf4j.impl.StaticLoggerBinder

# Keep PDFBox classes
-keep class org.apache.pdfbox.** { *; }
-keep interface org.apache.pdfbox.** { *; }

# Optional: if using commons-logging
-dontwarn org.apache.commons.logging.**

# --- SLF4J (binding is optional in many Android setups) ---
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder
-dontwarn org.slf4j.impl.StaticMarkerBinder
-dontwarn org.slf4j.**


-dontwarn com.github.sugunasriram.fisloanlibv4.LoanLib$LoanDetails
-dontwarn com.github.sugunasriram.fisloanlibv4.LoanLib$SessionDetails
-dontwarn com.github.sugunasriram.fisloanlibv4.LoanLib

