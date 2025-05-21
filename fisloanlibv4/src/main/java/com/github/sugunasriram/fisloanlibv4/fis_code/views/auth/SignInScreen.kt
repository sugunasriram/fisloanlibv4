package com.github.sugunasriram.fisloanlibv4.fis_code.views.auth

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToOtpScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToTermsConditionsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.slideActiveColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.textBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth.SignInViewModel

@OptIn(ExperimentalComposeUiApi::class)
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

    var showExitDialog by remember { mutableStateOf(false) }
    var checkboxState by remember { mutableStateOf(false) }
    var isAgreeTermsAndConditions by remember { mutableStateOf(true) }

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
                        generatedOtpData.value?.data?.orderId.toString(),
                    )
                }

            } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize().verticalScroll(rememberScrollState())
                    ) {
//                        Box (
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(350.dp)
//                                .clip(waveShape())
//                                .shadow(elevation = 16.dp, shape = waveShape())
//                                .background(appWhite)
//                        ) {
                            CenteredMoneyImage(
                                image = R.drawable.sign_in_screen_image,
                                imageSize = 200.dp,
                                top = 100.dp
                            )
//                        }

                        MultiStyleText("Sign", appBlack, "in", appOrange)
                        PhoneNumberField(
                            mobileNumber = mobileNumber ?: "",
                            focusPhNumber = focusPhNumber,
                            mobileNumberError = mobileNumberError,
                            signInViewModel = signInViewModel,
                            context = context,
                        )
                        CheckBoxText(textColor = appOrange,
                            style = normal16Text400,
                            boxState = checkboxState,
                            text = stringResource(id = R.string.i_agree_to_buyer_app_terms_and_conditions),
                            bottom = 0.dp, start = 0.dp, end = 0.dp,top=50.dp) {
                                isChecked -> checkboxState = isChecked
                            isAgreeTermsAndConditions=true
                        }
                        if (!isAgreeTermsAndConditions) {
                            StartingText(
                                text = stringResource(R.string.please_agree_buyer_App_terms),
                                textColor = errorRed, alignment = Alignment.Center
                            )
                        }
                        //Show Terms and Conditions as clickable text
                        Text(
                            text = stringResource(R.string.terms_and_conditions),
                            color = appOrange,
                            style = normal16Text700.copy(textDecoration = TextDecoration.Underline),
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .clickable {
                                    navigateToTermsConditionsScreen(navController)
                                }
                        )
                        CurvedPrimaryButton(
                            text = stringResource(id = R.string.get_otp),
                            modifier = Modifier.padding(top = 45.dp)
                        ) {
                            if(checkboxState){
                                isAgreeTermsAndConditions=true
                                mobileNumber?.let {
                                    signInViewModel.signInValidation(
                                        navController,
                                        mobileNumber = it, mobileNumberFocus = focusPhNumber,
                                        context = context,
                                    )
                                }
                            }else{
                                isAgreeTermsAndConditions=false
                            }

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
                            text = stringResource(id = R.string.exit_app), style = normal32Text500,
                            modifier = Modifier, color = textBlack
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
            navController = navController, showInternetScreen = showInternetScreen,
            showTimeOutScreen = showTimeOutScreen, showServerIssueScreen = showServerIssueScreen,
            unexpectedErrorScreen = unexpectedErrorScreen
        )
    }
}

private fun waveShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val path = Path().apply {
                moveTo(0f, size.height * 0.8f)
                quadraticBezierTo(
                    size.width * 0.5f, size.height * 1.2f,
                    size.width, size.height * 0.6f
                )
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
                close()
            }
            return Outline.Generic(path)
        }
    }
}

@Composable
fun PhoneNumberField(
    mobileNumber: String,
    focusPhNumber: FocusRequester,
    mobileNumberError: String?,
    signInViewModel: SignInViewModel, context: Context
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
                focusedBorderColor = if (mobileNumberError.isNullOrEmpty()) appOrange else appRed, // Apply red only if error exists
                unfocusedBorderColor = if (mobileNumberError.isNullOrEmpty()) appOrange else appRed,
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




