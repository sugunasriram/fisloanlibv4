package com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.personalLoan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.GeolocationPermissions
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.RENDERER_PRIORITY_BOUND
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.app.MainActivity
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TopBar
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormRejectedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}


private var filePathCallbackFn: ValueCallback<Array<Uri>>? = null
private var uploadMessage: ValueCallback<Uri>? = null
var mGeoLocationRequestOrigin: String? = null
var mGeoLocationCallback: GeolocationPermissions.Callback? = null

@Preview(showBackground = true)
@Composable
fun WebKycScreenPreview() {
//    val url="https://uat-api.refo.dev/pss/enach/form/9277121d-8413-4dba-ad74-efc3b8c531b2?sessionId=fiD32d21ip%2BqtbOyGMerSFzIKPBDpzwqXBZfch%2Fd%2Fr%2BiGQnrKBNfM22PZGZi8901NxQKC4qlU8wVqC3I8j76gQyBmtiy8iUzYIIIpagvfg7bPhy7RAtZvMp5WYh2jG0j%2F3rVkpgii3RAXwPhTLB4MT%2FtaJgf&redirectUrl=self"
//    var url = "https://ilpuat.finfotech.co.in/lms/ondc/kycuri?transactionId=d3d1c832-2848-51cf-bafa-9f53ca8c7985&status=1"
//    var url = "https://ilpuat.finfotech.co.in/lms/ondc/kycuri?transactionId=1329fefc-86e5-5f4b-b60e-6d32a7674ef0&status=2"
//    var url = "file:///android_asset/eKycLastPage.html"
//    var url = "https://upsell.abfldirect.com/kyc/selfie?account_id=9d61f0949525523c8a51173b6f66dcbf&z_request_id=ONA202503214c722e3b02ca4cf&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5ZDYxZjA5NDk1MjU1MjNjOGE1MTE3M2I2ZjY2ZGNiZiIsImF1ZCI6InVzciIsImlhdCI6MTc0MjU1OTU1MywiZXhwIjoxNzQ1MTUxNTUzLCJpc3MiOiJ3aWRnZXRfc2RrX2JhY2tlbmQifQ.10ic_goWA-LY7HWF4WKKRQ5uaTnnfypy6aoKAI5JbJw"
        var url="https://www.google.com"
    //    WebKycScreen(
//        navController = rememberNavController(), transactionId="transactionId",
//        url = url, id = "id", fromFlow = "Personal Loan"
//    ){}

    val loadedUrl = remember { mutableStateOf<String?>(null) }

    ProceedWithKYCProcess(
        navController = rememberNavController(),
        context = LocalContext.current,
        url = url,
        id = "id",
        pageContent = {  },
        isSelfScrollable = false,
        loadedUrl = loadedUrl,
    )
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebKycScreen(
    navController: NavHostController, transactionId: String, url: String, id: String, fromFlow:
    String,
    isSelfScrollable: Boolean = false, pageContent: () -> Unit
) {
//    var lateNavigate = false
    var lateNavigate by remember { mutableStateOf(false) }
    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val errorTitle = stringResource(id = R.string.kyc_failed)

    val loadedUrl = remember { mutableStateOf<String?>(null) }

    sseViewModel.startListening(ApiPaths().sse)

//    LaunchedEffect(Unit) {
//        delay(5 * 60 * 1000L)
//        if (sseEvents.isEmpty() && !lateNavigate) {
//            navigateApplyByCategoryScreen(navController)
//        }
//    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(3 * 60 * 1000L)
        if (sseEvents.isEmpty() && !lateNavigate) {
            Toast.makeText(
                context,
                "Waiting for lender response, please wait...",
                Toast
                    .LENGTH_SHORT
            ).show()
        }
    }



    if (sseEvents.isNotEmpty()) {
        Log.d("KyCScreen_KYC Web Screen", "SSE entered ")

        val sseData: SSEData? = try {
            json1.decodeFromString<SSEData>(sseEvents)
        } catch (e: Exception) {
            Log.e("SSEParsingError", "Error parsing SSE data", e)
            null
        }
        if (sseData != null) {

        val sseTransactionId = sseData.data?.data?.txnId;//tested
        Log.d(
            "KyCScreen_Kyc:", "transactionId :[" + transactionId + "] " +
                    "sseTransactionId:[" + sseTransactionId
        )
            sseData.data?.data?.type.let { type ->
                if (transactionId == sseTransactionId && (type == "ACTION" || type == "INFO")) {
                    Log.d("KyCScreen", "At SSE not empty - Sugu")
                    lateNavigate = true

                    //Check if Form Rejected or Pending
                    if (sseData.data?.data?.data?.error != null) {
                        Log.d("KyCScreen", "Error :" + sseData.data?.data?.data?.error?.message)
                        errorMsg = sseData.data?.data?.data?.error?.message
                        sseViewModel.stopListening()
                        navigateToFormRejectedScreen(
                            navController = navController,
                            errorTitle = errorTitle,
                            fromFlow = fromFlow, errorMsg = errorMsg
                        )
                    } else {
                        sseViewModel.stopListening()
                        navigateToAnimationLoader(
                            navController = navController, transactionId = transactionId, id = id,
                            fromFlow = fromFlow
                        )
                    }
                }
            }
         }

    }
    ProceedWithKYCProcess(
        navController = navController, context = LocalContext.current, url = url, id = id,
        pageContent = pageContent, isSelfScrollable = isSelfScrollable, loadedUrl = loadedUrl,
    )

    DisposableEffect(Unit) {
        onDispose {
            sseViewModel.stopListening()
        }
    }

}



