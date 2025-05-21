package com.github.sugunasriram.fisloanlibv4.fis_code.views.webview.personalLoan

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TopBar
import com.github.sugunasriram.fisloanlibv4.fis_code.components.WebViewTopBar
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToPrePaymentStatusScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PrePartPaymentWebView(
    navController: NavHostController, url: String, orderId: String, status: String,
    fromFlow: String
) {
    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        sseViewModel.startListening(ApiPaths().sse)
    }

    BackHandler { navigateToLoanDetailScreen(
        navController = navController, orderId = orderId, fromFlow = fromFlow,
        fromScreen = "PrePart Payment WebView"
    )  }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            navController = navController,
            onBackClick = {navigateToLoanDetailScreen(
                navController = navController, orderId = orderId, fromFlow = fromFlow,
                fromScreen = "PrePart Payment WebView"
            )  },
            topBarText = stringResource(R.string.pre_payment)
        )
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    configureWebViewSettings()
                    // Enable hardware acceleration for better performance
                    setLayerType(View.LAYER_TYPE_HARDWARE, null)

                    settings.setGeolocationEnabled(true)
                    settings.setJavaScriptEnabled(true)
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

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = createWebViewClient(
                        navController = navController, orderId = orderId,
                        status = status, fromFlow = fromFlow
                    )
                    webChromeClient = createWebChromeClient()
                }
            },
            update = { webView ->
                webView.layoutParams = ViewGroup.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )

                // Enable cookies
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(webView, true)
                webView.loadUrl(url)
            }
        )
    }

    val handler = remember {
        Handler(Looper.getMainLooper()).apply {
            postDelayed({
                if (sseEvents.isEmpty()) {
                    navigateToLoanDetailScreen(navController = navController, orderId = orderId, fromFlow = fromFlow,
                        fromScreen = "PrePart Payment WebView")
                }
            }, 3 * 60 * 1000)
        }
    }


    // Handle the events and navigate based on the presence of events
    LaunchedEffect(sseEvents) {
        if (sseEvents.isNotEmpty()) {
            handler.removeCallbacksAndMessages(null)
            try {

                val sseData: SSEData? = try {
                    json.decodeFromString<SSEData>(sseEvents)
                } catch (e: Exception) {
                    Log.e("SSEParsingError", "Error parsing SSE data", e)
                    null
                }

                sseData?.let {
                    it.data?.let {
                        it.data?.let { data ->
                            if (data.id == orderId) {
                                sseViewModel.stopListening()
                                navigateToPrePaymentStatusScreen(
                                    navController = navController,
                                    orderId = orderId,
                                    headerText = status,
                                    fromFlow = fromFlow
                                )
                            }
                        }
                    }
                }

                Log.d("Prepayment", "sseData : ${sseData}")


            } catch (e: Exception) {
                Log.e("SSEParsingError", "Error parsing SSE data", e)
            }
        }

    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun WebView.configureWebViewSettings() {
    settings.apply {
        javaScriptEnabled = true
        safeBrowsingEnabled = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        cacheMode = WebSettings.LOAD_DEFAULT
        domStorageEnabled = true
        allowFileAccess = true
        allowContentAccess = true
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true
        javaScriptCanOpenWindowsAutomatically = true
        setSupportMultipleWindows(true)
    }
}

private fun createWebViewClient(
    navController: NavHostController, orderId: String, status: String, fromFlow: String
) = object : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            handleUrlLoading(navController, orderId, status, url, fromFlow = fromFlow)
            view?.loadUrl(it)
            return true
        }
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.evaluateJavascript("notifyAppFinished();", null)
    }
}

private fun handleUrlLoading(
    navController: NavHostController, orderId: String, status: String, url: String,
    fromFlow: String
) {
    val delay = 5000L
    if (url.contains("https://pramaan.ondc.org/beta/staging/mock/seller/toPaymentUtility?")) {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToPrePaymentStatusScreen(
                navController = navController, orderId = orderId, headerText = status,
                fromFlow = fromFlow
            )
        }, delay)
    }

    if (url.contains("https://pramaan.ondc.org/beta/staging/mock/seller/toPaymentUtility?amount=451300&order_id=f695e959-3ae2-4053-b5a3-7df64c577e50&label=FORECLOSURE")) {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToPrePaymentStatusScreen(
                navController = navController, orderId = orderId, headerText = status,
                fromFlow = fromFlow
            )
        }, delay)
    }
}

private fun createWebChromeClient() = object : WebChromeClient() {
    override fun onCreateWindow(
        view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
    ): Boolean {
        val newWebView = view?.context?.let { WebView(it) }
        newWebView?.apply {
            configureWebViewSettings()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView, request: WebResourceRequest
                ): Boolean {
                    val url = request.url.toString()
                    if (view != null && url != null) {
                        Log.d("WebView", "1 Sugu URL: $url")
                        view.loadUrl(url)

                        return false
                    } else {
                        Log.d("WebView", "0 Sugu request: $request")

                        return super.shouldOverrideUrlLoading(view, request)
                    }
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

            webSettings.setSupportZoom(true)
            webSettings.builtInZoomControls = true
            webSettings.displayZoomControls = false

            webSettings.loadWithOverviewMode = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            webSettings.cacheMode = WebSettings.LOAD_DEFAULT
            webSettings.useWideViewPort = true
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(newWebView, true)
        cookieManager.setAcceptThirdPartyCookies(view, true)
        newWebView?.setWebChromeClient(this);

        view?.addView(newWebView)
        (resultMsg?.obj as? WebView.WebViewTransport)?.webView = newWebView
        resultMsg?.sendToTarget()
        return true
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
        (window?.parent as? ViewGroup)?.removeView(window)
        window?.destroy()
    }
}
