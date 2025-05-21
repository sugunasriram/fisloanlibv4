package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.EditableText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToBasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToBureauOffersScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.lightishGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AnnualIncomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

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

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val annualIncomeBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    BackHandler {
        goBack(navController = navController, fromFlow = fromFlow,loanPurpose = loanPurpose)
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
            fromFlow = fromFlow
        )
    } else {
        CommonMethods().HandleErrorScreens(
            navController = navController, showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen, showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen, unAuthorizedUser = unAuthorizedUser
        )
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
    registerViewModel: RegisterViewModel
) {
    var showError by remember { mutableStateOf(false) }

    if (inProgress) {
        ProcessingAnimation(text = "", image = R.raw.we_are_currently_processing_hour_glass)
    } else {
        if (!isCompleted) {
            registerViewModel.getUserDetail(context, navController)
        } else {
            var updatedIncome by remember { mutableStateOf(userDetails?.data?.income) }
            CustomModalBottomSheet(
                bottomSheetState = bottomSheetState,
                sheetContent = {
                    CompanyConsentContent(
                        bottomSheetState = bottomSheetState,
                        coroutineScope = coroutineScope,
                        registerViewModel = registerViewModel,
                        onAcceptConsent = { showError = false }
                    )
                }) {
                CustomModalBottomSheet(
                    bottomSheetState = annualIncomeBottomSheetState,
                    sheetContent = {
                        userDetails?.data?.income?.let {
                            AnnualIncomeModalContent(
                                bottomSheetState = annualIncomeBottomSheetState,
                                coroutineScope = coroutineScope,
                                context = context,
                                incomeValue = it,
                                onIncomeUpdated = { newIncome ->
                                    updatedIncome = newIncome  // Update state when income changes
                                }
                            )
                        }
                    }) {
                    FixedTopBottomScreen(
                        navController = navController,
                        topBarBackgroundColor = appOrange,
                        topBarText = stringResource(R.string.basic_detail),
                        showBackButton = true,
                        onBackClick = { goBack(navController = navController, fromFlow = fromFlow,loanPurpose = loanPurpose)},
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
                            if (!checkboxState) {
                                showError = true
                            } else {
                                showError = false
                                onReviewClick(
                                    navController = navController,
                                    loanPurpose = loanPurpose,
                                    context = context,
                                    fromFlow = fromFlow
                                )
                            }
                        },
                        backgroundColor = appWhite
                    ) {
                        updatedIncome?.let {
                            PersonalDetailView(
                                userDetails = userDetails, loanPurpose = loanPurpose,
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

fun goBack(navController: NavHostController, fromFlow: String,loanPurpose : String) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        navigateToBasicDetailsScreen(navController, fromFlow, loanPurpose = loanPurpose)
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        navigateToLoanProcessScreen(
            navController = navController, transactionId = "Sugu", statusId = 17,
            responseItem = "No Need",
            offerId = "1234", fromFlow = "Purchase Finance"
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalDetailView(
    userDetails: ProfileResponse?, loanPurpose: String,
    annualIncome: String,
    annualIncomeBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    userDetails?.let { response ->
        StartingText(
            text = stringResource(id = R.string.review_details),
            start = 20.dp, end = 30.dp, bottom = 10.dp, top = 20.dp, style = normal16Text500,
            textAlign = TextAlign.Start,
        )
        StartingText(
            text = stringResource(id = R.string.personal_detail),
            start = 20.dp, end = 30.dp, bottom = 5.dp, style = normal14Text700,
            textAlign = TextAlign.Start,
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
    profile: Profile, loanPurpose: String, annualIncome: String,
    annualIncomeBottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
) {
    DisplayCard(cardColor = backgroundOrange) {
        profile.firstName?.let { firstName ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.first_name), textValue = firstName,
                top = 10.dp, bottom = 10.dp, showImage = true
            )
        }
        profile.lastName?.let { lastName ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.last_name), textValue = lastName,
                bottom = 5.dp, showImage = true
            )
        }
        profile.email?.let { email ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.personal_email), textValue = email,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.email_icon)
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
                textHeader = stringResource(id = R.string.dob), textValue = dob,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.dob_icon)
            )
        }
        profile.gender?.let { gender ->
            val formattedGender = gender.replaceFirstChar { it.uppercase() }
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.gender), textValue = formattedGender,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.gender_icon)
            )
        }
        profile.panNumber?.let { panNumber ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.pan_number), textValue = panNumber,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.pan_number)
            )
        }
        profile.mobileNumber?.let { mobileNumber ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.contact_number), textValue = mobileNumber,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.call_icon)
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
                textHeader = stringResource(id = R.string.company_name), textValue = companyName,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.company_icon)
            )
        }
        profile.income?.let { income ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.annual_income), textValue = income,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.annual_income_range)
            )
        }

