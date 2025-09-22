package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.LoanLib
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoanDisburseAnimator
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.SpaceBetweenTextIcon
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanSummaryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CreateSessionRequest
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CreateSessionRequestData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.Catalog
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.DataContent
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.EventData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.Price
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEQuoteBreakUp
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayBackground
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal36Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.slateGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.ConsentHandlerScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.DecimalFormat
import java.time.Duration
import java.time.Period
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.max

private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

var loanId = ""

@SuppressLint("ResourceType")
@Composable
fun LoanDisbursementScreen(
    navController: NavHostController,
    transactionId: String,
    id: String,
    fromFlow: String
) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current
    val loanAgreementViewModel: LoanAgreementViewModel by lazy { LoanAgreementViewModel() }
    val consentHandled by loanAgreementViewModel.consentHandled.collectAsState()
    val sseDataForPf by loanAgreementViewModel.sseData.collectAsState()
    var apiTriggered by remember { mutableStateOf(false) }
    val pfCreateSessionInProgress by loanAgreementViewModel.pfCreateSessionInProgress.collectAsState(initial = false)
//    val sessionId by loanAgreementViewModel.sessionId.collectAsState("")
    val uiState by loanAgreementViewModel.createSessionState.collectAsStateWithLifecycle()


    val coroutineScope = rememberCoroutineScope()

//    BackHandler { navigateApplyByCategoryScreen(navController) }
    BackHandler {
        loanAgreementViewModel.createPfSession(loanId, context)
    }


    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        sseViewModel.startListening(ApiPaths().sse)
    }


    val downpaymentAmountValue = remember { mutableStateOf<String?>(null) }
    val loanTenureValue = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        downpaymentAmountValue.value = TokenManager.read("downpaymentAmount")
        loanTenureValue.value = TokenManager.read("pfloanTenure")
        Log.d(
            "LoanDisbursementScreen",
            "Sugu downpaymentAmountValue: ${downpaymentAmountValue.value}, loanTure: " +
                    "${loanTenureValue.value}"
        )
    }

    // One-shot side effect on SUCCESS
    LaunchedEffect(uiState) {
//        val loanAmount = sseData.data?.data?.catalog?.item_price?.value  // or your fields
        when (uiState) {
            is LoanAgreementViewModel.CreateSessionUiState.Success -> {
                val downpaymentAmountVal = downpaymentAmountValue.value?.toIntOrNull() ?: 0
                val loanTenureVal = loanTenureValue.value?.toIntOrNull() ?: 0
                val sessionId = (uiState as LoanAgreementViewModel.CreateSessionUiState.Success).sessionId
                val details = LoanLib.LoanDetails(
                    sessionId = sessionId,
                    interestRate = interestRate?.toDoubleOrNull() ?: 0.0,
                    loanAmount =loanAmount?.toDoubleOrNull() ?: 0.0,
                    tenure = loanTenureVal?.toInt()?:0,
                    downpaymentAmount = downpaymentAmountVal
                )
                LoanLib.callback?.invoke(details)
                (context as? Activity)?.finish()
            }
            else -> Unit
        }
    }

    if (sseDataForPf != null) {
        android.util.Log.d("SSETRIGGER", "Inside  MoveToDashBoard")
        sseDataForPf?.let { data ->
            MoveToDashBoard(
                navController = navController,
                id = id,
                fromFlow = fromFlow,
                sseData = data,
                loanAgreementViewModel = loanAgreementViewModel,
                downpaymentAmountValue = downpaymentAmountValue,
                loanTenureValue = loanTenureValue,
                pfCreateSessionInProgress = pfCreateSessionInProgress,
                context = context
            )
        }
    }

    val sseData: SSEData? = try {
        if (sseEvents.isNotEmpty()) {
            json1.decodeFromString(sseEvents)
        } else {
            null
        }
    } catch (e: SerializationException) {
        Log.e("LoanDisbursementScreen", "Failed to parse SSE events: $sseEvents", e)
        null
    }
    val type = sseData?.data?.data?.type
    var sseTransactionId = sseData?.data?.data?.txnId ?: sseData?.data?.data?.transactionId
        ?: sseData?.data?.data?.catalog?.txn_id

    if (sseData == null || type == "INFO"  || pfCreateSessionInProgress) {
        ProcessingAnimation(
            text = "Processing Please Wait...",
            image = R.raw.we_are_currently_processing_hour_glass
        )
    } else {
        Log.d(
            "LoanDisbursement:",
            "transactionId :[" + transactionId + "] " +
                "sseTransactionId:[" + sseTransactionId
        )
        if (transactionId == sseTransactionId && type == "ACTION") {
            sseData.data.data.type.let { actionType ->
                sseData.data.data.consent?.let { consent ->

                    android.util.Log.d("SSETRIGGER", "START")
                    if (fromFlow == "Purchase Finance") {
                        android.util.Log.d("SSETRIGGER", "INSIDE fromFlow")
                        loanAgreementViewModel.updateSSEData(sseData)
                        apiTriggered = true
                    } else if (transactionId == sseTransactionId && actionType == "ACTION" && consent) {
                        MoveToConsentHandlerScreen(
                            sseData = sseData,
                            navController = navController,
                            fromFlow = fromFlow
                        )
                    } else {
                        MoveToDashBoard(
                            navController = navController,
                            id = id,
                            fromFlow = fromFlow,
                            sseData = sseData,
                            loanAgreementViewModel = loanAgreementViewModel,
                            downpaymentAmountValue = downpaymentAmountValue,
                            loanTenureValue = loanTenureValue,
                            pfCreateSessionInProgress = pfCreateSessionInProgress,
                            context = context
                        )
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            sseViewModel.stopListening()
        }
    }

    LaunchedEffect(apiTriggered) {
        if (apiTriggered) {
            android.util.Log.d("SSETRIGGER", "API TRIGGERED")
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE",
                    id = sseDataForPf?.data?.data?.id?:sseDataForPf?.data?.data?.catalog?.id ?: "",
                    amount = "",
                    consentStatus = "DELIVERED",
                    loanType = "PURCHASE_FINANCE"
                ),
                context = context
            )
        }
    }
}

