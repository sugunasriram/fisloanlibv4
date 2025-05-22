package com.github.sugunasriram.fisloanlibv4.fis_code.views.webview

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TopBar
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToAAConsentApprovalScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.WebViewModel

@SuppressLint("ResourceType")
@Composable
fun SearchWebViewScreen(
    navController: NavHostController,
    purpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String
) {
    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    val webViewModel: WebViewModel = viewModel()

    val gstSearchResponse by webViewModel.gstSearchResponse.collectAsState()
    val navigationToSignIn by webViewModel.navigationToSignIn.collectAsState()

    val showInternetScreen by webViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by webViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by webViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by webViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by webViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val errorMessage by webViewModel.errorMessage.collectAsState()

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            AAWebScreen(
                navController = navController,
                transactionId = transactionId,
                urlToOpen =
                url,
                searchId = id,
                fromFlow = fromFlow,
                pageContent = {}
            )

//            if (webScreenLoading.value) {
//                LoaderAnimation(
//                    image = R.raw.we_are_currently_processing,
//                    updatedImage = R.raw.verify_monitoring_consent_success,
//                    showTimer = true
//                )
//            } else {
//                val endUse = if (purpose.equals("Other Consumption Purpose", ignoreCase = true))
//                    "other"
//                else if (purpose.equals("Consumer Durable Purchase", ignoreCase = true))
//                    "consumerDurablePurchase"
//                else purpose.lowercase()
//
//                if (!webScreenLoaded.value) {
//                    loadWebScreen(
//                        fromFlow = fromFlow, webViewModel = webViewModel, context = context,
//                        endUse = endUse, purpose = purpose
//                    )
//                } else {
// //                    NavigateToWebView(
// //                        searchResponse = searchResponse, gstSearchResponse = gstSearchResponse,
// //                        fromFlow = fromFlow, navController = navController
// //                    )
// //                    searchResponse?.let { search ->
// //                        if(search.data?.offers == true){
// //                            search.data.transactionId?.let { transactionId ->
// //                                navigateToLoanProcessScreen(
// //                                    navController, transactionId = transactionId,
// //                                    statusId = 2, responseItem = "No Need Response Item", offerId = "1234",
// //                                    fromFlow = fromFlow
// //                                )
// //                            }
// //                        }
// //                        else{
// //                            NavigateToWebView(
// //                                searchResponse = searchResponse, gstSearchResponse = gstSearchResponse,
// //                                fromFlow = fromFlow, navController = navController
// //                            )
// //                        }
// //                    }
//
//                    searchResponse?.let { search ->
//                        if(!search.data?.url.isNullOrEmpty()){
//                            NavigateToWebView(
//                                searchResponse = search, gstSearchResponse = gstSearchResponse,
//                                fromFlow = fromFlow, navController = navController
//                            )
//                        }
//                        else {
//                            if (search.data?.offers == true) {
//                                search.data.transactionId?.let { transactionId ->
//                                    navigateToLoanProcessScreen(
//                                        navController,
//                                        transactionId = transactionId,
//                                        statusId = 2,
//                                        responseItem = "No Need Response Item",
//                                        offerId = "1234",
//                                        fromFlow = fromFlow
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
        }
    }
}

@Composable
fun NavigateToWebView(
    searchModel: SearchModel?,
    fromFlow: String,
    navController: NavHostController,
    gstSearchResponse: GstSearchResponse?,
    searchResponse: SearchModel?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        searchModel.let { search ->
            search?.data?.transactionId?.let { transactionId ->
                search?.data?.id?.let { id ->
                    search.data.url?.let { url ->
                        navigateToWebViewFlowOneScreen(
                            navController = navController,
                            purpose = stringResource(R.string.getUerFlow),
                            fromFlow = fromFlow,
                            id = id,
                            transactionId = transactionId,
                            url = url
                        )
                    }
                }
            }
        }
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        gstSearchResponse.let { search ->
            search?.data?.transactionId?.let { transactionId ->
                search?.data?.id?.let { id ->
                    search.data.url?.let { url ->
                        navigateToWebViewFlowOneScreen(
                            navController = navController,
                            purpose = stringResource(R.string.getUerFlow),
                            fromFlow = fromFlow,
                            id = id,
                            transactionId = transactionId,
                            url = url
                        )
                    }
                }
            }
        }
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        searchResponse?.let { search ->
            search.data?.transactionId?.let { transactionId ->
                search.data?.id?.let { id ->
                    search.data.url?.let { url ->
                        navigateToWebViewFlowOneScreen(
                            navController = navController,
                            purpose = stringResource(R.string.getUerFlow),
                            fromFlow = fromFlow,
                            id = id,
                            transactionId = transactionId,
                            url = url
                        )
                    }
                }
            }
        }
    }
}

