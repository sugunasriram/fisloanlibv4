package com.github.sugunasriram.fisloanlibv4.fis_code.views.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DropDownField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.InputField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OutLineTextFieldHeader
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SideMenuProfileCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.PincodeModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun PreviewUpdateProfileScreen() {
    val navController = rememberNavController()
    UpdateProfileScreen(navController = navController, "Side Bar ")
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun UpdateProfileScreen(navController: NavHostController, fromFlow: String) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val initialIncome = "500000"

    val registerViewModel: RegisterViewModel = viewModel()

    val firstName by registerViewModel.firstName.observeAsState("")
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

    val income by registerViewModel.income.observeAsState(initialIncome)

    val udyamNumber by registerViewModel.udyamNumber.observeAsState(null)
    val udyamNumberError by registerViewModel.udyamNumberError.observeAsState(null)

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

    val officialAddress by registerViewModel.officialAddress.observeAsState("")
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

    val permanentAddress: String? by registerViewModel.permanentAddress.observeAsState("")
    val permanentAddressField by registerViewModel.permanentAddressField.observeAsState()
    val permanentAddressError by registerViewModel.permanentAddressError.observeAsState(null)

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
    val udyamNumberFocusRequester = remember { FocusRequester() }

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

    val checkboxValue: Boolean by registerViewModel.checkBox.observeAsState(false)
    val pinCode1Response by registerViewModel.pinCode1Response.collectAsState()
    val pinCode2Response by registerViewModel.pinCode2Response.collectAsState()

    val showInternetScreen by registerViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by registerViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by registerViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by registerViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by registerViewModel.unAuthorizedUser.observeAsState(false)
    val isProfileValidated by registerViewModel.isProfileValidated.observeAsState(false)

    val inProgress by registerViewModel.inProgress.collectAsState()
    val isCompleted by registerViewModel.isCompleted.collectAsState()
    val isUpdating by registerViewModel.isUpdating.collectAsState()
    val upDated by registerViewModel.upDated.collectAsState()
    val shownMsg by registerViewModel.shownMsg.collectAsState()
    val shouldShowKeyboard by registerViewModel.shouldShowKeyboard.observeAsState(false)

    LaunchedEffect(shouldShowKeyboard) {
        if (shouldShowKeyboard) {
            keyboardController?.show()
            registerViewModel.resetKeyboardRequest()
        }
    }
//    val tapfocusManager = LocalFocusManager.current

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
    var showError by remember { mutableStateOf(false) }

    if (!showInternetScreen && !showTimeOutScreen && !showServerIssueScreen && !unexpectedErrorScreen) {
        if (inProgress) {
            CenterProgress()
        } else {
            if (!isCompleted) {
                registerViewModel.getUserDetail(context, navController)
            } else {
                if (upDated && !shownMsg) {
                    CommonMethods().toastMessage(context = context, toastMsg = "Profile updated")
                    registerViewModel.updateShownMsg(true)
                    navigateApplyByCategoryScreen(navController)
//                    navController.popBackStack()
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
                            contentStart = 0.dp, contentEnd = 0.dp,
                            showTopBar = false, showBackButton = false,
                            showBottom = true,
                            showCheckBox = true,
                            checkboxState = checkboxValue,
                            onCheckBoxChange = { isChecked ->
                                registerViewModel.onCheckChanged(isChecked)
                                showError = false
                            },
                            checkBoxText = stringResource(R.string.please_accept_our_agent_may_call_u),
                            showErrorMsg = showError,
                            errorMsg = stringResource(R.string.please_accept_our_agent_may_call_u),
                            showSingleButton = true,
                            primaryButtonText = stringResource(R.string.next),
                            onPrimaryButtonClick = {
                                if (!checkboxValue) {
                                    showError = true
                                } else {
                                    showError = false
                                    val employmentTypeSmallCase =
                                        employeeType?.lowercase(Locale.ROOT)
                                    val genderFormat = when (genderSelectedText.lowercase(Locale.ROOT)) {
                                        "others" -> "transgender"
                                        else -> genderSelectedText.lowercase(Locale.ROOT).ifEmpty { gender ?: "" }
                                    }
                                    officialAddressField?.let { officialAddress ->
                                        permanentAddressField?.let { permanentAddress ->
                                            registerViewModel.onClickUpdateProfile(
                                                navController = navController,
                                                checkboxValue = checkboxValue,
                                                context = context,
                                                officialAddressField = officialAddress,
                                                permanentAddressField = permanentAddress,
                                                profile = Profile(
                                                    firstName = firstName?.trim(),
                                                    lastName = lastName?.trim(),
                                                    email = personalEmailId?.trim(),
                                                    officialEmail = officialEmailId?.trim(),
                                                    dob = dob?.trim(),
                                                    gender = genderFormat,
                                                    panNumber = panNumber?.trim(),
                                                    employmentType = if (employmentTypeSmallCase.equals("selfEmployed", true)) {
                                                        "selfEmployed"
                                                    } else {
                                                        employmentTypeSmallCase
                                                    },
                                                    companyName = companyName?.trim(),
                                                    income = income?.trim().toString(),
                                                    udyamNumber = udyamNumber,
//                                                    address1 = "${officialAddress.area?.trim()},${officialAddress.town?.trim()}",
                                                    address1 = "${officialAddress.area?.trim().orEmpty()},${officialAddress.town?.trim().orEmpty()}"
                                                        .trim(',')
                                                        .takeIf { it.isNotBlank() },
                                                    address2 = "${permanentAddress.area?.trim().orEmpty()},${permanentAddress.town?.trim().orEmpty()}"
                                                        .trim(',')
                                                        .takeIf { it.isNotBlank() },
//                                                    address2 ="${permanentAddress.area?.trim()},${permanentAddress.town?.trim()}",
                                                    pincode1 = officialAddress.pincode?.trim(),
                                                    pincode2 = permanentAddress.pincode?.trim(),
                                                    city1 = selectedCity1.ifEmpty { officialAddress.city?.trim() },
                                                    city2 = selectedCity2.ifEmpty { permanentAddress.city?.trim() },
                                                    state1 = officialAddress.state?.trim(),
                                                    state2 = permanentAddress.state?.trim()
                                                )
                                            )
                                        }
                                    }
//                                    }
                                }
                            },
                            backgroundColor = appWhite
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(appOrange)
                            ) {
                                SideMenuProfileCard(
                                    userName = "",
                                    contact = "",
                                    displayUserInfo = false,
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.first_name),
                                value = firstName,
                                readOnly = true,
                                showTrailingIcon = true,
                                onValueChange = { registerViewModel.onFirstNameChanged(it, context) },
                                error = firstNameError,
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
                                showTrailingIcon = true,
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
                                showStar = false,
                                readOnly = true,
                                showTrailingIcon = true,
                                onValueChange = {
                                    registerViewModel.onPersonalEmailIdChanged(it, context)
                                },
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
                                showStar = false,
                                readOnly = true,
                                showTrailingIcon = true,
                                onValueChange = {
                                    registerViewModel.onOfficialEmailIdChanged(
                                        it,
                                        context
                                    )
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
                                enable = false,
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
                                context = context
                            )
                            InputField(
                                inputText = "",
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
                                showTrailingIcon = true,
                                onValueChange = {
                                    registerViewModel.onPanNumberChanged(
                                        it,
                                        context
                                    )
                                },
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
                                topText = stringResource(id = R.string.employment_type),
                                showRadio = true,
                                showStar = true,
                                top = 0.dp,
                                start = 5.dp,
                                end = 10.dp,
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
                                showTrailingIcon = true,
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
                            BasicDetailsInputField(
                                label = stringResource(id = R.string.udyam_number),
                                value = udyamNumber,
                                readOnly = true,
                                showTrailingIcon = true,
                                onValueChange = {
                                    registerViewModel.onUdyamNumberChanged(
                                        it,
                                        context
                                    )
                                },
                                error = udyamNumberError,
                                showStar = false,
                                focusRequester = udyamNumberFocusRequester,
                                leadingImage = painterResource(R.drawable.udyam_number_icon),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
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
                                address2Error = permanentAddressError
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddressFields(
    bottomSheet1Value: ModalBottomSheetState,
    bottomSheet2Value: ModalBottomSheetState,
    address1: String?,
    address1Focus: FocusRequester,
    address1Error: String?,
    address2: String?,
    address2Focus: FocusRequester,
    address2Error: String?,
    scope: CoroutineScope,
    readOnly: Boolean = false
) {
    InputField(
        inputText = address1, topText = stringResource(id = R.string.official_address),
        boxText = address1.toString(), onValueChange = {},
        top = 0.dp, start = 5.dp, end = 10.dp, showOnlyTextField = false,
        showBox = true,
        error = address1Error,
        readOnly = readOnly,
        leadingImage = painterResource(R.drawable.location_icon),
        boxClick = { scope.launch { bottomSheet1Value.show() } }
    )

    InputField(
        inputText = address2, topText = stringResource(id = R.string.permanent_address),
        top = 0.dp, start = 5.dp, end = 10.dp, showOnlyTextField = false, error = address2Error,
        showOptional = true, showBox = true, boxText = address2.toString(), onValueChange = {},
        leadingImage = painterResource(R.drawable.location_icon),
        readOnly = readOnly,
        boxClick = {
            scope.launch {
                bottomSheet1Value.hide()
                bottomSheet2Value.show()
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FullAddress(
    id: String = "1",
    pinCodeError: String?,
    areaError: String?,
    townError: String?,
    cityError: String?,
    bottomSheetValue: ModalBottomSheetState,
    pinCode: String?,
    scope: CoroutineScope,
    state: String?,
    area: String?,
    town: String?,
    city: String?,
    pinCodeFocus: FocusRequester,
    areaFocus: FocusRequester,
    townFocus: FocusRequester,
    cityFocus: FocusRequester,
    context: Context,
    registerViewModel: RegisterViewModel,
    pinCodeResponse: PincodeModel?,
    selectedCity: String?,
    stateFocus: FocusRequester,
    stateError: String,
    headerText: String = stringResource(R.string.official_address),
    onItemSelected: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CustomImage(
            boxAlignment = Alignment.TopEnd,
            imageHeight = 20.dp,
            clickImage = true,
            onImageClick = { scope.launch { bottomSheetValue.hide() } },
            imageWidth = 20.dp,
            end = 20.dp,
            top = 15.dp
        )
        RegisterText(
            text = headerText,
            style = normal20Text700,
            bottom = 20.dp,
            textColor = appBlack
        )
        AreaField(
            id = id,
            area = area,
            areaError = areaError,
            areaFocus = areaFocus,
            registerViewModel = registerViewModel,
            context = context
        )
        PinCodeAndTownFields(
            id = id,
            pinCode = pinCode, pinCodeFocus = pinCodeFocus, registerViewModel = registerViewModel,
            pinCodeResponse = pinCodeResponse, areaFocus = areaFocus, pinCodeError = pinCodeError,
            context = context,
            state = state, stateFocus = stateFocus, stateError = stateError,
            selectedCity = selectedCity,
            cityFocus = cityFocus, cityError = cityError ?: "",
            town = town, townError = townError, townFocus = townFocus,
            onItemSelected = onItemSelected
        )

        CurvedPrimaryButton(text = stringResource(R.string.update)) {
            if (id == "1") {
                registerViewModel.address1Validation(
                    scope = scope,
                    bottomSheetStateValue = bottomSheetValue,
                    pinCode = pinCode ?: "",
                    area = area ?: "",
                    town = town ?: "",
                    city = selectedCity.takeUnless { it.isNullOrEmpty() } ?: city.orEmpty(),
                    context = context,
                    state = state ?: ""
                )
            } else {
                registerViewModel.address2Validation(
                    scope = scope,
                    bottomSheetStateValue = bottomSheetValue,
                    pinCode = pinCode ?: "",
                    area = area ?: "",
                    town = town ?: "",
                    city = selectedCity.takeUnless { it.isNullOrEmpty() } ?: city.orEmpty(),
                    context = context,
                    state = state ?: ""
                )
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun PinCodeAndTownFields(
    id: String = "1",
    pinCode: String?,
    pinCodeFocus: FocusRequester,
    registerViewModel: RegisterViewModel,
    pinCodeResponse: PincodeModel?,
    state: String?,
    stateFocus: FocusRequester,
    stateError: String?,
    town: String?,
    townError: String?,
    townFocus: FocusRequester,
    areaFocus: FocusRequester,
    pinCodeError: String?,
    selectedCity: String?,
    cityFocus: FocusRequester,
    cityError: String,
    onItemSelected: (String) -> Unit,
    context: Context
) {
    var expand by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val nearCityResponse by if (id == "1") {
        registerViewModel.pinCode1Response.collectAsState()
    } else {
        registerViewModel.pinCode2Response.collectAsState()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(0.4f)) {
            OutLineTextFieldHeader(
                topTextStart = 0.dp,
                topTextTop = 0.dp,
                topTextBottom = 0.dp,
                starTop = 0.dp,
                topText = stringResource(id = R.string.pincode),
                showStar = true,
                showOptional = false
            )

            OutlinedTextField(
                value = pinCode ?: "",
                singleLine = true,
                onValueChange = { input ->
                    if (input.length <= 6 && input.all { it.isDigit() }) {
                        if (id == "1") {
                            registerViewModel.onPinCode1Changed(input, context)
                        } else {
                            registerViewModel.onPinCode2Changed(input, context)
                        }
                        if (input.length == 6) {
                            keyboardController?.hide()
                        }
                    }
                },
                textStyle = normal16Text500.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .focusRequester(pinCodeFocus)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = appTheme,
                    unfocusedBorderColor = backgroundOrange,
                    cursorColor = cursorColor,
                    errorBorderColor = errorRed
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )
        }

        Column(modifier = Modifier.weight(0.6f)) {
            OutLineTextFieldHeader(
                topTextStart = 0.dp,
                topTextTop = 0.dp,
                topTextBottom = 0.dp,
                starTop = 0.dp,
                topText = stringResource(id = R.string.state),
                showStar = true,
                showOptional = false
            )

            OutlinedTextField(
                value = state ?: "",
                readOnly = true,
                textStyle = normal16Text500.copy(textAlign = TextAlign.Center),
                onValueChange = {
                    if (id == "1") {
                        registerViewModel.onState1Changed(it, context)
                    } else {
                        registerViewModel.onState2Changed(it, context)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = appTheme,
                    unfocusedBorderColor = backgroundOrange,
                    cursorColor = cursorColor,
                    errorBorderColor = errorRed
                )
            )
        }
    }
    if (pinCodeError?.isNotEmpty() == true) {
        StartingText(
            text = pinCodeError,
            textColor = errorRed,
            style = normal12Text400,
            start = 16.dp
        )
    }
    BasicDetailsInputField(
        label = stringResource(id = R.string.locality),
        value = town,
        onValueChange = {
            if (id == "1") {
                registerViewModel.onTown1Changed(it, context)
            } else {
                registerViewModel.onTown2Changed(it, context)
            }
        },
        error = townError,
        focusRequester = townFocus,
        showLeadingImage = false,
        topTextStart = 15.dp,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        )
    )
    Column {
        OutLineTextFieldHeader(
            topTextStart = 15.dp,
            topTextTop = 0.dp,
            topTextBottom = 0.dp,
            starTop = 0.dp,
            topText = stringResource(id = R.string.city),
            showStar = true,
            showOptional = false
        )

        DropDownField(
            start = 0.dp,
            end = 0.dp,
            top = 0.dp,
            bottom = 10.dp,
            selectedText = selectedCity.toString(),
            hint = stringResource(id = R.string.purpose),
            focus = cityFocus,
            expand = expand,
            setExpand = {
                expand = it
                if (nearCityResponse?.cities.isNullOrEmpty() && pinCode != null) {
                    if (id == "1") {
                        registerViewModel.onPinCode1Changed(pinCode, context)
                    } else {
                        registerViewModel.onPinCode2Changed(pinCode, context)
                    }
                }
            },

            itemList = nearCityResponse?.cities ?: emptyList(),
            onDismiss = { expand = false },
            modifier = Modifier.focusRequester(cityFocus),
            onItemSelected = onItemSelected
        )
    }
}

@Composable
fun AreaField(
    id: String = "1",
    area: String?,
    areaError: String?,
    registerViewModel: RegisterViewModel = RegisterViewModel(),
    context: Context,
    areaFocus: FocusRequester
) {
    BasicDetailsInputField(
        label = stringResource(id = R.string.house_number),
        value = area,
        onValueChange = {
            if (id == "1") {
                registerViewModel.onArea1Changed(it, context)
            } else {
                registerViewModel.onArea2Changed(it, context)
            }
        },
        error = areaError,
        focusRequester = areaFocus,
        showLeadingImage = false,
        topTextStart = 15.dp,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
fun BasicDetailsInputField(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    showStar: Boolean = true,
    enable: Boolean = true,
    error: String?,
    readOnly: Boolean = false,
    showLeadingImage: Boolean = true,
    focusRequester: FocusRequester,
    topTextStart: Dp = 40.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingImage: Painter = painterResource(R.drawable.update_profile),
    leadingIcon: @Composable (() -> Unit)? = null,
    showTrailingIcon: Boolean = false
) {
    val context = LocalContext.current
    var isEditable by remember { mutableStateOf(!readOnly) }
    val keyboardController = LocalSoftwareKeyboardController.current

    InputField(
        inputText = value,
        topText = label,
        top = 0.dp,
        start = 0.dp,
        end = 0.dp,
        enable = enable,
        showTopText = true,
        topTextStart = topTextStart,
        showStar = showStar,
        error = error,
        showOnlyTextField = false,
        showLeadingImage = showLeadingImage,
        readOnly = !isEditable,
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        leadingImage = leadingImage,
        leadingIcon = leadingIcon,
        trailingIcon = if (showTrailingIcon) {
            {
                Icon(
                    painter = painterResource(id = if (isEditable) R.drawable.done_icon else R.drawable.edit_icon),
                    contentDescription = "",
                    tint = appBlack,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            if (isEditable) {
                                if (!error.isNullOrBlank()) {
                                    CommonMethods().toastMessage(
                                        context = context,
                                        toastMsg = error
                                    )
                                } else {
                                    isEditable = false
                                    keyboardController?.hide()
                                }
                            } else {
                                isEditable = true
                                focusRequester.requestFocus()
                            }
                        }
                )
            }
        } else {
            null
        }
    )
}

@Composable
fun DatePickerField(
    dob: String,
    dobFocus: FocusRequester,
    dobError: String,
    registerViewModel: RegisterViewModel,
    context: Context,
    readOnly: Boolean = false
) {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val (selectedDate, setSelectedDate) = remember { mutableStateOf(dob) }

    // To check person's age should be greater than 18
    val maxDate = remember {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        calendar.timeInMillis
    }

    // Initialize Calendar instance
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val newDate = dateFormat.format(calendar.time)
            setSelectedDate(newDate)
            registerViewModel.onDobChanged(newDate, context)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.maxDate = maxDate
    }

    InputField(
        readOnly = readOnly,
        inputText = dob, error = dobError,
        modifier = Modifier
            .clickable { datePickerDialog.show() }
            .focusRequester(dobFocus),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        showStar = true,
        topText = stringResource(id = R.string.dob),
        top = 0.dp,
        start = 0.dp,
        end = 0.dp,
        showTopText = true,
        showOnlyTextField = false,
        onValueChange = { registerViewModel.onDobChanged(it, context) },
        leadingImage = painterResource(R.drawable.dob_icon),
        trailingIcon = {
            if (!readOnly) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(id = R.string.select_date),
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            }
        }
    )
}