var loanAmount: String? = "0.0"
var interestRate: String? = "0.0"
var tenure: String? = "0"

//@Composable
//fun MoveToDashBoard(
//    navController: NavHostController,
//    id: String,
//    fromFlow: String,
//    sseData: SSEData,
//    coroutineScope: CoroutineScope = rememberCoroutineScope(),
//    loanAgreementViewModel: LoanAgreementViewModel,
//    downpaymentAmountValue: MutableState<String?> = mutableStateOf(null),
//    pfCreateSessionInProgress: Boolean,
//    context: Context
//) {
//    var backPressedTime by remember { mutableLongStateOf(0L) }
//    FixedTopBottomScreen(
//        navController = navController,
//        topBarBackgroundColor = appOrange,
//        topBarText = stringResource(R.string.loan_status),
//        showBackButton = true,
//        onBackClick = { navigateApplyByCategoryScreen(navController) },
//        showBottom = true,
//        showSingleButton = true,
//        primaryButtonText = stringResource(R.string.home),
//        onPrimaryButtonClick = {
//            if (fromFlow == "Purchase Finance") {
////                val downpaymentAmountVal = downpaymentAmountValue.value?.toIntOrNull() ?: 0
////
////                CreateSessionRequestData(
////                    downPaymentAmount = downpaymentAmountValue.value,
////                    loanId = sseData.data?.data?.id?.toString() ?: "0"
////                ).let { requestData ->
////                    loanAgreementViewModel.pfRetailSendDetails(
////                        createSessionRequest = CreateSessionRequest(
////                            type = "RET",
////                            subType = "ORDER_DETAILS",
////                            id = requestData.loanId,
////                            message = null
////                        ),
////                        context = context
////                    )
////                }
////                coroutineScope.launch {
////                    delay(5000)
////                }
////                Log.d(
////                    "LoanDisbursementScreen",
////                    "Sugu downpaymentAmountVal: $downpaymentAmountVal, loanAmount: $loanAmount, " +
////                            "interestRate: $interestRate, tenure: $tenure"
////                )
////                LoanLib.callback?.invoke(LoanLib.LoanDetails(interestRate = interestRate?.toDoubleOrNull() ?: 0.0,
////                    loanAmount = loanAmount?.toDoubleOrNull() ?: 0.0,
////                    tenure = tenure?.toInt()?:0, downpaymentAmount = downpaymentAmountVal))
////                (context as? Activity)?.finish()
//
//
//                //2nd try
////                val downpaymentAmountVal = downpaymentAmountValue.value?.toIntOrNull() ?: 0
////
////                val requestData = CreateSessionRequestData(
////                    downPaymentAmount = downpaymentAmountValue.value,
////                    loanId = sseData.data?.data?.id?.toString() ?: "0"
////                )
////
////                coroutineScope.launch {
////                    val runId = System.currentTimeMillis()
////                    try {
////                        Log.d("Sugu", "[$runId] calling pfRetailSendDetails…")
////                        val response = loanAgreementViewModel.pfRetailSendDetails(
////                            createSessionRequest = CreateSessionRequest(
////                                type = "RET",
////                                subType = "ORDER_DETAILS",
////                                id = requestData.loanId,
////                                message = null
////                            ),
////                            context = context
////                        )
////
////
////                        Log.d("LoanDisbursementScreen", "Got response: $response")
////                        Log.d("LoanDisbursementScreen", "loanAmount: $loanAmount")
////                        Log.d("LoanDisbursementScreen", "interestRate: $interestRate")
////                        Log.d("LoanDisbursementScreen", "downpaymentAmount: $downpaymentAmountVal")
////                        Log.d("LoanDisbursementScreen", "tenure: $tenure")
////                        // Safely extract sessionId
////                        Log.d("LoanDisbursementScreen", "A: Got response: $response")
////                        Log.d("CreateSession", "A1: response class = ${response?.javaClass?.name}")
////                        Log.d("CreateSession", "A2: data.id = ${response?.data?.id}")
////
////                        val sessionId = response?.data?.id?.trim().orEmpty()
////                        Log.d("CreateSession", "A3: sessionId='$sessionId'")
////
////
////                        Log.d("CreateSession", "A3: sessionId='$sessionId'")
////
////                        // Use response
////    //                    LoanLib.callback?.invoke(
////    //                        LoanLib.LoanDetails(
////    //                            sessionId = sessionId ?: "",
////    //                            interestRate = interestRate?.toDoubleOrNull() ?: 0.0,
////    //                            loanAmount = loanAmount?.toDoubleOrNull() ?: 0.0,
////    //                            tenure = tenure?.toInt() ?: 0,
////    //                            downpaymentAmount = downpaymentAmountVal
////    //                        )
////    //                    )
////
////    //                    (context as? Activity)?.finish()
////
////
////                        val details = LoanLib.LoanDetails(
////                            sessionId = sessionId,
////                            interestRate = interestRate?.toDoubleOrNull() ?: 0.0,
////                            loanAmount = loanAmount?.toDoubleOrNull() ?: 0.0,
////                            tenure = tenure?.toIntOrNull() ?: 0,
////                            downpaymentAmount = downpaymentAmountVal
////                        )
////
////                        withContext(Dispatchers.Main) {
////                            if (sessionId.isNotEmpty()) {
////                                LoanLib.callback?.invoke(details)
////                                (context as? Activity)?.finish()
////                            } else {
////                                Log.w("LoanDisbursementScreen", "[$runId] Empty sessionId — not " +
////                                        "invoking callback")
////                            }
////                        }
////                    } catch (t: CancellationException) {
////                        Log.w("Sugu", "[$runId] cancelled", t)
////                        throw t
////                    } catch (t: Throwable) {
////                        Log.e("Sugu", "[$runId] failed", t)
////                    }
////                }
//
////
//                val downpaymentAmountVal = downpaymentAmountValue.value?.toIntOrNull() ?: 0
//                val requestData = CreateSessionRequestData(
//                    downPaymentAmount = downpaymentAmountValue.value,
//                    loanId = sseData.data?.data?.id?.toString() ?: "0"
//                )
//                coroutineScope.launch {
//                    val runId = System.currentTimeMillis()
//                    try {
//                        Log.d("Sugu", "[$runId] calling pfRetailSendDetails…")
//                        val response = loanAgreementViewModel.pfRetailSendDetails(
//                            createSessionRequest = CreateSessionRequest(
//                                type = "RET",
//                                subType = "ORDER_DETAILS",
//                                id = requestData.loanId,
//                                message = null
//                            ),
//                            context = context
//                        )
//                        Log.d("LoanDisbursementScreen", "Got response: $response")
//                        Log.d("LoanDisbursementScreen", "loanAmount: $loanAmount")
//                        Log.d("LoanDisbursementScreen", "interestRate: $interestRate")
//                        Log.d("LoanDisbursementScreen", "downpaymentAmount: $downpaymentAmountVal")
//                        Log.d("LoanDisbursementScreen", "tenure: $tenure")
//                        // Safely extract sessionId
//                        Log.d("LoanDisbursementScreen", "A: Got response: $response")
//                        Log.d("CreateSession", "A1: response class = ${response?.javaClass?.name}")
//                        Log.d("CreateSession", "A2: data.id = ${response?.data?.id}")
//
//                        val sessionId = response?.data?.id?.trim().orEmpty()
//                        Log.d("CreateSession", "A3: sessionId='$sessionId'")
//
//
//                        Log.d("CreateSession", "A3: sessionId='$sessionId'")
//
//                        val details = LoanLib.LoanDetails(
//                            sessionId = sessionId,
//                            interestRate = interestRate?.toDoubleOrNull() ?: 0.0,
//                            loanAmount = loanAmount?.toDoubleOrNull() ?: 0.0,
//                            tenure = tenure?.toIntOrNull() ?: 0,
//                            downpaymentAmount = downpaymentAmountVal
//                        )
//
//                        withContext(Dispatchers.Main) {
//                            if (sessionId.isNotEmpty()) {
//                                LoanLib.callback?.invoke(details)
//                                (context as? Activity)?.finish()
//                            } else {
//                                Log.w("LoanDisbursementScreen", "[$runId] Empty sessionId — not " +
//                                        "invoking callback")
//                            }
//                        }
//                    } catch (t: CancellationException) {
//                        Log.w("Sugu", "[$runId] cancelled", t)
//                        throw t
//                    } catch (t: Throwable) {
//                        Log.e("Sugu", "[$runId] failed", t)
//                    }
//                }
//            } else {
//                navigateToLoanSummaryScreen(
//                    navController = navController,
//                    id = id,
//                    consentHandler = "1",
//                    fromFlow = fromFlow
//                )
//            }
//
//        },
//        backgroundColor = appWhite
//    ) {
//        LoanDisburseAnimator()
////        val totalDisburseAmount = sseData.data?.data?.catalog?.item_price?.value
//        val totalDisburseAmount = sseData.data?.data?.catalog?.quote_breakup
//            ?.firstOrNull {
//                val formattedTitle = it.title?.let { title -> CommonMethods().displayFormattedText(title) }
//                formattedTitle.equals("Net Disbursed Amount", ignoreCase = true)
//            }
//            ?.value
//        RegisterText(
//            text = stringResource(id = R.string.loan_amount_is_approved),
//            style = normal18Text700,
//            textColor = appBlack,
//            top = 20.dp,
//            bottom = 20.dp
//        )
//
//        if (totalDisburseAmount != null) {
//            RegisterText(
//                text = totalDisburseAmount,
//                style = normal36Text700,
//                textColor = appGreen,
//                bottom = 20.dp
//            )
//        }
//        RegisterText(
//            text = stringResource(id = R.string.amount_disburse_time),
//            style = normal16Text400,
//            textColor = hintGray,
//            start = 20.dp,
//            end = 20.dp
//        )
//        val loanDetails = sseData.data?.data?.catalog
//        if (loanDetails != null) {
//            var loanDetailsStr = stringResource(id = R.string.loan_details) + "\n\n"
//
//            loanDetails.quote_breakup?.forEach { quoteBreakUp ->
//                quoteBreakUp.let {
//                    it.title?.let { title ->
//                        it.value?.let { description ->
//                            loanDetailsStr += title + " : " + description + "\n"
//                        }
//                    }
//                }
//            }
//
//            SpaceBetweenTextIcon(
//                text = stringResource(id = R.string.loan_details).uppercase(),
//                style = normal18Text700,
//                textColor = appOrange,
//                image = R.drawable.share,
//                start = 24.dp,
//                end = 24.dp
//            ) {
//                shareContent(context, loanDetailsStr)
//            }
//            FillTenureAndInterestRate(loanDetails)
//            LoanDisbursementCard(loanDetails)
//        }
//
//        if (pfCreateSessionInProgress) {
//            Log.d("Sugu test", "pfCreateSessionInProgress: showing loader")
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.4f)),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = appOrange)
//            }
//        }
//
//    }
//}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ResourceType")
@Composable
fun MoveToDashBoard(
    navController: NavHostController,
    id: String,
    fromFlow: String,
    sseData: SSEData,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    loanAgreementViewModel: LoanAgreementViewModel,
    downpaymentAmountValue: MutableState<String?> = mutableStateOf(null),
    loanTenureValue: MutableState<String?> = mutableStateOf(null),
    pfCreateSessionInProgress: Boolean,
    context: Context
) {
    var backPressedTime by remember { mutableLongStateOf(0L) }

    val uiState by loanAgreementViewModel.createSessionState.collectAsStateWithLifecycle()

// Show loader if pfCreateSessionInProgress is true
    if (pfCreateSessionInProgress) {
        ProcessingAnimation(
            text = "Processing Please Wait...",
            image = R.raw.we_are_currently_processing_hour_glass
        )
    } else {
        FixedTopBottomScreen(
            navController = navController,
            topBarBackgroundColor = appOrange,
            topBarText = stringResource(R.string.loan_status),
            showBackButton = true,
//        onBackClick = { navigateApplyByCategoryScreen(navController) },
            onBackClick = {
                loanId = sseData.data?.data?.id?.toString().orEmpty()
                loanAgreementViewModel.createPfSession(loanId, context)
            },
            showBottom = true,
            showSingleButton = true,
            primaryButtonText = stringResource(R.string.home),
            onPrimaryButtonClick = {
                if (fromFlow == "Purchase Finance") {

                    loanId = sseData.data?.data?.id?.toString().orEmpty()
                    loanAgreementViewModel.createPfSession(loanId, context)
                } else {
                    navigateToLoanSummaryScreen(
                        navController = navController,
                        id = id,
                        consentHandler = "1",
                        fromFlow = fromFlow
                    )
                }

            },
            backgroundColor = appWhite
        ) {
            Log.d("Sugu test", "pfCreateSessionInProgress: $pfCreateSessionInProgress")
            LoanDisburseAnimator()
//        val totalDisburseAmount = sseData.data?.data?.catalog?.item_price?.value
            val totalDisburseAmount = sseData.data?.data?.catalog?.quote_breakup
                ?.firstOrNull {
                    val formattedTitle =
                        it.title?.let { title -> CommonMethods().displayFormattedText(title) }
                    formattedTitle.equals("Net Disbursed Amount", ignoreCase = true)
                }
                ?.value
            RegisterText(
                text = stringResource(id = R.string.loan_amount_is_approved),
                style = normal18Text700,
                textColor = appBlack,
                top = 20.dp,
                bottom = 20.dp
            )

            if (totalDisburseAmount != null) {
                RegisterText(
                    text = totalDisburseAmount,
                    style = normal36Text700,
                    textColor = appGreen,
                    bottom = 20.dp
                )
            }
            RegisterText(
                text = stringResource(id = R.string.amount_disburse_time),
                style = normal16Text400,
                textColor = hintGray,
                start = 20.dp,
                end = 20.dp
            )
            val loanDetails = sseData.data?.data?.catalog
            if (loanDetails != null) {
                var loanDetailsStr = stringResource(id = R.string.loan_details) + "\n\n"

                loanDetails.quote_breakup?.forEach { quoteBreakUp ->
                    quoteBreakUp.let {
                        it.title?.let { title ->
                            it.value?.let { description ->
                                loanDetailsStr += title + " : " + description + "\n"
                            }
                        }
                    }
                }

                SpaceBetweenTextIcon(
                    text = stringResource(id = R.string.loan_details).uppercase(),
                    style = normal18Text700,
                    textColor = appOrange,
                    image = R.drawable.share,
                    start = 24.dp,
                    end = 24.dp
                ) {
                    shareContent(context, loanDetailsStr)
                }
                FillTenureAndInterestRate(loanDetails)
                LoanDisbursementCard(loanDetails)
            }
        }
    }
}



