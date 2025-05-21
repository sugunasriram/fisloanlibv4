package com.github.sugunasriram.fisloanlibv4.fis_code.views.webview

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstConsentResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalRequest
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.LoanNotApprovedScreen
import kotlinx.serialization.json.Json

@SuppressLint("ResourceType")
@Composable
fun AAConsentApprovalScreen(
    navController: NavHostController, id: String? = null, url: String? = null, fromFlow: String
) {
    val context = LocalContext.current
    val consentApprovalViewModel: WebViewModel = viewModel()
    val consentApprovalResponse by consentApprovalViewModel.consentApprovalResponse.collectAsState()
    val gstConsentApprovalResponse by consentApprovalViewModel.gstConsentApprovalResponse.collectAsState()
    val isLoading by consentApprovalViewModel.isLoading.collectAsState()
    val isLoadingSuccess by consentApprovalViewModel.isLoadingSuccess.collectAsState()

    val showInternetScreen by consentApprovalViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by consentApprovalViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by consentApprovalViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by consentApprovalViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by consentApprovalViewModel.unAuthorizedUser.observeAsState(false)
    val loanNotFound by consentApprovalViewModel.loanNotFound.observeAsState(false)
    val middleLoan by consentApprovalViewModel.middleLoan.observeAsState(false)
    val errorMessage by consentApprovalViewModel.errorMessage.collectAsState()
    val navigationToSignIn by consentApprovalViewModel.navigationToSignIn.collectAsState()

    when {
        navigationToSignIn -> navigateSignInPage (navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        loanNotFound -> { LoanNotApprovedScreen(navController) }

        else -> {
            if (isLoading) {
                LoaderAnimation(
                    text = stringResource(R.string.generating_account_aggregator),
                    updatedText = stringResource(id = R.string.generating_best_offers),
                    image = R.raw.generating_aa_consent,
                    showTimer = true, navController = navController
                )
            } else {
                if (isLoadingSuccess) {
                    ApiSuccess(
                        fromFlow = fromFlow, consentApprovalResponse = consentApprovalResponse,
                        gstConsentApprovalResponse = gstConsentApprovalResponse,
                        navController = navController
                    )
                } else {
                    decideApiCalling(
                        fromFlow = fromFlow, context = context, id = id, url = url,
                        consentApprovalViewModel = consentApprovalViewModel
                    )
                }
            }
        }
    }
}

fun decideApiCalling(
    fromFlow: String, consentApprovalViewModel: WebViewModel, context: Context, id: String?,
    url: String?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        consentApprovalViewModel.aaConsentApprovalApi(
            context = context, consentBodyModel = ConsentApprovalRequest(
                id = id, url = url, loanType = "PERSONAL_LOAN"
            )
        )
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        consentApprovalViewModel.gstConsentApproval(
            context = context, consentApproval = ConsentApprovalRequest(
                id = id, url = url, loanType = "INVOICE_BASED_LOAN"
            )
        )
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        consentApprovalViewModel.financeConsentApproval(
            context = context, consentApproval = ConsentApprovalRequest(
                id = id, url = url, loanType = "PURCHASE_FINANCE"
            )
        )
    }
}

@Composable
fun ApiSuccess(
    navController: NavHostController, gstConsentApprovalResponse: GstConsentResponse?,
    fromFlow: String, consentApprovalResponse: ConsentApprovalResponse?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        val transactionId = consentApprovalResponse?.data?.offerResponse?.get(0)?.offer?.txnId
        transactionId?.let {
            navigateToLoanOffersListScreen(navController,  "No Need Response Item", fromFlow)
//            navigateToLoanProcessScreen(
//                navController, transactionId= it,
//                statusId = 2, responseItem = "No Need Response Item", offerId = "1234",
//                fromFlow = fromFlow
//            )
        }
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        val transactionId = gstConsentApprovalResponse?.data?.offerResponse?.get(0)?.txnID
        transactionId?.let {
            gstConsentApprovalResponse?.data?.let { data ->
                val json = Json { prettyPrint = true }
                val responseItem = json.encodeToString(GstData.serializer(), data)
                navigateToLoanProcessScreen(
                    navController = navController,
                    transactionId=it,
                    statusId = 12,
                    offerId = "1234",
                    fromFlow = fromFlow,
                    responseItem = responseItem,
                )
            }
        }
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        val transactionId = consentApprovalResponse?.data?.offerResponse?.get(0)?.offer?.txnId
        transactionId?.let {
            consentApprovalResponse?.data?.let { offerResponseList ->
                val json = Json { prettyPrint = true }
                val responseItem =
                    json.encodeToString(OfferResponse.serializer(), offerResponseList)
                navigateToLoanProcessScreen(
                    navController = navController, transactionId=it,
                    statusId = 20, offerId = "1234",
                    fromFlow = fromFlow, responseItem = responseItem,
                )
            }
        }
    }
}




