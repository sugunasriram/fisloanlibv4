package com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OtpSuccessImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.disableColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.midNightBlue
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal36Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.gstLoan.GstBankDetailViewModel

@Composable
fun GstBankKycVerificationScreen(
    navController: NavHostController,
    transactionId: String,
    kycUrl: String,
    offerId: String,
    verificationStatus: String,
    fromFlow: String
) {
    val context = LocalContext.current

    val gstBankDetailViewModel: GstBankDetailViewModel = viewModel()
    val bankDetailCollecting by gstBankDetailViewModel.bankDetailCollecting.collectAsState()
    val bankDetailCollected by gstBankDetailViewModel.bankDetailCollected.collectAsState()
    val bankDetailResponse by gstBankDetailViewModel.bankDetailResponse.collectAsState()
    val navigationToSignIn by gstBankDetailViewModel.navigationToSignIn.collectAsState()

    val showInternetScreen by gstBankDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by gstBankDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by gstBankDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by gstBankDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by gstBankDetailViewModel.unAuthorizedUser.observeAsState(false)
    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            GstBankKycVerificationScreenView(
                navController = navController, verificationStatus = verificationStatus,
                transactionId = transactionId,
                bankDetailCollecting = bankDetailCollecting, fromFlow = fromFlow,
                bankDetailCollected = bankDetailCollected, offerId = offerId,
                bankDetailResponse = bankDetailResponse, context = context, kycUrl = kycUrl,
                gstBankDetailViewModel = gstBankDetailViewModel
            )
        }
    }
}

@Composable
fun GstBankKycVerificationScreenView(
    navController: NavHostController,
    verificationStatus: String,
    bankDetailCollecting: Boolean,
    transactionId: String,
    bankDetailCollected: Boolean,
    offerId: String,
    fromFlow: String,
    context: Context,
    bankDetailResponse: GstOfferConfirmResponse?,
    kycUrl: String,
    gstBankDetailViewModel: GstBankDetailViewModel
) {
    if (verificationStatus == "2") {
        if (bankDetailCollecting) {
            CenterProgress()
        } else {
            if (bankDetailCollected) {
                FixedTopBottomScreen(
                    navController,
                    onBackClick = { navController.popBackStack() },
                    primaryButtonText = stringResource(id = R.string.proceed),
//                    backGroudColorChange = verificationStatus.equals("3"),
                    onPrimaryButtonClick = {
                        if (verificationStatus.equals("3")) {
                            transactionId?.let {
                                navigateToLoanProcessScreen(
                                    navController = navController,
                                    transactionId = it,
                                    statusId = 14,
                                    offerId = offerId,
                                    responseItem = "No Need",
                                    fromFlow = "Invoice Loan"
                                )
                            }
                        }
                    }
                ) {
                    CenteredMoneyImage(imageSize = 175.dp, top = 10.dp)
                    RegisterText(
                        text = stringResource(id = R.string.bank_kyc_verification),
                        style = normal32Text700
                    )

                    IndividualButton(
                        verificationStatus = verificationStatus,
                        navController = navController,
                        transactionId = transactionId,
                        fromFlow = fromFlow,
                        kycUrl = kycUrl,
                        offerId = offerId,
                        context = context
                    )

                    EntityButton(
                        verificationStatus = verificationStatus,
                        navController = navController,
                        transactionId = transactionId,
                        context = context,
                        bankDetailResponse = bankDetailResponse,
                        fromFlow = fromFlow
                    )

                    if (verificationStatus.equals("3")) {
                        RegisterText(
                            text = stringResource(id = R.string.kyc_completed),
                            textColor = midNightBlue,
                            style = normal36Text500,
                            modifier = Modifier.padding(10.dp)
                        )
                        OtpSuccessImage(top = 40.dp)
                    }
                }
            } else {
                // Sugu - if entityKyc > 0 & GetUserStatus has led to this
                if (kycUrl.length > 0 && !kycUrl.equals("No Need KYC URL", true)) {
                    kycUrl?.let { entityKycUrl ->
                        navigateToGstKycWebViewScreen(
                            navController = navController,
                            transactionId = transactionId,
                            kycUrl = entityKycUrl,
                            offerId = offerId,
                            fromScreen = "2",
                            fromFlow = fromFlow
                        )
                    }
                } else {
                    gstBankDetailViewModel.gstLoanApproved(
                        id = offerId,
                        loanType = "INVOICE_BASED_LOAN",
                        context = context
                    )
                }
            }
        }
    } else {
        FixedTopBottomScreen(
            navController,
            primaryButtonText = stringResource(id = R.string.proceed),
//            backGroudColorChange = verificationStatus.equals("3"),
            onPrimaryButtonClick = {
                if (verificationStatus.equals("3")) {
                    transactionId?.let {
                        navigateToLoanProcessScreen(
                            navController = navController,
                            transactionId = it,
                            statusId = 14,
                            responseItem = "No Need",
                            offerId = offerId,
                            fromFlow = fromFlow
                        )
                    }
                }
            }
        ) {
            CenteredMoneyImage(imageSize = 175.dp, top = 10.dp)
            RegisterText(
                text = stringResource(id = R.string.bank_kyc_verification),
                style = normal32Text700
            )

            IndividualButton(
                verificationStatus = verificationStatus,
                navController = navController,
                transactionId = transactionId,
                fromFlow = fromFlow,
                kycUrl = kycUrl,
                offerId = offerId,
                context = context
            )

            EntityButton(
                verificationStatus = verificationStatus,
                navController = navController,
                transactionId = transactionId,
                context = context,
                bankDetailResponse = bankDetailResponse,
                fromFlow = fromFlow
            )

            if (verificationStatus.equals("3")) {
                RegisterText(
                    text = stringResource(id = R.string.kyc_completed),
                    textColor = midNightBlue,
                    modifier = Modifier.padding(10.dp),
                    style = normal36Text500
                )
                OtpSuccessImage(top = 40.dp)
            }
        }
    }
}

