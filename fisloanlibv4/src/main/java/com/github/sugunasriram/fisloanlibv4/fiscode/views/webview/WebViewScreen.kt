package com.github.sugunasriram.fisloanlibv4.fiscode.views.webview

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
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TopBar
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAAConsentApprovalScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToConsentSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormRejectedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OffersWithRejections
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.RejectedLenders
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.OfferLoaderScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("ResourceType")
@Composable
fun SearchWebViewScreen(
    navController: NavHostController,
    purpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String,
    lenderStatusData:String
) {
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

    val loadedUrl = remember { mutableStateOf<String?>(null) }
    var backPressedTime by remember { mutableLongStateOf(0L) }
    var isBackPressLocked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 3000) {
//            navigateApplyByCategoryScreen(navController)
            navigateToFISExitScreen(navController, loanId="1234")
        } else {
            CommonMethods().toastMessage(
                context = context,
                toastMsg ="Hold on! Finvu consent is in progress"
            )
            backPressedTime = currentTime
            isBackPressLocked = true

            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                isBackPressLocked = false
            }
        }
    }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
        else -> {
            AAWebScreen(
                navController = navController,
                transactionId = transactionId,
                urlToOpen =
                url,
                searchId = id,
                fromFlow = fromFlow,
                loadedUrl = loadedUrl,
                pageContent = {},lenderStatusData=lenderStatusData
            )
        }
    }
}

@Composable
fun NavigateToWebView(
    context: Context,
    searchModel: SearchModel?,
    fromFlow: String,
    navController: NavHostController,
    gstSearchResponse: GstSearchResponse?,
    searchResponse: SearchModel?
) {
    val webViewModel: WebViewModel = viewModel()
    val lenderStatusProgress by webViewModel.lenderStatusProgress.collectAsState()
    val lenderStatusLoaded by webViewModel.lenderStatusLoaded.collectAsState()
    val lenderStatusResponse by webViewModel.getLenderStatusResponse.collectAsState()
    val showInternetScreen by webViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by webViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by webViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by webViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by webViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val errorMessage by webViewModel.errorMessage.collectAsState()
    val navigationToSignIn by webViewModel.navigationToSignIn.collectAsState()

//    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
//        LaunchedEffect(Unit) {
//            webViewModel.getLenderStatusApi(
//                context = context,
//                loanType = "PERSONAL_LOAN",
//                step = "CONSENT_SELECT"
//            )
//        }
//
//        when{
//            navigationToSignIn -> navigateSignInPage(navController)
//            showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
//            showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
//            showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
//            unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
//            unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
//            middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
//            lenderStatusProgress -> CenterProgress()
//            lenderStatusLoaded -> {
//                val lenderStatus = lenderStatusResponse?.data?.response
//                if (lenderStatus.isNullOrEmpty()) {
//                    NoLoanOffersAvailableScreen(navController, titleText = stringResource(R.string.no_lenders_available))
//                } else {
//                    LaunchedEffect(lenderStatus) {
//                        try {
//                            val json = Json {
//                                prettyPrint = true
//                                ignoreUnknownKeys = true
//                            }
//
//                            val lenderStatusJson = json.encodeToString(
//                                LenderStatusResponse.serializer(),
//                                LenderStatusResponse(response = lenderStatus)
//                            )
//                            val transactionId = searchResponse?.data?.transactionId
//                            val searchId = searchResponse?.data?.id
//                            val webUrl = searchResponse?.data?.url
//
//                            if (searchId != null) {
//                                if (transactionId != null) {
//                                    if (webUrl != null) {
//                                        navigateToWebViewFlowOneScreen(
//                                            navController = navController,
//                                            purpose = context.getString(R.string.getUerFlow),
//                                            fromFlow = fromFlow,
//                                            id = searchId,
//                                            transactionId = transactionId,
//                                            url = webUrl,
//                                            lenderStatusData = lenderStatusJson
//                                        )
//                                    }
//                                }
//                            }
//
//                        } catch (e: Exception) {
//                            Log.e("JsonError", "Failed to serialize lender status", e)
//                        }
//                    }
//                }
//            }
//        }
//    }
     if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
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
                            url = url, lenderStatusData = ""
                        )
                    }
                }
            }
        }
    }
     else  {
//        if(fromFlow.equals("Purchase Finance", ignoreCase = true))
        LaunchedEffect(Unit) {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType =if(fromFlow.equals("Personal Loan", ignoreCase = true)) "PERSONAL_LOAN"
                else "PURCHASE_FINANCE",
                step = "CONSENT_SELECT"
            )
        }

        when{
            navigationToSignIn -> navigateSignInPage(navController)
            showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
            showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
            showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
            unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
            unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
            middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
            lenderStatusProgress -> CenterProgress()
            lenderStatusLoaded -> {
                val lenderStatus = lenderStatusResponse?.data?.response
                if (lenderStatus.isNullOrEmpty()) {
                    NoLoanOffersAvailableScreen(navController, titleText = stringResource(R.string.no_lenders_available))
                } else {
                    LaunchedEffect(lenderStatus) {
                        try {
                            val json = Json {
                                prettyPrint = true
                                ignoreUnknownKeys = true
                            }

                            val lenderStatusJson = json.encodeToString(
                                LenderStatusResponse.serializer(),
                                LenderStatusResponse(response = lenderStatus)
                            )
                            val transactionId = searchResponse?.data?.transactionId
                            val searchId = searchResponse?.data?.id
                            val webUrl = searchResponse?.data?.url

                            if (searchId != null) {
                                if (transactionId != null) {
                                    if (webUrl != null) {
                                        navigateToWebViewFlowOneScreen(
                                            navController = navController,
                                            purpose = context.getString(R.string.getUerFlow),
                                            fromFlow = fromFlow,
                                            id = searchId,
                                            transactionId = transactionId,
                                            url = webUrl,
                                            lenderStatusData = lenderStatusJson
                                        )
                                    }
                                }
                            }

                        } catch (e: Exception) {
                            Log.e("JsonError", "Failed to serialize lender status", e)
                        }
                    }
                }
            }
        }
    }
}
private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}
private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

