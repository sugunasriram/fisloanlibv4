package com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fiscode.components.InputField
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstInvoiceLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstNumberVerifyScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOtpResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.disableColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightishGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal24Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.gstLoan.GstDetailViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GstDetailsScreen(navController: NavHostController, fromFlow: String) {

    BackHandler {
        navigateToGstInvoiceLoanScreen(navController = navController, fromFlow = fromFlow)
    }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val gstDetailViewModel: GstDetailViewModel = viewModel()
    val gstNumber: String by gstDetailViewModel.gstNumber.observeAsState("")
    val gstUserName: String by gstDetailViewModel.gstUserName.observeAsState("")
    val gstNameError by gstDetailViewModel.gstNameError.observeAsState("")
    val gstNumberError by gstDetailViewModel.gstNumberError.observeAsState("")
    val shouldShowKeyboard by gstDetailViewModel.shouldShowKeyboard.observeAsState(false)
    val navigationToSignIn by gstDetailViewModel.navigationToSignIn.collectAsState()

    val generatingOtp by gstDetailViewModel.generatingOtp.collectAsState()
    val generatedOtp by gstDetailViewModel.generatedOtp.collectAsState()
    val cygnetGenerateOtpResponse by gstDetailViewModel.cygnetGenerateOtpResponse.collectAsState()

    val showInternetScreen by gstDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by gstDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by gstDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by gstDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by gstDetailViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by gstDetailViewModel.middleLoan.observeAsState(false)
    val errorMessage by gstDetailViewModel.errorMessage.collectAsState()


    val (focusGstName) = FocusRequester.createRefs()
    val (focusGstNumber) = FocusRequester.createRefs()

    LaunchedEffect(shouldShowKeyboard) {
        if (shouldShowKeyboard) {
            keyboardController?.show()
            gstDetailViewModel.resetKeyboardRequest()
        }
    }
    when {
        navigationToSignIn -> navigateSignInPage (navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            GstDetailsScreenView(
                generatedOtp = generatedOtp, navController = navController, gstNumber = gstNumber,
                focusGstName = focusGstName, focusGstNumber = focusGstNumber, fromFlow = fromFlow,
                generatingOtp = generatingOtp, gstUserName = gstUserName, context = context,
                cygnetGenerateOtpResponse = cygnetGenerateOtpResponse, gstNameError = gstNameError,
                gstNumberError = gstNumberError, gstDetailViewModel = gstDetailViewModel,
            )
        }
    }
}

