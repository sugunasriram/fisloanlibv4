package com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance.PfBankDetailViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PFLoanApprovedResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScheduleScreen

@Composable
fun PfBankKycVerificationScreen(
    navController: NavHostController,
    transactionId: String,
    kycUrl: String,
    offerId: String,
    fromFlow: String
) {
    val context = LocalContext.current

    val pfBankDetailViewModel: PfBankDetailViewModel = viewModel()
    val bankDetailCollecting by pfBankDetailViewModel.bankDetailCollecting.collectAsState()
    val bankDetailCollected by pfBankDetailViewModel.bankDetailCollected.collectAsState()
    val bankDetailResponse by pfBankDetailViewModel.bankDetailResponse.collectAsState()
    val navigationToSignIn by pfBankDetailViewModel.navigationToSignIn.collectAsState()

    val showInternetScreen by pfBankDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by pfBankDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by pfBankDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by pfBankDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by pfBankDetailViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by pfBankDetailViewModel.middleLoan.observeAsState(false)
    val errorMessage by pfBankDetailViewModel.errorMessage.collectAsState()
    BackHandler { navigateApplyByCategoryScreen(navController) }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
        else -> {
            PfBankKycVerificationScreenView(
                navController = navController,
                transactionId = transactionId,
                bankDetailCollecting = bankDetailCollecting,
                bankDetailResponse = bankDetailResponse,
                fromFlow = fromFlow,
                bankDetailCollected = bankDetailCollected,
                offerId = offerId,
                context = context,
                kycUrl = kycUrl,
                pfBankDetailViewModel = pfBankDetailViewModel
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun PfBankKycVerificationScreenView(
    navController: NavHostController,
    bankDetailCollecting: Boolean,
    bankDetailResponse: PFLoanApprovedResponse?,
    transactionId: String,
    bankDetailCollected: Boolean,
    offerId: String,
    fromFlow: String,
    context: Context,
    kycUrl: String,
    pfBankDetailViewModel: PfBankDetailViewModel
) {
    if (bankDetailCollecting) {
        ProcessingAnimation(text = "Processing Please Wait...",image = R.raw.we_are_currently_processing_hour_glass)
    } else {
        if (bankDetailCollected) {
            val formUrl = bankDetailResponse?.data?.catalog?.catalog?.fromURL
            val consent = bankDetailResponse?.data?.catalog?.consent
            //----fixed on 0128
            val orderId = bankDetailResponse?.data?.catalog?.id
            when {
                !formUrl.isNullOrBlank() -> {
                    navigateToRepaymentScreen(
                        navController = navController,
                        transactionId = transactionId,
                        url = formUrl,
                        id = offerId,
                        fromFlow = fromFlow
                    )
                }

                consent == false -> {
                    //----fixed on 0128
                    orderId?.let {
                        navigateToRepaymentScheduleScreen(
                            navController = navController,
                            orderId = it,
                            fromFlow = fromFlow,
                            fromScreen = fromFlow
                        )
                    }
//                    navigateApplyByCategoryScreen(navController)
                }
                else ->{
                    //----fixed on 0128
                    orderId?.let {
                        navigateToRepaymentScheduleScreen(
                            navController = navController,
                            orderId = it,
                            fromFlow = fromFlow,
                            fromScreen = fromFlow
                        )
                    }
//                    navigateToLoanSummaryScreen(
//                        navController = navController,
//                        id = "user-001",
//                        consentHandler = "1",
//                        fromFlow = fromFlow
//                    )
//                    navigateApplyByCategoryScreen(navController)
                }
            }
        } else {
            if (kycUrl.isNotEmpty() && !kycUrl.equals("No Need KYC URL", true)) {
                kycUrl.let { entityKycUrl ->
                    navigateToPfKycWebViewScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = entityKycUrl,
                        offerId = offerId,
                        fromScreen = "2",
                        fromFlow = fromFlow
                    )
                }
            } else {
                pfBankDetailViewModel.pfLoanApproved(
                    id = offerId,
                    loanType = "PURCHASE_FINANCE",
                    context = context
                )
            }
        }
    }
}

@Preview
@Composable
private fun KycPreviewScreen() {
    PfBankKycVerificationScreen(
        rememberNavController(),
        "transactionId",
        "kycUrl",
        "asdf",
        "Purchase Finance"
    )
}
