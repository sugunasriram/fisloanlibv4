package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
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
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DropDownField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.InputField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OutLineTextFieldHeader
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToPersonaLoanScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToReviewDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AnnualIncomeViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.auth.AddressFields
import com.github.sugunasriram.fisloanlibv4.fis_code.views.auth.BasicDetailsInputField
import com.github.sugunasriram.fisloanlibv4.fis_code.views.auth.DatePickerField
import com.github.sugunasriram.fisloanlibv4.fis_code.views.auth.FullAddress
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
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

    val pinCode2 by registerViewModel.pinCode2.observeAsState("")
    val pinCode2Error by registerViewModel.pinCode2Error.observeAsState(null)

    val city2 by registerViewModel.city2.observeAsState("")
    val city2Error by registerViewModel.city2Error.observeAsState(null)

    val area2 by registerViewModel.area2.observeAsState("")
    val area2Error by registerViewModel.area2Error.observeAsState(null)

    val town2 by registerViewModel.town2.observeAsState("")
    val town2Error by registerViewModel.town2Error.observeAsState(null)

    val state2 by registerViewModel.state2.observeAsState("")
    val state2Error by registerViewModel.state2Error.observeAsState(null)

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
    val pinCode2FocusRequester = remember { FocusRequester() }
    val city2FocusRequester = remember { FocusRequester() }
    val area2FocusRequester = remember { FocusRequester() }
    val town2FocusRequester = remember { FocusRequester() }
    val state2FocusRequester = remember { FocusRequester() }
    val permanentAddressFocusRequester = remember { FocusRequester() }

    val pinCode1Response by registerViewModel.pinCode1Response.collectAsState()
    val pinCode2Response by registerViewModel.pinCode2Response.collectAsState()

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
    val onCity2Selected: (String) -> Unit = { selectedText ->
        selectedCity2 = selectedText
    }

    val formattedGender = gender?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    } ?: ""
    var genderSelectedText = if (formattedGender.equals("Transgender", ignoreCase = true)) {
        stringResource(id = R.string.others)
    } else {
        formattedGender
    }
    val genderList = listOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female),
        stringResource(id = R.string.others)
//        stringResource(id = R.string.transgender)
    )
    val formattedEmployeeType = employeeType?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    } ?: ""
    var employmentSelectedText: String = formattedEmployeeType ?: ""
    val employeeTypeList = listOf(
        stringResource(id = R.string.salaried),
        stringResource(id = R.string.self_employment)
    )

    val scope = rememberCoroutineScope()
    val bottomSheet1Value = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val bottomSheet2Value = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val annualIncomeViewModel: AnnualIncomeViewModel = viewModel()
    LaunchedEffect(incomeValue) {
        annualIncomeViewModel.setInitialIncome(incomeValue?.toIntOrNull() ?: 30000)
    }
    val income: Int by annualIncomeViewModel.income.observeAsState(incomeValue?.toIntOrNull() ?: 30000)
    val selectedPurpose: String by annualIncomeViewModel.selectedPurpose.observeAsState(loanPurpose)
    val purposeError by annualIncomeViewModel.purposeError.observeAsState(null)
    val sliderPosition: Float by annualIncomeViewModel.sliderPosition.observeAsState(income.toFloat())
    val updatedIncome by annualIncomeViewModel.updatedIncome.collectAsState()
    val incomeFocus = remember { FocusRequester() }
    val purposeFocus = remember { FocusRequester() }