@Composable
fun GstDetailsScreenView(
    generatedOtp: Boolean, navController: NavHostController, focusGstName: FocusRequester,
    focusGstNumber: FocusRequester, generatingOtp: Boolean, fromFlow: String, gstNumber: String,
    cygnetGenerateOtpResponse: GstOtpResponse?, gstUserName: String, context: Context,
    gstNameError: String?, gstNumberError: String?, gstDetailViewModel: GstDetailViewModel
) {
    if (generatingOtp) {
        CenterProgress()
    } else {
        if (generatedOtp) {
            cygnetGenerateOtpResponse?.data?.id?.let { id ->
                navigateToGstNumberVerifyScreen(
                    navController = navController, mobileNumber = id, fromFlow = fromFlow
                )
            }
        } else {
            ScreenWithHamburger(
                isSelfScrollable = false, navController = navController
            ) {
                GstDetailsContent(
                    gstNumber = gstNumber, gstUserName = gstUserName, context = context,
                    gstNameError = gstNameError, gstNumberError = gstNumberError,
                    focusGstName = focusGstName, focusGstNumber = focusGstNumber,
                    gstDetailViewModel = gstDetailViewModel, fromFlow = fromFlow,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun GstDetailsContent(
    gstNumber: String, gstUserName: String, gstNameError: String?, gstNumberError: String?,
    focusGstName: FocusRequester, focusGstNumber: FocusRequester, context: Context,
    gstDetailViewModel: GstDetailViewModel, navController: NavHostController, fromFlow: String
) {
    CenteredMoneyImage(imageSize = 120.dp, top = 20.dp)
    RegisterText(
        text = stringResource(id = R.string.gst_details), textColor = appBlueTitle,
        style = normal32Text700
    )
    GstUserNameInputField(
        gstUserName = gstUserName, gstNameError = gstNameError, focusGstName = focusGstName,
        focusGstNumber = focusGstNumber, gstDetailViewModel = gstDetailViewModel
    )
    GstNumberInputField(
        gstNumber = gstNumber, gstNumberError = gstNumberError, focusGstNumber = focusGstNumber,
        gstDetailViewModel = gstDetailViewModel
    )
    RegisterText(
        text = stringResource(id = R.string.sample_gst), textColor = lightishGrayColor,
        start = 40.dp, end = 40.dp, style = normal20Text500
    )
    VerifyButton(
        gstNumber = gstNumber, gstUserName = gstUserName, gstDetailViewModel = gstDetailViewModel,
        context = context, navController = navController, focusGstName = focusGstName,
        focusGstNumber = focusGstNumber, fromFlow = fromFlow
    )
}

@Composable
fun GstUserNameInputField(
    gstUserName: String, gstNameError: String?, focusGstName: FocusRequester,
    focusGstNumber: FocusRequester, gstDetailViewModel: GstDetailViewModel
) {
    RegisterText(
        text = stringResource(id = R.string.gst_username), textColor = appBlueTitle,
        top = 60.dp, style = normal24Text700
    )
    RegisterText(
        text = stringResource(id = R.string.link_gst_account),
        textColor = appBlueTitle, start = 40.dp, end = 40.dp, style = normal14Text400
    )
    InputField(
        top = 10.dp, inputText = TextFieldValue(gstUserName), modifier = Modifier.focusRequester(focusGstName),
        hint = stringResource(id = R.string.username_gst),
        hintAlign = TextAlign.Center,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onNext = { focusGstNumber.requestFocus() }),
        onValueChange = {newValue->
            gstDetailViewModel.onGstUserNameChanged(newValue.text)
            gstDetailViewModel.updateGstNameError(null)
        },
        error = gstNameError
    )
}

@Composable
fun GstNumberInputField(
    gstNumber: String, gstNumberError: String?, focusGstNumber: FocusRequester,
    gstDetailViewModel: GstDetailViewModel
) {
    RegisterText(
        text = stringResource(id = R.string.enter_gstin), textColor = appBlueTitle, top = 60.dp,
        style = normal24Text700
    )
    RegisterText(
        text = stringResource(id = R.string.business_gstin),
        textColor = appBlueTitle, start = 40.dp, end = 40.dp, style = normal14Text400
    )
    InputField(
        top = 10.dp, inputText = TextFieldValue( gstNumber), hint = stringResource(id = R.string.gst_digit),
        hintAlign = TextAlign.Center,
        modifier = Modifier.focusRequester(focusGstNumber),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters, imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        onValueChange = {newValue->
            gstDetailViewModel.onGstNumberChanged(newValue.text)
            gstDetailViewModel.updateGstNumberError(null)
        },
        error = gstNumberError
    )
}

@Composable
fun VerifyButton(
    gstNumber: String, gstUserName: String, gstDetailViewModel: GstDetailViewModel,
    focusGstName: FocusRequester, focusGstNumber: FocusRequester, fromFlow: String,
    context: Context, navController: NavHostController
) {
    val buttonBackGroundColor =
        if (gstNumber.isNotEmpty() && gstUserName.isNotEmpty()) appOrange else disableColor
    CurvedPrimaryButtonFull(
        text = stringResource(id = R.string.verify_gst),
        backgroundColor = buttonBackGroundColor,
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 50.dp)
    ) {
        gstDetailViewModel.verifyGstValidation(
            gstNumber = gstNumber, gstUserName = gstUserName, navController = navController,
            context = context, focusGstName = focusGstName, focusGstNumber = focusGstNumber,
            fromFlow = fromFlow
        )
    }
}


