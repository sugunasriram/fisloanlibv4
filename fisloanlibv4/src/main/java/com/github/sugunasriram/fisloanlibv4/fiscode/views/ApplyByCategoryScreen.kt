package com.github.sugunasriram.fisloanlibv4.fiscode.views

// import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.json
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.accompanist.insets.HorizontalSide
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedElevatedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ImageTextButtonRow
import com.github.sugunasriram.fisloanlibv4.fiscode.components.PagerIndicator
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fiscode.components.VoiceRecorderButton
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateTOUnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBureauOffersScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToConsentSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToDownPaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormRejectedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstInvoiceLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToKycAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanOffersListDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPersonaLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToUpdateProfileScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.UserStatus
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFSearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.GetLenderStatusModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OffersWithRejections
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.RejectedLenders
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchResponseModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cardWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayA6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400Ht15
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.UserStatusViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.PersonalLoanViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance.PurchaseFinanceViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.UnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.NavigateToWebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ApplyByCategoryScreen(navController: NavHostController,
                          verifySessionResponse: VerifySessionResponse?) {
    val userStatusViewModel: UserStatusViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()

    val showInternetScreen by userStatusViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by userStatusViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by userStatusViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by userStatusViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by userStatusViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by userStatusViewModel.middleLoan.observeAsState(false)
    val showLoader by userStatusViewModel.showLoader.observeAsState(false)
    val errorMessage by userStatusViewModel.errorMessage.collectAsState()

    val checkingStatus by userStatusViewModel.checkingStatus.collectAsState()
    val checked by userStatusViewModel.checked.collectAsState()
    val userStatus by userStatusViewModel.userStatus.collectAsState()

    val navigationToSignIn by userStatusViewModel.navigationToSignIn.collectAsState()
    val userDetails by registerViewModel.getUserResponse.collectAsState()

    val userDetailsAPILoading by registerViewModel.inProgress.collectAsState()
    val userDetailsAPICompleted by registerViewModel.isCompleted.collectAsState()

    val activity = LocalContext.current as? Activity
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        registerViewModel.getUserDetail(context, navController)
    }
    BackHandler { activity?.finish() }
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
            Log.d("ApplyByCategoryScreen Sugu", "verifySessionResponse: $verifySessionResponse")
            SelectingFlow(
                checkingStatus = checkingStatus, navController = navController, context = context,
                userStatus = userStatus, userStatusViewModel = userStatusViewModel,
                checked = checked, showLoader = showLoader, errorMessage = errorMessage,
                userDetails = userDetails, userDetailsAPILoading = userDetailsAPILoading,
                userDetailsAPICompleted  = userDetailsAPICompleted,
                verifySessionResponse = verifySessionResponse
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SelectingFlow(
    checkingStatus: Boolean,
    navController: NavHostController,
    userStatus: UserStatus?,
    userStatusViewModel: UserStatusViewModel,
    context: Context,
    checked: Boolean,
    showLoader: Boolean,
    errorMessage: String,
    userDetails: ProfileResponse?,
    userDetailsAPILoading: Boolean,
    userDetailsAPICompleted: Boolean,
    verifySessionResponse :VerifySessionResponse?
) {
    val scope = rememberCoroutineScope()
    var setFlow by remember { mutableStateOf("") }
    var triggerApi by remember { mutableStateOf(true) }
    triggerApi = showLoader
    var apiCalled by remember { mutableStateOf(true) }

    if (checkingStatus || userDetailsAPILoading) {
        CenterProgress()
    } else if (triggerApi) {
        if (userStatus?.data?.data != null || userStatus?.data?.url != null) {
            PurchaseDecidedFlow(
                context = context,
                status = userStatus,
                navController = navController,
                fromFlow = setFlow,
                verifySessionResponse = verifySessionResponse
            )
        } else {
            if (apiCalled) {
                scope.launch {
                    delay(60000)
                    userStatusViewModel.getUserStatus(loanType = "PURCHASE_FINANCE", context =
                        context)
                    apiCalled = false
                }
            }

            UnexpectedErrorScreen(
                navController = navController,
                errorMsgShow = false,
                errorText = stringResource(id = R.string.request_is_still),
                errorMsg = stringResource(id = R.string.middle_loan_error_message),
                onClick = {
                    userStatusViewModel.getUserStatus(
                        loanType = "PERSONAL_LOAN",
                        context = context
                    )
                }
            )
        }
    } else {
        if (checked) {

            PurchaseDecidedFlow(
                context = context,
                status = userStatus,
                navController = navController,
                fromFlow = setFlow,
                verifySessionResponse = verifySessionResponse
            )
        } else {
//            LoanSelectionScreen(
//                navController = navController,
//                userDetails = userDetails,
//                context = context,
//                onLoanSelected = { loanType ->


//                    setFlow = loanType
                    val requiredFields = listOf(
                        userDetails?.data?.firstName,
                        userDetails?.data?.lastName,
                        userDetails?.data?.email,
                        userDetails?.data?.dob,
                        userDetails?.data?.panNumber,
                        userDetails?.data?.gender,
                        userDetails?.data?.employmentType
                    )

                    if (requiredFields.any { it.isNullOrEmpty() } && userDetailsAPICompleted) {
                        navigateToUpdateProfileScreen(navController, fromFlow = "Purchase Finance")
                        CommonMethods().toastMessage(
                            context,
                            context.getString(R.string.please_update_your_profile_to_proceed)
                        )
                    } else {
                        userStatusViewModel.getUserStatus(
                            loanType = "PURCHASE_FINANCE",
                            context = context
                        )
                    }
//                }
//            )
        }
    }
}


//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun SelectingFlow(
//    checkingStatus: Boolean,
//    navController: NavHostController,
//    userStatus: UserStatus?,
//    userStatusViewModel: UserStatusViewModel,
//    context: Context,
//    checked: Boolean,
//    showLoader: Boolean,
//    errorMessage: String,
//    userDetails: ProfileResponse?,
//    userDetailsAPILoading: Boolean
//) {
//    val scope = rememberCoroutineScope()
//    var setFlow by remember { mutableStateOf("") }
//    var triggerApi by remember { mutableStateOf(true) }
//    triggerApi = showLoader
//    var apiCalled by remember { mutableStateOf(true) }
//
//    if (checkingStatus) {
//        CenterProgress()
//    } else if (triggerApi) {
//        if (userStatus?.data?.data != null || userStatus?.data?.url != null) {
//            when {
//                setFlow.equals("Personal Loan", ignoreCase = true) -> {
//                    PersonalDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//
//                setFlow.equals("Invoice Loan", ignoreCase = true) -> {
//                    InvoiceDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//
//                setFlow.equals("Purchase Finance", ignoreCase = true) -> {
//                    PurchaseDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//            }
//        } else {
//            if (apiCalled) {
//                scope.launch {
//                    delay(60000)
//                    userStatusViewModel.getUserStatus(loanType = "PERSONAL_LOAN", context = context)
//                    apiCalled = false
//                }
//            }
//
//            UnexpectedErrorScreen(
//                navController = navController,
//                errorMsgShow = false,
//                errorText = stringResource(id = R.string.request_is_still),
//                errorMsg = stringResource(id = R.string.middle_loan_error_message),
//                onClick = {
//                    userStatusViewModel.getUserStatus(
//                        loanType = "PERSONAL_LOAN",
//                        context = context
//                    )
//                }
//            )
//        }
//    } else {
//        if (checked) {
//            when {
//                setFlow.equals("Personal Loan", ignoreCase = true) -> {
//                    PersonalDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//
//                setFlow.equals("Invoice Loan", ignoreCase = true) -> {
//                    InvoiceDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//
//                setFlow.equals("Purchase Finance", ignoreCase = true) -> {
//                    PurchaseDecidedFlow(
//                        context = context,
//                        status = userStatus,
//                        navController = navController,
//                        fromFlow = setFlow
//                    )
//                }
//            }
//        } else {
//            LoanSelectionScreen(
//                navController = navController,
//                userDetails = userDetails,
//                context = context,
//                onLoanSelected = { loanType ->
//                    setFlow = loanType
//                    val requiredFields = listOf(
//                        userDetails?.data?.firstName,
//                        userDetails?.data?.lastName,
//                        userDetails?.data?.email,
//                        userDetails?.data?.dob,
//                        userDetails?.data?.panNumber,
//                        userDetails?.data?.gender,
//                        userDetails?.data?.employmentType
//                    )
//                    when (loanType) {
//                        "Invoice Loan" -> {
//                            if (requiredFields.any { it.isNullOrEmpty() } && !userDetailsAPILoading) {
//                                navigateToUpdateProfileScreen(navController, fromFlow = loanType)
//                                CommonMethods().toastMessage(
//                                    context,
//                                    context.getString(R.string.please_update_your_profile_to_proceed)
//                                )
//                            } else if (userDetails?.data?.udyamNumber.isNullOrEmpty()) {
//                                navigateToUpdateProfileScreen(navController, fromFlow = loanType)
//                                CommonMethods().toastMessage(context, "Update UDYAM number")
//                            } else {
//                                userStatusViewModel.getUserStatus(
//                                    loanType = "INVOICE_BASED_LOAN",
//                                    context = context
//                                )
//                            }
//                        }
//
//                        "Personal Loan" -> {
//                            if (requiredFields.any { it.isNullOrEmpty() } && !userDetailsAPILoading) {
//                                navigateToUpdateProfileScreen(navController, fromFlow = loanType)
//                                CommonMethods().toastMessage(
//                                    context,
//                                    context.getString(R.string.please_update_your_profile_to_proceed)
//                                )
//                            } else {
//                                userStatusViewModel.getUserStatus(
//                                    loanType = "PERSONAL_LOAN",
//                                    context = context
//                                )
//                            }
//                        }
//
//                        "Purchase Finance" -> {
//
//                            if (requiredFields.any { it.isNullOrEmpty() } && !userDetailsAPILoading) {
//                                navigateToUpdateProfileScreen(navController, fromFlow = loanType)
//                                CommonMethods().toastMessage(
//                                    context,
//                                    context.getString(R.string.please_update_your_profile_to_proceed)
//                                )
//                            } else {
//                                userStatusViewModel.getUserStatus(
//                                    loanType = "PURCHASE_FINANCE",
//                                    context = context
//                                )
//                            }
//                        }
//                    }
//                }
//            )
//        }
//    }
//}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselContent(images: List<Int>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % images.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        PagerIndicator(currentPage = pagerState.currentPage, pageCount = images.size)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoanSelectionScreen() {
    val fakeNavController = rememberNavController()

    LoanSelectionScreen(
        navController = fakeNavController,
        userDetails = null,
        onLoanSelected = {},
        context = LocalContext.current
    )
}