//    val (purposeFocus) = FocusRequester.createRefs()
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
    val minRange = 30000f
    val maxRange = 2000000f
    val stepSize = 5000f
    val numberOfSteps = ((maxRange - minRange) / stepSize).toInt() - 1

    BackHandler { navigateToPersonaLoanScreen(navController, fromFlow) }

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
//                if (upDated && !shownMsg) {
//                    CommonMethods().toastMessage(context = context, toastMsg = "Profile updated")
//                    registerViewModel.updateShownMsg(true)
//                    navigateApplyByCategoryScreen(navController)
// //                    navController.popBackStack()
//                }
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
                    CustomModalBottomSheet(
                        bottomSheetState = bottomSheet2Value,
                        sheetContent = {
                            FullAddress(
                                id = "2",
                                pinCode = pinCode2, pinCodeFocus = pinCode2FocusRequester,
                                pinCodeError = pinCode2Error,
                                area = area2, areaFocus = area2FocusRequester,
                                areaError = area2Error,
                                bottomSheetValue = bottomSheet2Value, scope = scope,
                                state = state2, stateFocus = state2FocusRequester,
                                stateError = state2Error ?: "",
                                town = town2, townFocus = town2FocusRequester,
                                townError = town2Error,
                                city = city2, cityFocus = city2FocusRequester,
                                cityError = city2Error,
                                selectedCity = selectedCity2.ifEmpty { city2 ?: "" },
                                pinCodeResponse = pinCode2Response,
                                registerViewModel = registerViewModel, context = context,
                                onItemSelected = onCity2Selected,
                                headerText = stringResource(R.string.permanent_address)
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
                                annualIncomeViewModel.onNextClicked(context, selectedPurpose, income, purposeFocus)
                                val employmentTypeSmallCase =
                                    employeeType?.lowercase(Locale.ROOT)
                                val genderFormat = when (genderSelectedText.lowercase(Locale.ROOT)) {
                                    "others" -> "transgender"
                                    else -> genderSelectedText.lowercase(Locale.ROOT).ifEmpty { gender ?: "" }
                                }
//                                officialAddressField?.let { officialAddress ->
//                                    permanentAddressField?.let { permanentAddress ->
//                                        registerViewModel.onClickUpdateProfile(
//                                            navController = navController,
//                                            checkboxValue = true,
//                                            context = context,
//                                            officialAddressField = officialAddress,
//                                            permanentAddressField = permanentAddress,
//                                            profile = Profile(
//                                                firstName = firstName?.trim(),
//                                                lastName = lastName.trim(),
//                                                email = personalEmailId?.trim(),
//                                                officialEmail = officialEmailId?.trim(),
//                                                dob = dob?.trim(),
//                                                gender = genderFormat,
//                                                panNumber = panNumber?.trim(),
//                                                employmentType = if (employmentTypeSmallCase.equals("selfEmployed", true))
//                                                    "selfEmployed"
//                                                else employmentTypeSmallCase,
//                                                companyName = companyName?.trim(),
//                                                income = income.toString(),
//                                                udyamNumber = udyamNumber?.trim(),
//                                                address1 = "${officialAddress.area?.trim()},${officialAddress.town?.trim()}",
//                                                address2 = "${permanentAddress.area?.trim().orEmpty()},${permanentAddress.town?.trim().orEmpty()}"
//                                                    .trim(',')
//                                                    .takeIf { it.isNotBlank() },
//                                                pincode1 = officialAddress.pincode?.trim(),
//                                                pincode2 = permanentAddress.pincode?.trim(),
//                                                city1 = selectedCity1.ifEmpty { officialAddress.city?.trim() ?: "" },
//                                                city2 = selectedCity2.ifEmpty { permanentAddress.city?.trim() },
//                                                state1 = officialAddress.state?.trim(),
//                                                state2 = permanentAddress.state?.trim(),
//                                            ),
//                                        )
//                                    }
//                                }
                            },
                            backgroundColor = appWhite
                        ) {
                            RegisterText(
                                text = stringResource(id = R.string.please_provide_more_info),
                                style = normal20Text700,
                                top = 20.dp,
                                bottom = 20.dp,
                                start = 20.dp,
                                end = 20.dp
                            )

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.first_name),
                                value = firstName,
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
                                onValueChange = { registerViewModel.onLastNameChanged(it, context) },
                                error = lastNameError,
                                focusRequester = lastNameFocusRequester,
                                leadingImage = painterResource(R.drawable.person_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Sentences,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.personal_email_id),
                                value = personalEmailId,
                                readOnly = true,
                                onValueChange = { registerViewModel.onPersonalEmailIdChanged(it, context) },
                                error = personalEmailIdError,
                                focusRequester = personalEmailIdFocusRequester,
                                leadingImage = painterResource(R.drawable.email_icon),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                )
                            )
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.official_email_id),
                                value = officialEmailId,
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

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.phone_number),
                                value = phoneNumber,
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
                                dob = dob ?: " ",
                                dobFocus = dobFocusRequester,
                                registerViewModel = registerViewModel,
                                dobError = dobError ?: "",
                                context = context,
                                readOnly = true
                            )
                            InputField(
                                inputText = "",
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
                                onValueChange = {
                                    registerViewModel.onGenderChanged(it, context)
                                },
                                leadingImage = painterResource(R.drawable.gender_icon),
                                radioList = genderList,
                                selectedRadio = genderSelectedText,
                                onRadioSelected = { selectedText ->
                                    genderSelectedText = selectedText
                                    registerViewModel.onGenderChanged(selectedText, context)
                                }
                            )

                            BasicDetailsInputField(
                                label = stringResource(id = R.string.pan),
                                value = panNumber,
                                readOnly = true,
                                onValueChange = { registerViewModel.onPanNumberChanged(it, context) },
                                error = panError,
                                focusRequester = panFocusRequester,
                                leadingImage = painterResource(R.drawable.pan_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            InputField(
                                inputText = "",
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
                                onValueChange = {
                                    registerViewModel.onEmployeeTypeChanged(it, context)
                                },
                                leadingImage = painterResource(R.drawable.employee_type_icon),
                                radioList = employeeTypeList,
                                selectedRadio = employmentSelectedText,
                                onRadioSelected = { selectedEmployment ->
                                    employmentSelectedText = selectedEmployment
                                    registerViewModel.onEmployeeTypeChanged(
                                        selectedEmployment,
                                        context
                                    )
                                }
                            )
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.company_name),
                                value = companyName,
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
                                        starTop = 0.dp
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 5.dp, end = 10.dp, top = 3.dp)
                                            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                    ) {
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
                                                    inactiveTickColor = grayD9
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
                                                text = stringResource(id = R.string.tewnty_k),
                                                value = stringResource(id = R.string.tewnty_lakh),
                                                start = 35.dp,
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
                                                        context = context,
                                                        newText.text
                                                    )