fun loadWebScreen(
    fromFlow: String,
    webViewModel: WebViewModel,
    context: Context,
    endUse: String,
    purpose: String
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        webViewModel.searchApi(
            context = context,
            searchBodyModel = SearchBodyModel(
                loanType = "PERSONAL_LOAN",
                endUse = endUse,
                bureauConsent = "on"
            )
        )
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        // Here when it in the Gst Invoice Loan purpose equals to Invoice ID
        webViewModel.searchGst(
            gstSearchBody = GstSearchBody(
                loanType = "INVOICE_BASED_LOAN",
                bureauConsent = "on",
                tnc = "on",
                id = purpose
            ),
            context = context
        )
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        webViewModel.financeSearch(
            financeSearchModel = FinanceSearchModel(
                loanType = "PURCHASE_FINANCE", bureauConsent = "on", tnc = "on", endUse = "travel",
                downpayment = purpose, merchantGst = "24AAHFC3011G1Z4", merchantPan = "EGBQA2212D",
                isFinancing = "on", merchantBankAccountNumber = "639695357641006",
                merchantIfscCode = "XRSY0YPV5SW", merchantBankAccountHolderName = "mohan",
                productCategory = "fashion", productBrand = "style", productSKUID = "12345678",
                productPrice = "1000"
            ),
            context = context
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AAWebScreen(
    isSelfScrollable: Boolean = false,
    transactionId: String,
    navController: NavHostController,
    urlToOpen: String,
    searchId: String,
    fromFlow: String,
    pageContent: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            navController = navController,
            onBackClick = { navigateApplyByCategoryScreen(navController) },
            topBarText = stringResource(R.string.account_aggregator_consent)
        )
        if (isSelfScrollable) {
            Column(modifier = Modifier.weight(1f)) { pageContent() }
        } else {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            // Sugu - need to test with other lender, commented for Lint
                            settings.javaScriptEnabled = true
                            settings.setSupportMultipleWindows(true)

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
                            setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, false)

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
                                        if (it.startsWith("https://stagingondcfs.jtechnoparks.in/ondc/buyer-finance/consent-callback/")) {
                                            navigateToAAConsentApprovalScreen(
                                                navController = navController,
                                                searchId = searchId,
                                                url = url,
                                                fromFlow = fromFlow
                                            )
                                            return true
                                        }
                                        Log.d("SearchWebViewScreen", "urlToOpen: $it")

                                        view?.loadUrl(it)
                                    }
                                    return true
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                }
                            }
                        }
                    },
                    update = { webView ->
                        webView.webChromeClient = object : WebChromeClient() {
                            override fun onCreateWindow(
                                view: WebView?,
                                isDialog: Boolean,
                                isUserGesture: Boolean,
                                resultMsg: Message?
                            ): Boolean {
                                val newWebView = view?.context?.let { WebView(it) }
                                newWebView?.webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        view: WebView,
                                        request: WebResourceRequest
                                    ): Boolean {
                                        val url = request.url.toString()
                                        if (view != null && url != null) {
                                            Log.d("SearchWebViewScreen", "1 urlToOpen: $url")

                                            view.loadUrl(url)
                                            return false
                                        } else {
                                            Log.d("SearchWebViewScreen", "2 urlToOpen: $url")

                                            return super.shouldOverrideUrlLoading(view, request)
                                        }
                                    }
                                }
                                newWebView?.settings?.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    setAllowFileAccess(true)
                                    setAllowContentAccess(true)
                                    setAllowFileAccessFromFileURLs(true)
                                    setAllowUniversalAccessFromFileURLs(true)
                                    setSupportMultipleWindows(true)
                                    setJavaScriptCanOpenWindowsAutomatically(true)
                                    setGeolocationEnabled(true)
                                    loadsImagesAutomatically = true
                                    loadWithOverviewMode = true
                                    javaScriptCanOpenWindowsAutomatically = true
                                    cacheMode = WebSettings.LOAD_DEFAULT
                                }
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
                                newWebView?.setWebChromeClient(this)
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
                        urlToOpen.let {
                            Log.d("SearchWebViewScreen", " urlToOpen: $it")
                            webView.loadUrl(it)
                        }
                    }
                )
            }
        }
    }
}
