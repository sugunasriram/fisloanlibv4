package com.github.sugunasriram.fisloanlibv4.fiscode.views.auth

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToOtpScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToTermsConditionsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.slideActiveColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.textBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.SignInViewModel

@Composable
fun SignInScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val keyboardController = LocalSoftwareKeyboardController.current

    val signInViewModel: SignInViewModel = viewModel()

    val mobileNumber by signInViewModel.mobileNumber.observeAsState()
    val mobileNumberError by signInViewModel.mobileNumberError.observeAsState(null)
    val (focusPhNumber) = FocusRequester.createRefs()

    val isLoginSuccess = signInViewModel.isLoginSuccess.collectAsState()
    val isLoginInProgress = signInViewModel.isLoginInProgress.collectAsState()
    val generatedOtpData = signInViewModel.generatedOtpData.collectAsState()

    val showInternetScreen by signInViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by signInViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by signInViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by signInViewModel.unexpectedError.observeAsState(false)
    val shouldShowKeyboard by signInViewModel.shouldShowKeyboard.observeAsState(false)
    val checkboxState by signInViewModel.checkBoxChecked.observeAsState(false)
    val isAgreeTermsAndConditions by signInViewModel.isAgreeTermsAndConditions.observeAsState(true)

    var showExitDialog by remember { mutableStateOf(false) }
//    var checkboxState by remember { mutableStateOf(false) }
//    var isAgreeTermsAndConditions by remember { mutableStateOf(true) }

    LaunchedEffect(shouldShowKeyboard) {
        if (shouldShowKeyboard) {
            keyboardController?.show()
            signInViewModel.resetKeyboardRequest()
        }
    }

    BackHandler {
        if (navController.currentBackStackEntry?.destination?.route == "sign_in_screen") {
            showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    if (!showInternetScreen && !showTimeOutScreen && !showServerIssueScreen && !unexpectedErrorScreen) {
        if (isLoginInProgress.value) {
            CenterProgress()
        } else {
            if (isLoginSuccess.value) {
                mobileNumber?.let {
                    navigateToOtpScreen(
                        navController,
                        it,
                        generatedOtpData.value?.data?.orderId.toString()
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize().verticalScroll(rememberScrollState())
                ) {
                    CenteredMoneyImage(
                        image = R.drawable.sign_in_screen_image,
                        imageSize = 200.dp,
                        top = 100.dp
                    )

                    MultiStyleText("Sign", appBlack, "in", appOrange)
                    PhoneNumberField(
                        mobileNumber = mobileNumber ?: "",
                        focusPhNumber = focusPhNumber,
                        mobileNumberError = mobileNumberError,
                        signInViewModel = signInViewModel,
                        context = context
                    )
                    CheckBoxText(
                        textColor = appOrange,
                        style = normal16Text400,
                        boxState = checkboxState,
                        text = stringResource(id = R.string.i_agree_to_buyer_app_terms_and_conditions),
                        bottom = 0.dp,
                        start = 0.dp,
                        end = 0.dp,
                        top = 50.dp
                    ) {
                            isChecked ->
                        signInViewModel.onCheckBoxChanges(isChecked)
                        signInViewModel.onAgreeTermsAndConditions(isChecked)
                    }

                    // Show Terms and Conditions as clickable text
                    Text(
                        text = stringResource(R.string.terms_and_conditions),
                        color = appOrange,
                        style = normal16Text700.copy(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 8.dp)
                            .clickable {
                                navigateToTermsConditionsScreen(navController)
                            }
                    )

                    if (!isAgreeTermsAndConditions) {
                        RegisterText(
                            text = stringResource(R.string.please_agree_buyer_App_terms),
                            textColor = errorRed,
                            boxAlign = Alignment.Center,
                            style = normal12Text400
                        )
                    }
                    CurvedPrimaryButton(
                        text = stringResource(id = R.string.get_otp),
                        modifier = Modifier.padding(top = 30.dp)
                    ) {
//                            if(checkboxState){
//                                isAgreeTermsAndConditions=true
                        mobileNumber?.let {
                            signInViewModel.signInValidation(
                                navController = navController,
                                mobileNumber = it,
                                mobileNumberFocus = focusPhNumber,
                                checkBoxState = checkboxState,
                                context = context
                            )
                        }
//                            }else{
//                                isAgreeTermsAndConditions=false
//                            }
                    }
                }
            }

            if (showExitDialog) {
                AlertDialog(
                    onDismissRequest = { showExitDialog = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showExitDialog = false; activity.finish()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = appOrange)
                        ) {
                            Text("Yes", color = Color.White)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showExitDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = appOrange)
                        ) {
                            Text("No", color = Color.White)
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.exit_app),
                            style = normal32Text500,
                            modifier = Modifier,
                            color = textBlack
                        )
                    },
                    text = { Text(stringResource(id = R.string.are_you_sure_you_want_to_exit)) },
                    modifier = Modifier
                        .border(2.dp, slideActiveColor)
                        .padding(2.dp)
                )
            }
        }
    } else {
        CommonMethods().HandleErrorScreens(
            navController = navController,
            showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen,
            showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen
        )
    }
}

@Composable
fun PhoneNumberField(
    mobileNumber: String,
    focusPhNumber: FocusRequester,
    mobileNumberError: String?,
    signInViewModel: SignInViewModel,
    context: Context
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        TextField(
            value = mobileNumber,
            textStyle = normal18Text400,
            isError = mobileNumberError != null,
            placeholder = { Text(text = context.getString(R.string.enter_phone_number)) },
            onValueChange = {
                signInViewModel.onMobileNumberChanged(it)
                signInViewModel.updateMobileNumberError(null)
                if (it.length == 10) {
                    keyboardController?.hide()
                }
            },
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Phone Icon",
                        tint = appOrange
                    )
                    Text(
                        text = "+91",
                        style = normal18Text400,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusPhNumber),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = appOrange,
                errorBorderColor = appRed,
                focusedBorderColor = if (mobileNumberError.isNullOrEmpty()) appOrange else errorRed,
                unfocusedBorderColor = if (mobileNumberError.isNullOrEmpty()) appOrange else errorRed
            )
        )
        if (!mobileNumberError.isNullOrEmpty()) {
            Text(
                text = mobileNumberError,
                color = appRed,
                modifier = Modifier.padding(top = 0.dp)
            )
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = NavHostController(LocalContext.current))
}
