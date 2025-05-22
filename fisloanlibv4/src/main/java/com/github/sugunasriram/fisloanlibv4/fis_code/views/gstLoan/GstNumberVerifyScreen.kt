package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fis_code.components.MultipleColorText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OtpView
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstInformationScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appDarkTeal
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan.GstDetailViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableState")
@Composable
fun GstNumberVerifyScreen(
    navController: NavHostController,
    gstMobileNumber: String,
    fromFlow: String
) {
    val context = LocalContext.current
    val gstDetailViewModel: GstDetailViewModel = viewModel()
    val generatingOtp by gstDetailViewModel.generatingOtp.collectAsState()
    val generatedOtp by gstDetailViewModel.generatedOtp.collectAsState()
    val verifyOtpForGstIn by gstDetailViewModel.verifyOtpForGstIn.collectAsState()

    val showInternetScreen by gstDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by gstDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by gstDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by gstDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by gstDetailViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by gstDetailViewModel.middleLoan.observeAsState(false)
    val errorMessage by gstDetailViewModel.errorMessage.collectAsState()

    BackHandler {
        navigateToGstDetailsScreen(navController = navController, fromFlow = fromFlow)
    }
    val textList =
        List(6) { remember { mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))) } }
    val requesterList = List(6) { remember { FocusRequester() } }

    var count by remember { mutableIntStateOf(59) }
    var expired by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        while (count > 0) {
            delay(1000)
            count--
        }
        expired = true
    }
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            if (generatingOtp) {
                CenterProgress()
            } else {
                if (generatedOtp) {
                    verifyOtpForGstIn?.data?.id?.let { gstId ->
                        navigateToGstInformationScreen(
                            navController = navController,
                            fromFlow = fromFlow,
                            invoiceId = gstId
                        )
                    }
                } else {
                    ScreenWithHamburger(
                        isSelfScrollable = false,
                        navController = navController
                    ) {
                        CenteredMoneyImage(
                            image = R.drawable.otp_page_image,
                            imageSize = 150.dp,
                            top = 30.dp,
                            bottom = 30.dp
                        )

                        RegisterText(
                            text = stringResource(id = R.string.enter_otp),
                            textColor = appDarkTeal,
                            style = normal32Text700
                        )
                        RegisterText(
                            text = stringResource(id = R.string.please_enter_otp_sent_to),
                            textColor = appBlack,
                            style = normal16Text400,
                            top = 10.dp
                        )
                        gstMobileNumber.let {
                            val textToShow = "******" + it.takeLast(4)
                            RegisterText(
                                text = textToShow,
                                textColor = appBlack,
                                style = normal16Text400
                            )
                        }

                        OtpView(textList = textList, requestList = requesterList, {})

                        RegisterText(
                            text = if (expired) stringResource(id = R.string.time_expired) else "$count seconds",
                            textColor = appBlueTitle,
                            style = normal20Text500,
                            bottom = 10.dp
                        )
                        MultipleColorText(
                            text = stringResource(id = R.string.resend_otp),
                            textColor = appBlueTitle,
                            resendOtpColor = appRed
                        ) {}
                        CurvedPrimaryButtonFull(
                            text = stringResource(id = R.string.submit),
                            modifier = Modifier.padding(
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 20.dp,
                                top = 150.dp
                            )
                        ) {
                            /*val otp = textList.joinToString("") { it.value.text }
                            gstDetailViewModel.otpValidation(
                                orderId = gstMobileNumber, context = context, enteredOtp = otp
                            )*/
                            navigateToGstInformationScreen(
                                navController = navController,
                                fromFlow = fromFlow,
                                invoiceId = gstMobileNumber
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OtpScreenPreview() {
    GstNumberVerifyScreen(
        navController = rememberNavController(),
        gstMobileNumber = "9611909015",
        fromFlow = "Invoice Loan"
    )
}