//                                                    if (newText.text.replace("₹", "")
//                                                            .replace(",", "")
//                                                            .isEmpty()
//                                                    ) {
//                                                        annualIncomeViewModel.updateGeneralError(
//                                                            null
//                                                        )
//                                                    }
                                                },
                                                onLostFocusValidation = {
//                                                    newText ->
//                                                    annualIncomeViewModel.validateInputText(
//                                                        context = context, newText.text
//                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                                                    .focusRequester(incomeFocus),
                                                readOnly = true
                                            )
                                        }
                                    }
                                }
                            }

                            DropDownField(
                                start = 37.dp,
                                end = 3.dp,
                                top = 10.dp,
                                selectedText = selectedPurpose,
                                hint = stringResource(id = R.string.purpose),
                                focus = purposeFocus,
                                error = purposeError,
                                expand = purposeExpanded,
                                setExpand = { purposeExpanded = it },
                                itemList = purpose,
                                onDismiss = onPurposeDismiss,
                                onItemSelected = onPurposeSelected,
                                modifier = Modifier.focusRequester(purposeFocus)
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
            navController = navController,
            showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen,
            showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen,
            unAuthorizedUser = unAuthorizedUser
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BasicDetailsScreenPreview() {
    BasicDetailsScreen(rememberNavController(), "Personal", "")
}