private fun parseISOTermToMonths(term: String?): Int? {
    if (term.isNullOrBlank()) return null
    val t = term.trim().uppercase()

    // Date-based: PnYnMnWnD (weeks optional)
    val dateRegex = Regex("""^P(?:(\d+)Y)?(?:(\d+)M)?(?:(\d+)W)?(?:(\d+)D)?$""")
    dateRegex.matchEntire(t)?.let { m ->
        val years  = m.groupValues[1].toIntOrNull() ?: 0
        val months = m.groupValues[2].toIntOrNull() ?: 0
        val weeks  = m.groupValues[3].toIntOrNull() ?: 0
        val days   = m.groupValues[4].toIntOrNull() ?: 0
        val monthsFromYears = years * 12
        val daysTotal = weeks * 7 + days
        val monthsFromDays = if (daysTotal > 0) maxOf(1, daysTotal / 30) else 0
        return monthsFromYears + months + monthsFromDays
    }

    // Time-based: PTnHnMnS  -> approximate to months via days
    val timeRegex = Regex("""^PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+(?:\.\d+)?)S)?$""")
    timeRegex.matchEntire(t)?.let { m ->
        val h = m.groupValues[1].toIntOrNull() ?: 0
        val min = m.groupValues[2].toIntOrNull() ?: 0
        val s = m.groupValues[3].toDoubleOrNull() ?: 0.0
        val totalSeconds = h * 3600 + min * 60 + s
        val days = (totalSeconds / 86400.0).toInt()
        return if (days > 0) maxOf(1, days / 30) else 0
    }

    // Plain number like "7" => months
    t.filter { it.isDigit() }.toIntOrNull()?.let { return it }

    return null
}

