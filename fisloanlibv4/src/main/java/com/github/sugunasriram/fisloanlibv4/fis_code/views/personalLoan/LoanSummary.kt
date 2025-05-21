package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ImageTextButtonRow
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.LoanNotApprovedScreen
import kotlinx.serialization.json.Json
import java.util.Locale

private val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}
var loanAmt = ""

@SuppressLint("ResourceType")
@Composable
fun LoanSummaryScreen(
    navController: NavHostController,
    id: String,
    consentHandler: String,
    fromFlow: String
) {

    val context = LocalContext.current
    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()

    val loanList by loanAgreementViewModel.loanList.collectAsState()
    val loanListLoading by loanAgreementViewModel.loanListLoading.collectAsState()
    val loanListLoaded by loanAgreementViewModel.loanListLoaded.collectAsState()
    val consentHandling by loanAgreementViewModel.consentHandling.collectAsState()

    val showInternetScreen by loanAgreementViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by loanAgreementViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by loanAgreementViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by loanAgreementViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by loanAgreementViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by loanAgreementViewModel.middleLoan.observeAsState(false)
    val errorMessage by loanAgreementViewModel.errorMessage.collectAsState()

    BackHandler { navController.popBackStack() }
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        else -> {
            LoanSummaryDetail(
                loanListLoading = loanListLoading,
                loanListLoaded = loanListLoaded,
                consentHandling = consentHandling,
                navController = navController,
                id = id,
                consentHandler = consentHandler,
                context = context,
                fromFlow = fromFlow,
                loanAgreementViewModel = loanAgreementViewModel,
                loanList = loanList
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun LoanSummaryDetail(
    loanListLoading: Boolean, loanListLoaded: Boolean, consentHandling: Boolean,fromFlow: String,
    navController: NavHostController, id: String, consentHandler: String, context: Context,
    loanAgreementViewModel: LoanAgreementViewModel, loanList: CustomerLoanList?,
) {
    if (loanListLoading || consentHandling) {
        ProcessingAnimation(text = "Processing Please Wait...",  image = R.raw.we_are_currently_processing_hour_glass)
    } else {
        if (loanListLoaded) {
            if (loanList != null) {
                DashBoardView(
                    navController = navController, context = context, loanList = loanList,
                    fromFlow = fromFlow
                )
            } else {
                LoanNotApprovedScreen(navController)
            }

            //Sugu - Stop listening for SSE
            val sseViewModel: SSEViewModel = viewModel()
            sseViewModel.stopListening()
            //Sugu - End

        } else {
            ConsentHandling(
                loanAgreementViewModel = loanAgreementViewModel, id = id, context = context,
                consentHandler = consentHandler, fromFlow = fromFlow
            )
        }
    }
}

@Composable
fun ConsentHandling(
    consentHandler: String, fromFlow: String, loanAgreementViewModel: LoanAgreementViewModel,
    id: String, context: Context
) {
    if (consentHandler.equals("2")) {
        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE", id = id, amount = "", consentStatus = "SUCCESS",
                    loanType = "PERSONAL_LOAN"
                ),
                context = context,
            )
        } else {
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE", id = id, amount = "", consentStatus = "SUCCESS",
                    loanType = "INVOICE_BASED_LOAN"
                ),
               context =  context,
            )
        }
    } else {
        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            loanAgreementViewModel.getCustomerLoanList("PERSONAL_LOAN", context)
        } else if(fromFlow.equals("Purchase Finance", ignoreCase = true)) {
            loanAgreementViewModel.getCustomerLoanList("PURCHASE_FINANCE", context)
        } else {
            loanAgreementViewModel.getCustomerLoanList("INVOICE_BASED_LOAN", context)
        }
    }
}

@Composable
fun DashBoardView(
    navController: NavHostController, context: Context, loanList: CustomerLoanList,
    fromFlow: String
) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.loan_summary),
        showBackButton = true,
        backgroundColor = appWhite,
        contentStart = 0.dp, contentEnd = 0.dp,
        onBackClick = { navController.popBackStack() },
        showBottom = true,
        showSingleButton = true,
        primaryButtonText = stringResource(R.string.home),
        onPrimaryButtonClick = { navigateApplyByCategoryScreen(navController) },
    ) {
            loanList.data?.forEach { offers ->
                LoanDetailCard(navController, offer = offers, fromFlow = fromFlow)
                Spacer(modifier = Modifier.height(16.dp))
            }
    }
}

@Composable
fun LoanDetailCard(navController: NavHostController, offer: OfferResponseItem, fromFlow: String) {

    val loanDetail = json.encodeToString(OfferResponseItem.serializer(), offer)
    val bankName = offer.providerDescriptor?.name ?: "Bank"
    val logoUrl = offer.providerDescriptor?.images?.firstOrNull()?.url

    var loanAmount = "-"
    var interestRate = "-"
    var tenure = "-"
    var installmentAmount = "-"

    offer.quoteBreakUp?.forEach { quote ->
        if (quote?.title?.lowercase(Locale.ROOT)?.contains("principal") == true) {
            loanAmount = quote.value ?: "-"
        }
    }

    offer.itemTags?.forEach { itemTag ->
        itemTag?.tags?.forEach { tag ->
            when {
                tag.key.equals("interest_rate", ignoreCase = true) ||
                        tag.key.equals("interest rate", ignoreCase = true) -> {
                    interestRate = tag.value
                }
                tag.key.equals("loan_term", ignoreCase = true) ||
                        tag.key.equals("term", ignoreCase = true) -> {
                    tenure = tag.value
                }
                tag.key.contains("INSTALLMENT_AMOUNT", ignoreCase = true) -> {
                    installmentAmount = tag.value
                }
            }
        }
    }
    FullWidthRoundShapedCard(
        onClick = {
            offer.id?.let { orderId ->
                onCardClick(fromFlow, navController, orderId)
            }
        },
        cardColor = appOrange,
        bottomPadding = 15.dp,
        start = 10.dp,
        end = 10.dp,
        bottom = 10.dp
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = logoUrl ?: R.drawable.bank_icon)
                .apply {
                    crossfade(true)
                    placeholder(R.drawable.bank_icon)
                }.build()
        )

        ImageTextButtonRow(
            imagePainter = painter,
            textHeader = bankName,
            textColor = appBlack,
            textStyle = normal14Text700,
            buttonText = stringResource(id = R.string.more_details),
            buttonTextStyle = normal12Text400,
            onButtonClick = {
                offer.id?.let { orderId ->
                    onCardClick(fromFlow, navController, orderId)
                }
            }
        )
        Row {
            HeaderWithValue(
                textHeader = stringResource(id = R.string.loan_amount),
                textValue = loanAmount,
                modifier = Modifier.weight(0.5f)
            )
            HeaderWithValue(
                textHeader = stringResource(id = R.string.rate_of_interest),
                textValue = interestRate,
                modifier = Modifier.weight(0.5f)
            )
        }

        Spacer(Modifier.height(15.dp))

        Row {
            HeaderWithValue(
                textHeader = stringResource(id = R.string.tenure),
                textValue = tenure,
                modifier = Modifier.weight(0.5f)
            )
            HeaderWithValue(
                textHeader = stringResource(id = R.string.installment_amount),
                textValue = installmentAmount,
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

fun onCardClick(fromFlow: String, navController: NavHostController, orderId: String) {
    navigateToLoanDetailScreen(
        navController = navController, orderId = orderId, fromFlow = fromFlow, fromScreen = "Loan Summary"
    )
}


