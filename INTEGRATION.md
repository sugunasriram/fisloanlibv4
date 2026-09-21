# FISLib-V4 — Android Integration Guide

**Library:** `com.github.sugunasriram:fisloanlibv4`
**Version:** `1.0.8`
**Distribution:** JitPack
**Audience:** Third-party Android application developers

---

## 1. Overview

FISLib-V4 is a self-contained Android library that provides end-to-end loan origination journeys for consumer-facing fintech applications. It ships a complete Jetpack Compose UI, networking layer, KYC / Account Aggregator integration, and lender orchestration.

The library supports three product journeys:

- **Personal Loan (PL)** — Account Aggregator based unsecured personal lending.
- **GST Invoice Loan (GL)** — GST-authenticated SME/MSME lending.
- **Purchase Finance (PF)** — Point-of-sale merchant-integrated financing with configurable down payment.

Integration is single-call: the host app hands a session identifier to `LoanLib`, the library runs the entire journey, and returns final loan terms via a lambda callback.

---

## 2. Architecture

### 2.1 High-Level Component View

```
+---------------------------------------------------------------+
|                        Host Application                       |
|                                                               |
|   MainActivity  ---(SessionDetails)--->  LoanLib entry point  |
|          ^                                     |              |
|          |                                     v              |
|          |         +-------------------------------------+    |
|          |         |          FISLib-V4 (library)        |    |
|          |         |                                     |    |
|          |         |   Compose UI (views/)               |    |
|          |         |   Navigation graph (navigation/)    |    |
|          |         |   ViewModels (viewModel/)           |    |
|          |         |   Network layer — Ktor (network/)   |    |
|          |         |   SSE + WebSocket real-time updates |    |
|          |         |   TokenManager (DataStore)          |    |
|          |         |   AppBridge / callbacks             |    |
|          |         +-------------------------------------+    |
|          |                          |                         |
|          +----- LoanDetails <-------+                         |
+---------------------------------------------------------------+
                              |
                              v
                +----------------------------+
                |   FIS Backend (BAP)        |
                |   REST /api/v1/*           |
                |   SSE stream               |
                |   Consent / KYC redirects  |
                +----------------------------+
```

### 2.2 Architectural Style

- **UI:** Jetpack Compose + Material3, Navigation-Compose.
- **State:** MVVM / MVI with per-feature `ViewModel`s.
- **Networking:** Ktor Client (Android engine), kotlinx.serialization, JWT bearer authentication.
- **Persistence:** DataStore preferences via `TokenManager`.
- **Real-time:** Server-Sent Events (SSE) for status transitions during KYC and disbursement.
- **Host bridge:** Static callback registered on `LoanLib` and invoked at journey completion.

---

## 3. Prerequisites

| Item                 | Requirement                                         |
|----------------------|-----------------------------------------------------|
| Android Gradle Plugin| 8.12.x or newer                                     |
| Kotlin               | 2.1.20                                              |
| Java toolchain       | 17                                                  |
| `compileSdk`         | 36                                                  |
| `minSdk`             | 24                                                  |
| `targetSdk`          | 36                                                  |
| Jetpack Compose      | 1.7.x                                               |
| Backend session      | A valid `sessionId` issued by the FIS backend       |

---

## 4. Adding the Library

### 4.1 Repository

In `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

### 4.2 Module dependency

In the host application module's `build.gradle.kts`:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 36
    defaultConfig {
        minSdk = 24
        targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
}

dependencies {
    implementation("com.github.sugunasriram:fisloanlibv4:1.0.8")
}
```

### 4.3 Firebase configuration

The library uses Firebase Analytics, Crashlytics, and Dynamic Links. The host must supply its own `google-services.json` at the application module root.

---

## 5. Public API

Package: `com.github.sugunasriram.fisloanlibv4`

### 5.1 Entry-point object — `LoanLib`

```kotlin
object LoanLib {

    // Primary entry: launches full loan journey and returns final terms.
    fun LaunchFISAppWithParamsAndCallback(
        context: Context,
        sessionDetails: SessionDetails?,
        callback: (LoanDetails) -> Unit
    )

    // Variant to retrieve loan details for an existing loan.
    fun LaunchFISAppForLoanDetails(
        context: Context,
        sessionDetails: SessionDetails?,
        callback: (LoanDetails) -> Unit
    )

    // Fire-and-forget launch (no result callback).
    fun LaunchFISApp(context: Context)
}
```