@Composable
fun LoanSelectionScreen(
    navController: NavHostController,
    userDetails: ProfileResponse? = null,
    onLoanSelected: (String) -> Unit,
    context: Context
) {
    ScreenWithHamburger(
        isSelfScrollable = false,
        navController = navController,
        topBarText = ""
    ) {
        CarouselContent(
            images = listOf(
                R.drawable.dashboard_card,
                R.drawable.dashboard_card_1,
                R.drawable.dashboard_card_2,
                R.drawable.dashboard_card_3,
            )
        )

        val cardGradients = mapOf(
            "Personal Loan" to listOf(Color(0xFFffecb3), Color(0xFFFFFFFF)),
            "Purchase Finance" to listOf(Color(0xFFB4E2A5), Color(0xFFFFFFFF)),
            "GST" to listOf(Color(0xFFFFFFFF), Color(0xFF64B5F6))
        )
        val personalLoanGradient = cardGradients["Personal Loan"] ?: listOf(Color.Gray)
        val purchaseFinanceGradient = cardGradients["Purchase Finance"] ?: listOf(Color.Gray)
        val gstGradient = cardGradients["GST"] ?: listOf(Color.Gray)

        HeaderCard(
            start = 5.dp,
            end = 5.dp,
            top = 10.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp,
            topStart = 16.dp,
            topEnd = 16.dp,
            bottom = 20.dp,
            cardColor = cardWhite,
            borderColor = grayD6
        ) {

            RegisterText(
                text = stringResource(R.string.loan_types),
                style = bold20Text100,
                textColor = appBlack,
                boxAlign = Alignment.TopCenter,
                top = 10.dp,
                bottom = 10.dp
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 0.dp)
                        .wrapContentHeight()
                ) {
                    FullWidthRoundShapedElevatedCard(
                        onClick = { onLoanSelected("Personal Loan") },
                        gradientColors = personalLoanGradient,
                        bottomPadding = 10.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 10.dp,
                        top = 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp) //
                                // Allow space for protruding image + full text
                                .defaultMinSize(minHeight = 100.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(100.dp)) // Reserve space for image

                            Column(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.personal_loan),
                                    style = bold20Text100,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.need_quick_cash),
                                    style = normal12Text400Ht15,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.indian_rupee_money_bag),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(y = (-25).dp) // Protrude upward
                            .height(130.dp)       // Same as or larger than card
                            .width(120.dp)
                            .clickable {
                                onLoanSelected("Personal Loan")
                            }
                    )
                }

                Box(modifier = Modifier.padding(start = 10.dp, end = 8.dp, bottom = 10.dp)
                ) {
                    FullWidthRoundShapedElevatedCard(
                    onClick = {
//                        CommonMethods().toastMessage(
//                            context = context,
//                            toastMsg = context.getString(R.string.feature_supported_in_future)
//                        )
                        onLoanSelected("Purchase Finance")
                    },
                    gradientColors = purchaseFinanceGradient,
                    bottomPadding = 10.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(start = 6.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.purchase_finance),
                                style = bold20Text100,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.want_to_buy),
                                style = normal12Text400Ht15,
                                color = Color.Black
                            )
                        }
                    }
                }
                    Image(
                        painter = painterResource(id = R.drawable.purchase_finance),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(bottom = 4.dp)
                            .size(width = 120.dp, height = 110.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (2).dp)
                            .clickable {
//                                CommonMethods().toastMessage(
//                                    context = context,
//                                    toastMsg = context.getString(R.string.feature_supported_in_future)
//                                )
                                onLoanSelected("Purchase Finance")
                            }
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    FullWidthRoundShapedElevatedCard(
                        onClick = {
                            CommonMethods().toastMessage(
                                context = context,
                                toastMsg = context.getString(R.string.feature_supported_in_future)
                            )
//                            onLoanSelected("Invoice Loan")
                        },
                        gradientColors = gstGradient,
                        bottomPadding = 10.dp,
                        start = 8.dp, // give space inside card from image
                        end = 8.dp,
                        bottom = 10.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(50.dp)) // placeholder for protruding image
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(start = 36.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.gst_loan),
                                    style = bold20Text100,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.running_a_business),
                                    style = normal12Text400Ht15,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.coming_soon_dots),
                                    style = normal12Text400Ht15,
                                    modifier = Modifier.padding(horizontal = 25.dp),
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.gst_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 110.dp, height = 100.dp)
                            .align(Alignment.CenterStart)
                            .offset(x = (-10).dp) // protrude outside the card
                            .clickable {
                                CommonMethods().toastMessage(
                                    context = context,
                                    toastMsg = context.getString(R.string.feature_supported_in_future)
                                )
                            }
                    )
                }
            }
        }

        VoiceRecorderButton(navController, onClick = {
            CommonMethods().toastMessage(
                context = context,
                toastMsg = context.getString(R.string.feature_supported_in_future)
            )
        })
        RegisterText(
            text = stringResource(R.string.tell_us_what_you_are_looking_for),
            style = normal14Text400,
            textColor = hintGray,
            boxAlign = Alignment.Center,
            top = 5.dp,
            bottom = 10.dp
        )
    }
}

