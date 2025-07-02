package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableDropDownField
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.InputField
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OutLineTextFieldHeader
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPersonaLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToReviewDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.AnnualIncomeViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.AddressFields
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.BasicDetailsInputField
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.DatePickerField
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.FullAddress
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

val indianCurrencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
    maximumFractionDigits = 0
    minimumFractionDigits = 0
    isGroupingUsed = true
}
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BasicDetailsScreen(navController: NavHostController, fromFlow: String, loanPurpose: String) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val registerViewModel: RegisterViewModel = viewModel()

    val firstName: String? by registerViewModel.firstName.observeAsState()
    val firstNameError by registerViewModel.firstNameError.observeAsState(null)

    val lastName by registerViewModel.lastName.observeAsState("")
    val lastNameError by registerViewModel.lastNameError.observeAsState(null)

    val personalEmailId by registerViewModel.personalEmailId.observeAsState("")
    val personalEmailIdError by registerViewModel.personalEmailIdError.observeAsState(null)

    val officialEmailId by registerViewModel.officialEmailId.observeAsState("")
    val officialEmailIdError by registerViewModel.officialEmailIdError.observeAsState(null)

    val phoneNumber by registerViewModel.phoneNumber.observeAsState("")
    val phoneNumberError by registerViewModel.phoneNumberError.observeAsState(null)

    val dob by registerViewModel.dob.observeAsState("")
    val dobError by registerViewModel.dobError.observeAsState(null)

    val gender by registerViewModel.gender.observeAsState("")
    val genderError by registerViewModel.genderError.observeAsState(null)

    val panNumber by registerViewModel.panNumber.observeAsState("")
    val panError by registerViewModel.panError.observeAsState(null)

    val employeeType by registerViewModel.employeeType.observeAsState("")
    val employeeTypeError by registerViewModel.employeeTypeError.observeAsState(null)

    val companyName by registerViewModel.companyName.observeAsState("")
    val companyNameError by registerViewModel.companyNameError.observeAsState(null)

    val udyamNumber by registerViewModel.udyamNumber.observeAsState("")


    val pinCode1 by registerViewModel.pinCode1.observeAsState("")
    val pinCode1Error by registerViewModel.pinCode1Error.observeAsState(null)

    val city1 by registerViewModel.city1.observeAsState("")
    val city1Error by registerViewModel.city1Error.observeAsState(null)

    val area1 by registerViewModel.area1.observeAsState("")
    val area1Error by registerViewModel.area1Error.observeAsState(null)

    val town1 by registerViewModel.town1.observeAsState("")
    val town1Error by registerViewModel.town1Error.observeAsState(null)

    val state1 by registerViewModel.state1.observeAsState("")
    val state1Error by registerViewModel.state1Error.observeAsState(null)

    val officialAddressField by registerViewModel.officialAddressField.observeAsState()
    val officialAddressError by registerViewModel.officialAddressError.observeAsState(null)

    val city2 by registerViewModel.city2.observeAsState("")
    val city2Error by registerViewModel.city2Error.observeAsState(null)

    val permanentAddressField by registerViewModel.permanentAddressField.observeAsState()
    val permanentAddressError by registerViewModel.permanentAddressError.observeAsState(null)
    val incomeValue: String? by registerViewModel.income.observeAsState("")

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val personalEmailIdFocusRequester = remember { FocusRequester() }
    val officialEmailIdFocusRequester = remember { FocusRequester() }
    val phoneNumberFocusRequester = remember { FocusRequester() }
    val dobFocusRequester = remember { FocusRequester() }
    val genderFocusRequester = remember { FocusRequester() }
    val panFocusRequester = remember { FocusRequester() }
    val employeeTypeFocusRequester = remember { FocusRequester() }
    val companyNameFocusRequester = remember { FocusRequester() }

    val pinCode1FocusRequester = remember { FocusRequester() }
    val city1FocusRequester = remember { FocusRequester() }
    val area1FocusRequester = remember { FocusRequester() }
    val town1FocusRequester = remember { FocusRequester() }
    val state1FocusRequester = remember { FocusRequester() }
    val officialAddressFocusRequester = remember { FocusRequester() }
    val permanentAddressFocusRequester = remember { FocusRequester() }

    val pinCode1Response by registerViewModel.pinCode1Response.collectAsState()

    val showInternetScreen by registerViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by registerViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by registerViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by registerViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by registerViewModel.unAuthorizedUser.observeAsState(false)

    val inProgress by registerViewModel.inProgress.collectAsState()
    val isCompleted by registerViewModel.isCompleted.collectAsState()

    var selectedCity1 by remember { mutableStateOf(city1 ?: "") }
    val onCity1Selected: (String) -> Unit = { selectedText ->
        selectedCity1 = selectedText
    }
    var selectedCity2 by remember { mutableStateOf(city2 ?: "") }

    val formattedGender = gender?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    } ?: ""
    var genderSelectedText = if (formattedGender.equals("Transgender", ignoreCase = true)) {
        stringResource(id = R.string.others)
    } else {
        formattedGender
    }
    val genderList = listOf(
        stringResource(id = R.string.male), stringResource(id = R.string.female),
        stringResource(id = R.string.others)
    )
    val formattedEmployeeType = employeeType?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    } ?: ""
    var employmentSelectedText: String = formattedEmployeeType ?: ""
    val employeeTypeList = listOf(
        stringResource(id = R.string.salaried), stringResource(id = R.string.self_employment)
    )

    val scope = rememberCoroutineScope()
    val bottomSheet1Value = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val bottomSheet2Value = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val annualIncomeViewModel: AnnualIncomeViewModel = viewModel()
    LaunchedEffect(incomeValue) {
        annualIncomeViewModel.setInitialIncome(incomeValue?.toIntOrNull() ?: 200000)
    }
    val income: Int by annualIncomeViewModel.income.observeAsState(
        incomeValue?.toIntOrNull() ?: 200000
    )
    val selectedPurpose: String by annualIncomeViewModel.selectedPurpose.observeAsState(loanPurpose)
    val purposeError by annualIncomeViewModel.purposeError.observeAsState(null)
    val sliderPosition: Float by annualIncomeViewModel.sliderPosition.observeAsState(income.toFloat())
    val annualIncomeErrorMessage by annualIncomeViewModel.annualIncomeErrorMessage.collectAsState()
    val updatedIncome by annualIncomeViewModel.updatedIncome.collectAsState()
    val incomeFocus = remember { FocusRequester() }
    val purposeFocus = remember { FocusRequester() }
    val bringPurposeFocusRequester = remember { BringIntoViewRequester() }
    var sliderSelected = remember { false }
    var purposeExpanded by rememberSaveable { mutableStateOf(false) }
    val purpose = listOf(
        stringResource(id = R.string.travel),
        stringResource(id = R.string.education),
        stringResource(id = R.string.health),
        stringResource(id = R.string.consumer_durable_purchase),
        stringResource(id = R.string.other_consumption_purpose)
    )

    val onPurposeSelected: (String) -> Unit = { selectedText ->
        annualIncomeViewModel.onPurposeSelected(selectedText)
        purposeExpanded = false
        purposeFocus.requestFocus()
    }

    val onPurposeDismiss: () -> Unit = { purposeExpanded = false }
    val minRange = 200000f
    val maxRange = 8000000f
    val stepSize = 1000f
    val numberOfSteps = ((maxRange - minRange) / stepSize).toInt() - 1

    BackHandler { navigateToPersonaLoanScreen(navController, fromFlow) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue( )) }
    val formattedIncome by remember(income) {
        mutableStateOf(annualIncomeViewModel.formatIncome(income))
    }

    DisposableEffect(income) {
        val incomeWithoutSymbol = income
        if (sliderSelected) {
            val roundedValue = (incomeWithoutSymbol / stepSize).roundToInt() * stepSize.toFloat()
            annualIncomeViewModel.updateSliderPosition(roundedValue, context)
        } else {
            annualIncomeViewModel.updateSliderPosition(incomeWithoutSymbol.toFloat(), context)
        }

        onDispose { }
    }

    LaunchedEffect(Unit) {
        val actualIncome = income
        annualIncomeViewModel.updateIncome(if (actualIncome < 200000) 200000 else actualIncome)
    }

    if (!showInternetScreen && !showTimeOutScreen && !showServerIssueScreen && !unexpectedErrorScreen) {
        if (inProgress) {
            CenterProgress()
        } else {
            if (!isCompleted) {
                registerViewModel.getUserDetail(context, navController)
            } else {
                if (updatedIncome) {
                    navigateToReviewDetailsScreen(navController, selectedPurpose, fromFlow)
                }
                CustomModalBottomSheet(
                    bottomSheetState = bottomSheet1Value,
                    sheetContent = {
                        FullAddress(
                            pinCode = pinCode1,
                            pinCodeFocus = pinCode1FocusRequester,
                            pinCodeError = pinCode1Error,
                            area = area1,
                            areaFocus = area1FocusRequester,
                            areaError = area1Error,
                            bottomSheetValue = bottomSheet1Value,
                            scope = scope,
                            state = state1,
                            stateFocus = state1FocusRequester,
                            stateError = state1Error ?: "",
                            town = town1,
                            townFocus = town1FocusRequester,
                            townError = town1Error,
                            city = city1,
                            cityFocus = city1FocusRequester,
                            cityError = city1Error,
                            selectedCity = selectedCity1.ifEmpty { city1 ?: "" },
                            pinCodeResponse = pinCode1Response,
                            registerViewModel = registerViewModel,
                            context = context,
                            onItemSelected = onCity1Selected
                        )
                    }
                ) {
                        FixedTopBottomScreen(
                            navController = navController,
                            contentStart = 0.dp,
                            topBarBackgroundColor = appOrange,
                            topBarText = stringResource(R.string.basic_detail),
                            showBackButton = true,
                            onBackClick = { navigateToPersonaLoanScreen(navController, fromFlow) },
                            showBottom = true,
                            showSingleButton = true,
                            primaryButtonText = stringResource(R.string.next),
                            onPrimaryButtonClick = {
                                annualIncomeViewModel.onNextClicked(
                                    context, selectedPurpose, income,
                                    onFocusRequester = {
                                        scope.launch {
                                            purposeFocus.requestFocus()
                                            bringPurposeFocusRequester.bringIntoView()
                                        }
                                    }
                                )
                            },
                            backgroundColor = appWhite
                        ) {
                            RegisterText(
                                text = stringResource(id = R.string.please_provide_more_info),
                                style = normal20Text700,
                                top = 20.dp, bottom = 20.dp,
                                start = 20.dp, end = 20.dp,
                            )

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.first_name),
                                value = firstName?:"",
                                onValueChange = {
                                    registerViewModel.onFirstNameChanged(it, context)
                                },
                                error = firstNameError,
                                readOnly = true,
                                focusRequester = firstNameFocusRequester,
                                leadingImage = painterResource(R.drawable.person_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Sentences,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.last_name),
                                value = lastName,
                                readOnly = true,
                                onValueChange = {
                                    registerViewModel.onLastNameChanged(
                                        it,
                                        context
                                    )
                                },
                                error = lastNameError,
                                focusRequester = lastNameFocusRequester,
                                leadingImage = painterResource(R.drawable.person_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Sentences,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )
                            InputField(
                                inputText = TextFieldValue(""),
                                readOnly = true,
                                topText = stringResource(id = R.string.employment_type),
                                showRadio = true,
                                top = 0.dp,
                                start = 5.dp,
                                end = 10.dp,
                                showStar = true,
                                showOnlyTextField = false,
                                modifier = Modifier.focusRequester(employeeTypeFocusRequester),
                                error = employeeTypeError,
                                showBox = true,
                                boxHeight = 55.dp,
                                onValueChange = {},
                                leadingImage = painterResource(R.drawable.employee_type_icon),
                                radioList = employeeTypeList,
                                selectedRadio = employmentSelectedText,
                                onRadioSelected = { selectedEmployment ->
                                    employmentSelectedText = selectedEmployment
                                    registerViewModel.onEmployeeTypeChanged(
                                        selectedEmployment,
                                        context
                                    )
                                },
                            )

                                BasicDetailsInputField(
                                    label = stringResource(id = R.string.personal_email_id),
                                    value = personalEmailId?:"",
                                    showStar = true,
                                    readOnly = true,
                                    onValueChange = {
                                        registerViewModel.onPersonalEmailIdChanged(
                                            it,
                                            context
                                        )
                                    },
                                    error = personalEmailIdError,
                                    focusRequester = personalEmailIdFocusRequester,
                                    leadingImage = painterResource(R.drawable.email_icon),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Email
                                    )
                                )

                            officialEmailId?.let {
                                BasicDetailsInputField(
                                    label = stringResource(id = R.string.official_email_id),
                                    value = it,
                                    showStar = true,
                                    readOnly = true,
                                    onValueChange = {
                                        registerViewModel.onOfficialEmailIdChanged(it, context)
                                    },
                                    error = officialEmailIdError,
                                    focusRequester = officialEmailIdFocusRequester,
                                    leadingImage = painterResource(R.drawable.email_icon),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Email
                                    )
                                )
                            }

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.phone_number),
                                value = phoneNumber?:"",
                                showStar = true,
                                readOnly = true,
//                                enable = false,
                                leadingIcon = { Text(text = "+91", style = normal14Text500) },
                                onValueChange = {},
                                error = phoneNumberError,
                                focusRequester = phoneNumberFocusRequester,
                                leadingImage = painterResource(R.drawable.call_icon),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Phone
                                )
                            )
                            DatePickerField(
                                dob = dob ?: " ", dobFocus = dobFocusRequester,
                                registerViewModel = registerViewModel, dobError = dobError ?: "",
                                context = context, readOnly = true,
                                keyboardController = keyboardController
                            )
                            InputField(
                                inputText = TextFieldValue(""),
                                readOnly = true,
                                topText = stringResource(id = R.string.gender),
                                showStar = true,
                                showRadio = true,
                                top = 0.dp,
                                start = 5.dp,
                                end = 10.dp,
                                showOnlyTextField = false,
                                modifier = Modifier.focusRequester(genderFocusRequester),
                                error = genderError,
                                showBox = true,
                                boxHeight = 55.dp,
                                onValueChange = {},
                                leadingImage = painterResource(R.drawable.gender_icon),
                                radioList = genderList,
                                selectedRadio = genderSelectedText,
                                onRadioSelected = { selectedText ->
                                    genderSelectedText = selectedText
                                    registerViewModel.onGenderChanged(selectedText, context)
                                },
                            )

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.pan),
                                value = panNumber?:"",
                                readOnly = true,
                                onValueChange = { registerViewModel.onPanNumberChanged(it, context ,navController)},
                                error = panError,
                                focusRequester = panFocusRequester,
                                leadingImage = painterResource(R.drawable.pan_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )


                            BasicDetailsInputField(
                                label = stringResource(id = R.string.company_name),
                                value = companyName?:"",
                                readOnly = true,
                                onValueChange = { registerViewModel.onCompanyNameChanged(it) },
                                error = companyNameError,
                                focusRequester = companyNameFocusRequester,
                                leadingImage = painterResource(R.drawable.company_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Sentences,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.annual_income_range),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .size(24.dp)
                                )
                                Column {
                                    OutLineTextFieldHeader(
                                        topText = stringResource(R.string.annual_income),
                                        showOptional = false,
                                        showStar = true,
                                        topTextStart = 0.dp,
                                        topTextBottom = 0.dp,
                                        topTextTop = 0.dp,
                                        starTop = 0.dp,
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 5.dp, end = 10.dp, top = 3.dp)
                                            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                    ) {
                                        Column {
                                            Slider(
                                                value = sliderPosition,
                                                onValueChange = { newValue ->
                                                    sliderSelected = true
                                                    val roundedValue =
                                                        (newValue / stepSize).roundToInt() * stepSize.toFloat()
                                                    annualIncomeViewModel.updateSliderPosition(
                                                        roundedValue,
                                                        context
                                                    )
                                                },
                                                valueRange = 200000f..8000000f,
                                                steps = numberOfSteps,
                                                colors = SliderDefaults.colors(
                                                    thumbColor = appOrange,
                                                    activeTickColor = appOrange,
                                                    inactiveTickColor = grayD9,
                                                ),
                                                modifier = Modifier.padding(
                                                    start = 30.dp,
                                                    end = 30.dp,
                                                    top = 8.dp
                                                )
                                            )

                                            SpaceBetweenText(
                                                end = 35.dp,
                                                top = 0.dp,
                                                text = stringResource(id = R.string.two_lakh),
                                                value = stringResource(id = R.string.eighty_lakh),
                                                start = 35.dp,
                                                textColor = checkBoxGray
                                            )
                                            TextInputLayout(
                                                textFieldVal = TextFieldValue(text = formattedIncome,
                                                    selection = if (textFieldValue.selection.start <= 1) {
                                                    TextRange(1)
                                                } else {
                                                    textFieldValue.selection
                                                }),
                                                keyboardOptions = KeyboardOptions(
                                                    imeAction = ImeAction.Next,
                                                    keyboardType = KeyboardType.Number
                                                ),
                                                onTextChanged = { newText ->
                                                    sliderSelected = false
                                                    val rawInput = newText.text
                                                    val cursorPosition = newText.selection.start
                                                    val (cleanInput, newCursor) = processRawInput(rawInput, cursorPosition)
                                                    val formatted = formatCurrency(applyMaxLimit(cleanInput))

                                                    textFieldValue =
                                                        TextFieldValue(
                                                            text = formatted,
                                                            selection = TextRange(calculateFormattedCursorPosition(
                                                                cleanInput = cleanInput,
                                                                originalCursor = newCursor,
                                                                formattedValue = formatCurrency(cleanInput)
                                                            ))
                                                        )

                                                    val loanAmountWithoutSymbol =
                                                        newText.text.replace("₹", "").replace(",", "")
                                                    if (loanAmountWithoutSymbol.all { it.isDigit() }) {
                                                        annualIncomeViewModel.onIncomeChanged( context,formatted)
                                                    }
                                                    if (newText.text.replace("₹", "")
                                                            .replace(",", "").isEmpty()
                                                    ) {
                                                        annualIncomeViewModel.updateGeneralError(null)
                                                    }
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 30.dp,
                                                        end = 30.dp,
                                                        bottom = 5.dp
                                                    )
                                                    .focusRequester(incomeFocus),
                                                readOnly = false
                                            )
                                            annualIncomeErrorMessage?.let {
                                                RegisterText(
                                                    text = it, style = normal12Text400,
                                                    textColor = errorRed, bottom = 5.dp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            ClickableDropDownField(
                                start = 40.dp,
                                end = 10.dp,
                                top = 10.dp,
                                bottom = 5.dp,
                                selectedText = selectedPurpose,
                                focus = purposeFocus,
                                error = purposeError,
                                errorTextStart = 42.dp,
                                expand = purposeExpanded,
                                setExpand = { purposeExpanded = it },
                                itemList = purpose,
                                onDismiss = onPurposeDismiss,
                                onItemSelected = onPurposeSelected,
                                modifier = Modifier.focusRequester(purposeFocus)
                                    .bringIntoViewRequester(bringPurposeFocusRequester)
                            )

                            val address1 = if (officialAddressField != null) {
                                listOfNotNull(
                                    listOfNotNull(
                                        officialAddressField!!.area?.takeIf { it.isNotBlank() },
                                        officialAddressField!!.town?.takeIf { it.isNotBlank() }
                                    ).takeIf { it.isNotEmpty() }?.joinToString(", "),

                                    listOfNotNull(
                                        officialAddressField!!.city?.takeIf { it.isNotBlank() },
                                        officialAddressField!!.state?.takeIf { it.isNotBlank() }
                                    ).takeIf { it.isNotEmpty() }?.joinToString(", "),

                                    officialAddressField!!.pincode?.takeIf { it.isNotBlank() }
                                ).joinToString("\n")
                            } else {
                                ""
                            }

                            val address2 = if (permanentAddressField != null) {
                                listOfNotNull(
                                    listOfNotNull(
                                        permanentAddressField!!.area?.takeIf { it.isNotBlank() },
                                        permanentAddressField!!.town?.takeIf { it.isNotBlank() }
                                    ).takeIf { it.isNotEmpty() }?.joinToString(", "),

                                    listOfNotNull(
                                        permanentAddressField!!.city?.takeIf { it.isNotBlank() },
                                        permanentAddressField!!.state?.takeIf { it.isNotBlank() }
                                    ).takeIf { it.isNotEmpty() }?.joinToString(", "),

                                    permanentAddressField!!.pincode?.takeIf { it.isNotBlank() }
                                ).joinToString("\n")
                            } else {
                                ""
                            }
                            if(!pinCode1.isNullOrEmpty()) {
                                AddressFields(
                                    bottomSheet1Value = bottomSheet1Value,
                                    address1 = address1,
                        //                                address1 ="${officialAddressField?.area},${officialAddressField?.town} \n ${officialAddressField?.city},${officialAddressField?.state}\n ${officialAddressField?.pincode}",
                                    bottomSheet2Value = bottomSheet2Value,
                                    scope = scope,
                                    address1Focus = officialAddressFocusRequester,
                                    address1Error = officialAddressError,
                        //                                address2 ="${permanentAddressField?.area},${permanentAddressField?.town} \n ${permanentAddressField?.city},${permanentAddressField?.state}\n ${permanentAddressField?.pincode}",
                                    address2 = address2,
                                    address2Focus = permanentAddressFocusRequester,
                                    address2Error = permanentAddressError,
                                    readOnly = true
                                )
                            }
                        }

                    }

            }
        }
    } else {
        CommonMethods().HandleErrorScreens(
            navController = navController, showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen, showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen, unAuthorizedUser = unAuthorizedUser
        )
    }
}
 fun processRawInput(input: String, cursorPos: Int): Pair<String, Int> {
    // Remove non-digit characters and leading zeros
    var clean = input.filter { it.isDigit() }
    clean = clean.replaceFirst("^0+(?!$)".toRegex(), "").ifEmpty { "0" }

    // Calculate original cursor position in digits
    val nonDigitsBefore = input.take(cursorPos).count { !it.isDigit() }
    val originalDigitCursor = (cursorPos - nonDigitsBefore).coerceIn(0, input.length)

    // Adjust cursor for leading zero removal
    val originalDigits = input.filter { it.isDigit() }
    val leadingZerosRemoved = originalDigits.length - clean.length
    val newCursor = (originalDigitCursor - leadingZerosRemoved).coerceIn(0, clean.length)

    return clean to newCursor
}

 fun calculateFormattedCursorPosition(
    cleanInput: String,
    originalCursor: Int,
    formattedValue: String
): Int {
    if (cleanInput.isEmpty() || cleanInput == "0") {
        return formattedValue.length
    }

    val digitsBeforeCursor = originalCursor.coerceIn(0, cleanInput.length)

    val digitIndices = mutableListOf<Int>()
    formattedValue.forEachIndexed { index, c ->
        if (c.isDigit()) {
            digitIndices.add(index)
        }
    }

    return when {
        digitsBeforeCursor == 0 -> {
            // Force cursor to start after currency symbol
            1.coerceAtLeast(formattedValue.indexOfFirst { it.isDigit() })
        }
        digitsBeforeCursor <= digitIndices.size -> {
            val digitIndex = digitIndices[digitsBeforeCursor - 1]
            digitIndex + 1
        }
        else -> formattedValue.length
    }
}


 fun formatCurrency(amount: String): String {
    return try {
        val number = amount.ifEmpty { "0" }.toLong()
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
            isGroupingUsed = true
        }.format(number)
    } catch (e: Exception) {
        "₹0"
    }
}

fun applyMaxLimit(amount: String,maxLimit: Long=8_000_000): String {
    return try {
        val numericValue = amount.toLong()
        numericValue.coerceAtMost(maxLimit).toString()
    } catch (e: Exception) {
        "0"
    }
}



@Preview(showBackground = true)
@Composable
fun BasicDetailsScreenPreview() {
    BasicDetailsScreen(rememberNavController(), "Personal", "")
}