//        profile.income?.let {
//            EditableText(
//                textHeader = stringResource(id = R.string.annual_income), textValue = annualIncome,
//                bottom = 5.dp,
//                showImage = true, image = painterResource(R.drawable.annual_income_range),
//                onClickEdit = { coroutineScope.launch { annualIncomeBottomSheetState.show() } }
//            )
//        }
        OnlyReadAbleText(
            textHeader = stringResource(id = R.string.loan_purpose), textValue = loanPurpose,
            bottom = 5.dp,
            showImage = true, image = painterResource(R.drawable.annual_income_range)
        )

        profile.address1?.let { address1 ->
            val address = address1.split(",").map { it.trim() }
            val area = address.getOrNull(0) ?: ""
            val town = address.getOrNull(1) ?: ""
            val city = profile.city1 ?: ""
            val state = profile.state1 ?: ""
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.official_address),
                textValue = "$area\n$town\n$city\n$state",
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.location_icon)
            )
        }
        profile.pincode1?.let { pinCode ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.pincode), textValue = pinCode,
                 bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.pincode_icon)
            )
        }
        profile.address2?.let { address2 ->
            val address = address2.split(",").map { it.trim() }
            val area = address.getOrNull(0) ?: ""
            val town = address.getOrNull(1) ?: ""
            val city = profile.city2 ?: ""
            val state = profile.state2 ?: ""
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.permanent_address),
                textValue = "$area\n$town\n$city\n$state",
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.location_icon)
            )
        }
        profile.pincode2?.let { pinCode ->
            OnlyReadAbleText(
                textHeader = stringResource(id = R.string.pincode), textValue = pinCode,
                bottom = 5.dp,
                showImage = true, image = painterResource(R.drawable.pincode_icon)
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyConsentContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    registerViewModel: RegisterViewModel,
    onAcceptConsent: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(shape = RoundedCornerShape(40.dp), color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = stringResource(id = R.string.bottom_sheet_close),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                .clickable {
                    coroutineScope.launch { bottomSheetState.hide() }
                    registerViewModel.onCheckBoxDetailReset()
                }
        )
        Text(
            text = stringResource(id = R.string.bottom_sheet_header), style = bold20Text100,
            color = appBlack, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider(top = 5.dp, color = backgroundOrange)
        Text(
            text = stringResource(id = R.string.bottom_sheet_body),
            style = normal14Text400, textAlign = TextAlign.Justify, color = appGray,
            modifier = Modifier.padding(start = 45.dp, end = 45.dp, top = 10.dp, bottom = 10.dp)
        )
        CurvedPrimaryButton(text = stringResource(id = R.string.accept),) {
            coroutineScope.launch { bottomSheetState.hide() }
            registerViewModel.onCheckBoxDetailChanged(true)
            onAcceptConsent()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnualIncomeModalContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    context: Context, incomeValue: String,
    onIncomeUpdated: (String) -> Unit
) {
    val annualIncomeViewModel: AnnualIncomeViewModel = viewModel()
    LaunchedEffect(incomeValue) {
        annualIncomeViewModel.setInitialIncome(incomeValue.toIntOrNull() ?: 30000)
    }

    val income: Int by annualIncomeViewModel.income.observeAsState(
        incomeValue.toIntOrNull() ?: 30000
    )
    val sliderPosition: Float by annualIncomeViewModel.sliderPosition.observeAsState(income.toFloat())
    val incomeFocus = remember { FocusRequester() }
    val minRange = 30000f
    val maxRange = 2000000f
    val stepSize = 5000f
    val numberOfSteps = ((maxRange - minRange) / stepSize).toInt() - 1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(shape = RoundedCornerShape(40.dp), color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = stringResource(id = R.string.bottom_sheet_close),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                .clickable {
                    coroutineScope.launch { bottomSheetState.hide() }
                }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.annual_income_range),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp)
            )
            Text(
                text = stringResource(id = R.string.annual_income),
                style = normal20Text700
            )

        }
        Column {
            val formattedIncome =
                annualIncomeViewModel.formatIncome(income)
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    val roundedValue =
                        (newValue / stepSize).roundToInt() * stepSize
                    annualIncomeViewModel.updateSliderPosition(
                        roundedValue,
                        context
                    )
                },
                valueRange = 30000f..2000000f,
                steps = numberOfSteps,
                colors = SliderDefaults.colors(
                    thumbColor = appOrange,
                    activeTickColor = appOrange,
                    inactiveTickColor = grayD9,
                ),
                modifier = Modifier.padding(
                    start = 40.dp,
                    end = 40.dp,
                    top = 8.dp
                )
            )

            SpaceBetweenText(
                end = 45.dp,
                top = 0.dp,
                text = stringResource(id = R.string.tewnty_k),
                value = stringResource(id = R.string.tewnty_lakh),
                start = 45.dp,
                textColor = checkBoxGray
            )
            TextInputLayout(
                textFieldVal = TextFieldValue(
                    text = formattedIncome,
                    selection = TextRange(formattedIncome.length)
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {}),
                onTextChanged = { newText ->
                    annualIncomeViewModel.onIncomeChanged(
                        context = context, newText.text
                    )
                },
                onLostFocusValidation = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                    .focusRequester(incomeFocus),
                readOnly = true
            )
        }
        CurvedPrimaryButton(text = stringResource(id = R.string.update)
        ) {
            annualIncomeViewModel.updateUserIncomeApi(context = context, income = income.toString())
            onIncomeUpdated(income.toString())
            coroutineScope.launch { bottomSheetState.hide() }
        }
    }
}

fun onReviewClick(
    navController: NavHostController, loanPurpose: String, context: Context,
    fromFlow: String
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        navigateToBureauOffersScreen(navController,loanPurpose, fromFlow)
//        navigateToAccountAggregatorScreen(navController, loanPurpose, fromFlow)
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        navigateToLoanProcessScreen(
            navController = navController, transactionId = "Sugu",
            statusId = 18, responseItem = loanPurpose,
            offerId = "1234", fromFlow = fromFlow
        )
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






