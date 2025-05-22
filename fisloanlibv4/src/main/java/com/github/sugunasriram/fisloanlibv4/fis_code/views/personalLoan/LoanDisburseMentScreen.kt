package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoanDisburseAnimator
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenTextIcon
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanSummaryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiPaths
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.Catalog
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.DataContent
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.EventData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.Price
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEQuoteBreakUp
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayBackground
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal36Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.slateGrayColor
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.webview.ConsentHandlerScreen
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json1 = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

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

    BackHandler { navigateApplyByCategoryScreen(navController) }

    val sseViewModel: SSEViewModel = viewModel()
    val sseEvents by sseViewModel.events.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        sseViewModel.startListening(ApiPaths().sse)
    }

    if (sseDataForPf != null) {
        android.util.Log.d("SSETRIGGER", "Inside  MoveToDashBoard")
        sseDataForPf?.let { data ->
            MoveToDashBoard(
                navController = navController,
                id = id,
                fromFlow = fromFlow,
                sseData = data,
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

    if (sseData == null || type == "INFO") {
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
                        apiTriggered = true
                        loanAgreementViewModel.updateSSEData(sseData)
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
                            context = context
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(apiTriggered) {
        if (apiTriggered) {
            android.util.Log.d("SSETRIGGER", "API TRIGGERED")
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE",
                    id = id,
                    amount = "40000.0",
                    consentStatus = "DELIVERED",
                    loanType = "PURCHASE_FINANCE"
                ),
                context = context
            )
        }
    }
}

@Composable
fun MoveToDashBoard(
    navController: NavHostController,
    id: String,
    fromFlow: String,
    sseData: SSEData,
    context: Context
) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.loan_status),
        showBackButton = true,
        onBackClick = { navigateApplyByCategoryScreen(navController) },
        showBottom = true,
        showSingleButton = true,
        primaryButtonText = stringResource(R.string.home),
        onPrimaryButtonClick = {
            navigateToLoanSummaryScreen(
                navController = navController,
                id = id,
                consentHandler = "1",
                fromFlow = fromFlow
            )
        },
        backgroundColor = appWhite
    ) {
        LoanDisburseAnimator()
        val totalDisburseAmount = sseData.data?.data?.catalog?.item_price?.value
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
            var loanDetailsStr =
                stringResource(id = R.string.loan_details) + "\n\n"

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
            LoanDisbursementCard(loanDetails)
        }
    }
}

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
        context = context
    )
}
