package com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.purchaseFinance

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.JsResult
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
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
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
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormRejectedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}
//
//private var filePathCallbackFn: ValueCallback<Array<Uri>>? = null
//private var uploadMessage: ValueCallback<Uri>? = null
//var redirectionSet = false
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun PfKycWebViewScreen(
//    navController: NavHostController,
//    transactionId: String,
//    url: String,
//    id: String,
//    fromScreen: String,
//    fromFlow: String,
//    isSelfScrollable: Boolean = false,
//    pageContent: () -> Unit
//) {
//    val decidedScreen = if (fromScreen == "1") "2" else "3"
//    val sseViewModel: SSEViewModel = viewModel()
//    val sseEvents by sseViewModel.events.collectAsState(initial = "")
//    var lateNavigate = false
//    var errorMsg by remember { mutableStateOf<String?>(null) }
//    val errorTitle = stringResource(id = R.string.kyc_failed)
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        delay(3 * 60 * 1000L)
//        if (sseEvents.isEmpty() && !lateNavigate) {
//            Toast.makeText(
//                context,
//                "Waiting for lender response, please wait...",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    // Start listening to SSE events when the KYC Screen is displayed
//    LaunchedEffect(Unit) {
//        sseViewModel.startListening(ApiPaths().sse)
//    }
//    // Handle the events and navigate based on the presence of events
//    LaunchedEffect(sseEvents) {
//        if (sseEvents.isNotEmpty()) {
//            try {
//                val sseData = json1.decodeFromString<SSEData>(sseEvents)
//                var sseTransactionId = sseData.data?.data?.txnId
//                val formId = sseData.data?.data?.data?.form_id
//
//                Log.d(
//                    "PfWebView:",
//                    "transactionId :[" + transactionId + "] " +
//                        "sseTransactionId:[" + sseTransactionId
//                )
//
//                sseData.data?.data?.type?.let { type ->
//                    // For KYC Verification Flow
//                    if (transactionId == sseTransactionId && (type == "ACTION" || type == "INFO")) {
//                        // Check if Form Rejected or Pending
//                        if (sseData.data?.data?.data?.error != null) {
//                            Log.d("KyCScreen", "Error :" + sseData.data?.data?.data?.error?.message)
//                            errorMsg = sseData.data?.data?.data?.error?.message
//                            sseViewModel.stopListening()
//                            navigateToFormRejectedScreen(
//                                navController = navController,
//                                fromFlow = fromFlow,
//                                errorTitle = errorTitle,
//                                errorMsg = errorMsg
//                            )
//                        } else {
//                            formId?.let {
//                                TokenManager.save("formId", formId).toString()
//                                lateNavigate = true
//                                navigateToBankKycVerificationScreen(
//                                    navController = navController,
//                                    kycUrl = "No Need KYC URL",
//                                    transactionId = transactionId,
//                                    offerId = id,
//                                    verificationStatus = decidedScreen,
//                                    fromFlow = fromFlow
//                                )
//                            }
//                        }
//                    }
//
//                }
//            } catch (e: Exception) {
//                Log.e("SSEParsingError", "Error parsing SSE data", e)
//            }
//        }
//    }
//
//    ProceedWithKYCProcess(
//        navController = navController,
//        context = LocalContext.current,
//        url = url,
//        pageContent = pageContent,
//        isSelfScrollable = isSelfScrollable
//    )
//}
//
//@Composable
//fun ProceedWithKYCProcess(
//    navController: NavHostController,
//    context: Context,
//    url: String,
//    pageContent: () -> Unit,
//    isSelfScrollable: Boolean = false
//) {
//    val activity = context as Activity
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        TopBar(
//            navController = navController,
////            onBackClick = { navigateApplyByCategoryScreen(navController) },
//            onBackClick = { navigateToFISExitScreen(navController, loanId="1234") },
//            topBarText = "Kyc Verification"
//        )
////        WebViewTopBar(navController, title = "Kyc Verification")
//        if (isSelfScrollable) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(top = 0.dp)
//            ) {
//                pageContent()
//            }
//        } else {
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//                    .padding(top = 0.dp)
//            ) {
//                val fileChooserLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.GetContent()
//                ) { uri: Uri? ->
//                    if (filePathCallbackFn != null) {
//                        filePathCallbackFn?.onReceiveValue(uri?.let { arrayOf(it) })
//                        filePathCallbackFn = null
//                    } else if (uploadMessage != null) {
//                        uploadMessage?.onReceiveValue(uri)
//                        uploadMessage = null
//                    }
//                }
//
//                AndroidView(
//                    factory = { ctx ->
//                        WebView(ctx).apply {
//                            // Sugu - need to test with other lender, commented for Lint
//                            settings.javaScriptEnabled = true
//                            settings.loadsImagesAutomatically = true
//                            settings.domStorageEnabled = true
//                            settings.allowFileAccess = true
//                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//                            settings.allowContentAccess = true
//                            settings.allowFileAccessFromFileURLs = true
//                            settings.allowUniversalAccessFromFileURLs = true
//                            settings.setSupportZoom(true)
//                            settings.builtInZoomControls = true
//                            settings.displayZoomControls = false
//                            settings.loadWithOverviewMode = true
//                            settings.useWideViewPort = true
//                            settings.setSupportMultipleWindows(true)
//                            settings.javaScriptCanOpenWindowsAutomatically = true
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                settings.safeBrowsingEnabled = true
//                            }
//
////                            WebView.setWebContentsDebuggingEnabled(true)
//
//                            settings.cacheMode = WebSettings.LOAD_DEFAULT
//
//                            // Enable hardware acceleration for better performance
//                            setLayerType(View.LAYER_TYPE_HARDWARE, null)
//
//                            layoutParams = ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT
//                            )
//
//                            webViewClient = object : WebViewClient() {
//                                override fun shouldOverrideUrlLoading(
//                                    view: WebView?,
//                                    url: String?
//                                ): Boolean {
//
//                                    return false
//                                }
//
//                                override fun onPageFinished(view: WebView?, url: String?) {
//                                    super.onPageFinished(view, url)
//                                }
//
//                                override fun onReceivedError(
//                                    view: WebView?,
//                                    request: WebResourceRequest?,
//                                    error: WebResourceError?
//                                ) {
//                                    super.onReceivedError(view, request, error)
//                                }
//
//                                override fun onReceivedHttpError(
//                                    view: WebView?,
//                                    request: WebResourceRequest?,
//                                    errorResponse: WebResourceResponse?
//                                ) {
//                                    super.onReceivedHttpError(view, request, errorResponse)
//                                }
//
//                                override fun onReceivedSslError(
//                                    view: WebView?,
//                                    handler: SslErrorHandler?,
//                                    error: SslError?
//                                ) {
//                                    super.onReceivedSslError(view, handler, error)
//                                }
//
//                                override fun onPageStarted(
//                                    view: WebView?,
//                                    url: String?,
//                                    favicon: Bitmap?
//                                ) {
//                                    super.onPageStarted(view, url, favicon)
//                                }
//
//                                // For Android 4.1+
//                                fun openFileChooser(
//                                    uploadMsg: ValueCallback<Uri>,
//                                    acceptType: String,
//                                    capture: String
//                                ) {
//                                    uploadMessage = uploadMsg
//                                    fileChooserLauncher.launch("*/*")
//                                }
//
//                                // For Android 3.0+
//                                fun openFileChooser(
//                                    uploadMsg: ValueCallback<Uri>,
//                                    acceptType: String
//                                ) {
//                                    openFileChooser(uploadMsg, acceptType, "")
//                                }
//
//                                // For Android < 3.0
//                                fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
//                                    openFileChooser(uploadMsg, "*/*")
//                                }
//                            }
//                        }
//                    },
//                    update = { webView ->
//                        // Sugu - need to test with other lender, commented for Lint
//                        webView.settings.javaScriptEnabled = true
//
//                        webView.settings.loadsImagesAutomatically = true
//                        webView.settings.domStorageEnabled = true
//                        webView.settings.allowFileAccess = true
//                        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//                        webView.settings.allowContentAccess = true
//                        webView.settings.allowFileAccessFromFileURLs = true
//                        webView.settings.allowUniversalAccessFromFileURLs = true
//                        webView.settings.setSupportZoom(true)
//
//                        webView.settings.builtInZoomControls = true
//                        webView.settings.displayZoomControls = false
//
//                        webView.settings.loadWithOverviewMode = true
//                        webView.settings.useWideViewPort = true
//                        webView.settings.setSupportMultipleWindows(true)
//                        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//                        webView.settings.safeBrowsingEnabled = true
//
//                        // Apply layout parameters to the WebView
//                        webView.layoutParams = ViewGroup.MarginLayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                        )
//
//                        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
//
//                        webView.webChromeClient = object : WebChromeClient() {
//
//                            override fun onPermissionRequest(request: PermissionRequest) {
//                                activity.runOnUiThread {
//                                    if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
//                                        MainActivity.webPermissionRequest = request
//                                        ActivityCompat.requestPermissions(
//                                            activity,
//                                            arrayOf(Manifest.permission.CAMERA),
//                                            CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
//                                        )
//                                    } else {
//                                        request.deny()
//                                    }
//                                }
//                            }
//
//                            private fun showPermissionDialog(request: PermissionRequest) {
//                                AlertDialog.Builder(context)
//                                    .setTitle("Camera Permission Request")
//                                    .setMessage("This site wants to access your camera. Do you allow it?")
//                                    .setPositiveButton("Allow") { _, _ ->
//                                        ActivityCompat.requestPermissions(
//                                            context,
//                                            arrayOf
//                                            (Manifest.permission.CAMERA),
//                                            CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
//                                        )
//                                    }
//                                    .setNegativeButton("Deny") { _, _ ->
//                                        request.deny()
//                                    }
//                                    .setCancelable(false)
//                                    .show()
//                            }
//
//                            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
//                                if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
//                                    Log.e("WebView JS Error", consoleMessage.message())
//                                }
//                                return super.onConsoleMessage(consoleMessage)
//                            }
//
//                            // For Android 5.0+
//                            override fun onShowFileChooser(
//                                webView: WebView,
//                                filePathCallback: ValueCallback<Array<Uri>>,
//                                fileChooserParams: FileChooserParams
//                            ): Boolean {
//                                filePathCallbackFn = filePathCallback
//                                fileChooserLauncher.launch("*/*")
//                                return true
//                            }
//
//                            // For Android 4.1+
//                            fun openFileChooser(
//                                uploadMsg: ValueCallback<Uri>,
//                                acceptType: String,
//                                capture: String
//                            ) {
//                                uploadMessage = uploadMsg
//                                fileChooserLauncher.launch("*/*")
//                            }
//
//                            // For Android 3.0+
//                            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
//                                openFileChooser(uploadMsg, acceptType, "")
//                            }
//
//                            // For Android < 3.0
//                            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
//                                openFileChooser(uploadMsg, "*/*")
//                            }
//                        }
//
//                        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//
//                        webView.clearCache(true)
//                        val cookieManager = CookieManager.getInstance()
//
//                        // Enable cookies
//                        cookieManager.setAcceptCookie(true)
//
//                        // If you want to allow third-party cookies (optional)
//                        // Pass the WebView instance directly
//                        cookieManager.setAcceptThirdPartyCookies(webView, true)
//
//                        // Set the CookieManager as the WebView's CookieManager
//                        CookieManager.getInstance().setAcceptCookie(true)
//
//                        webView.loadUrl(url)
//                    }
//                )
//            }
//        }
//    }
//}


//Option -2 - workig in android, but failig in React app
//@Composable
//fun PfKycWebViewScreen(
//    navController: NavHostController,
//    transactionId: String,
//    url: String,
//    id: String,
//    fromScreen: String,
//    fromFlow: String,
//) {
//    CompositionLocalProvider(
//        LocalSaveableStateRegistry provides null
//    ) {
//        PfKycWebViewScreenInternal(
//            navController = navController,
//            transactionId = transactionId,
//            url = url,
//            id = id,
//            fromScreen = fromScreen,
//            fromFlow = fromFlow,
//        )
//    }
//}
//
//private var filePathCallbackFn: ValueCallback<Array<Uri>>? = null
//private var uploadMessage: ValueCallback<Uri>? = null
//var redirectionSet = false
//@Composable
//private fun PfKycWebViewScreenInternal(
//    navController: NavHostController,
//    transactionId: String,
//    url: String,
//    id: String,
//    fromScreen: String,
//    fromFlow: String,
//) {
//    val context = LocalContext.current
//    val activity = context.findActivity()
//
//    val decidedScreen = if (fromScreen == "1") "2" else "3"
//    val sseViewModel: SSEViewModel = viewModel()
//
//    var lateNavigate by remember { mutableStateOf(false) }
//    var errorMsg by remember { mutableStateOf<String?>(null) }
//
//    val errorTitle = stringResource(id = R.string.kyc_failed)
//
//    LaunchedEffect(Unit) {
//        sseViewModel.startListening(ApiPaths().sse)
//
//        sseViewModel.events.collect { event ->
//            if (event.isEmpty() || lateNavigate) return@collect
//
//            if (event == "__SSE_FAILURE__") {
//                sseViewModel.stopListening()
//                navigateToFormRejectedScreen(
//                    navController,
//                    errorTitle = "Session Time Out",
//                    fromFlow = fromFlow,
//                    errorMsg = "Please try again after some time"
//                )
//                return@collect
//            }
//
//            runCatching {
//                val sseData = json1.decodeFromString<SSEData>(event)
//                val sseTxnId = sseData.data?.data?.txnId
//                val formId = sseData.data?.data?.data?.form_id
//                val type = sseData.data?.data?.type
//
//                if (
//                    transactionId == sseTxnId &&
//                    (type == "ACTION" || type == "INFO")
//                ) {
//                    if (sseData.data.data.data?.error != null) {
//                        errorMsg = sseData.data.data.data.error.message
//                        sseViewModel.stopListening()
//                        navigateToFormRejectedScreen(
//                            navController,
//                            fromFlow = fromFlow,
//                            errorTitle = errorTitle,
//                            errorMsg = errorMsg
//                        )
//                    } else {
//                        formId?.let {
//                            TokenManager.save("formId", it)
//                            lateNavigate = true
//                            sseViewModel.stopListening()
//                            navigateToBankKycVerificationScreen(
//                                navController,
//                                kycUrl = "No Need KYC URL",
//                                transactionId = transactionId,
//                                offerId = id,
//                                verificationStatus = decidedScreen,
//                                fromFlow = fromFlow
//                            )
//                        }
//                    }
//                }
//            }.onFailure {
//                Log.e("SSE", "Parsing error", it)
//            }
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        TopBar(
//            navController = navController,
//            onBackClick = { navigateApplyByCategoryScreen(navController) },
//            topBarText = "Kyc Verification"
//        )
//
//        PFKycWebView(
//            url = url,
//            modifier = Modifier.weight(1f),
//            activity = activity
//        )
//    }
//}
//@Composable
//private fun PFKycWebView(
//    url: String,
//    modifier: Modifier,
//    activity: Activity?
//) {
//    val context = LocalContext.current
//
//    var fileChooserCallback by remember {
//        mutableStateOf<ValueCallback<Array<Uri>>?>(null)
//    }
//
//    val fileChooserLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
//            try {
//                if (uris.isEmpty()) {
//                    fileChooserCallback?.onReceiveValue(null)
//                } else {
//                    fileChooserCallback?.onReceiveValue(uris.toTypedArray())
//                }
//            } catch (_: Exception) {
//                fileChooserCallback?.onReceiveValue(null)
//            } finally {
//                fileChooserCallback = null
//            }
//        }
//
//    val webView = remember {
//        WebView(context).apply {
//            id = View.NO_ID
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            isSaveEnabled = false
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        setupWebViewDefaults(
//            webView = webView,
//            context = context,
//            activity = activity,
//            fileChooserLauncher = fileChooserLauncher
//        ) { callback ->
//            // Cancel any previous callback first
//            fileChooserCallback?.onReceiveValue(null)
//            fileChooserCallback = callback
//        }
//        webView.loadUrl(url)
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            fileChooserCallback?.onReceiveValue(null)
//            fileChooserCallback = null
//
//            webView.apply {
//                stopLoading()
//                clearHistory()
//                clearCache(true)
//                removeAllViews()
//                destroy()
//            }
//        }
//    }
//
//    AndroidView(
//        modifier = modifier .fillMaxSize(),
//        factory = { webView }
//    )
//}
//@SuppressLint("SetJavaScriptEnabled")
//private fun setupWebViewDefaults(
//    webView: WebView,
//    activity: Activity?,
//    context: Context = webView.context,
//    fileChooserLauncher: ManagedActivityResultLauncher<String, List<Uri>>,
//    onFileChooserReady: (ValueCallback<Array<Uri>>?) -> Unit
//) {
//    webView.settings.apply {
//        javaScriptEnabled = true
//        domStorageEnabled = true
//
//        allowFileAccess = true
//        allowContentAccess = true
//
//        useWideViewPort = true
//        loadWithOverviewMode = true
//
//        setSupportZoom(false)
//        builtInZoomControls = false
//        displayZoomControls = false
//
//        cacheMode = WebSettings.LOAD_NO_CACHE
//        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//        mediaPlaybackRequiresUserGesture = false
//    }
//
//    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
//
//    webView.webViewClient = WebViewClient()
//
//    webView.webChromeClient = object : WebChromeClient() {
//
////        override fun onPermissionRequest(request: PermissionRequest?) {
////            request?.grant(request.resources)
////        }
//
////        override fun onShowFileChooser(
////            webView: WebView?,
////            filePathCallback: ValueCallback<Array<Uri>>?,
////            fileChooserParams: FileChooserParams?
////        ): Boolean {
////
////            onFileChooserReady(filePathCallback)
////            fileChooserLauncher.launch("image/*")
////            return true
////        }
//
//        override fun onJsAlert(
//            view: WebView?,
//            url: String?,
//            message: String?,
//            result: JsResult?
//        ): Boolean {
//            AlertDialog.Builder(activity ?: view?.context)
//                .setMessage(message)
//                .setPositiveButton("OK") { _, _ -> result?.confirm() }
//                .setCancelable(false)
//                .show()
//            return true
//        }
//
//        override fun onPermissionRequest(request: PermissionRequest) {
//            activity?.runOnUiThread {
//                if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
//                    MainActivity.webPermissionRequest = request
//                    ActivityCompat.requestPermissions(
//                        activity,
//                        arrayOf(Manifest.permission.CAMERA),
//                        CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
//                    )
//                } else {
//                    request.deny()
//                }
//            }
//        }
//
//        private fun showPermissionDialog(request: PermissionRequest) {
//            AlertDialog.Builder(context)
//                .setTitle("Camera Permission Request")
//                .setMessage("This site wants to access your camera. Do you allow it?")
//                .setPositiveButton("Allow") { _, _ ->
//                    ActivityCompat.requestPermissions(
//                        activity!!,
//                        arrayOf
//                        (Manifest.permission.CAMERA),
//                        CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
//                    )
//                }
//                .setNegativeButton("Deny") { _, _ ->
//                    request.deny()
//                }
//                .setCancelable(false)
//                .show()
//        }
//
//        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
//            if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
//                Log.e("WebView JS Error", consoleMessage.message())
//            }
//            return super.onConsoleMessage(consoleMessage)
//        }
//
//        // For Android 5.0+
//        override fun onShowFileChooser(
//            webView: WebView,
//            filePathCallback: ValueCallback<Array<Uri>>,
//            fileChooserParams: FileChooserParams
//        ): Boolean {
//            filePathCallbackFn = filePathCallback
//            fileChooserLauncher.launch("*/*")
//            return true
//        }
//
//        // For Android 4.1+
//        fun openFileChooser(
//            uploadMsg: ValueCallback<Uri>,
//            acceptType: String,
//            capture: String
//        ) {
//            uploadMessage = uploadMsg
//            fileChooserLauncher.launch("*/*")
//        }
//
//        // For Android 3.0+
//        fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
//            openFileChooser(uploadMsg, acceptType, "")
//        }
//
//        // For Android < 3.0
//        fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
//            openFileChooser(uploadMsg, "*/*")
//        }
//    }
//
//    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//
//    webView.clearCache(true)
//    val cookieManager = CookieManager.getInstance()
//                            // Enable cookies
//    cookieManager.setAcceptCookie(true)
//
//    // If you want to allow third-party cookies (optional)
//    // Pass the WebView instance directly
//    cookieManager.setAcceptThirdPartyCookies(webView, true)
//
//    // Set the CookieManager as the WebView's CookieManager
//    CookieManager.getInstance().setAcceptCookie(true)
//
//
//
//
//}
//
//private fun Context.findActivity(): Activity? = when (this) {
//    is Activity -> this
//    is ContextWrapper -> baseContext.findActivity()
//    else -> null
//}

//Code updated to work o react app as well - Option 3
// ✅ Updated complete file (fixes WebView <input type="file"> upload in RN host too)
// Key fixes:
// 1) Use StartActivityForResult + FileChooserParams.createIntent() + parseResult()
// 2) Store callback in Compose state via onFileChooserReady(...)
// 3) Remove unused global callbacks (filePathCallbackFn/uploadMessage) that were breaking flow

@Composable
fun PfKycWebViewScreen(
    navController: NavHostController,
    transactionId: String,
    url: String,
    id: String,
    fromScreen: String,
    fromFlow: String,
) {
    CompositionLocalProvider(
        LocalSaveableStateRegistry provides null
    ) {
        PfKycWebViewScreenInternal(
            navController = navController,
            transactionId = transactionId,
            url = url,
            id = id,
            fromScreen = fromScreen,
            fromFlow = fromFlow,
        )
    }
}

var redirectionSet = false

@Composable
private fun PfKycWebViewScreenInternal(
    navController: NavHostController,
    transactionId: String,
    url: String,
    id: String,
    fromScreen: String,
    fromFlow: String,
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    val decidedScreen = if (fromScreen == "1") "2" else "3"
    val sseViewModel: SSEViewModel = viewModel()

    var lateNavigate by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val errorTitle = stringResource(id = R.string.kyc_failed)

    LaunchedEffect(Unit) {
        sseViewModel.startListening(ApiPaths().sse)

        sseViewModel.events.collect { event ->
            if (event.isEmpty() || lateNavigate) return@collect

            if (event == "__SSE_FAILURE__") {
                sseViewModel.stopListening()
                navigateToFormRejectedScreen(
                    navController,
                    errorTitle = "Session Time Out",
                    fromFlow = fromFlow,
                    errorMsg = "Please try again after some time"
                )
                return@collect
            }

            runCatching {
                val sseData = json1.decodeFromString<SSEData>(event)
                val sseTxnId = sseData.data?.data?.txnId
                val formId = sseData.data?.data?.data?.form_id
                val type = sseData.data?.data?.type

                if (
                    transactionId == sseTxnId &&
                    (type == "ACTION" || type == "INFO")
                ) {
                    if (sseData.data.data.data?.error != null) {
                        errorMsg = sseData.data.data.data.error.message
                        sseViewModel.stopListening()
                        navigateToFormRejectedScreen(
                            navController,
                            fromFlow = fromFlow,
                            errorTitle = errorTitle,
                            errorMsg = errorMsg
                        )
                    } else {
                        formId?.let {
                            TokenManager.save("formId", it)
                            lateNavigate = true
                            sseViewModel.stopListening()
                            navigateToBankKycVerificationScreen(
                                navController,
                                kycUrl = "No Need KYC URL",
                                transactionId = transactionId,
                                offerId = id,
                                verificationStatus = decidedScreen,
                                fromFlow = fromFlow
                            )
                        }
                    }
                }
            }.onFailure {
                Log.e("SSE", "Parsing error", it)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            navController = navController,
            onBackClick = { navigateApplyByCategoryScreen(navController) },
            topBarText = "Kyc Verification"
        )

        PFKycWebView(
            url = url,
            modifier = Modifier.weight(1f),
            activity = activity
        )
    }
}

@Composable
private fun PFKycWebView(
    url: String,
    modifier: Modifier,
    activity: Activity?
) {
    val context = LocalContext.current

    var fileChooserCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    // ✅ Best-practice launcher for WebView file chooser
    val fileChooserLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val cb = fileChooserCallback
            fileChooserCallback = null

            try {
                val uris = WebChromeClient.FileChooserParams.parseResult(
                    result.resultCode,
                    result.data
                )
                cb?.onReceiveValue(uris)
            } catch (_: Exception) {
                cb?.onReceiveValue(null)
            }
        }

    val webView = remember {
        WebView(context).apply {
            id = View.NO_ID
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            isSaveEnabled = false
        }
    }

    LaunchedEffect(Unit) {
        setupWebViewDefaults(
            webView = webView,
            context = context,
            activity = activity,
            fileChooserLauncher = fileChooserLauncher
        ) { callback ->
            // Cancel any previous callback first
            fileChooserCallback?.onReceiveValue(null)
            fileChooserCallback = callback
        }
        webView.loadUrl(url)
    }

    DisposableEffect(Unit) {
        onDispose {
            fileChooserCallback?.onReceiveValue(null)
            fileChooserCallback = null

            webView.apply {
                stopLoading()
                clearHistory()
                clearCache(true)
                removeAllViews()
                destroy()
            }
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { webView }
    )
}

@SuppressLint("SetJavaScriptEnabled")
private fun setupWebViewDefaults(
    webView: WebView,
    activity: Activity?,
    context: Context = webView.context,
    fileChooserLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    onFileChooserReady: (ValueCallback<Array<Uri>>?) -> Unit
) {
    webView.settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true

        allowFileAccess = true
        allowContentAccess = true

        useWideViewPort = true
        loadWithOverviewMode = true

        setSupportZoom(false)
        builtInZoomControls = false
        displayZoomControls = false

        cacheMode = WebSettings.LOAD_NO_CACHE
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        mediaPlaybackRequiresUserGesture = false
    }

    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptCookie(true)
    cookieManager.setAcceptThirdPartyCookies(webView, true)

    webView.webViewClient = WebViewClient()

    webView.webChromeClient = object : WebChromeClient() {

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            AlertDialog.Builder(activity ?: view?.context)
                .setMessage(message)
                .setPositiveButton("OK") { _, _ -> result?.confirm() }
                .setCancelable(false)
                .show()
            return true
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            activity?.runOnUiThread {
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

        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
            if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                Log.e("WebView JS Error", consoleMessage.message())
            }
            return super.onConsoleMessage(consoleMessage)
        }

        // ✅ Android 5.0+ file chooser support (this is what <input type="file"> uses)
        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            // Store callback in Compose state so launcher can return the selected URIs
            onFileChooserReady(filePathCallback)

            return try {
                val intent = fileChooserParams.createIntent().apply {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                fileChooserLauncher.launch(intent)
                true
            } catch (_: ActivityNotFoundException) {
                onFileChooserReady(null)
                filePathCallback.onReceiveValue(null)
                false
            }
        }
    }

    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    webView.clearCache(true)
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