@Composable
fun EntityButton(
    verificationStatus: String,
    navController: NavHostController,
    transactionId: String,
    context: Context,
    fromFlow: String,
    bankDetailResponse: GstOfferConfirmResponse?
) {
    CurvedPrimaryButtonFull(
        text = stringResource(id = R.string.entity_kyc),
        backgroundColor = if (verificationStatus.equals("1")) disableColor else appOrange,
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, bottom = 20.dp, top = 30.dp)
    ) {
        if (verificationStatus.equals("2")) {
            bankDetailResponse?.let { response ->
                response.data?.catalog?.fromURL?.let { entityKycUrl ->
                    response.data.catalog.itemID?.let { offerId ->
                        navigateToGstKycWebViewScreen(
                            navController = navController,
                            transactionId = transactionId,
                            kycUrl = entityKycUrl,
                            offerId = offerId,
                            fromScreen = "2",
                            fromFlow = fromFlow
                        )
                    }
                }
            }
        } else if (verificationStatus.equals("3")) {
            CommonMethods().toastMessage(context, "Entity KYC Competed")
        } else if (verificationStatus.equals("1")) {
            CommonMethods().toastMessage(context, "Please Complete Individual KYC Competed")
        }
    }
}

@Composable
fun IndividualButton(
    verificationStatus: String,
    navController: NavHostController,
    fromFlow: String,
    kycUrl: String,
    transactionId: String,
    offerId: String,
    context: Context
) {
    CurvedPrimaryButtonFull(
        text = stringResource(id = R.string.individual_kyc),
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, bottom = 20.dp, top = 50.dp)
    ) {
        if (verificationStatus.equals("1")) {
            navigateToGstKycWebViewScreen(
                navController = navController,
                transactionId = transactionId,
                kycUrl = kycUrl,
                offerId = offerId,
                fromScreen = "1",
                fromFlow = fromFlow
            )
        } else {
            CommonMethods().toastMessage(context, "Individual KYC Competed")
        }
    }
}