### 5.2 Input model — `SessionDetails`

```kotlin
data class SessionDetails(
    val sessionId: String,   // UUID issued by host backend
    val loanId: String       // "" for new applications; existing id to resume/inspect
)
```

### 5.3 Output model — `LoanDetails`

Returned via the `callback` lambda on successful journey completion.

```kotlin
data class LoanDetails(
    val sessionId: String,        // Echo of the input session id
    val loanAmount: Double,       // Sanctioned principal
    val interestRate: Double,     // Annualised rate (%)
    val tenure: Int,              // Tenure in months
    val downpaymentAmount: Int    // Down payment collected (Purchase Finance)
)
```

### 5.4 Additional models (Purchase Finance)

`ProductDetails` and `PersonalDetails` are used by the Purchase Finance journey when the backend session already carries product metadata (SKU, category, merchant PAN/GST, bank details, price, down payment). Third parties do not construct these directly; they are populated from the verified session response.

---

## 6. Integration Snippet

```kotlin
class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoanLib.LaunchFISAppWithParamsAndCallback(
            context = this,
            sessionDetails = LoanLib.SessionDetails(
                sessionId = "ee31146f-b654-512d-bca0-7879df316fec",
                loanId    = ""
            )
        ) { loanDetails ->
            Log.i("FIS", "Loan sanctioned: " +
                    "amount=${loanDetails.loanAmount}, " +
                    "rate=${loanDetails.interestRate}%, " +
                    "tenure=${loanDetails.tenure} months, " +
                    "downpayment=${loanDetails.downpaymentAmount}")
        }
    }
}
```

When the library completes (successful disbursement or user exit confirmation), the callback fires and the library's activity finishes, returning the user to the host.

---

## 7. Session and Authentication Model

1. The host backend calls the FIS platform to allocate a session and returns a `sessionId` (UUID) to the mobile client.
2. The mobile client passes `SessionDetails(sessionId, loanId)` to `LoanLib`.
3. The library calls `POST /api/v1/superAppSessions/verifySession` to exchange the session for:
   - `accessToken` (JWT — attached to all subsequent calls as `Authorization: Bearer <token>`)
   - `refreshToken`
   - `sseId` (Server-Sent Events channel)
   - `securityKey`
   - `sessionData` (product / merchant context for Purchase Finance)
4. Token lifecycle is managed internally by `TokenManager` (DataStore-backed).

The host application does not need to manage tokens or refresh cycles.

---

## 8. Call Flow

### 8.1 High-level sequence

```
Host App           FISLib (Compose UI)         FIS Backend
   |                     |                          |
   |-- LaunchFISApp ---->|                          |
   |  (sessionId)        |-- verifySession -------->|
   |                     |<--- accessToken, sse ----|
   |                     |                          |
   |                     |-- SignIn / OTP --------->|
   |                     |<--- authenticated -------|
   |                     |                          |
   |                     |-- Journey APIs --------->|
   |                     |   (AA / GST / PF)        |
   |                     |<--- SSE status stream ---|
   |                     |                          |
   |                     |-- KYC WebView ---------->|
   |                     |<--- consent callback ----|
   |                     |                          |
   |                     |-- Disbursement --------->|
   |                     |<--- final loan terms ----|
   |                     |                          |
   |<--- LoanDetails ----|                          |
   |    (callback)       |                          |
```

### 8.2 Personal Loan journey

1. Splash → SignIn → OTP → Update Profile
2. Category selection → Personal Loan → Basic Details
3. Share Bank Statement → Select Account Aggregator → AA linking → Consent approval
4. Bureau Offers → Loan Offers → Review Details
5. Loan Process (KYC WebView) → Bank KYC Verification
6. Loan Disbursement *(callback fires)*
7. Optional: Loan Agreement, Repayment Schedule

### 8.3 GST Invoice Loan journey

1. Category selection → GST Invoice Loan
2. GST Details → GSTIN verification → GST Information
3. Invoice details → Invoice loans list
4. Loan Offer selection → GST KYC WebView
5. Completion *(callback fires)*

