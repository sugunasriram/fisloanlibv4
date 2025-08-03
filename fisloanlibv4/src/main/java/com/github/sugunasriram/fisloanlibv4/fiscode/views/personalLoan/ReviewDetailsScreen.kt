package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold13Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.Locale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.shape.CircleShape

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReviewDetailsScreen(navController: NavHostController, loanPurpose: String, fromFlow: String) {
    val registerViewModel: RegisterViewModel = viewModel()
    val checkboxState: Boolean by registerViewModel.checkBoxDetail.observeAsState(false)
    val showInternetScreen by registerViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by registerViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by registerViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by registerViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by registerViewModel.unAuthorizedUser.observeAsState(false)

    val inProgress by registerViewModel.inProgress.collectAsState()
    val isCompleted by registerViewModel.isCompleted.collectAsState()
    val userDetails by registerViewModel.getUserResponse.collectAsState()
    val showDialog : MutableState<Boolean> =  remember {  mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val annualIncomeBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    BackHandler {
        goBack(navController = navController, fromFlow = fromFlow, loanPurpose = loanPurpose)
    }
    if (!showInternetScreen && !showTimeOutScreen && !showServerIssueScreen && !unexpectedErrorScreen && !unAuthorizedUser) {
        ReviewDetailView(
            bottomSheetState = bottomSheetState,
            annualIncomeBottomSheetState = annualIncomeBottomSheetState,
            navController = navController,
            context = context,
            coroutineScope = coroutineScope,
            inProgress = inProgress,
            isCompleted = isCompleted,
            userDetails = userDetails,
            checkboxState = checkboxState,
            loanPurpose = loanPurpose,
            registerViewModel = registerViewModel,
            fromFlow = fromFlow,
            showDialog = showDialog
        )
    } else {
        CommonMethods().HandleErrorScreens(
            navController = navController,
            showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen,
            showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen,
            unAuthorizedUser = unAuthorizedUser
        )
    }

    if(showDialog.value){
        ConsentDialogBox(showDialog)
    }
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReviewDetailView(
    bottomSheetState: ModalBottomSheetState,
    annualIncomeBottomSheetState: ModalBottomSheetState,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    context: Context,
    inProgress: Boolean,
    isCompleted: Boolean,
    userDetails: ProfileResponse?,
    checkboxState: Boolean,
    loanPurpose: String,
    fromFlow: String,
    registerViewModel: RegisterViewModel,
    showDialog : MutableState<Boolean>
) {
    var showError by remember { mutableStateOf(false) }
    val webViewModel: WebViewModel = viewModel()
    val webScreenLoading by webViewModel.webProgress.collectAsState()
    val webScreenLoaded = webViewModel.webViewLoaded.collectAsState()

    val webInProgress by webViewModel.webInProgress.collectAsState()

    val showInternetScreen by webViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by webViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by webViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by webViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by webViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val errorMessage by webViewModel.errorMessage.collectAsState()
    val navigationToSignIn by webViewModel.navigationToSignIn.collectAsState()
    var showNoLendersAvailableScreen by remember { mutableStateOf(false) }

    if (showNoLendersAvailableScreen) {
        NoLoanOffersAvailableScreen(navController, titleText = stringResource(R.string.no_lenders_available))
        return
    }
    when {
    navigationToSignIn -> navigateSignInPage(navController)
    showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
    showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
    showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
    unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
    unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
        else->{
            if (inProgress) {
                ProcessingAnimation(text = "Processing Please Wait...", image = R.raw.we_are_currently_processing_hour_glass)
            } else if(webInProgress){
                CenterProgress()
            }
            else{
                if (!isCompleted) {
                    registerViewModel.getUserDetail(context, navController)
                }
                else {
                    var updatedIncome by remember { mutableStateOf(userDetails?.data?.income) }
                    CompanyConsentExplain()

                    CustomModalBottomSheet(
                        bottomSheetState = bottomSheetState,
                        sheetBackgroundColor = Color.Transparent,
                        sheetContent = {
                            CompanyConsentContent(
                                bottomSheetState = bottomSheetState,
                                coroutineScope = coroutineScope,
                                registerViewModel = registerViewModel,
                                onAcceptConsent = { showError = false },
                                startTimer = bottomSheetState.isVisible,
                                showDialog = showDialog
                            )
                        }
                    ) {
                            FixedTopBottomScreen(
                                navController = navController,
                                topBarBackgroundColor = appOrange,
                                topBarText = stringResource(R.string.basic_detail),
                                showBackButton = true,
                                onBackClick = { goBack(navController = navController, fromFlow = fromFlow, loanPurpose = loanPurpose) },
                                showBottom = true,
                                showCheckBox = true,
                                checkboxState = checkboxState,
                                onCheckBoxChange = { isChecked ->
                                    if (isChecked) {
                                        coroutineScope.launch { bottomSheetState.show() }
                                    } else {
                                        registerViewModel.onCheckBoxDetailChanged(false)
                                        showError = !checkboxState
                                    }
                                },
                                checkBoxText = stringResource(R.string.credit_info_company_consent),
                                showErrorMsg = showError,
                                errorMsg = stringResource(R.string.please_accept_credit_info_company_consent),
                                showSingleButton = true,
                                primaryButtonText = stringResource(R.string.next),
                                onPrimaryButtonClick = {
                                    if (!checkboxState ) {
                                        showError = true
                                    } else if(!webScreenLoading) {
                                        showError = false
                                        onReviewClick(
                                            navController = navController,
                                            loanPurpose = loanPurpose,
                                            context = context,
                                            fromFlow = fromFlow,
                                            webViewModel=webViewModel,
                                            onNoLenders = { showNoLendersAvailableScreen = true }
                                        )
                                    }
                                },
                                backgroundColor = appWhite
                            ) {
                                updatedIncome?.let {
                                    PersonalDetailView(
                                        userDetails = userDetails,
                                        loanPurpose = loanPurpose,
                                        annualIncome = it,
                                        annualIncomeBottomSheetState = annualIncomeBottomSheetState,
                                        coroutineScope = coroutineScope
                                    )
                                }
                            }
                    }
                }
            }
        }
}


}

fun goBack(navController: NavHostController, fromFlow: String, loanPurpose: String) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        navigateToBasicDetailsScreen(navController, fromFlow, loanPurpose = loanPurpose)
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        navigateToLoanProcessScreen(
            navController = navController,
            transactionId = "Sugu",
            statusId = 17,
            responseItem = "No Need",
            offerId = "1234",
            fromFlow = "Purchase Finance"
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalDetailView(
    userDetails: ProfileResponse?,
    loanPurpose: String,
    annualIncome: String,
    annualIncomeBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    userDetails?.let { response ->
        StartingText(
            text = stringResource(id = R.string.review_details),
            start = 20.dp,
            end = 30.dp,
            bottom = 10.dp,
            top = 20.dp,
            style = normal16Text500,
            textAlign = TextAlign.Start
        )
        StartingText(
            text = stringResource(id = R.string.personal_detail),
            start = 20.dp,
            end = 30.dp,
            bottom = 5.dp,
            style = normal14Text700,
            textAlign = TextAlign.Start
        )
        response.data?.let { profile ->
            PersonalDetailsCard(
                profile,
                loanPurpose,
                annualIncome,
                annualIncomeBottomSheetState,
                coroutineScope
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalDetailsCard(
    profile: Profile,
    loanPurpose: String,
    annualIncome: String,
    annualIncomeBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    DisplayCard(cardColor = backgroundOrange) {
        profile.firstName?.let { firstName ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.first_name),
                textValue = firstName,
                top = 10.dp,
                bottom = 10.dp,
                showImage = true
            )
        }
        profile.lastName?.let { lastName ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.last_name),
                textValue = lastName,
                bottom = 5.dp,
                showImage = true
            )
        }
        profile.email?.let { email ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.personal_email),
                textValue = email,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.email_icon)
            )
        }
        profile.officialEmail?.let { officialEmail ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.official_email),
                textValue = officialEmail,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.email_icon)
            )
        }
        profile.dob?.let { dob ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.dob),
                textValue = dob,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.dob_icon)
            )
        }
        profile.gender?.let { gender ->
            val formattedGender = gender.replaceFirstChar { it.uppercase() }
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.gender),
                textValue = formattedGender,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.gender_icon)
            )
        }
        profile.panNumber?.let { panNumber ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.pan_number),
                textValue = panNumber,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.pan_number)
            )
        }
        profile.mobileNumber?.let { mobileNumber ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.contact_number),
                textValue = mobileNumber,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.call_icon)
            )
        }
        profile.employmentType?.let { employmentType ->
            val formattedEmployeeType = employmentType.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.employment_type),
                textValue = formattedEmployeeType,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.employee_type_icon)
            )
        }
        profile.companyName?.let { companyName ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.company_name),
                textValue = companyName,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.company_icon)
            )
        }
        profile.income?.let { income ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.annual_income),
                textValue = income,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.annual_income_range)
            )
        }

        OnlyReadAbleText(
            textHeader = stringResource(id = R.string.loan_purpose),
            textValue = loanPurpose,
            bottom = 5.dp,
            showImage = true,
            image = painterResource(R.drawable.annual_income_range)
        )

        profile.address1?.let { address1 ->
            val area = address1 ?: ""
            val town = profile.address2 ?: ""
            val city = profile.city1 ?: ""
            val state = profile.state1 ?: ""
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.address),
                textValue = "$area\n$town\n$city\n$state",
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.location_icon)
            )
        }
        profile.pincode1?.let { pinCode ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.pincode),
                textValue = pinCode,
                bottom = 5.dp,
                showImage = true,
                image = painterResource(R.drawable.pincode_icon)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyConsentExplain() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 5.dp)
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(24.dp),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.what_is_cic_consent),
                        style = bold14Text500,
                        color = appOrange,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(id = R.string.bottom_sheet_close),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                // Optional: Handle close logic or remove if unused
                            }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.cic_consent_meaning),
                    style = normal12Text400,
                    textAlign = TextAlign.Justify,
                    color = appBlack,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyConsentContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    registerViewModel: RegisterViewModel,
    onAcceptConsent: () -> Unit,
    showDialog: MutableState<Boolean>,
    startTimer: Boolean = false,
    timer: Int = 3
) {
    var secondsLeft by remember { mutableStateOf(timer) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(startTimer) {
        if (startTimer) {
            secondsLeft = timer
            isButtonEnabled = false
            while (secondsLeft > 0) {
                delay(1000L)
                secondsLeft--
            }
            isButtonEnabled = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 30.dp,
                bottom = 0.dp
            )
            .background(Color.Transparent)
    ) {
        // Bottom Sheet Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 0.dp)
                .background(
                    shape = RoundedCornerShape(topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp),
                    color = Color.White
                )
        ) {
//            Text(
//                text = stringResource(id = R.string.bottom_sheet_header),
//                style = bold20Text100,
//                color = appBlack,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 40.dp, bottom = 12.dp)
//            )
//
//            Divider(
//                color = backgroundOrange,
//                modifier = Modifier.padding(top = 5.dp)
//            )
            Row( modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 25.dp),horizontalArrangement = Arrangement
                .SpaceBetween) {
            Text(
                text = stringResource(id = R.string.bottom_sheet_header),
                style = bold20Text100,
                color = appBlack,
                textAlign = TextAlign.Center,
            )

            Icon(
                painter = painterResource(id = R.drawable.info_icon),
                contentDescription = stringResource(id = R.string.bottom_sheet_close),
                modifier = Modifier
//                    .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                    .clickable {
//                        coroutineScope.launch { bottomSheetState.hide() }
                        showDialog.value = true
                        registerViewModel.onCheckBoxDetailReset()
                    }
            )

        }
        HorizontalDivider(top = 5.dp, color = backgroundOrange, thickness=2.dp)

            HtmlText(stringResource(id = R.string.bottom_sheet_body))

            CurvedPrimaryButton(
                text = if (isButtonEnabled) stringResource(id = R.string.accept) else "Accept (${secondsLeft}s)",
                enabled = isButtonEnabled
            ) {
                coroutineScope.launch { bottomSheetState.hide() }
                registerViewModel.onCheckBoxDetailChanged(true)
                onAcceptConsent()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Floating Close Icon - Overlapping the top-right
        Icon(
            painter = painterResource(id = R.drawable.ic_close), // Replace with your actual icon
            contentDescription = "Close",
            modifier = Modifier
                .size(36.dp)
                .absoluteOffset(x = 12.dp, y = (-18).dp) // Move slightly outside top right
                .align(Alignment.TopEnd)
                .background(Color.White, shape = CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
                .clickable {
                    coroutineScope.launch {
                        coroutineScope.launch { bottomSheetState.hide() }
                        registerViewModel.onCheckBoxDetailReset()
                    }

                }
                .padding(6.dp),
            tint = appOrange
        )
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyConsentContentPreview(){
    CompanyConsentContent(
        bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
        coroutineScope = rememberCoroutineScope(),
        registerViewModel = viewModel(),
        onAcceptConsent = {},
        showDialog = remember { mutableStateOf(false) },
        startTimer = true,
        timer = 3
    )
}


fun onReviewClick(
    navController: NavHostController,
    loanPurpose: String,
    context: Context,
    fromFlow: String,
    webViewModel: WebViewModel,
    onNoLenders: () -> Unit
) {
    val endUse = if (loanPurpose.equals("Other Consumption Purpose", ignoreCase = true))
                    "other"
                else if (loanPurpose.equals("Consumer Durable Purchase", ignoreCase = true))
                    "consumerDurablePurchase"
                else loanPurpose.lowercase()

    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        CoroutineScope(Dispatchers.Main).launch {
            webViewModel.setWebInProgress(true)
            try {
            webViewModel.getLenderStatusApi(
                context = context,
                loanType = "PERSONAL_LOAN",
                step = "FORM_SUBMISSION_REQUEST"
            )
                val lenderStatusModel = webViewModel.getLenderStatusResponse
                    .filterNotNull().first()
            val lenderStatus = lenderStatusModel.data?.response
            if (lenderStatus.isNullOrEmpty()) {
                webViewModel.setWebInProgress(false)
                onNoLenders()
                return@launch
            }

            val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

            val lenderStatusJson: String = try {
                json.encodeToString(
                    LenderStatusResponse.serializer(),
                    LenderStatusResponse(response = lenderStatus)
                )
            } catch (e: Exception) {
                Log.e("JsonParseError", "Failed to serialize lender status", e)
                onNoLenders()
                webViewModel.setWebInProgress(false)
                return@launch
            }

            navigateToFormSubmissionWebScreen(navController, fromFlow, lenderStatusJson)
            loadWebScreen(
                fromFlow = fromFlow,
                webViewModel = webViewModel,
                context = context,
                endUse = endUse,
                purpose = loanPurpose,
            )
            } catch (e: Exception) {
                Log.e("LenderStatusError", "Error processing lender status", e)
                onNoLenders()
            } finally {
                webViewModel.setWebInProgress(false)
            }
        }
    }
    else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        loadWebScreen(
            fromFlow = fromFlow,
            webViewModel = webViewModel,
            context = context,
            endUse = loanPurpose,
            purpose =loanPurpose,
        )
//        navigateToBureauOffersScreen(navController, loanPurpose, fromFlow)
//        navigateToLoanProcessScreen(
//            navController = navController,
//            transactionId = "Sugu",
//            statusId = 18,
//            responseItem = loanPurpose,
//            offerId = "1234",
//            fromFlow = fromFlow
//        )
    }
}


@Composable
fun HtmlText(html: String) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                movementMethod = LinkMovementMethod.getInstance()
                setTextColor(Color(0xFF4E4E4E).toArgb())
                textSize = 14f
                typeface = ResourcesCompat.getFont(context, R.font.robotocondensed_regular)

                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                }
            }
        },
        modifier = Modifier.padding(start = 45.dp, end = 45.dp, top = 10.dp, bottom = 10.dp)
    )
}


@Composable
fun ConsentDialogBox(showDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 200.dp, max = 300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.what_is_cic_consent), style = bold14Text700, color = appOrange)
                Spacer(modifier = Modifier.height(20.dp))
                Text(stringResource(R.string.by_giving_your_cic_credit_information),style = normal12Text400)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { showDialog.value = false },
                ) {
                    Text("Got it",style = bold13Text700)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewReviewDetailsScreen() {
    val navController = rememberNavController()
    ReviewDetailsScreen(
        navController = navController,
        loanPurpose = "Education",
        fromFlow = "Personal Loan"
    )
}
