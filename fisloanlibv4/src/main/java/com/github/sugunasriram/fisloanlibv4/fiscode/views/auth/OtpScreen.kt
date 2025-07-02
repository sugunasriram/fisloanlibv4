package com.github.sugunasriram.fisloanlibv4.fiscode.views.auth

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultipleColorText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OtpView
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.DeviceInfo
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.OtpViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.SignInViewModel
import kotlinx.coroutines.delay


@SuppressLint("UnrememberedMutableState")
@Composable
fun OtpScreen(
    navController: NavHostController, number: String?, orderId: String?
) {
    val textList =
        List(4) { remember { mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))) } }
    val requesterList = List(4) { remember { FocusRequester() } }

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val otpViewModel: OtpViewModel = viewModel()
    val signInViewModel: SignInViewModel = viewModel()

    val showInternetScreen by otpViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by otpViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by otpViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by otpViewModel.unexpectedError.observeAsState(false)

    val isLoginOtpLoading by otpViewModel.isLoginOtpLoading.collectAsState()
    val isLoginOtpLoadingSuccess by otpViewModel.isLoginOtpLoadingSuccess.collectAsState()
    val isOtpInvalid by otpViewModel.isOtpInvalid.observeAsState(false)
    val navigationToSignIn by otpViewModel.navigationToSignIn.collectAsState()

    val clipboardManager = LocalClipboardManager.current

    // Timer for OTP expiry
    var count by remember { mutableIntStateOf(59) }
    var expired by remember { mutableStateOf(false) }
    var reSendTriggered by remember { mutableStateOf(false) }
    var reloadTimer by remember { mutableStateOf(false) }

    val deviceInfo = otpViewModel.getDeviceInfo(context,configuration)
    LaunchedEffect (Unit){
        requesterList[0].requestFocus()
    }

    LaunchedEffect(reSendTriggered, reloadTimer) {
        if (navigationToSignIn) {
            navigateSignInPage(navController)
        } else {
            count = 59
            expired = false
            reloadTimer = false
            reSendTriggered = false

            while (count > 0) {
                delay(1000)
                count--
            }
            expired = true
        }
    }
    BackHandler {
        navigateSignInPage(navController)
    }
    if (!showInternetScreen && !showTimeOutScreen && !showServerIssueScreen && !unexpectedErrorScreen) {
        if (isLoginOtpLoading) {
            CenterProgress()
        } else {
            if (isLoginOtpLoadingSuccess) {
               navigateApplyByCategoryScreen(navController)
            } else {
                FixedTopBottomScreen(
                    navController = navController,
                    showBackButton = true,
                    onBackClick = { navigateSignInPage(navController) },
                    topBarBackgroundColor = appWhite,
                    topBarText = stringResource(R.string.otp_verification),
                    backgroundColor= appWhite
                ) {
                    RegisterText(
                        text = stringResource(id = R.string.please_enter_otp_sent_to),
                        textColor = appBlack,
                        style = normal18Text500,
                        top = 100.dp,
                        bottom = 10.dp
                    )
                    number?.let {
                        val textToShow = "******" + it.takeLast(4)
                        RegisterText(
                            text = textToShow,textColor = appBlack,
                            style = normal18Text500
                        )
                    }

                    OtpView(textList = textList, requestList = requesterList, pastedEvent =  {
                        val annotatedString = clipboardManager.getText()
                        annotatedString?.let {
                            extractOtp(it.text)?.let {
                                textList.forEachIndexed { key, textView ->
                                    textView.value = TextFieldValue("${annotatedString.text[key]}", selection = TextRange(1))
                                }
//                                submitOTP(
//                                    orderId,
//                                    textList,
//                                    otpViewModel,
//                                    context,
//                                    navController,
//                                    deviceInfo
//                                )
                                requesterList[3].requestFocus()
                            }
                        }
                    })

                    MultipleColorText(
                        text = stringResource(id = R.string.resend_otp),
                        textColor = if (count > 0) appGray else appBlack,
                        resendOtpColor = if (count > 0) appGray else appOrange,
                    ) {
                        if (expired) {
                            signInViewModel.getUserRole(
                                number.toString(), context.getString(R.string.country_code),
                                context
                            )
                            reSendTriggered = true
                            expired = false
                            reloadTimer = true
                        } else {
                            if (count > 0) {
                                CommonMethods().toastMessage(
                                    context = context, toastMsg = "Wait For $count Seconds"
                                )
                            }
                        }
                    }
                    if (isOtpInvalid) {
                        StartingText(
                            text = stringResource(R.string.please_enter_valid_otp),
                            textColor = errorRed, alignment = Alignment.Center
                        )
                    }

                    RegisterText(
                        text = if (expired) stringResource(id = R.string.time_expired) else "$count seconds",
                       style = normal20Text700, textColor = appBlack,
                        top = 80.dp
                    )


                    val isOtpComplete = textList.all { it.value.text.length == 1 }

                    CurvedPrimaryButton(
                        text = stringResource(id = R.string.verify),
                        modifier = Modifier.padding(top = 20.dp),
                        enabled = isOtpComplete
                    ) {
                        val otp = textList.joinToString("") { it.value.text }
                        if (expired) {
                            CommonMethods().toastMessage(
                                context = context,
                                toastMsg = context.getString(R.string.time_expired_click_on_resend_otp)
                            )
                        } else {
                            if (reSendTriggered) {
                                orderId?.let { otpId ->
                                    otpViewModel.loginOtpValidation(
                                        enteredOtp = otp,
                                        orderId = otpId,
                                        context = context,
                                        navController = navController,
                                        deviceInfo=deviceInfo
                                    )
                                }
                                expired = false

                            } else {
                                orderId?.let { id ->
                                    otpViewModel.loginOtpValidation(
                                        enteredOtp = otp, orderId = id, context = context,
                                        navController = navController,deviceInfo=deviceInfo
                                    )
                                }
                            }
                            textList.forEach { it.value = TextFieldValue("") }

                        }
                    }
                }
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

private fun submitOTP(
    orderId: String?,
    textList: List<MutableState<TextFieldValue>>,
    otpViewModel: OtpViewModel,
    context: Context,
    navController: NavHostController,
    deviceInfo: DeviceInfo
) {
    orderId?.let { id ->
        val otp = textList.joinToString("") { it.value.text }
        otpViewModel.loginOtpValidation(
            enteredOtp = otp, orderId = id, context = context,
            navController = navController,
            deviceInfo = deviceInfo
        )
    }
}

fun extractOtp(input: String): String? {
    val regex = Regex("\\b\\d{4}\\b")
    return regex.find(input)?.value
}

@Preview
@Composable
fun OtpScreenPreview() {
    Surface {
        OtpScreen(
            navController = rememberNavController(), orderId = "1111", number = "11111"
        )
    }

}