@SuppressLint("SetJavaScriptEnabled", "ResourceType")
@Composable
fun AAWebScreen(
    isSelfScrollable: Boolean = false,
    transactionId: String,
    navController: NavHostController,
    urlToOpen: String,
    searchId: String,
    fromFlow: String,
    loadedUrl: MutableState<String?>,
    lenderStatusData:String,
    pageContent: () -> Unit,

    ) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    var isBackPressLocked by remember { mutableStateOf(false) }
    var lateNavigate by remember { mutableStateOf(false) }
    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val errorTitle = stringResource(id = R.string.consent_failed)
    var showLoader by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { sseViewModel.startListening(ApiPaths().sse) }

    LaunchedEffect(Unit) {
        delay(3 * 60 * 1000L)
        if (sseEvents.isEmpty() && !lateNavigate) {
            Toast.makeText(
                context,
                "Waiting for lender response, please wait...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (sseEvents.isNotEmpty()) {
        Log.d("AAConsent", "SSE entered")
        val sseData: SSEData? = try {
            json1.decodeFromString<SSEData>(sseEvents)
        } catch (e: Exception) {
            Log.e("AAConsent", "Error parsing SSE data", e)
            null
        }
        Log.d("AAConsent", "SSEData: $sseData")

        if (sseData != null) {
            val sseTransactionId = sseData.data?.data?.txnId
            val type = sseData.data?.data?.type
            Log.d(
                "AAConsent",
                "transactionId :[$transactionId] " +
                        "sseTransactionId:[$sseTransactionId], type=[$type]"
            )

            if (transactionId == sseTransactionId) {
                // match transaction
                lateNavigate = true

                val errorObj = sseData.data.data.error
                if (errorObj != null) {
                    Log.d("AAConsent", "Error: ${errorObj.message}")
                    errorMsg = errorObj.message
                    sseViewModel.stopListening()
                    errorMsg?.let { CommonMethods().toastMessage(context = context, toastMsg = it) }
//                    sseViewModel.clearEvent()
                    navigateToFormRejectedScreen(
                        navController = navController,
                        errorTitle = errorTitle,
                        fromFlow = fromFlow,
                        errorMsg = errorMsg
                    )
                } else {
                    when (sseData.data.data.type) {
                        "INFO" -> {
                            Log.d("AAConsent", "Showing loader for info type")
                            showLoader = true
                        }

                        "ACTION" -> {
                            Log.d("AAConsent", "Navigating LoanOffers for action type")
                            val offers = sseData.data.data.offers
                            Log.d("AAConsent", "Offers : $offers")

                            val rejectedLenders = sseData.data.data.rejectedLenders
                            Log.d("AAConsent", "rejectedLenders : $rejectedLenders")

                            if (!offers.isNullOrEmpty()) {
                                val offerList = offers.map { offerCatalog ->
                                    Offer(
                                        offer = offerCatalog.offer,
                                        id = offerCatalog.id,
                                        bureauConsent = offerCatalog.bureauConsent,
                                    )
                                }
                                val rejectedList = rejectedLenders?.map {
                                    RejectedLenders(
                                        name = it.name,
                                        image = it.image,
                                        reason = it.reason,
                                        minLoanAmount = it.minLoanAmount,
                                        maxLoanAmount = it.maxLoanAmount,
                                        maxInterestRate = it.maxInterestRate,
                                        minInterestRate = it.minInterestRate
                                    )
                                }
                                val combined = OffersWithRejections(
                                    offers = offerList,
                                    rejectedLenders = rejectedList
                                )

                                val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
                                val jsonString = json.encodeToString(combined)

                                showLoader = false
                                sseViewModel.stopListening()
                                navigateToLoanOffersListScreen(navController, jsonString, fromFlow)
                            }
                        }
                        else -> {
                            Log.d("AAConsent", "Unknown type: $type, ignoring")
                        }
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            navController = navController,
            onBackClick = {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime < 3000) {
//                    navigateApplyByCategoryScreen(navController)
                    navigateToFISExitScreen(navController, loanId="1234")
                } else {
                    CommonMethods().toastMessage(
                        context = context,
                        toastMsg ="Hold on! Finvu consent is in progress"
                    )
                    backPressedTime = currentTime
                    isBackPressLocked = true

                    // Unlock back press after 3 seconds
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        isBackPressLocked = false
                    }
                }
            },
            topBarText = stringResource(R.string.account_aggregator_consent)
        )
        if (isSelfScrollable) {
            Column(modifier = Modifier.weight(1f)) { pageContent() }
        } else if (showLoader) {
            OfferLoaderScreen(navController=navController, lenderStatusData=lenderStatusData, isBureauOffers = false)
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
//                                        if (it == BuildConfig.CONSENT_CALLBACK_REDIRECT_URL) {
                                        if (it == "https://stagingondcfs.jtechnoparks.in/jt-bap/api/v1/finvu/consent-callback") {
//                                            navigateApplyByCategoryScreen(navController)
                                            navigateToFISExitScreen(navController, loanId="1234")
                                            return true
                                        }
                                        Log.d("SearchWebViewScreen", "urlToOpen: $it")

                                        view?.loadUrl(it)
                                    }
                                    return false
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                }
                            }
                        }
                    },
                    update = { webView ->
                        webView.settings.setGeolocationEnabled(true)
                        webView.settings.setJavaScriptEnabled(true)
                        // Sugu - need to test with other lender, commented for Lint
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
                            override fun onCreateWindow(
                                view: WebView?,
                                isDialog: Boolean,
                                isUserGesture: Boolean,
                                resultMsg: Message?
                            ): Boolean {
                                val newWebView = view?.context?.let { WebView(it) }
                                newWebView?.webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        innerView: WebView,
                                        request: WebResourceRequest
                                    ): Boolean {
                                        return super.shouldOverrideUrlLoading(innerView, request)
                                    }
                                    override fun shouldOverrideUrlLoading(
                                        innerView: WebView?,
                                        url: String?
                                    ): Boolean {
                                        return false
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
                        val cookieManager = CookieManager.getInstance()

                        // Enable cookies
                        cookieManager.setAcceptCookie(true)

                        // If you want to allow third-party cookies (optional)
                        // Pass the WebView instance directly
                        cookieManager.setAcceptThirdPartyCookies(webView, true)

//                        urlToOpen.let {
//                            Log.d("SearchWebViewScreen", "3 urlToOpen: $it")
//                            webView.loadUrl(it)
//                        }
                        // webView.loadUrl(url)
                        if (loadedUrl.value != urlToOpen) {
                            Log.d("SearchWebViewScreen", "1 Loading new URL: $urlToOpen")
                            loadedUrl.value = urlToOpen
                            webView.loadUrl(urlToOpen)
                        } else {
                            Log.d("SearchWebViewScreen", "1 Loading new URL, skipping: $urlToOpen")
                        }
                    }
                )
            }
        }
    }
}