### 8.4 Purchase Finance journey

1. Down Payment (product cart, DP entry from session's product context)
2. PF Loan Offers → Offer selection
3. PF KYC WebView → PF Bank KYC Verification
4. PF Loan Status *(callback fires)*

### 8.5 Exit and error surfaces

The library renders its own error and exit UIs — `FISExitConfirmationScreen`, `FormRejectionScreen`, `KycFailedScreen`, `EMandateESignFailedScreen`, `UnexpectedErrorScreen`, `UnAuthorizedScreen`, `RequestTimeOutScreen`, `NegativeCommonScreen`. Control returns to the host when the library's activity finishes.

---

## 9. Backend Endpoints Consumed

Base URL: `https://ondcfs.jtechnoparks.in/jt-bap`
API prefix: `/api/v1/`

Representative endpoints (managed internally by the library):

| Area           | Endpoint                                            |
|----------------|-----------------------------------------------------|
| Session        | `superAppSessions/verifySession`, `createSession`   |
| Authentication | `auth/login`, `auth/signUp`, `auth/generateOtp`, `auth/authOtp` |
| User           | `users/profile`, `users/updateUserDetails`, `users/getUserstatus` |
| Loans          | `loans/getCustomerLoanList`, `loans/ordersList`     |
| Lender         | `lender/search`, `lender/formSubmissionRequest`, `lender/update_loan_agreement`, `lender/add_account_details` |
| GST            | `cygnet/cygnetGenerateOtp`, `cygnet/gstinDetails`   |
| Grievance      | `issues/createIssue`, `issues/issueList`, `issues/issue_status` |
| Streaming      | `sse` (Server-Sent Events)                          |
| Consent        | `finvu/consent-callback` (redirect target)          |

The host does not need to call these directly.

---

## 10. Permissions

The library declares the following in its manifest; they are merged into the host application automatically:

| Permission                                    | Purpose                                    |
|-----------------------------------------------|--------------------------------------------|
| `INTERNET`                                    | API and SSE                                |
| `ACCESS_NETWORK_STATE`                        | Connectivity checks                        |
| `CAMERA`                                      | KYC photo / video                          |
| `RECORD_AUDIO`                                | Video KYC                                  |
| `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION` | Purchase Finance geolocation           |
| `POST_NOTIFICATIONS`                          | Android 13+ status notifications           |
| `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE` | Document download (API ≤ 32)           |

Runtime permission prompts are managed by the library via `EasyPermissions`. The host does not need to request these separately.

### FileProvider

The library declares a `FileProvider` under `${applicationId}.provider`. The host must include an `xml/file_paths.xml` resource (a standard file-paths declaration) so that document downloads and camera capture work in release builds.

---

## 11. ProGuard / R8

The library ships consumer rules that keep the public API intact:

```proguard
-keep class com.github.sugunasriram.fisloanlibv4.LoanLib { *; }
-keep class com.github.sugunasriram.fisloanlibv4.LoanLib$** { *; }
-keepattributes Signature,InnerClasses,EnclosingMethod
```

For release builds, the following complementary rules are recommended in the host's `proguard-rules.pro`:

```proguard
-keep class io.ktor.** { *; }
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class **$$serializer { *; }
-keep class com.google.firebase.** { *; }
```

---

## 12. Dependencies

The library brings its own transitive dependencies; the host generally does not need to declare them explicitly.

**UI / Compose**
- `androidx.compose.ui:ui:1.7.0`
- `androidx.compose.material:material:1.7.0`
- `androidx.compose.material3:material3-android:1.3.2`
- `androidx.navigation:navigation-compose:2.7.7`
- `androidx.activity:activity-compose:1.9.1`
- `com.google.accompanist:accompanist-pager:0.20.0`
- `com.google.accompanist:accompanist-permissions:0.30.1`
- `com.google.accompanist:accompanist-systemuicontroller:0.27.0`
- `com.airbnb.android:lottie-compose:4.1.0`
- `io.coil-kt:coil-compose:2.2.2`, `coil-gif:2.2.2`, `coil-svg:2.2.2`
- `androidx.webkit:webkit:1.14.0`

**Networking**
- `io.ktor:ktor-client-android:2.3.12`
- `io.ktor:ktor-client-content-negotiation:2.3.12`
- `io.ktor:ktor-serialization-kotlinx-json:2.3.12`
- `io.ktor:ktor-client-logging:2.3.12`
- `io.ktor:ktor-client-websockets:2.3.12`
- `org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1`
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1`

**Firebase (BOM 33.1.2)**
- `firebase-analytics-ktx`, `firebase-crashlytics`, `firebase-dynamic-links-ktx`

**Security / Storage**
- `androidx.security:security-crypto-ktx:1.1.0-alpha06`
- `androidx.datastore:datastore-preferences:1.1.1`
- `androidx.datastore:datastore-core:1.1.1`

**Utilities**
- `pub.devrel:easypermissions:3.0.0`
- `com.google.android.gms:play-services-location:21.3.0`
- `com.google.android.gms:play-services-auth-api-phone:18.1.0`
- `com.google.android.play:app-update:2.1.0`, `app-update-ktx:2.1.0`
- `com.googlecode.libphonenumber:libphonenumber:9.0.1`
- `com.google.code.gson:gson:2.10.1`

---

## 13. Operating Constraints

- **Platform:** Android 7.0 (API 24) and above.
- **Toolchain:** Java 17, Kotlin 2.1.x, Compose 1.7.x.
- **Session issuance:** The `sessionId` is a server-side artefact; the host backend must integrate with the FIS platform to allocate one before invoking `LoanLib`.
- **Network:** An online connection is required throughout the journey; parts of the flow rely on real-time SSE updates.
- **UI ownership:** The library owns the full Compose UI for the loan journey. The host retains its own theming and returns to its own UI when the callback fires.
- **Activity lifecycle:** The library launches its own `MainActivity`; on completion it finishes itself and returns control to the host activity that invoked it.
- **Single active journey:** One journey should be launched at a time per session.

---

## 14. Testing Approach

### 14.1 Manual / staging testing

- Obtain a staging `sessionId` from the FIS staging backend (`https://stagingondcfs.jtechnoparks.in/jt-bap`).
- Build a debug variant of the host application and invoke `LoanLib.LaunchFISAppWithParamsAndCallback(...)`.
- Walk each of the three journeys (PL, GL, PF) using staging test data (test PAN / GSTIN / bank credentials issued by the FIS team).
- Assert that the `callback` fires with the expected `LoanDetails` and the host activity resumes.

### 14.2 Instrumented tests (host side)

Recommended host-side smoke test:

```kotlin
@RunWith(AndroidJUnit4::class)
class FisIntegrationSmokeTest {

    @Test
    fun launches_and_returns_result() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val latch = CountDownLatch(1)
        var received: LoanLib.LoanDetails? = null

        LoanLib.LaunchFISAppWithParamsAndCallback(
            context = context,
            sessionDetails = LoanLib.SessionDetails(
                sessionId = BuildConfig.FIS_TEST_SESSION_ID,
                loanId    = ""
            )
        ) { details ->
            received = details
            latch.countDown()
        }

        assertTrue(latch.await(10, TimeUnit.MINUTES))
        assertNotNull(received)
    }
}
```

### 14.3 Release-build verification

- Build a `release` (R8-enabled) APK / AAB of the host application.
- Verify that the consumer ProGuard rules keep `LoanLib` and its inner classes.
- Run at least one full journey end-to-end on the release binary before shipping.

---

## 15. Release Checklist

- [ ] JitPack repository added to `settings.gradle.kts`.
- [ ] `com.github.sugunasriram:fisloanlibv4:1.0.8` declared in the host module.
- [ ] `compileSdk 36`, `minSdk 24`, Java 17, Kotlin 2.1.x configured.
- [ ] `google-services.json` present.
- [ ] `res/xml/file_paths.xml` provided for `FileProvider`.
- [ ] ProGuard / R8 rules for Ktor, kotlinx.serialization, and Firebase added.
- [ ] Staging session tested end-to-end for the required journey(s).
- [ ] Callback path in the host activity verified with a release build.

---

## 16. Support

For session provisioning, backend onboarding, and staging credentials, contact the FIS platform team. Library issues can be raised on the JitPack-published repository for `com.github.sugunasriram/fisloanlibv4`.