@Composable
fun ProceedWithKYCProcess(
    navController: NavHostController, context: Context, url: String, id: String,
    pageContent: () -> Unit, isSelfScrollable: Boolean = false,  loadedUrl: MutableState<String?>
) {
    BackHandler { navigateApplyByCategoryScreen(navController) }
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
                            settings.setGeolocationEnabled(true)
                            //Sugu - need to test with other lender, commented for Lint
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
                            settings.mediaPlaybackRequiresUserGesture = false
                            settings.safeBrowsingEnabled = true
//                            WebView.setWebContentsDebuggingEnabled(true);
                            settings.cacheMode = WebSettings.LOAD_DEFAULT

                            // Enable hardware acceleration for better performance
                            setLayerType(View.LAYER_TYPE_HARDWARE, null)
                            setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, false)

                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            isFocusable = true
                            isFocusableInTouchMode = true

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
                                                return false
                                            }
                                        }
                                    }
                                    return true
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
                                    view: WebView?, handler: SslErrorHandler?, error: SslError?
                                ) {
                                    super.onReceivedSslError(view, handler, error)
                                }

                                override fun onPageStarted(
                                    view: WebView?, url: String?, favicon: Bitmap?
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
                            val webSettings = webViewClient?.let { it1 -> settings }
                            if (webSettings != null) {
                                webSettings.javaScriptEnabled = true

                                webSettings.cacheMode = WebSettings.LOAD_DEFAULT
                                webSettings.setDomStorageEnabled(true)
                                webSettings.setAllowFileAccess(true)
                                webSettings.setAllowContentAccess(true)
                                webSettings.setAllowFileAccessFromFileURLs(true)
                                webSettings.setAllowUniversalAccessFromFileURLs(true)
                                webSettings.setJavaScriptEnabled(true)
                                webSettings.setSupportMultipleWindows(true)
                                webSettings.setJavaScriptCanOpenWindowsAutomatically(true)
                                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                webSettings.safeBrowsingEnabled = true
                            }
                        }
                    },
                    update = { webView ->


                        webView.settings.setGeolocationEnabled(true)
                        webView.settings.setJavaScriptEnabled(true)
                        //Sugu - need to test with other lender, commented for Lint
                        webView.settings.loadsImagesAutomatically = true
                        webView.settings.domStorageEnabled = true
                        webView.settings.allowFileAccess = true
                        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        webView.settings.allowContentAccess = true
                        webView.settings.allowFileAccessFromFileURLs = true
                        webView.settings.allowUniversalAccessFromFileURLs = true
                        webView.settings.setSupportZoom(true)

                        webView.settings.loadWithOverviewMode = true
                        webView.settings.useWideViewPort = true
                        webView.settings.setSupportMultipleWindows(true)
                        webView.settings.javaScriptCanOpenWindowsAutomatically = true

                        // Apply layout parameters to the WebView
                        webView.layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
                        webView.isFocusable = true
                        webView.isFocusableInTouchMode = true
                        webView.webChromeClient = object : WebChromeClient() {
                            override fun onPermissionRequest(request: PermissionRequest) {
                                activity.runOnUiThread {
                                    when {
                                        request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE) -> {
                                            MainActivity.webPermissionRequest = request
                                            ActivityCompat.requestPermissions(
                                                activity,
                                                arrayOf(Manifest.permission.CAMERA),
                                                CommonMethods().CAMERA_PERMISSION_REQUEST_CODE
                                            )
                                        }

                                        request.resources.contains(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID) -> {
                                            MainActivity.webPermissionRequest = request
//                                            ActivityCompat.requestPermissions(
//                                                activity,
//                                                arrayOf(
//                                                    Manifest.permission.ACCESS_FINE_LOCATION,
//                                                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                                                ),
//                                                1001
//                                            )

                                            val permissions = mutableListOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )

                                            // Add ACCESS_BACKGROUND_LOCATION only for API 29 and above
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                                            }

                                            ActivityCompat.requestPermissions(
                                                activity,
                                                permissions.toTypedArray(),
                                                1002
                                            )
                                        }

                                        else -> {
                                            request.deny()
                                        }
                                    }
                                }
                            }

                            override fun onGeolocationPermissionsShowPrompt(
                                origin: String?,
                                callback: GeolocationPermissions.Callback?
                            ) {

                                if (ContextCompat.checkSelfPermission(
                                        activity,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    )
                                    != PackageManager.PERMISSION_GRANTED
                                ) {

                                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                                            activity,
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        )
                                    ) {
                                        AlertDialog.Builder(activity)
                                            .setMessage("Please turn ON the GPS to make app work smoothly")
                                            .setNeutralButton(
                                                android.R.string.ok,
                                                DialogInterface.OnClickListener { dialogInterface, i ->
                                                    mGeoLocationCallback = callback
                                                    mGeoLocationRequestOrigin = origin
                                                    ActivityCompat.requestPermissions(
                                                        activity,
                                                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                                        1001
                                                    )
                                                })
                                            .show()

                                    } else {
                                        //no explanation need we can request the location
                                        mGeoLocationCallback = callback
                                        mGeoLocationRequestOrigin = origin
                                        ActivityCompat.requestPermissions(
                                            activity,
                                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1002
                                        )
                                    }
                                } else {
                                    //tell the webview that permission has granted
                                    callback!!.invoke(origin, true, true)
                                }

                            }

                            private fun showPermissionDialog(request: PermissionRequest) {
                                AlertDialog.Builder(context)
                                    .setTitle("Camera Permission Request")
                                    .setMessage("This site wants to access your camera. Do you allow it?")
                                    .setPositiveButton("Allow") { _, _ ->
                                        ActivityCompat.requestPermissions(
                                            context, arrayOf
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

                            override fun onCreateWindow(
                                view: WebView?,
                                isDialog: Boolean,
                                isUserGesture: Boolean,
                                resultMsg: Message?
                            ): Boolean {
                                val newWebView = view?.context?.let { WebView(it) }
                                if (newWebView != null) {

                                    newWebView.webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(
                                            view: WebView,
                                            request: WebResourceRequest
                                        ): Boolean {
                                            val url = request.url.toString()
                                            if (view != null && url != null) {
                                                view.loadUrl(url)
                                                return false
                                            } else {
                                                return super.shouldOverrideUrlLoading(view, request)
                                            }
                                        }

                                        override fun shouldOverrideUrlLoading(
                                            view: WebView?,
                                            url: String?
                                        ): Boolean {
                                            if (view != null && url != null) {
                                                view.loadUrl(url)
                                                return false
                                            }
                                            return true
                                        }

                                    }
                                }


                                val webSettings = newWebView?.settings
                                if (webSettings != null) {
                                    webSettings.javaScriptEnabled = true
                                    webSettings.domStorageEnabled = true
                                    webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    webSettings.setAllowFileAccess(true)
                                    webSettings.setAllowContentAccess(true)
                                    webSettings.setAllowFileAccessFromFileURLs(true)
                                    webSettings.setAllowUniversalAccessFromFileURLs(true)
                                    webSettings.setSupportMultipleWindows(true)
                                    webSettings.setJavaScriptCanOpenWindowsAutomatically(true)
                                    webSettings.setGeolocationEnabled(true)
                                    webSettings.loadsImagesAutomatically = true
                                    webSettings.loadWithOverviewMode = true
                                    webSettings.javaScriptCanOpenWindowsAutomatically = true
                                    webSettings.cacheMode = WebSettings.LOAD_DEFAULT
                                }

                                val webViewSettings = view?.settings
                                if (webViewSettings != null) {
                                    webViewSettings.javaScriptEnabled = true
                                    webViewSettings.domStorageEnabled = true
                                    webViewSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    webViewSettings.setAllowFileAccess(true)
                                    webViewSettings.setAllowContentAccess(true)
                                    webViewSettings.setAllowFileAccessFromFileURLs(true)
                                    webViewSettings.setAllowUniversalAccessFromFileURLs(true)
                                    webViewSettings.setSupportMultipleWindows(true)
                                    webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true)
                                    webViewSettings.setGeolocationEnabled(true)
                                    webViewSettings.loadsImagesAutomatically = true
                                    webViewSettings.loadWithOverviewMode = true
                                    webViewSettings.useWideViewPort = true
                                    webViewSettings.javaScriptCanOpenWindowsAutomatically = true
                                    webViewSettings.cacheMode = WebSettings.LOAD_DEFAULT
                                }
//                                WebView.setWebContentsDebuggingEnabled(true)

                                // Apply layout parameters to the WebView
                                newWebView?.layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )

                                newWebView?.isFocusable = true
                                newWebView?.isFocusableInTouchMode = true

                                val cookieManager = CookieManager.getInstance()

                                // Enable cookies
                                cookieManager.setAcceptCookie(true)

                                // If you want to allow third-party cookies (optional)
                                // Pass the WebView instance directly
                                cookieManager.setAcceptThirdPartyCookies(newWebView, true)
                                cookieManager.setAcceptThirdPartyCookies(view, true)
                                newWebView?.setWebChromeClient(this);
                                view?.addView(newWebView)
                                val transport = resultMsg?.obj as? WebView.WebViewTransport
                                transport?.webView = newWebView
                                resultMsg?.sendToTarget()

                                return true
                            }


                            override fun onCloseWindow(window: WebView?) {
                                super.onCloseWindow(window)

                                (window?.parent as? ViewGroup)?.removeView(window)
                                window?.destroy()
                            }
                        }

                        val cookieManager = CookieManager.getInstance()

                        // Enable cookies
                        cookieManager.setAcceptCookie(true)

                        // If you want to allow third-party cookies (optional)
                            // Pass the WebView instance directly
                        cookieManager.setAcceptThirdPartyCookies(webView, true)

                        //webView.loadUrl(url)
                        if (loadedUrl.value != url) {
                            Log.d("KYCWebView", "1 Loading new URL: $url")
                            loadedUrl.value = url
                            webView.loadUrl(url)
                        } else {
                            Log.d("KYCWebView", "1 Loading new URL, skipping: $url")
                        }
                    }
                )
            }
        }
    }
}