@SuppressLint("ResourceType", "CoroutineCreationDuringComposition")
@Composable
fun PersonalDecidedFlow(
    context: Context,
    status: UserStatus?,
    navController: NavHostController,
    fromFlow: String
) {
    val personalLoanViewModel: PersonalLoanViewModel = viewModel()
    val searchInProgress by personalLoanViewModel.searchInProgress.collectAsState()
    val searchLoaded by personalLoanViewModel.searchLoaded.collectAsState()
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

    LaunchedEffect(status?.data) {
        if (status?.data == null || status.data.data?.any { it == null } == true) {
            Log.d("test status: ", "null")
            personalLoanViewModel.personalLoanSearch(
                context = context,
                searchBodyModel = SearchBodyModel(
                    loanType = "PERSONAL_LOAN",
                    endUse = "travel",
                    bureauConsent = "on"
                )
            )
        }
    }
    if (status?.data == null || status.data.data?.any { it == null } == true) {
        Log.d("test status: ", "null")
        if (searchInProgress) {
            CenterProgress()
        } else if (searchLoaded) {
            navigateToPersonaLoanScreen(navController = navController, fromFlow = "Personal Loan")
        }
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "loan_select_form_submission_PENDING", true
        ) ||
        status.data.data?.lastOrNull()?.step.equals("loan_select_form_submission_REJECTED", true)
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.kyc_failed),
            fromFlow = fromFlow,
            errorMsg =
            if (status?.data?.data?.lastOrNull()?.step.equals(
                    "loan_select_form_submission_PENDING",
                    true
                )
            ) {
                stringResource(id = R.string.form_submission_pending)
            } else {
                stringResource(id = R.string.form_submission_rejected)
            }
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "emandate_form_submission_PENDING",
            true
        ) ||
        status.data.data?.lastOrNull()?.step.equals("emandate_form_submission_REJECTED", true)
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
//            errorTitle = stringResource(id = R.string.emandate_failed),
            errorTitle = "emandate_form_submission_REJECTED",
            fromFlow = fromFlow,
            errorMsg = if (status?.data?.data?.lastOrNull()?.step.equals(
                    "emandate_form_submission_PENDING",
                    true
                )
            ) {
                stringResource(id = R.string.form_submission_pending)
            } else {
                stringResource(id = R.string.form_submission_rejected)
            }
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "account_information_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.repayment_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "loan_agreement_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.loan_agreement_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.firstOrNull()?.step.equals("form_submission_request", true)) {
        LaunchedEffect(Unit) {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType = "PERSONAL_LOAN",
                step = "FORM_SUBMISSION_REQUEST"
            )
        }
        when {
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
                    NoLoanOffersAvailableScreen(
                        navController,
                        titleText = stringResource(R.string.no_lenders_available)
                    )
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

                            navigateToFormSubmissionWebScreen(
                                navController,
                                fromFlow,
                                lenderStatusJson
                            )
                        } catch (e: Exception) {
                            Log.e("JsonError", "Failed to serialize lender status", e)
                        }
                    }
                }
            }
        }
    } else if (status.data.data?.firstOrNull()?.step.equals("consent_request_sent", true)) {
        LaunchedEffect(Unit) {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType = "PERSONAL_LOAN",
                step = "CONSENT_SELECT"
            )
        }
        when {
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
                    NoLoanOffersAvailableScreen(
                        navController,
                        titleText = stringResource(R.string.no_lenders_available)
                    )
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
                            navigateToConsentSubmissionWebScreen(
                                navController,
                                fromFlow,
                                lenderStatusJson
                            )
                        } catch (e: Exception) {
                            Log.e("JsonError", "Failed to serialize lender status", e)
                        }
                    }
                }
            }
        }

    } else if (status.data.data?.firstOrNull()?.step.equals("search", true)) {
        if (status.data.data?.any { !it?.data.isNullOrEmpty() } == true) {
            navigateToPersonaLoanScreen(navController = navController, fromFlow = "Personal Loan")
        }
        val offerList =
            status.data.data?.lastOrNull()?.offerResponse?.flatMap { offerResponseItem ->
                offerResponseItem?.data?.map { data ->
                    Offer(
                        data.offer,
                        data.id,
                        data.bureauConsent
                    )
                } ?: emptyList()
            }
        val searchResponse = SearchResponseModel(
            id = status.data.data?.lastOrNull()?.id,
            url = status.data.data?.lastOrNull()?.url?.takeIf { it.isNotBlank() },
            transactionId = status.data.txnId,
            consentResponse = status.data.data?.firstOrNull()?.consentResponse,
            rejectedLenders = status.data.data?.firstOrNull()?.rejectedLenders,
            offerResponse = offerList
        )

        if (offerList.isNullOrEmpty()) {
            val searchModel = SearchModel(
                data = searchResponse,
                status = true,
                statusCode = 200
            )
            NavigateToWebView(
                context = context,
                searchModel = searchModel,
                gstSearchResponse = null,
                fromFlow = fromFlow,
                navController = navController,
                searchResponse = searchModel
            )
        } else {
            val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
            val searchJson = json.encodeToString(
                SearchModel.serializer(),
                SearchModel(data = searchResponse, status = true, statusCode = 200)
            )
            navigateToBureauOffersScreen(
                navController,
                stringResource(R.string.getUerFlow),
                fromFlow,
                searchJson
            )
        }
    } else {
        if (status.data.url != null) {
            LaunchedEffect(Unit) {
                webViewModel.getLenderStatusApi(
                    context = context,
                    loanType = "PERSONAL_LOAN",
                    step = "CONSENT_SELECT"
                )
            }
            when {
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
                        NoLoanOffersAvailableScreen(
                            navController,
                            titleText = stringResource(R.string.no_lenders_available)
                        )
                    } else {
                        LaunchedEffect(lenderStatus) {
                            val json = Json {
                                prettyPrint = true
                                ignoreUnknownKeys = true
                            }
                            val lenderStatusJson = json.encodeToString(
                                LenderStatusResponse.serializer(),
                                LenderStatusResponse(response = lenderStatus)
                            )

                            val transactionId = status.data.data?.get(0)?.data?.get(0)?.txnId
                            val searchId = status.data.id
                            val webUrl = status.data.url

                            if (!transactionId.isNullOrBlank() && !searchId.isNullOrBlank() && !webUrl.isNullOrBlank()) {

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
                }
            }

//            status.data.url.let { webUrl ->
//                val transactionId = status?.data?.data?.get(0)?.data?.get(0)?.txnId
//                transactionId.let { transactionId ->
//                    transactionId?.let { Log.d("test transactionId: ", it) }
//                    status.data.id?.let { searchId ->
//                        transactionId?.let {
//                            navigateToWebViewFlowOneScreen(
//                                navController = navController,
//                                purpose = stringResource(R.string.getUerFlow),
//                                fromFlow = fromFlow,
//                                id = searchId,
//                                transactionId = transactionId,
//                                url = webUrl,
//                                lenderStatusData = ""
//                            )
//                        }
//                    }
//                }
//            }
        } else {
            val transactionId = status.data.txnId
            val rejectedLenders = status.data.data?.firstOrNull()?.rejectedLenders
            transactionId?.let { Log.d("test transactionId: ", it) }
            status.data.data?.forEach { data ->
                data?.step?.let { step ->
                    if (step.equals("consent_select", true)) {
                        val offerList = data.offerResponse
                            ?.flatMap { offerResponseItem ->
                                offerResponseItem?.data?.map { d ->
                                    Offer(d.offer, d.id, d.bureauConsent)
                                } ?: emptyList()
                            }
                        val rejectedList = rejectedLenders?.map {
                            RejectedLenders(
                                name = it?.name,
                                image = it?.image,
                                reason = it?.reason,
                                minLoanAmount = it?.minLoanAmount,
                                maxLoanAmount = it?.maxLoanAmount,
                                maxInterestRate = it?.maxInterestRate,
                                minInterestRate = it?.minInterestRate
                            )
                        }
                        val combined = OffersWithRejections(
                            offers = offerList,
                            rejectedLenders = rejectedList
                        )

                        val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
                        val jsonString = json.encodeToString(combined)
                        navigateToLoanOffersListScreen(navController, jsonString, fromFlow)
                    } else if (step.equals("loan_select", true)) {
                        status.data.data.forEach { item ->
                            item?.data?.forEach { item2 ->
                                item2?.fromUrl?.let { kycUrl ->
                                    item2.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            Log.d("UserStatus-kycUrl ", kycUrl)
                                            transactionId?.let { Log.d("test transactionId: ", it) }
                                            navigateToKycAnimation(
                                                navController,
                                                transactionId,
                                                id,
                                                kycUrl,
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals(
                            "loan_select_form_submission_APPROVED",
                            ignoreCase = true
                        )
                    ) {
                        status.data.data.forEach { userItem ->
                            userItem?.id?.let { id ->
                                transactionId?.let { transactionId ->
                                    navigateToBankDetailsScreen(
                                        navController = navController,
                                        id = id,
                                        fromFlow = fromFlow,
                                        closeCurrent = false
                                    )
//                                    navigateToLoanProcessScreen(
//                                        responseItem = "No need ResponseItem",
//                                        transactionId = transactionId,
//                                        offerId = id,
//                                        navController = navController, statusId = 4,
//                                        fromFlow = fromFlow
//                                    )
                                }
                            }
                        }
                    } else if (step.equals("emandate_approved", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { enachUrl ->
                                    Log.d("UserStatus_emandate", enachUrl)
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToRepaymentScreen(
                                                navController,
                                                transactionId,
                                                enachUrl,
                                                id,
                                                fromFlow
                                            )
//                                            navigateToLoanProcessScreen(
//                                                navController = navController,
//                                                transactionId = transactionId,
//                                                statusId = 5,
//                                                responseItem = enachUrl, offerId = id,
//                                                fromFlow = fromFlow
//                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("loan_agreement_approved", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { loanAgreementUrl ->
                                    Log.d("UserStatus_loanAg", loanAgreementUrl)
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            if (loanAgreementUrl.equals(
                                                    "No need ResponseItem",
                                                    ignoreCase = true
                                                ) || loanAgreementUrl.contains(
                                                    "No need ResponseItem",
                                                    ignoreCase = true
                                                )
                                            ) {
                                                navigateTOUnexpectedErrorScreen(navController, true)
                                            } else {
                                                navigateToLoanAgreementAnimationLoader(
                                                    navController= navController,
                                                    transactionId=transactionId,
                                                    id=id,
                                                    fromFlow = fromFlow,
                                                    formUrl=loanAgreementUrl,
                                                )
//                                                navigateToLoanAgreementScreen(
//                                                    navController,
//                                                    transactionId,
//                                                    id,
//                                                    loanAgreementUrl,
//                                                    fromFlow = fromFlow
//                                                )
                                            }
//                                            navigateToLoanProcessScreen(
//                                                transactionId = transactionId,
//                                                offerId = id, responseItem = loanAgreementUrl,
//                                                navController = navController, statusId = 6,
//                                                fromFlow = fromFlow
//                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        UnexpectedErrorScreen(
                            navController = navController,
//                            onClick = { navigateApplyByCategoryScreen(navController) })
                            onClick = { navigateToFISExitScreen(navController, loanId="4321") })
//                        navigateToEMandateESignFailedScreen(navController,step)
                    }
                }
            }
        }
    }
}

@Composable
fun InvoiceDecidedFlow(
    context: Context,
    status: UserStatus?,
    navController: NavHostController,
    fromFlow: String
) {
    if (status?.data == null) {
        navigateToGstInvoiceLoanScreen(navController = navController, fromFlow = "Invoice Loan")
    } else if (status?.data?.data?.lastOrNull()?.step.equals(
            "individual_ekyc_form_submission_failed",
            true
        ) ||
        status?.data?.data?.lastOrNull()?.step.equals("entity_ekyc_form_submission_failed", true)
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.kyc_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status?.data?.data?.lastOrNull()?.step.equals(
            "emandate_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.emandate_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status?.data?.data?.lastOrNull()?.step.equals(
            "account_information_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.repayment_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status?.data?.data?.lastOrNull()?.step.equals(
            "loan_agreement_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.loan_agreement_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status?.data?.data?.lastOrNull()?.step.equals("search", true)) {
        val searchResponseData = GstSearchData(
            id = status?.data?.data?.lastOrNull()?.id,
            url = status?.data?.data?.lastOrNull()?.url ?: "",
            transactionId = status?.data?.txnId
        )

        // Create an instance of SearchModel
        val searchResponse = GstSearchResponse(
            data = searchResponseData,
            status = true,
            statusCode = 200
        )
        NavigateToWebView(
            context = context,
            searchResponse = null,
            gstSearchResponse = searchResponse,
            fromFlow = fromFlow,
            navController = navController,
            searchModel = null
        )
    } else {
        if (status.data.url != null) {
            status.data.url.let { webUrl ->
                val transactionId = status?.data?.data?.get(0)?.data?.get(0)?.txnId
                transactionId.let { transactionId ->
                    transactionId?.let { Log.d("test transactionId: ", it) }

                    status.data.id?.let { searchId ->
                        transactionId?.let {
                            navigateToWebViewFlowOneScreen(
                                navController = navController,
                                purpose = stringResource(R.string.getUerFlow),
                                fromFlow = fromFlow,
                                id = searchId,
                                transactionId = transactionId,
                                url = webUrl,
                                lenderStatusData = ""
                            )
//                            SearchWebView(
//                                navController = navController, urlToOpen = webUrl,
//                                searchId = searchId, transactionId = it, fromFlow = fromFlow,
//                                pageContent = {}
//                            )
                        }
                    }
                }
            }
        } else {
            val transactionId = status?.data?.txnId

            transactionId?.let { Log.d("test transactionId: ", it) }

            status.data.data?.forEach { data ->
                data?.step?.let { step ->
                    if (step.equals("consent_select", true)) { // verified
                        transactionId?.let {
                            data?.data?.let { userItem ->
                                userItem?.let {
//                                    val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
//                                    val nonNullUserList = userItem.filterNotNull()
//                                    val offerDetail = json.encodeToString(ListSerializer(UserStatusItem.serializer()), nonNullUserList)
                                    navigateToLoanProcessScreen(
                                        navController = navController,
                                        transactionId = transactionId,
                                        statusId = 20,
                                        responseItem = "Not required",
                                        offerId = "1234",
                                        fromFlow = "Invoice Loan"
                                    )
                                }
                            }
                        }
                    } else if (step.equals("offer_select", true)) {
                        status.data.data.forEach { item ->
                            item?.data?.forEach { item2 ->
                                item2?.fromUrl?.let { kycUrl ->
                                    item2.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            transactionId?.let { Log.d("test transactionId: ", it) }

//                                            navigateToLoanProcessScreen(
//                                                navController = navController,
//                                                transactionId = transactionId,
//                                                statusId = 3,
//                                                offerId = id, fromFlow = fromFlow,
//                                                responseItem = kycUrl
//                                            )
                                            navigateToLoanProcessScreen(
                                                navController = navController,
                                                statusId = 13,
                                                responseItem = kycUrl,
                                                offerId = id,
                                                fromFlow = fromFlow,
                                                transactionId = transactionId
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("offer_confirm", true)) { // verified
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { loanAgreementUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToLoanProcessScreen(
                                                transactionId = transactionId,
                                                offerId = id,
                                                responseItem = loanAgreementUrl,
                                                navController = navController,
                                                statusId = 13,
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("loan_approved", true)) { // Verified
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { verificationUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToBankKycVerificationScreen(
                                                navController = navController,
                                                kycUrl = verificationUrl,
                                                transactionId = transactionId,
                                                offerId = id,
                                                verificationStatus = "2",
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("entity_ekyc_after_form_submission", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                userItem.id?.let { id ->
                                    transactionId?.let { transactionId ->
                                        navigateToLoanProcessScreen(
                                            transactionId = transactionId,
                                            offerId = id,
                                            responseItem = "No Need",
                                            navController = navController,
                                            statusId = 14,
                                            fromFlow = fromFlow
                                        )
                                    }
                                }
                            }
                        }
                    } else if (step.equals("account_information_approved", true)) { // verified
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { enachUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToLoanProcessScreen(
                                                navController = navController,
                                                transactionId = transactionId,
                                                statusId = 5,
                                                responseItem = enachUrl,
                                                offerId = id,
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("loan_agreement_approved", true)) { // verified
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { loanAgreementUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToLoanProcessScreen(
                                                transactionId = transactionId,
                                                offerId = id,
                                                responseItem = loanAgreementUrl,
                                                navController = navController,
                                                statusId = 16,
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
        }
    }
}

@Composable
fun PurchaseDecidedFlow(
    context: Context,
    status: UserStatus?,
    navController: NavHostController,
    fromFlow: String,
    verifySessionResponse: VerifySessionResponse?
) {
    val purchaseFinanceViewModel: PurchaseFinanceViewModel = viewModel()
    val searchInProgress by purchaseFinanceViewModel.searchInProgress.collectAsState()
    val searchLoaded by purchaseFinanceViewModel.searchLoaded.collectAsState()
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

    LaunchedEffect(status?.data) {
        if (status?.data == null || status.data.data?.any { it == null } == true) {
            Log.d("test status: ", "null")
            purchaseFinanceViewModel.pFSearch(
                context = context,
                searchBodyModel = PFSearchBodyModel(
                    loanType = "PURCHASE_FINANCE",
                    bureauConsent = "on"
                )
            )
        }
    }

    val fromFlow = "Purchase Finance"


    if (status?.data == null || status.data.data?.any { it == null } == true) {
        Log.d("test status: ", "null")
        if (searchInProgress) {
            CenterProgress()
        } else if (searchLoaded) {
            navigateToDownPaymentScreen(navController = navController,
                fromFlow= fromFlow,
                verifySessionResponse = verifySessionResponse)
        }
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "loan_select_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.kyc_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "emandate_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.emandate_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "account_information_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.repayment_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.lastOrNull()?.step.equals(
            "loan_agreement_form_submission_failed",
            true
        )
    ) {
        navigateToFormRejectedScreen(
            navController = navController,
            errorTitle = stringResource(id = R.string.loan_agreement_failed),
            fromFlow = fromFlow,
            errorMsg = stringResource(id = R.string.form_submission_rejected_or_pending)
        )
    } else if (status.data.data?.firstOrNull()?.step.equals("pre_initial_search", true)) {
        Log.d("Sugu", "pre_initial_search")
        Log.d("Sugu", "verifySessionResponse : $verifySessionResponse")
        navigateToDownPaymentScreen(navController = navController, fromFlow = fromFlow,
            verifySessionResponse=verifySessionResponse)
    } else if (status.data.data?.firstOrNull()?.step.equals("form_submission_request", true)) {
        LaunchedEffect(Unit) {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType = "PURCHASE_FINANCE",
                step = "FORM_SUBMISSION_REQUEST"
            )
        }
        when {
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
                    NoLoanOffersAvailableScreen(
                        navController,
                        titleText = stringResource(R.string.no_lenders_available)
                    )
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

                            navigateToFormSubmissionWebScreen(
                                navController,
                                fromFlow,
                                lenderStatusJson
                            )
                        } catch (e: Exception) {
                            Log.e("JsonError", "Failed to serialize lender status", e)
                        }
                    }
                }
            }
        }
    } else if (status.data.data?.firstOrNull()?.step.equals("consent_request_sent", true)) {
        LaunchedEffect(Unit) {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType = "PURCHASE_FINANCE",
                step = "CONSENT_SELECT"
            )
        }
        when {
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
                    NoLoanOffersAvailableScreen(
                        navController,
                        titleText = stringResource(R.string.no_lenders_available)
                    )
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
                            navigateToConsentSubmissionWebScreen(
                                navController,
                                fromFlow,
                                lenderStatusJson
                            )
                        } catch (e: Exception) {
                            Log.e("JsonError", "Failed to serialize lender status", e)
                        }
                    }
                }
            }
        }

    } else if (status.data.data?.lastOrNull()?.step.equals("search", true)) {
        Log.d("res_H", "saerch")
        if (status.data.data?.any { !it?.data.isNullOrEmpty() } == true) {
            navigateToDownPaymentScreen(navController = navController, fromFlow = fromFlow,
                verifySessionResponse=verifySessionResponse)
        }
        val offerList =
            status.data.data?.lastOrNull()?.offerResponse?.flatMap { offerResponseItem ->
                offerResponseItem?.data?.map { data ->
                    Offer(
                        data.offer,
                        data.id,
                        data.bureauConsent
                    )
                } ?: emptyList()
            }
        val searchResponse = SearchResponseModel(
            id = status.data.data?.lastOrNull()?.id,
            url = status.data.data?.lastOrNull()?.url?.takeIf { it.isNotBlank() },
            transactionId = status.data.txnId,
            consentResponse = status.data.data?.firstOrNull()?.consentResponse,
            rejectedLenders = status.data.data?.firstOrNull()?.rejectedLenders,
            offerResponse = offerList
        )

        if (offerList.isNullOrEmpty()) {
            Log.d("res_H", "offerListnotEmpty")
            val searchModel = SearchModel(
                data = searchResponse,
                status = true,
                statusCode = 200
            )
            NavigateToWebView(
                context = context,
                searchModel = searchModel,
                gstSearchResponse = null,
                fromFlow = fromFlow,
                navController = navController,
                searchResponse = searchModel
            )
        } else {
            Log.d("res_H", "offerListEmpty")
            val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
            val searchJson = json.encodeToString(
                SearchModel.serializer(),
                SearchModel(data = searchResponse, status = true, statusCode = 200)
            )
            navigateToBureauOffersScreen(
                navController,
                stringResource(R.string.getUerFlow),
                fromFlow,
                searchJson
            )
        }
    } else {
        if (status.data.url != null) {
            LaunchedEffect(Unit) {
                webViewModel.getLenderStatusApi(
                    context = context,
                    loanType = "PURCHASE_FINANCE",
                    step = "CONSENT_SELECT"
                )
            }
            when {
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
                        NoLoanOffersAvailableScreen(
                            navController,
                            titleText = stringResource(R.string.no_lenders_available)
                        )
                    } else {
                        LaunchedEffect(lenderStatus) {
                            val json = Json {
                                prettyPrint = true
                                ignoreUnknownKeys = true
                            }
                            val lenderStatusJson = json.encodeToString(
                                LenderStatusResponse.serializer(),
                                LenderStatusResponse(response = lenderStatus)
                            )

                            val transactionId = status.data.data?.get(0)?.data?.get(0)?.txnId
                            val searchId = status.data.id
                            val webUrl = status.data.url

                            if (!transactionId.isNullOrBlank() && !searchId.isNullOrBlank() && !webUrl.isNullOrBlank()) {

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
                }
            }
        } else {
            val transactionId = status.data.txnId
            val rejectedLenders = status.data.data?.firstOrNull()?.rejectedLenders
            transactionId?.let { Log.d("test transactionId: ", it) }
            status.data.data?.forEach { data ->
                data?.step?.let { step ->
                    if (step.equals("post_search", true)) {
                        val offerList = data.offerResponse
                            ?.flatMap { offerResponseItem ->
                                offerResponseItem?.data?.map { d ->
                                    Offer(d.offer, d.id, d.bureauConsent)
                                } ?: emptyList()
                            }
                        val rejectedList = rejectedLenders?.map {
                            RejectedLenders(
                                name = it?.name,
                                image = it?.image,
                                reason = it?.reason,
                                minLoanAmount = it?.minLoanAmount,
                                maxLoanAmount = it?.maxLoanAmount,
                                maxInterestRate = it?.maxInterestRate,
                                minInterestRate = it?.minInterestRate
                            )
                        }
                        val combined = OffersWithRejections(
                            offers = offerList,
                            rejectedLenders = rejectedList
                        )

                        val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
                        val jsonString = json.encodeToString(combined)
                        navigateToLoanOffersListScreen(navController, jsonString, fromFlow)
                    } else if (step.equals("offer_confirm", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { kycUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToKycAnimation(
                                                navController,
                                                transactionId,
                                                id,
                                                kycUrl,
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("offer_confirm_form_submission_APPROVED", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { verificationUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToBankKycVerificationScreen(
                                                navController = navController,
                                                kycUrl = verificationUrl,
                                                transactionId = transactionId,
                                                offerId = id,
                                                verificationStatus = "2",
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("loan_approved", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { verificationUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToRepaymentScreen(
                                                navController,
                                                transactionId,
                                                verificationUrl,
                                                id,
                                                fromFlow
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (step.equals("loan_agreement_approved", true)) {
                        status.data.data.forEach { userItem ->
                            userItem?.data?.forEach { item ->
                                item?.fromUrl?.let { loanAgreementUrl ->
                                    userItem.id?.let { id ->
                                        transactionId?.let { transactionId ->
                                            navigateToLoanAgreementScreen(
                                                navController = navController,
                                                url = loanAgreementUrl,
                                                transactionId = transactionId,
                                                id = id,
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplyByCategoryScreenPreview() {
                                    var verifySessionResponse1 = "{\n" +
                                        "    \"statusCode\": 201,\n" +
                                        "    \"status\": true,\n" +
                                        "    \"message\": \"Session Retrived Successfully\",\n" +
                                        "    \"data\": {\n" +
                                        "        \"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxMDE2NzA0LCJleHAiOjE3NTE2MjE1MDR9.WpYrU6AvEsubAarJyna2rkHFWGK3oM208RPxORKBPfk\",\n" +
                                        "        \"sessionId\": \"83f29f24-704d-529f-a3b4-4a5560cd2c70\",\n" +
                                        "        \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxNDQyNDUxLCJleHAiOjE3NTE0NDI2MzF9.ziPqEqA9eFr_Co-B1b6mwYMKgzgTjjrFR8lYeUIilME\",\n" +
                                        "        \"sseId\": \"a41bb04e18a5545395ba6cba4e34607c\",\n" +
                                        "        \"securityKey\": \"110e5c0419135e22814945c143ab9fda\",\n" +
                                        "        \"sessionData\": {\n" +
                                        "            \"downPayment\": 0,\n" +
                                        "            \"productId\": \"2a9a7100-bf22-5858-83f3-62e65c2e3a8c\",\n" +
                                        "            \"merchantPAN\": null,\n" +
                                        "            \"merchantBankAccount\": null,\n" +
                                        "            \"merchantGST\": null,\n" +
                                        "            \"merchantAccountHolderName\": null,\n" +
                                        "            \"merchantIfscCode\": null,\n" +
                                        "            \"productBrand\": null,\n" +
                                        "            \"productCategory\": \"Mobile Phone\",\n" +
                                        "            \"productSKUID\": \"47af53c0-0add-5c49-9365-8f42fafa18fe\",\n" +
                                        "            \"productReturnWindow\": \"P7D\",\n" +
                                        "            \"productModel\": null,\n" +
                                        "            \"productSellingPrice\": \"90000\",\n" +
                                        "            \"productCancellable\": true,\n" +
                                        "            \"productReturnable\": true,\n" +
                                        "            \"productName\": \"VRIDDHI\",\n" +
                                        "            \"productMrpPrice\": \"100000\",\n" +
                                        "            \"productSymbol\": \"https://storage.googleapis.com/nslive/62bab198-9391-5be4-b672-e78a6a299a1f/raw/1742808735478_1742808733756.png\",\n" +
                                        "            \"productQuantity\": \"1\"\n" +
                                        "        },\n" +
                                        "        \"sessionType\": \"FIS_PF\"\n" +
                                        "    }\n" +
                                        "}"

    val verifySessionResponseObj = Json {
        ignoreUnknownKeys = true // skips unknown JSON keys safely
        coerceInputValues = true
        isLenient = true
    }.decodeFromString<VerifySessionResponse>(verifySessionResponse1)

    ApplyByCategoryScreen(navController = NavHostController(LocalContext.current),
        verifySessionResponse = verifySessionResponseObj)
}