fun FillTenureAndInterestRate(loanDetail: Catalog){
    val interestRateStr = findTagValue(loanDetail, "INTEREST_RATE")
    val termStr = findTagValue(loanDetail, "TERM")
    val interest = parsePercent(interestRateStr)

    var months = parseISOTermToMonths(termStr)
    if (months == null) {
        val freq = parseISOTermToMonths(findTagValue(loanDetail, "REPAYMENT_FREQUENCY")) ?: 0
        val nInst = findTagValue(loanDetail, "NUMBER_OF_INSTALLMENTS")?.filter { it.isDigit() }?.toIntOrNull() ?: 0
        months = (freq * nInst).takeIf { it > 0 }
    }

    interestRate = interest.toString()
    tenure = months?.toString()
}

private fun findTagValue(loanDetail: Catalog, key: String): String? =
    loanDetail.item_tags.orEmpty()
        .asSequence()
        .mapNotNull { it.tags }
        .mapNotNull { it[key] ?: it.entries.firstOrNull { e -> e.key.equals(key, true) }?.value }
        .firstOrNull()

private fun parsePercent(value: String?): Double? =
    value?.replace(Regex("[^0-9.]"), "")?.toDoubleOrNull()

@Composable
fun MoveToConsentHandlerScreen(
    sseData: SSEData,
    navController: NavHostController,
    fromFlow: String
) {
    sseData.data?.data?.url?.let { url ->
        sseData.data.data.id?.let { consentId ->
            ConsentHandlerScreen(
                navController = navController,
                urlToOpen = url,
                id = consentId,
                fromFlow = fromFlow
            ) {}
        }
    }
}

