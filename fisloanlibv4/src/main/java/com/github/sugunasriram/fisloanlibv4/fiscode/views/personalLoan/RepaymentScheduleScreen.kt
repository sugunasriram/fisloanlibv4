package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.app.MainActivity.Companion.NOTIFICATION_PERMISSION_REQUEST_CODE
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgressFixedHeight
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderValueWithTextBelow
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyClickAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.SpaceBetweenTextIcon
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TextDescriptionWithRadioButton
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToCreateIssueScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToIssueListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPrePaymentWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.StatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.OrderPaymentStatusItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CheckOrderIssueModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OrderByIdResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanAgreement
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanBody
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.semiBold20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.slateGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.GetOrderPaymentStatusViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.PrePaymentStatusScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Period
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CancelLoan
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CancelLoanResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal28Text700

var amount_to_be_paid = ""
var coolOffPeriodDate = ""
var principal = ""
var prepartPaymentCharges: String? = null
const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1003


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepaymentScheduleScreen(
    navController: NavHostController,
    orderId: String,
    fromFlow: String,
    fromScreen: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val getOrderPaymentStatusViewModel: GetOrderPaymentStatusViewModel = viewModel()

    val updateProcessing by loanAgreementViewModel.updateProcessing.collectAsState()
    val updateProcessed by loanAgreementViewModel.updateProcessed.collectAsState()
    val updatedLoanAgreement by loanAgreementViewModel.updatedLoanAgreement.collectAsState()
    val gettingOrderById by loanAgreementViewModel.gettingOrderById.collectAsState()
    val orderByIdLoaded by loanAgreementViewModel.orderByIdLoaded.collectAsState()
    val orderByIdResponse by loanAgreementViewModel.orderByIdResponse.collectAsState()
    val checkedOrderIssues by loanAgreementViewModel.checkedOrderIssues.collectAsState()
    val checkingOrderIssues by loanAgreementViewModel.checkingOrderIssues.collectAsState()
    val checkOrderIssueResponse by loanAgreementViewModel.checkOrderIssueResponse.collectAsState()
    val checkingStatus by loanAgreementViewModel.checkingStatus.collectAsState()
    val checked by loanAgreementViewModel.checked.collectAsState()
    val loanStatus by loanAgreementViewModel.status.collectAsState()

    val showServerIssueScreen by loanAgreementViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by loanAgreementViewModel.unexpectedError.observeAsState(false)
    val showInternetScreen by loanAgreementViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by loanAgreementViewModel.showTimeOutScreen.observeAsState(false)
    val unAuthorizedUser by loanAgreementViewModel.unAuthorizedUser.observeAsState(false)
    val errorHandling by loanAgreementViewModel.errorHandling.observeAsState(false)
    val middleLoan by loanAgreementViewModel.middleLoan.observeAsState(false)
    val errorMessage by loanAgreementViewModel.errorMessage.collectAsState()

    val orderPaymentListLoading by getOrderPaymentStatusViewModel.orderPaymentListLoading.collectAsState()
    val orderPaymentListLoaded by getOrderPaymentStatusViewModel.orderPaymentListLoaded.collectAsState()
    val orderPaymentStatusList by getOrderPaymentStatusViewModel.orderPaymentStatusList.collectAsState()
    val navigationToSignIn by getOrderPaymentStatusViewModel.navigationToSignIn.collectAsState()

    val paymentOptionBottomSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheetStateValue = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val loanType = CommonMethods().setFromFlow(fromFlow)


    // Call getOrderPaymentStatus when the composable first loads
    LaunchedEffect(Unit) {
        // Fetch payment status
        getOrderPaymentStatusViewModel.getOrderPaymentStatus(
            loanType = loanType,
            loanId = orderId,
            context = context
        )
    }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(
            navController,
            errorMessage,
            onGoBack = {
                navController.popBackStack() // or any custom behavior
            }
        )
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        errorHandling -> {
            PrePaymentStatusScreen(
                navController = navController,
                orderId = orderId,
                fromFlow = fromFlow,
                headerText = stringResource(id = R.string.repayment_un_successful),
                showButton = true,
                image = painterResource(id = R.drawable.payment_unsuccess)
            ) {}
        }

        else -> {
            RepaymentScheduleScreenHandle(
                checkingOrderIssues = checkingOrderIssues, scope = scope, loanType = loanType,
                checkedOrderIssues = checkedOrderIssues, orderId = orderId,
                bottomSheetStateValue = bottomSheetStateValue, context = context,
                getOrderPaymentStatusViewModel = getOrderPaymentStatusViewModel,
                loanAgreementViewModel = loanAgreementViewModel, navController = navController,
                updateProcessed = updateProcessed, gettingOrderById = gettingOrderById,
                updateProcessing = updateProcessing, fromFlow = fromFlow,
                paymentOptionBottomSheet = paymentOptionBottomSheet, orderByIdLoaded = orderByIdLoaded,
                updatedLoanAgreement = updatedLoanAgreement,
                orderPaymentListLoading = orderPaymentListLoading,
                checkOrderIssueResponse = checkOrderIssueResponse,
                orderPaymentStatusList = orderPaymentStatusList,
                orderPaymentListLoaded = orderPaymentListLoaded,
                orderByIdResponse = orderByIdResponse, checked = checked,
                checkingStatus = checkingStatus, loanStatus = loanStatus,
                fromScreen = fromScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepaymentScheduleScreenHandle(
    checkingOrderIssues: Boolean,
    checkedOrderIssues: Boolean,
    gettingOrderById: Boolean,
    orderId: String,
    bottomSheetStateValue: ModalBottomSheetState,
    context: Context,
    getOrderPaymentStatusViewModel: GetOrderPaymentStatusViewModel,
    loanAgreementViewModel: LoanAgreementViewModel,
    updateProcessed: Boolean,
    updateProcessing: Boolean,
    fromFlow: String,
    navController: NavHostController,
    paymentOptionBottomSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    updatedLoanAgreement: UpdateLoanAgreement?,
    orderPaymentListLoading: Boolean,
    checkOrderIssueResponse: CheckOrderIssueModel?,
    orderByIdLoaded: Boolean,
    orderPaymentStatusList: ArrayList<OrderPaymentStatusItem>?,
    loanType: String,
    orderPaymentListLoaded: Boolean,
    orderByIdResponse: OrderByIdResponse?,
    checked: Boolean,
    checkingStatus: Boolean,
    loanStatus: StatusResponse?,
    fromScreen: String
) {
    var selectedOption by remember { mutableStateOf("") }
    if (checkingOrderIssues || gettingOrderById || checkingStatus || orderPaymentListLoading) {
        CenterProgress()
    } else {
        if (checkedOrderIssues || orderByIdLoaded || checked || orderPaymentListLoaded) {
            if (checked) {
                loanStatus?.data?.data?.data?.catalog?.let { loanDetails ->
//                    ModalBottomSheetLayout(
//                        sheetState = bottomSheetStateValue,
//                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

                    // Extract the PRE_PAYMENT_CHARGE value from quoteBreakUp
                    loanDetails.quoteBreakUp?.forEach { item ->
                        if (item?.title?.lowercase() == "pre_payment_charge" ||
                            item?.title?.lowercase() == "pre_payment_charges" ) {
                            prepartPaymentCharges = item.value
                            Log.d("PrePaymentCharges 1",
                                "prepartPaymentCharges: $prepartPaymentCharges")
                        }
                    }

                    CustomModalBottomSheet(
                        bottomSheetState = bottomSheetStateValue,
                        sheetContent = {
                            BottomSheetHandle(
                                loanDetails = loanDetails, context = context,
                                loanAgreementViewModel = loanAgreementViewModel,
                                updateProcessed = updateProcessed, updateProcessing = updateProcessing,
                                fromFlow = fromFlow, navController = navController,
                                scope = scope, bottomSheetStateValue = bottomSheetStateValue,
                                updatedLoanAgreement = updatedLoanAgreement, loanType = loanType,
                                orderPaymentListLoading = orderPaymentListLoading,
                                selectedOption = selectedOption, orderId = orderId
                            )
                        }
                    ) {
                        CustomModalBottomSheet(
                            bottomSheetState = paymentOptionBottomSheet,
                            sheetContent = {
                                var showMissedEmi = false
                                loanDetails.payments?.forEach { payment ->
                                    if (payment?.type == "POST_FULFILLMENT" && payment.status == "DELAYED") {
                                        showMissedEmi = true
                                    }
                                }
                                PaymentOptionsPopUp(
                                    fromFlow = fromFlow, context = context, loanType = loanType,
                                    showMissedEmi = showMissedEmi, navController = navController,
                                    onDismiss = { scope.launch { paymentOptionBottomSheet.hide() } },
                                    onOptionSelected = { option ->
                                        selectedOption = option
//                                        if (option == "PAY_EMI" || option == "MISSED_EMI_PAYMENT" || option == "PRE_PART_PAYMENT" || option == "FORECLOSURE") {
                                        if (option == "MISSED_EMI_PAYMENT" || option == "PRE_PART_PAYMENT" || option == "FORECLOSURE") {
                                            scope.launch { bottomSheetStateValue.show() }
                                        }
                                    },
                                    loanDetails = loanDetails, orderId = orderId
                                )
                            }
                        ) {
                            RepaymentScheduleView(
                                navController = navController, scope = scope, context = context,
                                checkOrderIssueResponse = checkOrderIssueResponse,
                                loanDetails = loanDetails, fromFlow = fromFlow,
                                orderPaymentListLoaded = orderPaymentListLoaded,
                                orderPaymentStatusList = orderPaymentStatusList,
                                bottomSheetState = paymentOptionBottomSheet, orderId = orderId,
                                getOrderPaymentStatusViewModel = getOrderPaymentStatusViewModel,
                                loanAgreementViewModel = loanAgreementViewModel,
                                loanType = loanType, fromScreen = fromScreen,
                                checkingStatus=checkingStatus,
                                orderPaymentListLoading=orderPaymentListLoading
                            )
                        }
                    }
                }
            } else {
                orderByIdResponse?.data?.let { loanDetails ->
//                    ModalBottomSheetLayout(
//                        sheetState = bottomSheetStateValue,
//                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

                    // Extract the PRE_PAYMENT_CHARGE value from quoteBreakUp
                    loanDetails.quoteBreakUp?.forEach { item ->
                        if (item?.title?.lowercase() == "pre_payment_charge" ||
                            item?.title?.lowercase() == "pre_payment_charges" ) {
                            prepartPaymentCharges = item.value
                            Log.d("PrePaymentCharges 2",
                                "prepartPaymentCharges: $prepartPaymentCharges")

                        }
                    }


                    CustomModalBottomSheet(
                        bottomSheetState = bottomSheetStateValue,
                        sheetContent = {
                            BottomSheetHandle(
                                loanDetails = loanDetails, context = context,
                                loanAgreementViewModel = loanAgreementViewModel,
                                updateProcessed = updateProcessed, fromFlow = fromFlow,
                                navController = navController, updateProcessing = updateProcessing,
                                scope = scope, bottomSheetStateValue = bottomSheetStateValue,
                                updatedLoanAgreement = updatedLoanAgreement, loanType = loanType,
                                orderPaymentListLoading = orderPaymentListLoading,
                                selectedOption = selectedOption, orderId = orderId
                            )
                        }
                    ) {
//                        ModalBottomSheetLayout(
//                            sheetState = paymentOptionBottomSheet,
                        CustomModalBottomSheet(
                            bottomSheetState = paymentOptionBottomSheet,
                            sheetContent = {
                                var showMissedEmi = false
                                loanDetails.payments?.forEach { payment ->
                                    if (payment?.type == "POST_FULFILLMENT" && payment.status == "DELAYED") {
                                        showMissedEmi = true
                                    }
                                }
                                PaymentOptionsPopUp(
                                    fromFlow = fromFlow, context = context, loanType = loanType,
                                    showMissedEmi = showMissedEmi, navController = navController,
                                    onDismiss = { scope.launch { paymentOptionBottomSheet.hide() } },
                                    onOptionSelected = { option ->
                                        selectedOption = option
//                                        if (option == "PAY_EMI" || option == "MISSED_EMI_PAYMENT" || option == "PRE_PART_PAYMENT" || option == "FORECLOSURE") {
                                        if (option == "MISSED_EMI_PAYMENT" || option == "PRE_PART_PAYMENT" || option == "FORECLOSURE") {
                                            scope.launch {
                                                bottomSheetStateValue.show()
                                            }
                                        }
                                    },
                                    loanDetails = loanDetails, orderId = orderId
                                )
                            },
                            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ) {
                            RepaymentScheduleView(
                                navController = navController, scope = scope, context = context,
                                checkOrderIssueResponse = checkOrderIssueResponse,
                                loanDetails = loanDetails, fromFlow = fromFlow,
                                orderPaymentListLoaded = orderPaymentListLoaded,
                                orderPaymentStatusList = orderPaymentStatusList,
                                bottomSheetState = paymentOptionBottomSheet, orderId = orderId,
                                getOrderPaymentStatusViewModel = getOrderPaymentStatusViewModel,
                                loanAgreementViewModel = loanAgreementViewModel,
                                loanType = loanType, fromScreen = fromScreen,
                                checkingStatus=checkingStatus,
                                orderPaymentListLoading=orderPaymentListLoading
                            )
                        }
                    }
                }
            }
        } else {
            loanAgreementViewModel.getOrderById(
                orderId = orderId,
                context = context,
                loanType = loanType
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepaymentScheduleView(
    navController: NavHostController,
    checkOrderIssueResponse: CheckOrderIssueModel?,
    loanDetails: OfferResponseItem,
    fromFlow: String,
    orderPaymentListLoaded: Boolean,
    orderPaymentStatusList: ArrayList<OrderPaymentStatusItem>?,
    context: Context,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    orderId: String,
    getOrderPaymentStatusViewModel: GetOrderPaymentStatusViewModel,
    loanAgreementViewModel: LoanAgreementViewModel,
    loanType: String,
    fromScreen: String,
    checkingStatus:Boolean,
    orderPaymentListLoading:Boolean
) {
    var showLoanCancelPopUp by remember { mutableStateOf(false) }

    val pfLoanCancelling by loanAgreementViewModel.pfLoanCancelling.collectAsState()
    val pfLoanCancelled by loanAgreementViewModel.pfLoanCancelled.collectAsState()
    var cancelReason by remember { mutableStateOf("") }
    var cancelReasonError by remember { mutableStateOf(false) }

//    val isLoanDisbursed = loanDetails.fulfillments
//        ?.any { fulfilment ->
//            fulfilment?.state?.descriptor?.code?.contains("Disbursed", ignoreCase = true) == true
//        } ?: false

    val isLoanDisbursed: Boolean = if (
        loanDetails.status == null || loanDetails.status.equals("ACTIVE", ignoreCase = true)
    ) {
        loanDetails.fulfillments
            ?.any { fulfilment ->
                fulfilment?.state?.descriptor?.code?.contains("Disbursed", ignoreCase = true) == true
            } ?: false
    } else {
        false
    }
//    val isLoanClosed = loanDetails.fulfillments
//        ?.any { fulfilment ->
//            val status = fulfilment?.state?.descriptor?.code.orEmpty()
//            status.contains("completed", ignoreCase = true) ||
//                status.contains("closed", ignoreCase = true)
//        } ?: false
    val isLoanClosed: Boolean =
        if (loanDetails.status.equals("CANCELLED", ignoreCase = true)) {
            true
        } else {
            loanDetails.fulfillments
                ?.any { fulfilment ->
                    val status = fulfilment?.state?.descriptor?.code.orEmpty()
                    status.contains("completed", ignoreCase = true) ||
                        status.contains("closed", ignoreCase = true)
                } ?: false
        }
    val isLoanInitiated = loanDetails.fulfillments
        ?.any { fulfilment ->
            val status = fulfilment?.state?.descriptor?.code.orEmpty()
            status.contains("INITIATED", ignoreCase = true) ||
                status.contains("SANCTIONED", ignoreCase = true)
        } ?: false
    BackHandler {
        if (fromScreen == "Loan Summary") {
            navController.popBackStack()
        } else if (fromScreen == "Loan Status") {
            navController.popBackStack()
        } else if (fromScreen == "PrePayment") {
            navController.popBackStack()
        } else {
            //Sugu todo
//            navigateApplyByCategoryScreen(navController)
            navigateToFISExitScreen(navController, loanId="4321")
        }
    }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.repayment_schedule),
        showBackButton = true,
        backgroundColor = backgroundOrange,
        contentStart = 0.dp, contentEnd = 0.dp,
        onBackClick = {
            if (fromScreen == "Loan Summary") {
                navController.popBackStack()
            } else if (fromScreen == "Loan Status") {
                navController.popBackStack()
            } else if (fromScreen == "PrePayment") {
                navController.popBackStack()
            } else {
                //Sugu todo
//                navigateApplyByCategoryScreen(navController)
                navigateToFISExitScreen(navController, loanId="4321")

            }
        },
        showBottom = true,
        showTripleButton = true,
        primaryButtonText = stringResource(R.string.repayment),
        onPrimaryButtonClick = {
            if (isLoanDisbursed) {
                scope.launch { bottomSheetState.show() }
            } else if (isLoanClosed) {
                CommonMethods().toastMessage(context, "Loan has been closed.")
            } else {
                CommonMethods().toastMessage(context, "Loan has not been disbursed yet. Please wait.")
            }
        },
        secondaryButtonText = stringResource(R.string.refresh),
        onSecondaryButtonClick = {
            loanAgreementViewModel.status(loanType = loanType, context = context, orderId = orderId)
            getOrderPaymentStatusViewModel.getOrderPaymentStatus(
                loanType = loanType,
                loanId = orderId,
                context = context
            )
        },
        tertiaryButtonText = stringResource(R.string.home),
//        onTertiaryButtonClick = { navigateApplyByCategoryScreen(navController) }
        onTertiaryButtonClick = {             navigateToFISExitScreen(navController, loanId="4321") }
    ) {
        if(checkingStatus || orderPaymentListLoading){
            CenterProgressFixedHeight(top=320.dp)
        }else {
//        Get Cool Off Period
            loanDetails.itemTags?.forEach { itemTags ->
                itemTags?.let {
                    it.tags.let { tags ->
                        tags.forEach { tag ->
                            if (tag.key.contains("cool_off", ignoreCase = true)) {
                                coolOffPeriodDate = tag.value ?: ""
                                return@forEach // Exit the loop after processing the first matching tag
                            }
                        }
                    }
                }
            }

            // PRINCIPAL
            loanDetails.quoteBreakUp?.forEach { quoteBreakUp ->
                quoteBreakUp?.let {
                    it.title?.let { title ->
                        it.value?.let { description ->
                            if (title.equals("PRINCIPAL", ignoreCase = true) ||
                                title.lowercase().contains("PRINCIPAL_", ignoreCase = true)) {
                                principal = description
                                return@forEach // Exit the loop after processing the first matching tag
                            }
                        }
                    }
                }
            }
            // only if PF and Loan is disbursed
            if (isLoanDisbursed && fromFlow == "LOAN") {
                Spacer(modifier = Modifier.height(8.dp))
                CurvedPrimaryButton(
                    text = "Cancel Loan Request",
                    textColor = appOrange,
                    backgroundColor = appWhite,
                    start = 80.dp,
                    end = 80.dp
                ) {
                    val cancelLoan = CancelLoan(
                        loanType = "PURCHASE_FINANCE",
                        orderId = orderId,
                        cancelType = "SOFT_CANCEL",
                        cancelReason = "something"
                    )
                    loanAgreementViewModel.cancelLoanRequest(cancelLoan, context)
                    showLoanCancelPopUp = true
                }
            }
            if (showLoanCancelPopUp) {
                AlertDialog(
                    onDismissRequest = { showLoanCancelPopUp = false },
                    confirmButton = {
                        CurvedPrimaryButton(
                            text = stringResource(id = R.string.yes),
                            start = 15.dp,
                            end = 15.dp,
                            top = 5.dp,
                            bottom = 5.dp,
                            enabled = !pfLoanCancelling
                        ) {
                            if (cancelReason.isBlank()) {
                                cancelReasonError = true
                            } else {
                                val cancelLoan = CancelLoan(
                                    loanType = "PURCHASE_FINANCE",
                                    orderId = orderId,
                                    cancelType = "CONFIRM_CANCEL",
                                    cancelReason = cancelReason
                                )
                                loanAgreementViewModel.cancelLoanRequest(cancelLoan, context)
                                if (pfLoanCancelled && !pfLoanCancelling) {
                                    loanAgreementViewModel.status(
                                        loanType = loanType,
                                        context = context,
                                        orderId = orderId
                                    )
                                    getOrderPaymentStatusViewModel.getOrderPaymentStatus(
                                        loanType = loanType,
                                        loanId = orderId,
                                        context = context
                                    )
                                }
                                showLoanCancelPopUp = false
                            }
                        }
                    },
                    dismissButton = {
                        CurvedPrimaryButton(
                            text = stringResource(id = R.string.no),
                            start = 15.dp,
                            end = 15.dp,
                            top = 5.dp,
                            bottom = 5.dp,
                            enabled = !pfLoanCancelling
                        ) {
                            showLoanCancelPopUp = false
                        }
                    },
                    title = {
                        Text(
                            text = "Confirm Cancellation",
                            style = normal28Text700,
                            modifier = Modifier,
                            color = appBlack
                        )
                    },
                    text = {
                        if (pfLoanCancelling && !pfLoanCancelled) CenterProgressFixedHeight(top = 0.dp, size = 20.dp)
                        if (pfLoanCancelled && !pfLoanCancelling) {
                            Column {
                                Text(
                                    "This action will stop your loan process. Do you want to continue?",
                                    style = normal14Text700
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = cancelReason,
                                    onValueChange = {
                                        cancelReason = it
                                        cancelReasonError = false
                                    },
                                    label = {
                                        Text(
                                            "Enter reason",
                                            color = hintGray,
                                            style = normal14Text400,
                                            textAlign = TextAlign.Center
                                        )
                                    },
                                    singleLine = true,
                                    isError = cancelReasonError,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    visualTransformation = VisualTransformation.None,
                                    textStyle = normal14Text400,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = appOrange,
                                        unfocusedBorderColor = grayD6,
                                        cursorColor = appOrange,
                                        textColor = appBlack
                                    )
                                )
                                if (cancelReasonError) {
                                    Text(
                                        text = "Reason is required",
                                        color = errorRed,
                                        style = normal12Text400,
                                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, appWhite, shape = RoundedCornerShape(8.dp))
                )
            }
            CompleteLoanDetails(
                loanDetails = loanDetails,
                context = context,
                orderPaymentListLoaded = orderPaymentListLoaded,
                orderPaymentStatusList = orderPaymentStatusList,
                checkOrderIssueResponse = checkOrderIssueResponse,
                fromFlow = fromFlow,
                navController = navController,
                isLoanClosed = isLoanClosed,
                isLoanInitiated = isLoanInitiated
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetHandle(
    loanDetails: OfferResponseItem,
    context: Context,
    navController: NavHostController,
    loanAgreementViewModel: LoanAgreementViewModel,
    scope: CoroutineScope,
    updateProcessed: Boolean,
    updateProcessing: Boolean,
    fromFlow: String,
    bottomSheetStateValue: ModalBottomSheetState,
    updatedLoanAgreement: UpdateLoanAgreement?,
    orderPaymentListLoading: Boolean,
    selectedOption: String,
    orderId: String,
    loanType: String
) {
    when (selectedOption) {
//        "PAY_EMI" -> {
//            PayEmi(
//                updateProcessing = updateProcessing, navController = navController,
//                updateProcessed = updateProcessed, loanDetails = loanDetails,
//                loanAgreementViewModel = loanAgreementViewModel,
//                fromFlow = fromFlow, scope = scope, context = context,
//                bottomSheetStateValue = bottomSheetStateValue, loanType = loanType,
//                updatedLoanAgreement = updatedLoanAgreement, orderId = orderId
//            )
//        }
        "MISSED_EMI_PAYMENT" -> {
            MissedEmi(
                updateProcessing = updateProcessing, navController = navController,
                updateProcessed = updateProcessed, loanDetails = loanDetails,
                loanAgreementViewModel = loanAgreementViewModel,
                fromFlow = fromFlow, scope = scope, context = context,
                bottomSheetStateValue = bottomSheetStateValue, loanType = loanType,
                updatedLoanAgreement = updatedLoanAgreement, orderId = orderId
            )
        }

        "PRE_PART_PAYMENT" -> {
            if (updateProcessing || orderPaymentListLoading) {
                CenterProgressFixedHeight()
            }
        }

        "FORECLOSURE" -> {
            ForeClosure(
                loanDetails = loanDetails, updatedLoanAgreement = updatedLoanAgreement,
                updateProcessed = updateProcessed, updateProcessing = updateProcessing,
                context = context, navController = navController,
                loanAgreementViewModel = loanAgreementViewModel, scope = scope,
                bottomSheetStateValue = bottomSheetStateValue,
                fromFlow = fromFlow, orderId = orderId, loanType = loanType
            )
        }
    }
}

@Composable
fun CompleteLoanDetails(
    loanDetails: OfferResponseItem,
    orderPaymentListLoaded: Boolean,
    checkOrderIssueResponse: CheckOrderIssueModel?,
    context: Context,
    orderPaymentStatusList: ArrayList<OrderPaymentStatusItem>?,
    navController: NavHostController,
    fromFlow: String,
    isLoanClosed: Boolean,
    isLoanInitiated: Boolean
) {
    val relevantPayments = orderPaymentStatusList?.filter { payment ->
        payment.status == "PAID"
    }
    // Application Details
    ApplicantDetails(loanDetails, context)
    // Loan Summary
    LoanSummary(loanDetails)
    // EMI Details Table
    EmiDetail(loanDetails)
    if (orderPaymentListLoaded) {
        Log.d("PaymentHistory", "orderPaymentStatusList : $orderPaymentStatusList")
        if (!relevantPayments.isNullOrEmpty()) {
            Log.d("PaymentHistory", "orderPaymentStatusList-Not empty: $orderPaymentStatusList")
            PaymentHistoryCard(relevantPayments)
        }
    }
    // GRO details
    GRODetailsCard(loanDetails, context)
    // Contact details
    ContactDetailsCard(loanDetails)
    // Loan Cancellation Terms
    CancellationTermsCard(loanDetails, context)
//    if(!isLoanInitiated && !isLoanClosed){
    ReportIssueCard(checkOrderIssueResponse, loanDetails, navController, fromFlow)
    // Loan Agreement Details
    LoanAgreementDetailsCard(loanDetails, context)
    DownloadLoanDetailsCard(loanDetails, relevantPayments ?: emptyList(), context)
}

@Composable
fun ApplicantDetails(loanDetails: OfferResponseItem, context: Context) {
    DisplayCard(
        cardColor = appWhite,
        borderColor = appWhite,
        roundedCornerDp = 6.dp,
        start = 10.dp,
        end = 10.dp,
        bottom = 10.dp,
        top = 10.dp
    ) {
        loanDetails.fulfillments?.firstOrNull()?.customer?.let { customer ->
            customer.person?.name?.let { name ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.applicant_name),
                    textColorHeader = slateGrayColor,
                    textValue = name,
                    style = normal14Text400,
                    end = 5.dp,
                    start = 8.dp,
                    top = 10.dp,
                    bottom = 8.dp
                )
            }

            customer.contact?.email?.let { email ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.applicant_email),
                    textColorHeader = slateGrayColor,
                    textValue = email.lowercase(),
                    style = normal14Text400,
                    end = 5.dp,
                    start = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            }

            customer.contact?.phone?.let { mobileNumber ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.mobile_number),
                    textColorHeader = slateGrayColor,
                    textValue = mobileNumber,
                    style = normal14Text400,
                    end = 5.dp,
                    start = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            }
        }
    }
    DisplayCard(
        cardColor = appWhite,
        borderColor = appWhite,
        roundedCornerDp = 6.dp,
        start = 10.dp,
        end = 10.dp,
        bottom = 10.dp
    ) {
//        val applicationId = loanDetails.itemId
        val applicationId = loanDetails.id

        val status = loanDetails.status

        if (status == null || status.equals("ACTIVE", ignoreCase = true)) {
            // ✅ take from fulfillments
            loanDetails.fulfillments?.firstOrNull()?.state?.descriptor?.code?.let { loanStatus ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.loan_status),
                    textColorHeader = slateGrayColor,
                    textValue = loanStatus,
                    style = normal14Text400,
                    end = 5.dp,
                    start = 8.dp,
                    top = 10.dp,
                    bottom = 8.dp
                )
            }
        } else {
            // ✅ show status directly
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.loan_status),
                textColorHeader = slateGrayColor,
                textValue = status,
                style = normal14Text400,
                end = 5.dp,
                start = 8.dp,
                top = 10.dp,
                bottom = 8.dp
            )
        }
//        loanDetails.fulfillments?.firstOrNull()?.state?.let { state ->
//                state.descriptor?.code?.let { loanStatus ->
//                    OnlyReadAbleText(
//                        textHeader = stringResource(id = R.string.loan_status),
//                        textColorHeader = slateGrayColor,
//                        textValue = loanStatus,
//                        style = normal14Text400,
//                        end = 5.dp,
//                        start = 8.dp,
//                        top = 10.dp,
//                        bottom = 8.dp
//                    )
//                }
//            }

        applicationId?.let {
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.loan_application_id),
                textColorHeader = slateGrayColor,
                textValue = applicationId,
                style = normal14Text400,
                end = 5.dp,
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        }

        loanDetails.itemDescriptor?.let { itemDescriptor ->
            itemDescriptor.name?.let { loanType ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.loan_type),
                    textColorHeader = slateGrayColor,
                    textValue = loanType,
                    style = normal14Text400,
                    end = 5.dp,
                    start = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            }
        }
        OnlyReadAbleText(
            textHeader = stringResource(id = R.string.loan_amount),
            textColorHeader = slateGrayColor,
//                    textValue = CommonMethods().formatIndianDoubleCurrency(principal.takeIf { it.isNotEmpty() }?.toDoubleOrNull() ?: 0.0),
            textValue = principal,
            style = normal14Text400,
            end = 5.dp,
            start = 8.dp,
            top = 8.dp,
            bottom = 8.dp
        )

        loanDetails.itemPrice?.value?.let {
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.total_payable_amount),
                textValue = it,
                textColorHeader = slateGrayColor,
                style = normal14Text400,
                end = 5.dp,
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        }
        loanDetails.itemTags?.forEach { itemTags ->
            itemTags?.let {
                // Check if display is true
                if (it.display == true) {
                    it.tags.let { tags ->
                        tags.forEach { tag ->
                            val newTitle = CommonMethods().displayFormattedText(tag.key)

                            val displayValue =
                                if (tag.key.contains("cool_off", ignoreCase = true) ||
                                    tag.key.contains("cool off", ignoreCase = true)
                                ) {
                                    convertUTCToLocalDateTime(tag.value)
                                } else {
                                    tag.value
                                }

                            if (newTitle.equals("Tnc Link", ignoreCase = true)) {
                                OnlyClickAbleText(
                                    textHeader = newTitle,
                                    textValue = displayValue,
                                    style = normal14Text400,
                                    bottom = 5.dp,
                                    start = 6.dp,
                                    onClick = { CommonMethods().openLink(context, displayValue) }
                                )
                            } else if (newTitle.equals("kfs Link", ignoreCase = true)) {
                                OnlyClickAbleText(
                                    textHeader = newTitle,
                                    textValue = displayValue,
                                    style = normal14Text400,
                                    bottom = 5.dp,
                                    start = 6.dp,
                                    onClick = { CommonMethods().openLink(context, displayValue) }
                                )
                            } else if ((
                                newTitle.equals("term", ignoreCase = true) ||
                                    newTitle.contains("frequency", ignoreCase = true)
                                ) &&
                                displayValue?.startsWith("P") == true
                            ) {
                                convertISODurationToReadable(displayValue ?: "").let {
                                        readableDuration ->
                                    OnlyReadAbleText(
                                        textHeader = newTitle,
                                        textValue = readableDuration ?: "",
                                        style = normal14Text400,
                                        textColorHeader = slateGrayColor,
                                        end = 5.dp,
                                        start = 5.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                }
                            } else {
                                OnlyReadAbleText(
                                    textHeader = newTitle,
                                    textValue = displayValue ?: "",
                                    style = normal14Text400,
                                    textColorHeader = slateGrayColor,
                                    end = 5.dp,
                                    start = 5.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun convertISODurationToReadable(durationStr: String): String {
    return try {
        if (durationStr.startsWith("P") && !durationStr.contains("T")) {
            val period = Period.parse(durationStr)
            buildString {
                if (period.years > 0) append("${period.years} year${if (period.years > 1) "s" else ""} ")
                if (period.months > 0) append("${period.months} month${if (period.months > 1) "s" else ""} ")
                if (period.days > 0) append("${period.days} day${if (period.days > 1) "s" else ""}")
            }.trim()
        } else if (durationStr.contains("T")) {
            val duration = Duration.parse(durationStr)
            val hours = duration.toHours()
            val minutes = (duration.toMinutes() % 60)
            buildString {
                if (hours > 0) append("$hours hour${if (hours > 1) "s" else ""} ")
                if (minutes > 0) append("$minutes minute${if (minutes > 1) "s" else ""}")
            }.trim()
        } else {
            durationStr
        }
    } catch (e: DateTimeParseException) {
        Log.d("LoanOffersListDetail", "Error parsing duration: ${e.message}")
        durationStr
    }
}

@Composable
fun LoanSummary(offer: OfferResponseItem) {
    var showLoanSummaryCard by remember { mutableStateOf(false) }
    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.loan_summary),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showLoanSummaryCard) R.drawable.arrow_up else R.drawable.arrow_forward
    ) { showLoanSummaryCard = !showLoanSummaryCard }
    if (showLoanSummaryCard) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            offer.quoteBreakUp?.forEach { quoteBreakUp ->
                quoteBreakUp?.let {
                    it.title?.let { title ->
                        val newTitle = CommonMethods().displayFormattedText(title)
                        it.value?.let { description ->
                            OnlyReadAbleText(
                                textHeader = newTitle,
//                                textValue = CommonMethods().formatIndianDoubleCurrency(description.takeIf{it.isNotEmpty()}?.toDoubleOrNull() ?: 0.0),
                                textValue = description,
                                style = normal14Text400,
                                textColorHeader = slateGrayColor,
                                end = 5.dp, start = 5.dp, top = 8.dp, bottom = 5.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactDetailsCard(lspTags: OfferResponseItem) {
    var showContactDetailsCard by remember { mutableStateOf(false) }
    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.lending_service_provider_details),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showContactDetailsCard) R.drawable.arrow_up else R.drawable.arrow_forward
    ) { showContactDetailsCard = !showContactDetailsCard }
    if (showContactDetailsCard) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            val lspInfo = lspTags.providerTags?.firstOrNull { it?.name == "Lsp Info" }?.tags
            lspInfo?.forEach { (key, value) ->
                val newTitle = CommonMethods().displayFormattedText(key)
                OnlyReadAbleText(
                    textHeader = newTitle,
                    textValue = value,
                    textColorHeader = slateGrayColor,
                    style = normal14Text400,
                    end = 5.dp,
                    start = 5.dp,
                    top = 8.dp,
                    bottom = 5.dp
                )
            }
        }
    }
}

@Composable
fun EmiDetail(loanDetails: OfferResponseItem) {
    var showEmiDetailsCard by remember { mutableStateOf(false) }

    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.emi_schedule),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showEmiDetailsCard) R.drawable.arrow_up else R.drawable.arrow_forward
    ) {
        showEmiDetailsCard = !showEmiDetailsCard
    }

    if (showEmiDetailsCard) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                TableRow(
                    emiNumber = "EMI No.",
                    dueDate = "EMI Due Date",
                    amount = "EMI Amount",
                    status = "Status",
                    isTitleRow = true
                )

                loanDetails.payments?.forEach { payment ->
                    if (payment?.type == "POST_FULFILLMENT") {
                        payment?.let { emiItem ->
                            emiItem.id?.let { emiNum ->
                                emiItem.status?.let { status ->
                                    emiItem.params?.amount?.let { amount ->
                                        emiItem.time?.range?.start?.let { timestamp ->
                                            val date = CommonMethods().displayFormattedDate(timestamp)
                                            TableRow(
                                                emiNumber = emiNum,
                                                dueDate = date,
                                                amount = "₹$amount",
                                                status = status
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
fun TableRow(
    emiNumber: String,
    dueDate: String,
    amount: String,
    status: String,
    isTitleRow: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, bottom = 8.dp)
    ) {
        Text(
            text = emiNumber,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Center
        )
        Text(
            text = dueDate,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = amount,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = status,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PaymentHistoryCard(payment: List<OrderPaymentStatusItem?>?) {
    var showPaymentHistoryCard by remember { mutableStateOf(false) }
    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.payment_history),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showPaymentHistoryCard) R.drawable.arrow_up else R.drawable.arrow_forward
    ) { showPaymentHistoryCard = !showPaymentHistoryCard }
    if (showPaymentHistoryCard) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            payment?.forEach { payment ->
                val paymentDate =
                    payment?.updatedAt?.let {
                        CommonMethods().displayFormattedDate(it)
                    }
                payment?.params?.amount?.let { amount ->
                    val newTextHeader =
                        CommonMethods().displayFormattedText(payment.time?.label ?: "Down Payment")

                    HeaderValueWithTextBelow(
                        textHeader = newTextHeader,
                        start = 10.dp, end = 20.dp, top = 8.dp, bottom = 5.dp,
                        textColorHeader = slateGrayColor,
                        textValue = "₹$amount",
//                        textBelowValue = "$paymentDate  PAID",
                        textBelowValue = "PAID",
                        textBelowStyle = normal12Text400,
                        textColorBelowValue = appGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun GRODetailsCard(groTags: OfferResponseItem, context: Context) {
    var showGroDetails by remember { mutableStateOf(false) }
    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.gro_details),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showGroDetails) R.drawable.arrow_up else R.drawable.arrow_forward
    ) { showGroDetails = !showGroDetails }
    if (showGroDetails) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            val groInfo = groTags.providerTags?.firstOrNull { it?.name == "Contact Info" }?.tags
            groInfo?.forEach { (key, value) ->
                val newTitle = CommonMethods().displayFormattedText(key)

                if (newTitle.equals("Customer support link", ignoreCase = true)) {
                    OnlyClickAbleText(
                        textHeader = newTitle,
                        textValue = value,
                        style = normal14Text400,
                        end = 5.dp, start = 5.dp, top = 8.dp, bottom = 5.dp,
                        onClick = { CommonMethods().openLink(context, value) }
                    )
                } else {
                    OnlyReadAbleText(
                        textHeader = newTitle,
                        textValue = value,
                        textColorHeader = slateGrayColor,
                        style = normal14Text400,
                        end = 5.dp,
                        start = 5.dp,
                        top = 8.dp,
                        bottom = 5.dp
                    )
                }
            }
        }
    }
}

@Composable
fun LoanAgreementDetailsCard(loanDocument: OfferResponseItem, context: Context) {
    val lenderName = loanDocument.providerDescriptor?.name.orEmpty()
    loanDocument.documents
        ?.find { it?.descriptor?.name?.equals("Loan Agreement Document", ignoreCase = true) == true }
        ?.url?.let { loanPdfUrl ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth().padding( top = 5.dp)
                    .background(color = Color.Transparent)
            ) {
                Text(
                    text = stringResource(id = R.string.download_loan_agreement),
                    style = normal16Text700,
                    color = appBlack,
                    modifier = Modifier.padding(start = 10.dp)
                )
                CurvedPrimaryButton(
                    text = stringResource(id = R.string.download),
                    style = normal14Text700,
                    start = 15.dp,
                    end = 15.dp,top=5.dp, bottom = 5.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    val fileName = loanPdfUrl.substringAfterLast("/").substringBefore("?").ifEmpty { "loan_agreement" }
                    CommonMethods().downloadPdf(
                        context,
                        loanPdfUrl,
                        fileName,
                        "$lenderName-LoanAgreement"
                    )
                }
            }
        }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DownloadLoanDetailsCard(loanDocument: OfferResponseItem, payment: List<OrderPaymentStatusItem>, context: Context) {
    val activity = context as Activity
    val lenderName = loanDocument.providerDescriptor?.name.orEmpty()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth().fillMaxWidth().padding(top = 5.dp, bottom = 5.dp)
            .background(color = Color.Transparent)
    ) {
        Text(
            text = stringResource(id = R.string.download_loan_details),
            style = normal16Text700,
            color = appBlack,
            modifier = Modifier.padding(start = 10.dp)
        )
        CurvedPrimaryButton(
            text = stringResource(id = R.string.download),
            style = normal14Text700,
            start = 15.dp,
            end = 15.dp,
            top = 5.dp,
            bottom = 5.dp,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                    )
                    return@CurvedPrimaryButton // Wait for the permission result before proceeding
                }
            }
            CommonMethods().generatePdfAndNotify(context, loanDocument, payment, lenderName)
        }
    }
}

@Composable
fun CancellationTermsCard(loanDocument: OfferResponseItem, context: Context) {
    var showCancellationTermsCard by remember { mutableStateOf(false) }
    SpaceBetweenTextIcon(
        text = stringResource(id = R.string.cancellation_terms),
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (showCancellationTermsCard) R.drawable.arrow_up else R.drawable.arrow_forward
    ) { showCancellationTermsCard = !showCancellationTermsCard }
    if (showCancellationTermsCard) {
        DisplayCard(
            cardColor = appWhite,
            borderColor = appWhite,
            roundedCornerDp = 6.dp,
            start = 10.dp,
            end = 10.dp
        ) {
            loanDocument.cancellationTerms?.firstOrNull()?.let { cancellationTerms ->
                cancellationTerms.cancellationFeePercentage?.let { cancellationFeePercentage ->
                    OnlyReadAbleText(
                        textHeader = stringResource(id = R.string.cancellation_fee),
                        textValue = cancellationFeePercentage,
                        textColorHeader = slateGrayColor,
                        style = normal14Text400,
                        end = 5.dp,
                        start = 5.dp,
                        top = 8.dp,
                        bottom = 5.dp
                    )
                }
                cancellationTerms.externalRefUrl?.let { privacy ->
                    OnlyClickAbleText(
                        textHeader = stringResource(id = R.string.privacy),
                        textValue = privacy,
                        style = normal14Text400,
                        end = 5.dp, start = 5.dp, top = 8.dp, bottom = 10.dp,
                        onClick = { CommonMethods().openLink(context, privacy) }
                    )
                }
            }
        }
    }
}

@Composable
fun ReportIssueCard(
    checkOrderIssueResponse: CheckOrderIssueModel?,
    loanDetails: OfferResponseItem,
    navController: NavHostController,
    fromFlow: String
) {
    var isReportIssueClicked by remember { mutableStateOf(false) }
    val summary = checkOrderIssueResponse?.data?.data?.firstOrNull()?.summary

    val headerText = if (summary == null) {
        stringResource(id = R.string.raise_issue)
    } else {
        stringResource(id = R.string.view_issue)
    }
    SpaceBetweenTextIcon(
        text = headerText,
        imageSize = 15.dp,
        textStart = 10.dp,
        bottom = 0.dp,
        image = if (isReportIssueClicked) R.drawable.arrow_down else R.drawable.arrow_forward
    ) { isReportIssueClicked = !isReportIssueClicked }
    if (isReportIssueClicked) {
//        loanDetails.fulfillments?.forEach { fulfilment ->
//            fulfilment?.state?.let { state ->
        loanDetails.fulfillments?.firstOrNull()?.state?.let { state ->
            state.descriptor?.code?.let { loanState ->
                loanDetails.id?.let { orderId ->
                    loanDetails.providerId?.let { providerId ->
                        if (summary == null) {
                            navigateToCreateIssueScreen(
                                navController = navController,
                                orderId = orderId,
                                providerId = providerId,
                                orderState = loanState,
                                fromFlow = fromFlow
                            )
                        } else {
                            navigateToIssueListScreen(
                                navController = navController,
                                orderId = orderId,
                                fromFlow = fromFlow,
                                providerId = providerId,
                                loanState = loanState,
                                fromScreen = "Loan Detail"
                            )
                        }
                    }
                }
            }
//            }
        }
    }
}

@Composable
fun PaymentOptionsPopUp(
    fromFlow: String,
    context: Context,
    showMissedEmi: Boolean,
    onDismiss: () -> Unit,
    onOptionSelected: (String) -> Unit,
    loanDetails: OfferResponseItem,
    navController: NavHostController,
    orderId: String,
    loanType: String
) {
    var selectedOption by remember { mutableStateOf("") }
    var prePartPaymentAmount by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String>("") }

    var showConfirmationPopUp by remember { mutableStateOf(false) }
    var showPaymentOptionPopup by remember { mutableStateOf(true) }

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val updateProcessed by loanAgreementViewModel.updateProcessed.collectAsState()
    val updateProcessing by loanAgreementViewModel.updateProcessing.collectAsState()
    val updatedLoanAgreement by loanAgreementViewModel.updatedLoanAgreement.collectAsState()
    val lenderName = loanDetails.providerDescriptor?.name.orEmpty().lowercase()

    if (updateProcessed) {
        PrePartPaymentResponseHandle(
            updatedLoanAgreement = updatedLoanAgreement,
            selectedOption = selectedOption,
            navController = navController,
            prePartPaymentAmount = prePartPaymentAmount,
            fromFlow = fromFlow,
            orderId = orderId
        )
    }

    if (showPopup) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            if (showPaymentOptionPopup) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StartingText(
                        text = stringResource(id = R.string.payment_option),
                        textColor = appBlack,
                        style = semiBold20Text500,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(0.8f)
                    )
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = appOrange
                        )
                    }
                }

                HorizontalDivider(start = 0.dp, end = 0.dp, bottom = 8.dp, top = 0.dp, color = appOrange)

//                TextDescriptionWithRadioButton(
//                    text = stringResource(id = R.string.pay_emi).uppercase(),
//                    description = stringResource(id = R.string.buyer_can_make_a_scheduled_installment_payment),
//                    selectedOption = selectedOption,
//                    optionValue = "PAY_EMI",
//                    onSelectOption = {
//                        selectedOption = "PAY_EMI"
//                        onOptionSelected(selectedOption)
//                    }
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
                // Radio Button Options
                if (showMissedEmi) {
                    TextDescriptionWithRadioButton(
                        text = stringResource(id = R.string.missed_emi_payment).uppercase(),
                        description = stringResource(id = R.string.when_buyer_missed_to_make_a_scheduled),
                        selectedOption = selectedOption,
                        optionValue = "MISSED_EMI_PAYMENT",
                        onSelectOption = {
                            selectedOption = "MISSED_EMI_PAYMENT"
                            onOptionSelected(selectedOption)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
                TextDescriptionWithRadioButton(
                    text = stringResource(id = R.string.foreclosure).uppercase(),
                    description = stringResource(id = R.string.when_buyer_initiates_the_foreclosure_intent),
                    selectedOption = selectedOption,
                    optionValue = "FORECLOSURE",
                    onSelectOption = {
                        selectedOption = "FORECLOSURE"
                        onOptionSelected(selectedOption)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextDescriptionWithRadioButton(
                    text = stringResource(id = R.string.pre_part_payment).uppercase(),
                    description = stringResource(id = R.string.when_buyer_initiates_the_pre_part_intent),
                    selectedOption = selectedOption,
                    optionValue = "PRE_PART_PAYMENT",
                    onSelectOption = {
                        selectedOption = "PRE_PART_PAYMENT"
                        onOptionSelected(selectedOption)
                    }
                )

                // Conditional Text Field for Pre-Part Payment Amount
                if (selectedOption == "PRE_PART_PAYMENT") {
                    if (lenderName == "p2pl") {
                        CommonMethods().toastMessage(
                            context = context,
                            toastMsg = context.getString(R.string.feature_supported_in_future)
                        )
                    } else {
                        OutlinedTextField(
                            value = prePartPaymentAmount,
                            onValueChange = { input ->
                                val filteredInput = input.filterIndexed { index, c ->
                                    c.isDigit() || (c == '.' && input.indexOf('.') == index)
                                }
                                val parts = filteredInput.split(".")
                                val limitedInput = if (parts.size == 2) {
                                    "${parts[0]}.${parts[1].take(2)}"
                                } else {
                                    filteredInput
                                }
                                // Convert to number for validation
                                val inputAmount = limitedInput.toDoubleOrNull() ?: 0.0
                                val maxAmount = loanDetails.itemPrice?.value?.toDoubleOrNull()
                                    ?: Double.MAX_VALUE

                                // Update the value
                                prePartPaymentAmount = limitedInput

                                // Validation
                                showError = inputAmount <= 0 || inputAmount > maxAmount
                                if (showError) errorMsg = "Enter Valid Amount"
                            },
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.enter_amount),
                                    color = hintGray,
                                    style = normal18Text400,
                                    textAlign = TextAlign.Center
                                )
                            },
                            visualTransformation = VisualTransformation.None,
                            textStyle = LocalTextStyle.current,
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = appOrange,
                                unfocusedBorderColor = grayD6,
                                cursorColor = appOrange,
                                textColor = appBlack
                            )
                        )
                    }
                }
                if (showError) {
                    Text(
                        text = errorMsg,
                        style = normal12Text400,
                        color = errorRed,
                        modifier = Modifier.padding(start = 16.dp, top = 15.dp)
                    )
                }
                CurvedPrimaryButton(
                    text = stringResource(id = R.string.proceed),
                    style = bold16Text400,
                    modifier = Modifier.padding(top = 15.dp)
                ) {
                    if (selectedOption == "PRE_PART_PAYMENT") {
                        if (prePartPaymentAmount.isEmpty()) {
                            showError = true
                            errorMsg = "Please Enter the Amount to make Pre Part Payment"
                        } else {
                            if (!showError) {
                                onOptionSelected(selectedOption)
                                showConfirmationPopUp = true
                            }
                        }
                    } else if (selectedOption == "") {
                        showError = true
                        errorMsg = "Please select an option to proceed further"
                    }
//                    else{
//                        Log.d("res_H",selectedOption)
//                        onOptionSelected(selectedOption)
//                        showConfirmationPopUp = true
//                    }
                }
            }
        }

        if (showConfirmationPopUp) {
            showPaymentOptionPopup = false
            val msg = "Are you sure you want to pay"
            RepaymentBottomCommon(
                headerText = stringResource(id = R.string.pre_part_payment),
                updateProcessing = updateProcessing,
                text = msg,
                amount = "₹$prePartPaymentAmount",
                showTextDescriptor = false,
                onYesClick = {
                    loanDetails.id?.let { id ->
                        loanAgreementViewModel.updateLoanAgreementApi(
                            context,
                            UpdateLoanBody(
                                subType = "PRE_PART_PAYMENT",
                                id = id,
                                amount = prePartPaymentAmount,
                                loanType = loanType
                            )
                        )
                    }
                },
                onNoClick = {
                    showConfirmationPopUp = false
                    onOptionSelected(selectedOption)
                    showPaymentOptionPopup = true
                    showPopup = true
                }
            )
        }
    }
}

@Composable
fun convertUTCToLocalDateTime(utcDateTime: String): String {
    val zonedDateTime =
        ZonedDateTime.parse(utcDateTime).withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a, dd MMM yyyy", Locale.getDefault())

    val remainingTime =
        CommonMethods().getRemainingTime(utcDateTime) ?: CommonMethods.RemainingTime(
            isFuture = false,
            days = 0,
            hours = 0,
            minutes = 0,
            seconds = 0
        )
    val remainingTimeStr = CommonMethods().timeBufferString(remainingTime)

    val dateTimeStr = zonedDateTime.format(formatter)
    // Sugu2
//    val formatedStr = "$remainingTimeStr\n($dateTimeStr)"
    val formatedStr = "Till - $dateTimeStr"

    return formatedStr
}

@Composable
fun RepaymentBottomCommon(
    headerText: String = stringResource(id = R.string.foreclosure),
    text: String = stringResource(id = R.string.are_u_sure_want_to_pay),
    amount: String = "",
    textDescriptor: String = stringResource(id = R.string.may_include_late_charges),
    showTextDescriptor: Boolean = true,
    updateProcessing: Boolean = false,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StartingText(
                text = headerText,
                style = bold20Text100,
                alignment = Alignment.TopCenter
            )
            RegisterText(
                text = text,
                textColor = hintGray,
                top = 10.dp,
                style = normal14Text500
//                boxAlign = Alignment.TopCenter
            )

            MultiStyleText(
                amount, appBlack, " ?", hintGray, normal16Text700,
                normal16Text500, start = 0.dp, top = 5.dp,
                arrangement = Arrangement.Center
            )

            if (showTextDescriptor) {
                StartingText(
                    text = textDescriptor,
                    textColor = hintGray,
                    top = 10.dp,
                    style = normal14Text500,
                    alignment = Alignment.TopCenter
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 25.dp, horizontal = 90.dp)
                    .fillMaxWidth()
            ) {
                if (!updateProcessing) {
                    CurvedPrimaryButton(
                        text = stringResource(id = R.string.no),
                        textColor = appOrange,
                        backgroundColor = appWhite,
                        style = normal14Text500,
                        start = 30.dp,
                        end = 30.dp
                    ) { onNoClick() }
                    CurvedPrimaryButton(
                        text = stringResource(id = R.string.yes),
                        style = normal14Text500,
                        start = 30.dp,
                        end = 30.dp
                    ) { onYesClick() }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = appOrange,
                            modifier = Modifier.padding(all = 8.dp).size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PayEmi(
    updateProcessing: Boolean,
    navController: NavHostController,
    updateProcessed: Boolean,
    loanDetails: OfferResponseItem,
    loanAgreementViewModel: LoanAgreementViewModel,
    scope: CoroutineScope,
    bottomSheetStateValue: ModalBottomSheetState,
    fromFlow: String,
    updatedLoanAgreement: UpdateLoanAgreement?,
    context: Context,
    orderId: String,
    loanType: String
) {
    val firstUnpaidEmi = loanDetails.payments
        ?.firstOrNull { it?.type == "POST_FULFILLMENT" && it.status == "NOT-PAID" }

    val amount = firstUnpaidEmi?.params?.amount
    val msg = "Are you sure you want to pay"
    if (updateProcessed) {
        PayEMIResponseHandling(
            emiAmount = amount,
            navController = navController,
            fromFlow = fromFlow,
            updatedLoanAgreement = updatedLoanAgreement,
            orderId = orderId
        )
    }
    RepaymentBottomCommon(
        headerText = stringResource(id = R.string.pay_emi),
        updateProcessing = updateProcessing,
        text = msg,
        amount = "₹ $amount",
        showTextDescriptor = false,
        onYesClick = {
            loanDetails.id?.let { id ->
                loanAgreementViewModel.updateLoanAgreementApi(
                    context,
                    UpdateLoanBody(
                        subType = "PAY_EMI",
                        id = id,
                        amount = "",
                        loanType = loanType
                    )
                )
            }
        },
        onNoClick = {
            scope.launch {
                bottomSheetStateValue.hide()
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MissedEmi(
    updateProcessing: Boolean,
    navController: NavHostController,
    updateProcessed: Boolean,
    loanDetails: OfferResponseItem,
    loanAgreementViewModel: LoanAgreementViewModel,
    scope: CoroutineScope,
    bottomSheetStateValue: ModalBottomSheetState,
    fromFlow: String,
    updatedLoanAgreement: UpdateLoanAgreement?,
    context: Context,
    orderId: String,
    loanType: String
) {
    if (updateProcessed) {
        MissedEmiPaymentResponseHandling(
            updatedLoanAgreement = updatedLoanAgreement,
            loanDetails = loanDetails,
            navController = navController,
            fromFlow = fromFlow,
            orderId = orderId
        )
    }
    var delayFound = false
    loanDetails.payments?.forEach { payment ->
        if (payment?.type == "POST_FULFILLMENT" && payment.status == "DELAYED") {
            amount_to_be_paid = payment.params?.amount ?: ""
            delayFound = true
            val missingPayment = "Are you sure you want to pay"
//                    "\n *this amount may include late fee charges"
            RepaymentBottomCommon(
                updateProcessing = updateProcessing,
                headerText = stringResource(id = R.string.missed_emi_payment),
                text = missingPayment,
                amount = "₹${payment.params?.amount}",
                onYesClick = {
                    loanDetails.id?.let { id ->
                        loanAgreementViewModel.updateLoanAgreementApi(
                            context,
                            UpdateLoanBody(
                                subType = "MISSED_EMI_PAYMENT",
                                id = id,
                                amount = "",
                                loanType = loanType
                            )
                        )
                    }
                },
                onNoClick = { scope.launch { bottomSheetStateValue.hide() } }
            )
        }
    }
    if (!delayFound) {
        CommonMethods().toastMessage(context, "There is No Missed Emi")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForeClosure(
    loanDetails: OfferResponseItem,
    updatedLoanAgreement: UpdateLoanAgreement?,
    fromFlow: String,
    updateProcessed: Boolean,
    updateProcessing: Boolean,
    context: Context,
    orderId: String,
    navController: NavHostController,
    loanAgreementViewModel: LoanAgreementViewModel,
    scope: CoroutineScope,
    bottomSheetStateValue: ModalBottomSheetState,
    loanType: String
) {
    val fullAmount = loanDetails.itemPrice?.value
//    if (updateProcessing) {
//        CenterProgressFixedHeight()
//    }

    if (updateProcessed) {
        ForeClosureResponseHandling(
            fullAmount = fullAmount,
            navController = navController,
            fromFlow = fromFlow,
            updatedLoanAgreement = updatedLoanAgreement,
            orderId = orderId
        )
    }

    // Sugu
//    fullAmount?.let { amount ->
    principal?.let { amount ->
        amount_to_be_paid = amount
        val remainingTime = CommonMethods().getRemainingTime(coolOffPeriodDate)
        val timeBuffer = remainingTime?.let { CommonMethods().timeBufferString(it) }
        if (remainingTime?.isFuture == true) {
            RepaymentBottomCommon(
                text = "Are you sure you want to pay",
                showTextDescriptor = false,
                updateProcessing = updateProcessing,
                amount = amount_to_be_paid,
                onYesClick = {
                    loanDetails.id?.let { id ->
                        loanAgreementViewModel.updateLoanAgreementApi(
                            context = context,
                            updateLoanBody = UpdateLoanBody(
                                subType = "FORECLOSURE",
                                id = id,
                                amount = "",
                                loanType = loanType
                            )
                        )
                    }
                },
                onNoClick = { scope.launch { bottomSheetStateValue.hide() } }
            )
        } else {
            RepaymentBottomCommon(
                text = "Are you sure you want to pay",
                updateProcessing = updateProcessing,
                amount = amount_to_be_paid,
                onYesClick = {
                    loanDetails.id?.let { id ->
                        loanAgreementViewModel.updateLoanAgreementApi(
                            context,
                            UpdateLoanBody(
                                subType = "FORECLOSURE",
                                id = id,
                                amount = "",
                                loanType = loanType
                            )
                        )
                    }
                },
                onNoClick = { scope.launch { bottomSheetStateValue.hide() } }
            )
        }
    }
}

@Composable
fun MissedEmiPaymentResponseHandling(
    updatedLoanAgreement: UpdateLoanAgreement?,
    loanDetails: OfferResponseItem,
    navController: NavHostController,
    fromFlow: String,
    orderId: String
) {
    loanDetails.payments?.forEach {
        if (it?.type == "POST_FULFILLMENT" && it.status == "DELAYED") {
            val paymentStatusText =
                "Missed EMI of ₹$amount_to_be_paid successfully processed"
            updatedLoanAgreement?.data?.let { data ->
                data.updatedObject?.let { updatedLoan ->
                    updatedLoan.payments?.get(0)?.let { payment ->
                        payment.url?.let { paymentUrl ->
                            navigateToPrePaymentWebViewScreen(
                                navController = navController,
                                orderId = orderId,
                                headerText = paymentUrl,
                                status = paymentStatusText,
                                fromFlow = fromFlow,
                                paymentOption = "Missed EMI"
                            )
                        }
                    }
                }
            }
            return@forEach
        }
    }
}

@Composable
fun PayEMIResponseHandling(
    emiAmount: String?,
    navController: NavHostController,
    updatedLoanAgreement: UpdateLoanAgreement?,
    fromFlow: String,
    orderId: String
) {
    emiAmount?.let {
        val paymentStatusText = "EMI of ₹$amount_to_be_paid successfully processed"
        updatedLoanAgreement?.data?.let {
            it.updatedObject?.let { updatedLoan ->
                updatedLoan.payments?.get(0)?.let { payment ->
                    payment.url?.let { paymentUrl ->
                        navigateToPrePaymentWebViewScreen(
                            navController = navController,
                            orderId = orderId,
                            fromFlow = fromFlow,
                            headerText = paymentUrl,
                            status = paymentStatusText,
                            paymentOption = "Pay EMI"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ForeClosureResponseHandling(
    fullAmount: String?,
    navController: NavHostController,
    updatedLoanAgreement: UpdateLoanAgreement?,
    fromFlow: String,
    orderId: String
) {
    fullAmount?.let {
        val paymentStatusText = "ForeClosure of ₹$amount_to_be_paid successfully processed"
        updatedLoanAgreement?.data?.let {
            it.updatedObject?.let { updatedLoan ->
                updatedLoan.payments?.get(0)?.let { payment ->
                    payment.url?.let { paymentUrl ->
                        navigateToPrePaymentWebViewScreen(
                            navController = navController,
                            orderId = orderId,
                            fromFlow = fromFlow,
                            headerText = paymentUrl,
                            status = paymentStatusText,
                            paymentOption = "ForeClosure"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrePartPaymentResponseHandle(
    updatedLoanAgreement: UpdateLoanAgreement?,
    selectedOption: String,
    fromFlow: String,
    navController: NavHostController,
    prePartPaymentAmount: String,
    orderId: String
) {
    var paymentStatusText = ""
    if (selectedOption == "PRE_PART_PAYMENT") {
        paymentStatusText = "Repayment of ₹$prePartPaymentAmount "
        if (!prepartPaymentCharges.isNullOrEmpty()) {
            paymentStatusText += " with prepayment charge of ₹$prepartPaymentCharges"
        } else {
            Log.d("PrePaymentCharges", "prepartPaymentCharges is not found or empty")
        }
    } else if (selectedOption == "FORECLOSURE" || selectedOption == "MISSED_EMI_PAYMENT") {
        paymentStatusText = "Repayment of $amount_to_be_paid "
    }
    updatedLoanAgreement?.data?.let agreement@{
        it.updatedObject?.let { updatedLoan ->
            val matchingPayment = updatedLoan.payments
                ?.firstOrNull { payment ->
                    payment?.time?.label.equals(selectedOption, ignoreCase = true) &&
                        !payment?.url.isNullOrEmpty()
                }

            matchingPayment?.url?.let { paymentUrl ->
                navigateToPrePaymentWebViewScreen(
                    navController = navController,
                    orderId = orderId,
                    headerText = paymentUrl,
                    status = paymentStatusText,
                    fromFlow = fromFlow,
                    paymentOption = CommonMethods().displayFormattedText(selectedOption)
                )
                return@agreement
            }
        }
    }
}
