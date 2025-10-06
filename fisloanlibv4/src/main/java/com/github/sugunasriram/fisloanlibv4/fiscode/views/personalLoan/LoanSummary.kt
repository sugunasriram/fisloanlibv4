package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ImageTextButtonRow
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScheduleScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.sse.SSEViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.LoanNotApprovedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import kotlinx.serialization.json.Json
import java.util.Locale
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.WrapBorderButton

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
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
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
    loanListLoading: Boolean,
    loanListLoaded: Boolean,
    consentHandling: Boolean,
    fromFlow: String,
    navController: NavHostController,
    id: String,
    consentHandler: String,
    context: Context,
    loanAgreementViewModel: LoanAgreementViewModel,
    loanList: CustomerLoanList?
) {
    if (loanListLoading || consentHandling) {
        ProcessingAnimation(text = "Processing Please Wait...", image = R.raw.we_are_currently_processing_hour_glass)
    } else {
        if (loanListLoaded) {
            if (loanList != null) {
                DashBoardView(
                    navController = navController,
                    context = context,
                    loanList = loanList,
                    fromFlow = fromFlow
                )
            } else {
                LoanNotApprovedScreen(navController)
            }

            // Sugu - Stop listening for SSE
            val sseViewModel: SSEViewModel = viewModel()
            sseViewModel.stopListening()
            // Sugu - End
        } else {
            ConsentHandling(
                loanAgreementViewModel = loanAgreementViewModel,
                id = id,
                context = context,
                consentHandler = consentHandler,
                fromFlow = fromFlow
            )
        }
    }
}

@Composable
fun ConsentHandling(
    consentHandler: String,
    fromFlow: String,
    loanAgreementViewModel: LoanAgreementViewModel,
    id: String,
    context: Context
) {
    if (consentHandler.equals("2")) {
        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE",
                    id = id,
                    amount = "",
                    consentStatus = "SUCCESS",
                    loanType = "PERSONAL_LOAN"
                ),
                context = context
            )
        } else {
            loanAgreementViewModel.updateConsentHandler(
                updateConsentHandlerBody = UpdateConsentHandlerBody(
                    subType = "CONSENT_UPDATE",
                    id = id,
                    amount = "",
                    consentStatus = "SUCCESS",
                    loanType = "INVOICE_BASED_LOAN"
                ),
                context = context
            )
        }
    } else {
        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            loanAgreementViewModel.getCustomerLoanList("PERSONAL_LOAN", context)
        } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
            loanAgreementViewModel.getCustomerLoanList("PURCHASE_FINANCE", context)
        } else {
            loanAgreementViewModel.getCustomerLoanList("INVOICE_BASED_LOAN", context)
        }
    }
}

@Composable
fun DashBoardView(
    navController: NavHostController,
    context: Context,
    loanList: CustomerLoanList,
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
        onPrimaryButtonClick = { navigateApplyByCategoryScreen(navController) }
    ) {
        loanList.data?.forEachIndexed { index, offers ->
            LoanDetailCard(navController, offer = offers, fromFlow = fromFlow, index = index)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LoanDetailCard(navController: NavHostController, offer: OfferResponseItem, fromFlow: String, index: Int) {
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
                //For PF
                tag.key.contains("PRINCIPAL_AMOUNT", ignoreCase = true) -> {
                    loanAmount = tag.value
                }
            }
        }
    }

    val gradientColors = when (index % 5) {
        0 -> listOf(
            Color(0xFFF3D5B5), // green
            Color(0xFF8B5E34), // light green
            Color(0xFF533101) // lime

        )
        1 -> listOf(
            Color(0xFFFF8A65), // light red-orange
            Color(0xFFFF7043), // warm red
            Color(0xFFD84315) // deep red-orange
        )
        2 -> listOf(
            Color(0xFFB2FF59), // lime
            Color(0xFF81C784), // light green
            Color(0xFF4CAF50) // green

        )

        3 -> listOf(
            Color(0xFFFF8A80), // light red
            Color(0xFFFF5252), // red
            Color(0xFF800000) // deep red
        )
        else -> listOf(
            Color(0xFF64B5F6), // light blue
            Color(0xFF42A5F5), // blue
            Color(0xFF1976D2) // deep blue
        )
    }
    val gradient = Brush.verticalGradient(colors = gradientColors)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp), clip = false) // gray shadow
    ) {
        Card(
            modifier = Modifier.clickable {
                offer.id?.let { orderId ->
                    onCardClick(fromFlow, navController, orderId)
                }
            },
            elevation = 8.dp,
            backgroundColor = Color.White
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(appWhite),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                        ) {
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = logoUrl ?: R.drawable.bank_icon)
                                    .crossfade(true)
                                    .placeholder(R.drawable.bank_icon)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build()
                            )

                            Image(
                                painter = painter,
                                contentDescription = "Bank",
                                modifier = Modifier.size(32.dp).weight(1f)
                            )

                            StartingText(
                                text = bankName,
                                start = 8.dp,
                                style = normal14Text700,
                                textColor = gradientColors.last(),
                                modifier = Modifier.weight(3f)
                            )
                            WrapBorderButton(
                                text = stringResource(id = R.string.more_details),
                                modifier = Modifier
                                    .padding(
                                        start = 0.dp,
                                        end = 0.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                                    .weight(2.5f),
                                style = normal12Text400,
                                shape = RoundedCornerShape(10.dp),
                                backgroundColor = gradientColors.last(),
                                textColor = appWhite
                            ) {
                                offer.id?.let { orderId ->
                                    onCardClick(fromFlow, navController, orderId)
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(brush = gradient)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                        ) {
                            Row {
                                HeaderWithValue(
                                    textHeader = stringResource(id = R.string.loan_amount_inr),
                                    textValue =  loanAmount,
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
                                //Sugu ToDo
                                var loanTenure = "-"
                                if (tenure?.startsWith("P") == true) {
                                    convertISODurationToReadable(
                                        tenure ?: ""
                                    ).let { readableDuration ->
                                        loanTenure = readableDuration
                                    }
                                }else{
                                    loanTenure= tenure
                                }
                                HeaderWithValue(
                                    textHeader = stringResource(id = R.string.tenure),
                                    textValue = loanTenure,
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
                }
            }
        }
    }
}

fun onCardClick(fromFlow: String, navController: NavHostController, orderId: String) {
    navigateToRepaymentScheduleScreen(
        navController = navController,
        orderId = orderId,
        fromFlow = fromFlow,
        fromScreen = "Loan Summary"
    )
}