fun shareContent(context: Context, content: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(shareIntent, null)
    startActivity(context, chooser, null)
}

@Composable
fun LoanDisbursementCard(loanDetail: Catalog?) {
    DisplayCard(cardColor = grayBackground, borderColor = grayD9) {
        loanDetail?.quote_breakup?.forEach { quoteBreakUp ->
            val title = quoteBreakUp.title.orEmpty()
            val rawValue = quoteBreakUp.value.orEmpty()

            if ( title.contains("principal", ignoreCase = true)) {
                loanAmount = rawValue
            }

            quoteBreakUp.let {
                it.title?.let { title ->
                    it.value?.let { description ->
                        val newTitle = CommonMethods().displayFormattedText(title)
                        OnlyReadAbleText(
                            textHeader = newTitle,
                            style = normal16Text400,
                            textColorHeader = slateGrayColor,
                            textValue = description,
                            textColorValue = appBlack,
                            textValueAlignment = TextAlign.End,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoveToDashBoardPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Mock Quote Breakup
    val quoteBreakups = listOf(
        SSEQuoteBreakUp(title = "Loan Amount", value = "₹40,000", currency = "INR"),
        SSEQuoteBreakUp(title = "Processing Fee", value = "₹500", currency = "INR"),
        SSEQuoteBreakUp(title = "Interest", value = "₹2,000", currency = "INR"),
        SSEQuoteBreakUp(title = "Loan Amount", value = "₹40,000", currency = "INR"),
        SSEQuoteBreakUp(title = "Processing Fee", value = "₹500", currency = "INR"),
        SSEQuoteBreakUp(title = "Interest", value = "₹2,000", currency = "INR")
    )

    // Mock Catalog
    val catalog = Catalog(
        item_price = Price(currency = "INR", value = "₹42,500"),
        quote_breakup = quoteBreakups
    )

    // Mock SSEData
    val sseData = SSEData(
        sseId = "sse-123",
        data = DataContent(
            status = true,
            statusCode = 200,
            data = EventData(
                txnId = "txn-456",
                type = "ACTION",
                catalog = catalog
            )
        )
    )

    MoveToDashBoard(
        navController = navController,
        id = "user-001",
        fromFlow = "Purchase Finance",
        sseData = sseData,
        loanAgreementViewModel = LoanAgreementViewModel(),
        pfCreateSessionInProgress = true,
        context = context
    )
}
