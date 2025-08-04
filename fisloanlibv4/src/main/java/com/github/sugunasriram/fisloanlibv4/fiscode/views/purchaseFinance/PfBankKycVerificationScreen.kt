package com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance

import android.content.Context
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
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance.PfBankDetailViewModel

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
    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
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

@Composable
fun PfBankKycVerificationScreenView(
    navController: NavHostController,
    bankDetailCollecting: Boolean,
    bankDetailResponse: PfOfferConfirmResponse?,
    transactionId: String,
    bankDetailCollected: Boolean,
    offerId: String,
    fromFlow: String,
    context: Context,
    kycUrl: String,
    pfBankDetailViewModel: PfBankDetailViewModel
) {
    if (bankDetailCollecting) {
        CenterProgress()
    } else {
        if (bankDetailCollected) {
            bankDetailResponse?.data?.catalog?.fromURL?.let { url ->
                navigateToRepaymentScreen(
                    navController,
                    transactionId,
                    url,
                    offerId,
                    fromFlow
                )
            }
        } else {
            if (kycUrl.length > 0 && !kycUrl.equals("No Need KYC URL", true)) {
                kycUrl?.let { entityKycUrl ->
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
