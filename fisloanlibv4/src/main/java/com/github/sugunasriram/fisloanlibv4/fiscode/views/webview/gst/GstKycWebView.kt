package com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.gst

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.app.MainActivity
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TopBar
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormRejectedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

private var filePathCallbackFn: ValueCallback<Array<Uri>>? = null
private var uploadMessage: ValueCallback<Uri>? = null
var redirectionSet = false

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun GstKycWebViewScreen(
    navController: NavHostController,
    transactionId: String,
    url: String,
    id: String,
    fromScreen: String,
    fromFlow: String,
    isSelfScrollable: Boolean = false,
    pageContent: () -> Unit
) {
    val decidedScreen = if (fromScreen == "1") "2" else "3"
    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")
    var lateNavigate = false
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val errorTitle = stringResource(id = R.string.kyc_failed)

    val handler = remember {
        Handler(Looper.getMainLooper()).apply {
            postDelayed({
                if (sseEvents.isEmpty()) {
                    if (!lateNavigate) {
                        navigateToBankKycVerificationScreen(
                            navController = navController,
                            kycUrl = "No Need KYC URL",
                            transactionId = transactionId,
                            offerId = id,
                            verificationStatus = decidedScreen,
                            fromFlow = fromFlow
                        )
                    }
                }
            }, 3 * 60 * 1000)
        }
    }

    // Start listening to SSE events when the KYC Screen is displayed
    LaunchedEffect(Unit) {
        sseViewModel.startListening(ApiPaths().sse)
    }
    // Handle the events and navigate based on the presence of events
    LaunchedEffect(sseEvents) {
        if (sseEvents.isNotEmpty()) {
            handler.removeCallbacksAndMessages(null)
            val sseData: SSEData? = try {
                json1.decodeFromString<SSEData>(sseEvents)
            } catch (e: Exception) {
                Log.e("SSEParsingError", "Error parsing SSE data", e)
                null
            }

            if (sseData != null) {
                val sseTransactionId = sseData.data?.data?.txnId

                val formId = sseData.data?.data?.data?.form_id

                Log.d(
                    "GSTWebView:",
                    "transactionId :[" + transactionId + "] " +
                        "sseTransactionId:[" + sseTransactionId
                )

                sseData.data?.data?.type?.let { type ->
                    if (transactionId == sseTransactionId && (type == "ACTION" || type == "INFO")) {
                        // Check if Form Rejected or Pending
                        if (sseData.data?.data?.data?.error != null) {
                            Log.d("KyCScreen", "Error :" + sseData.data?.data?.data?.error?.message)
                            errorMsg = sseData.data?.data?.data?.error?.message

                            navigateToFormRejectedScreen(
                                navController = navController,
                                fromFlow = fromFlow,
                                errorTitle = errorTitle,
                                errorMsg = errorMsg
                            )
                        } else {
                            if (decidedScreen == "2") {
                                formId?.let {
                                    TokenManager.save("formId", formId).toString()
                                    lateNavigate = true
                                    navigateToBankKycVerificationScreen(
                                        navController = navController,
                                        kycUrl = "No Need KYC URL",
                                        transactionId = transactionId,
                                        offerId = id,
                                        verificationStatus = decidedScreen,
                                        fromFlow = fromFlow
                                    )
                                }
                            } else {
                                val check = TokenManager.read("formId")
                                if (formId != check && check != null) {
                                    navigateToBankKycVerificationScreen(
                                        navController = navController,
                                        kycUrl = "No Need KYC URL",
                                        transactionId = transactionId,
                                        offerId = id,
                                        verificationStatus = decidedScreen,
                                        fromFlow = fromFlow
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    ProceedWithKYCProcess(
        navController = navController,
        context = LocalContext.current,
        url = url,
        id = id,
        decidedScreen = decidedScreen,
        pageContent = pageContent,
        isSelfScrollable = isSelfScrollable
    )
}

@Composable
fun ProceedWithKYCProcess(
    navController: NavHostController,
    context: Context,
    url: String,
    id: String,
    decidedScreen: String,
    pageContent: () -> Unit,
    isSelfScrollable: Boolean = false
) {
    val activity = context as Activity
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            onBackClick = { navigateApplyByCategoryScreen(navController) },
            topBarText = "Kyc Verification"
        )
//        WebViewTopBar(navController, title = "Kyc Verification")
        if (isSelfScrollable) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 0.dp)
            ) {
                pageContent()
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 0.dp)
            ) {
                val fileChooserLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    if (filePathCallbackFn != null) {
                        filePathCallbackFn?.onReceiveValue(uri?.let { arrayOf(it) })
                        filePathCallbackFn = null
                    } else if (uploadMessage != null) {
                        uploadMessage?.onReceiveValue(uri)
                        uploadMessage = null
                    }
                }

                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            // Sugu - need to test with other lender, commented for Lint
                            settings.javaScriptEnabled = true
                            settings.loadsImagesAutomatically = true
                            settings.domStorageEnabled = true
                            settings.allowFileAccess = true
                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            settings.allowContentAccess = true
                            settings.allowFileAccessFromFileURLs = true
                            settings.allowUniversalAccessFromFileURLs = true
                            settings.setSupportZoom(true)
                            settings.builtInZoomControls = true
                            settings.displayZoomControls = false
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.setSupportMultipleWindows(true)
                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.safeBrowsingEnabled = true

//                            WebView.setWebContentsDebuggingEnabled(true)

                            settings.cacheMode = WebSettings.LOAD_DEFAULT

                            // Enable hardware acceleration for better performance
                            setLayerType(View.LAYER_TYPE_HARDWARE, null)

                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(
                                    view: WebView?,
                                    url: String?
                                ): Boolean {
                                    url?.let {
                                        when {
                                            it.contains("ekycdone") || it.contains("https://pramaan.ondc.org/beta/staging/mock/seller/toENach") -> {
                                                view?.loadUrl(it)
                                                return false
                                            }

                                            else -> {
                                                view?.loadUrl(it)
                                                return true
                                            }
                                        }
                                    }
                                    return false
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                }

                                override fun onReceivedError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    error: WebResourceError?
                                ) {
                                    super.onReceivedError(view, request, error)
                                }

                                override fun onReceivedHttpError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    errorResponse: WebResourceResponse?
                                ) {
                                    super.onReceivedHttpError(view, request, errorResponse)
                                }

                                override fun onReceivedSslError(
                                    view: WebView?,
                                    handler: SslErrorHandler?,
                                    error: SslError?
                                ) {
                                    super.onReceivedSslError(view, handler, error)
                                }

                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    super.onPageStarted(view, url, favicon)
                                }

                                // For Android 4.1+
                                fun openFileChooser(
                                    uploadMsg: ValueCallback<Uri>,
                                    acceptType: String,
                                    capture: String
                                ) {
                                    uploadMessage = uploadMsg
                                    fileChooserLauncher.launch("*/*")
                                }

                                // For Android 3.0+
                                fun openFileChooser(
                                    uploadMsg: ValueCallback<Uri>,
                                    acceptType: String
                                ) {
                                    openFileChooser(uploadMsg, acceptType, "")
                                }

                                // For Android < 3.0
                                fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                                    openFileChooser(uploadMsg, "*/*")
                                }
                            }
                        }
                    },
                    update = { webView ->
                        // Sugu - need to test with other lender, commented for Lint
                        webView.settings.javaScriptEnabled = true

                        webView.settings.loadsImagesAutomatically = true
                        webView.settings.domStorageEnabled = true
                        webView.settings.allowFileAccess = true
                        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        webView.settings.allowContentAccess = true
                        webView.settings.allowFileAccessFromFileURLs = true
                        webView.settings.allowUniversalAccessFromFileURLs = true
                        webView.settings.setSupportZoom(true)

                        webView.settings.builtInZoomControls = true
                        webView.settings.displayZoomControls = false

                        webView.settings.loadWithOverviewMode = true
                        webView.settings.useWideViewPort = true
                        webView.settings.setSupportMultipleWindows(true)
                        webView.settings.javaScriptCanOpenWindowsAutomatically = true
                        webView.settings.safeBrowsingEnabled = true

                        // Apply layout parameters to the WebView
                        webView.layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

                        webView.webChromeClient = object : WebChromeClient() {

                            override fun onPermissionRequest(request: PermissionRequest) {
                                activity.runOnUiThread {
                                    if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                                        MainActivity.webPermissionRequest = request
                                        ActivityCompat.requestPermissions(
                                            activity,
                                            arrayOf(Manifest.permission.CAMERA),
                                            CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
                                        )
                                    } else {
                                        request.deny()
                                    }
                                }
                            }

                            private fun showPermissionDialog(request: PermissionRequest) {
                                AlertDialog.Builder(context)
                                    .setTitle("Camera Permission Request")
                                    .setMessage("This site wants to access your camera. Do you allow it?")
                                    .setPositiveButton("Allow") { _, _ ->
                                        ActivityCompat.requestPermissions(
                                            context,
                                            arrayOf
                                            (Manifest.permission.CAMERA),
                                            CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
                                        )
                                    }
                                    .setNegativeButton("Deny") { _, _ ->
                                        request.deny()
                                    }
                                    .setCancelable(false)
                                    .show()
                            }

                            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                                if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                                    Log.e("WebView JS Error", consoleMessage.message())
                                }
                                return super.onConsoleMessage(consoleMessage)
                            }

                            // For Android 5.0+
                            override fun onShowFileChooser(
                                webView: WebView,
                                filePathCallback: ValueCallback<Array<Uri>>,
                                fileChooserParams: FileChooserParams
                            ): Boolean {
                                filePathCallbackFn = filePathCallback
                                fileChooserLauncher.launch("*/*")
                                return true
                            }

                            // For Android 4.1+
                            fun openFileChooser(
                                uploadMsg: ValueCallback<Uri>,
                                acceptType: String,
                                capture: String
                            ) {
                                uploadMessage = uploadMsg
                                fileChooserLauncher.launch("*/*")
                            }

                            // For Android 3.0+
                            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
                                openFileChooser(uploadMsg, acceptType, "")
                            }

                            // For Android < 3.0
                            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                                openFileChooser(uploadMsg, "*/*")
                            }
                        }

                        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

                        webView.clearCache(true)
                        val cookieManager = CookieManager.getInstance()

                        // Enable cookies
                        cookieManager.setAcceptCookie(true)

                        // If you want to allow third-party cookies (optional)
                        // Pass the WebView instance directly
                        cookieManager.setAcceptThirdPartyCookies(webView, true)

                        // Set the CookieManager as the WebView's CookieManager
                        CookieManager.getInstance().setAcceptCookie(true)

                        webView.loadUrl(url)
                    }
                )
            }
        }
    }
}
