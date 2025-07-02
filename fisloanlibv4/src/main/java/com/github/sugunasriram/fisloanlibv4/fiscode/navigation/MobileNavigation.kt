package com.github.sugunasriram.fisloanlibv4.fiscode.navigation

import android.net.Uri
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.AnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.components.KycAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.views.ApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.LoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.OtpScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.SignInScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.SpalashScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.UpdateProfileScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.documents.AboutUsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.documents.PrivacyPolicyScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.documents.TermsConditionsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstInformationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstInvoiceDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstInvoiceLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstInvoiceLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstInvoiceLoansScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstLoanOfferListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.GstNumberVerifyScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan.InvoiceDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.CreateIssueScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.IssueDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.igm.IssueListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.FormRejectionScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.UnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.AccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.AddBankDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BankDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.DownPaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.LanguageSelectionScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.EMandateESignFailedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.KYCFailedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BureauOffersScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanDisbursementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffers
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffersListDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanSummaryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.PersonaLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.PrePaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.PrePaymentStatusScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.RepaymentScheduleScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.ReviewDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.SelectAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.SelectBankScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.PfBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.PfLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanStatusDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanStatusScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.AAConsentApprovalScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.LoanAgreementWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.RepaymentWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.AAWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.SearchWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.gst.GstKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.personalLoan.PrePartPaymentWebView
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.personalLoan.WebKycScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.purchaseFinance.PfKycWebViewScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.EditBankDetailScreen
import com.google.gson.Gson


fun NavGraphBuilder.mobileNavigation(
    navController: NavHostController, startDestination: String
) {
    navigation(route = AppNavGraph.GRAPH_LAUNCH, startDestination = startDestination) {

        // Authentication Screen
        composable(AppScreens.SplashScreen.route) {
            SpalashScreen(navController = navController)
        }

        composable(AppScreens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }

        composable("${AppScreens.OtpScreen.route}/{mobileNumber}/{orderId}") { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber")
            val orderId = backStackEntry.arguments?.getString("orderId")
            if (orderId != null && mobileNumber != null) {
                OtpScreen(
                    navController = navController, number = mobileNumber, orderId = orderId,
                )
            }
        }

//        composable(AppScreens.UpdateProfileScreen.route) {
        composable("${AppScreens.UpdateProfileScreen.route}/{fromFlow}") {backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                UpdateProfileScreen(navController = navController, fromFlow = fromFlow)
            }
        }

        composable(AppScreens.LanguageSelectionScreen.route) {
            LanguageSelectionScreen(navController = navController)
        }

        composable(AppScreens.ApplyByCategoryScreen.route) {
            ApplyByCategoryScreen(navController = navController)
        }

        // Personal Loan Screens
        composable("${AppScreens.PersonalLoanScreen.route}/{fromFlow}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                PersonaLoanScreen(navController = navController, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.BasicDetailsScreen.route}/{fromFlow}/{loanPurpose}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val loanPurpose = backStack.arguments?.getString("loanPurpose")
            if (fromFlow != null &&  loanPurpose != null) {
               BasicDetailsScreen(navController = navController, fromFlow = fromFlow,loanPurpose = loanPurpose)
            }
        }

        composable("${AppScreens.ReviewDetailsScreen.route}/{purpose}/{fromFlow}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (purpose != null && fromFlow != null) {
                ReviewDetailsScreen(
                    navController = navController, loanPurpose = purpose, fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.BureauOffersScreen.route}/{offerResponse}/{fromFlow}/{encodedSearchResponse}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val withoutAAResponse = backStackEntry.arguments?.getString("encodedSearchResponse")
            if (offerResponse != null && fromFlow != null ) {
                BureauOffersScreen(
                    navController = navController, loanPurpose = offerResponse, fromFlow = fromFlow,withoutAAResponse = withoutAAResponse ?: ""
                )
            }
        }

        composable("${AppScreens.AccountAggregatorScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id !=null && transactionId != null && url != null) {
                AccountAggregatorScreen(
                    navController = navController, loanPurpose = purpose, fromFlow = fromFlow,id=id,transactionId=transactionId,url=url
                )
            }
        }

        composable("${AppScreens.SelectAccountAggregatorScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id !=null && transactionId != null && url != null) {
                SelectAccountAggregatorScreen(
                    navController = navController, loanPurpose = purpose, fromFlow = fromFlow,id=id,transactionId=transactionId,url=url
                )
            }
        }

        composable("${AppScreens.SelectBankScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null&& id !=null && transactionId != null && url != null) {
                SelectBankScreen(
                    navController = navController, loanPurpose = purpose, fromFlow = fromFlow,id=id,transactionId=transactionId,url=url
                )
            }
        }

        composable("${AppScreens.SearchWebViewScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id !=null && transactionId != null && url != null) {
                SearchWebViewScreen(
                    navController = navController, purpose = purpose, fromFlow = fromFlow,id=id,transactionId=transactionId,url=url
                )
            }
        }


        composable("${AppScreens.LoanProcessScreen
            .route}/{transactionId}/{statusId}/{responseItem}/{offerId}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val statusId = backStack.arguments?.getString("statusId")
            val responseItem = backStack.arguments?.getString("responseItem")
            val offerId = backStack.arguments?.getString("offerId")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && statusId != null && responseItem != null && offerId !=
            null &&
                    fromFlow != null) {
                LoanProcessScreen(
                    navController = navController, transactionId = transactionId, statusId =
                    statusId,
                    responseItem = responseItem, offerId = offerId, fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.EMandateESignFailedScreen.route}/{title}") { backStack ->
            val title = backStack.arguments?.getString("title")
            if (title != null) {
                EMandateESignFailedScreen(navController = navController, title = title)
            }
        }

        composable(AppScreens.KycFailedScreen.route) {
            KYCFailedScreen(navController = navController)
        }


        composable("${AppScreens.SearchWebView.route}/{transactionId}/{urlToOpen}/{searchId}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val urlToOpen = backStack.arguments?.getString("urlToOpen")
            val searchId = backStack.arguments?.getString("searchId")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val urlState = remember { mutableStateOf(urlToOpen) }

            if (transactionId != null && urlToOpen != null && searchId != null && fromFlow != null){
                AAWebScreen(
                    navController = navController, urlToOpen = urlToOpen, searchId = searchId,
                    fromFlow = fromFlow, transactionId=transactionId, loadedUrl = urlState,
                ) { }
            }
        }

        composable("${AppScreens.AAConsentApprovalScreen.route}/{searchId}/{url}/{fromFlow}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("searchId")
            val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (id != null && url != null && fromFlow != null) {
                AAConsentApprovalScreen(
                    navController = navController, id = id, url = url, fromFlow = fromFlow
                )
            }
        }
        composable("${AppScreens.LoanOffersScreen.route}/{offerResponse}/{fromFlow}/{encodedSearchResponse}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val withoutAAResponse = backStackEntry.arguments?.getString("encodedSearchResponse")
            if (offerResponse != null && fromFlow != null ) {
                LoanOffers(
                    navController = navController, purpose = offerResponse, fromFlow = fromFlow,withoutAAResponse = withoutAAResponse ?: ""
                )
            }
        }

        composable("${AppScreens.LoanOffersListScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                LoanOffersListScreen(
                    navController = navController, offerItem = offerResponse, fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.LoanOffersListDetailScreen.route}/{responseItem}/{id}/{showButtonId}/{fromFlow}") {
            val responseItem = it.arguments?.getString("responseItem")
            val id = it.arguments?.getString("id")
            val showButton = it.arguments?.getString("showButtonId")
            val fromFlow = it.arguments?.getString("fromFlow")
            if (responseItem != null && id != null && showButton != null && fromFlow != null) {
                LoanOffersListDetailsScreen(
                    navController = navController, responseItem = responseItem, id = id,
                    showButtonId = showButton, fromFlow = fromFlow
                )
            }
        }



        composable("${AppScreens.WebKycScreen.route}/{transactionId}/{url}/{id}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val url = backStack.arguments?.getString("url")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && url != null && id != null && fromFlow != null) {
                WebKycScreen(
                    navController = navController, transactionId = transactionId,
                    url = url, id = id,
                    fromFlow = fromFlow
                ) {}
            }
        }

        composable("${AppScreens.KycAnimation.route}/{transactionId}/{responseItem}/{offerID}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val offerID = backStack.arguments?.getString("offerID")
            val responseItem = backStack.arguments?.getString("responseItem")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && offerID != null && responseItem != null && fromFlow != null) {
                KycAnimation(
                    navController = navController, transactionId = transactionId, offerId = offerID, responseItem = responseItem, fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.AccountDetailsScreen.route}/{id}/{fromFlow}/{fromScreen}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            if (id != null && fromFlow != null&& fromScreen != null) {
                AddBankDetailScreen(navController = navController, id = id, fromFlow = fromFlow, fromScreen = fromScreen)
            }
        }

        composable("${AppScreens.EditAccountDetailsScreen.route}/{id}/{fromFlow}/{accountId}/" +
                "{bankAccountHolderName}/{bankAccountType}/{bankIfsc}/{bankAccountNumber}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val accountId = backStack.arguments?.getString("accountId")
            val bankAccountHolderName = backStack.arguments?.getString("bankAccountHolderName")
            val bankAccountType = backStack.arguments?.getString("bankAccountType")
            val bankIfsc = backStack.arguments?.getString("bankIfsc")
            val bankAccountNumber = backStack.arguments?.getString("bankAccountNumber")
            if (id != null && fromFlow != null &&
                accountId!= null && bankAccountHolderName != null &&
                    bankAccountType != null &&  bankIfsc != null &&  bankAccountNumber != null ) {
                EditBankDetailScreen(
                    navController = navController,
                    id = id,
                    fromFlow = fromFlow,
                    accountId = accountId,
                    bankAccountHolderName = bankAccountHolderName,
                    bankAccountType = bankAccountType,
                    bankIfsc = bankIfsc,
                    bankAccountNumber = bankAccountNumber,
                )
            }
        }

        composable("${AppScreens.RepaymentWebScreen.route}/{transactionId}/{url}/{id}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val url = backStack.arguments?.getString("url")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && url != null && id != null && fromFlow != null) {
                RepaymentWebScreen(
                    navController = navController, transactionId=transactionId,
                    url = url, id = id, fromFlow = fromFlow
                ) {}
            }
        }

        composable("${AppScreens.LoanAgreementWebScreen
            .route}/{transactionId}/{id}/{loanAgreementFormUrl}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val loanAgreementFormUrl = backStack.arguments?.getString("loanAgreementFormUrl")
            if (transactionId != null && id != null && fromFlow != null && loanAgreementFormUrl !=
                null) {
                LoanAgreementWebScreen(
                    navController = navController, id = id, transactionId = transactionId,
                    url = loanAgreementFormUrl, fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.LoanDisbursementScreen.route}/{transactionId}/{id}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && id != null && fromFlow != null) {
                LoanDisbursementScreen(navController = navController, transactionId =
                transactionId, id = id, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.LoanSummary.route}/{id}/{consentHandler}/{fromFlow}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val consentHandler = backStack.arguments?.getString("consentHandler")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (id != null && consentHandler != null && fromFlow != null) {
                LoanSummaryScreen(
                    navController = navController, id = id, consentHandler = consentHandler,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.RepaymentScheduleScreen.route}/{orderId}/{fromFlow}/{fromScreen}") { backStack ->
            val orderId = backStack.arguments?.getString("orderId")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            if (orderId != null && fromFlow != null && fromScreen != null) {
                RepaymentScheduleScreen(
                    navController = navController, orderId = orderId, fromFlow = fromFlow,
                    fromScreen=fromScreen
                )
            }
        }

        composable("${AppScreens.AnimationLoader.route}/{transactionId}/{id}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && id != null && fromFlow != null) {
                AnimationLoader(navController = navController, transactionId = transactionId, id =
                id, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.PrePaymentStatusScreen.route}/{offer}/{headerText}/{fromFlow}") { backStack ->
            val offer = backStack.arguments?.getString("offer")
            val headerText = backStack.arguments?.getString("headerText")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (offer != null && headerText != null && fromFlow != null) {
                PrePaymentStatusScreen(
                    navController = navController, orderId = offer, headerText = headerText,
                    fromFlow = fromFlow
                ) {}
            }
        }
        composable("${AppScreens.PrePaymentWebViewScreen.route}/{orderId}/{headerText}/{status}/{fromFlow}/{paymentOption}") { backStack ->
            val orderId = backStack.arguments?.getString("orderId")
            val headerText = backStack.arguments?.getString("headerText")
            val status = backStack.arguments?.getString("status")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val paymentOption = backStack.arguments?.getString("paymentOption")
            if (orderId != null && headerText != null && status != null && fromFlow != null&& paymentOption != null) {
                PrePartPaymentWebView(
                    navController = navController, url = headerText, orderId = orderId,
                    status = status, fromFlow = fromFlow, paymentOption=paymentOption
                )
            }
        }

        composable("${AppScreens.CreateIssueScreen.route}/{orderId}/{providerId}/{orderState}/{fromFlow}") {
            val orderId = it.arguments?.getString("orderId")
            val providerId = it.arguments?.getString("providerId")
            val orderState = it.arguments?.getString("orderState")
            val fromFlow = it.arguments?.getString("fromFlow")
            if (orderId != null && providerId != null && orderState != null && fromFlow != null) {
                CreateIssueScreen(
                    navController = navController, orderId = orderId, providerId = providerId,
                    orderState = orderState, fromFlow = fromFlow
                )
            }
        }
        composable("${AppScreens.BankDetailScreen.route}/{id}/{fromFlow}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null && id != null) {
                BankDetailScreen(navController = navController, id = id, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.IssueListScreen.route}/{orderId}/{loanState}/{providerId}/{fromFlow}/{fromScreen}") { backStack ->
            val orderId = backStack.arguments?.getString("orderId")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val providerId = backStack.arguments?.getString("providerId")
            val loanState = backStack.arguments?.getString("loanState")
            if (orderId != null && fromScreen != null && fromFlow != null && providerId != null && loanState != null) {
                IssueListScreen(
                    navController = navController, orderId = orderId, loanState = loanState,
                    providerId = providerId, fromFlow = fromFlow, fromScreen = fromScreen
                )
            }
        }

        composable(AppScreens.UnexpectedErrorScreen.route) {
            UnexpectedErrorScreen(navController = navController,onClick = {})
        }

        composable("${AppScreens.BankKycVerificationScreen
            .route}/{transactionId}/{kycUrl}/{offerId}/{verificationStatus}/{fromFlow}") {
            backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val verificationStatus = backStack.arguments?.getString("verificationStatus")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && verificationStatus !=
                    null && fromFlow != null) {
                if(fromFlow == "Purchase Finance"){
                    PfBankKycVerificationScreen(
                        navController = navController, transactionId = transactionId, kycUrl = kycUrl,
                        offerId = offerId,
                        fromFlow = fromFlow
                    )
                }else{
                    GstBankKycVerificationScreen(
                        navController = navController, transactionId = transactionId, kycUrl = kycUrl,
                        offerId = offerId,
                        verificationStatus = verificationStatus, fromFlow = fromFlow
                    )
                }

            }
        }
        composable(AppScreens.LoanStatusScreen.route) {
            LoanStatusScreen(navController = navController)
        }

        composable(AppScreens.PrePaymentScreen.route) {
            PrePaymentScreen(navController = navController)
        }

        composable(AppScreens.LoanListScreen.route) {
            LoanListScreen(navController = navController)
        }

        composable(AppScreens.LoanStatusDetailScreen.route) {
            LoanStatusDetailScreen(navController = navController)
        }

        // Gst Loan Flow Mobile Navigation

        composable("${AppScreens.GstInvoiceLoanScreen.route}/{fromFlow}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                GstInvoiceLoanScreen(navController = navController, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.GstDetailsScreen.route}/{fromFlow}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                GstDetailsScreen(navController = navController, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.GstInformationScreen.route}/{fromFlow}/{invoiceId}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val invoiceId = backStack.arguments?.getString("invoiceId")
            if (fromFlow != null && invoiceId != null) {
                GstInformationScreen(
                    navController = navController, fromFlow = fromFlow, invoiceId = invoiceId
                )
            }
        }

        composable("${AppScreens.GstNumberVerifyScreen.route}/{mobileNumber}/{fromFlow}") { backStack ->
            val mobileNumber = backStack.arguments?.getString("mobileNumber")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null && mobileNumber != null) {
                GstNumberVerifyScreen(
                    navController = navController, gstMobileNumber = mobileNumber,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.GstInvoiceDetailScreen.route}/{fromFlow}/{invoiceId}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val invoiceId = backStack.arguments?.getString("invoiceId")
            if (fromFlow != null && invoiceId != null) {
                GstInvoiceDetailScreen(
                    navController = navController, fromFlow = fromFlow,invoiceId = invoiceId
                )
            }
        }

        composable("${AppScreens.GstInvoiceLoansScreen.route}/{fromFlow}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                GstInvoiceLoansScreen(navController = navController, fromFlow = fromFlow)
            }
        }

        composable("${AppScreens.InvoiceDetailScreen.route}/{fromFlow}/{invoiceId}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val invoiceId = backStack.arguments?.getString("invoiceId")
            if (fromFlow != null && invoiceId != null) {
                InvoiceDetailScreen(
                    navController = navController, fromFlow = fromFlow,invoiceId = invoiceId
                )
            }
        }
        composable("${AppScreens.GstLoanOfferListScreen.route}/{transactionId}/{offerResponse}/{fromFlow}") {
            backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (transactionId != null && offerResponse != null && fromFlow != null) {
                GstLoanOfferListScreen(
                    navController = navController, transactionId=transactionId, fromFlow = fromFlow,
                    offerResponse = offerResponse,
                )
            }
        }

        composable("${AppScreens.GstKycWebViewScreen
            .route}/{transactionId}/{kycUrl}/{offerId}/{fromScreen}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && fromScreen != null &&
                fromFlow != null) {
                GstKycWebViewScreen(
                    navController = navController, transactionId= transactionId, url = kycUrl, id
                    = offerId,
                    fromScreen = fromScreen, fromFlow = fromFlow, pageContent = {}
                )
            }
        }

        composable("${AppScreens.PfKycWebViewScreen
            .route}/{transactionId}/{kycUrl}/{offerId}/{fromScreen}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && fromScreen != null &&
                fromFlow != null) {
                PfKycWebViewScreen(
                    navController = navController, transactionId= transactionId, url = kycUrl, id
                    = offerId,
                    fromScreen = fromScreen, fromFlow = fromFlow, pageContent = {}
                )
            }
        }

        composable("${AppScreens.GstInvoiceLoanOfferScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                GstInvoiceLoanOfferScreen(
                    navController = navController, offerResponse = offerResponse,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.PfLoanOfferScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                PfLoanOfferScreen(
                    navController = navController, offerResponse = offerResponse,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.IssueDetailScreen.route}/{issueId}/{fromFlow}") { backStackEntry ->
            val issueId = backStackEntry.arguments?.getString("issueId")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (issueId != null && fromFlow != null) {
                IssueDetailScreen(
                    navController = navController, issueId = issueId, fromFlow = fromFlow
                )
            }
        }

        //Purchase Finance

//        composable("${AppScreens.DownPaymentScreen.route}/{verifySessionResponse}") {
//            val verifySessionResponseJson = it.arguments?.getString("verifySessionResponse")
//                ?:"purchase_finance"
//            if (verifySessionResponseJson != null) {
//                val verifySessionResponse = Gson().fromJson(verifySessionResponseJson, VerifySessionResponse::class.java)
//                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
//            }
//        }

//        composable(AppScreens.DownPaymentScreen.route) { navBackStackEntry ->
//            val json = navBackStackEntry.savedStateHandle.get<String>("verifySessionResponse")
//            val verifySessionResponse = json?.let { Gson().fromJson(it, VerifySessionResponse::class.java) }
//
//            if (verifySessionResponse != null) {
//                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
//            } else {
//                Log.d ("Sugu : ", "Missing session data")
//            }
//        }

        composable(AppScreens.DownPaymentScreen.route) { backStackEntry ->
            val json = backStackEntry.savedStateHandle.get<String>("verifySessionResponse")
            val verifySessionResponse = json?.let {
                Gson().fromJson(it, VerifySessionResponse::class.java)
            }

            if (verifySessionResponse != null) {
                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
            } else {
                Log.e("Sugu", "Missing session data")
            }
        }




//        composable("${AppScreens.DownPaymentScreen.route}") {
//            val verifySessionResponseJson = it.arguments?.getString("verifySessionResponse")
//                ?:"{\n" +
//                "    \"statusCode\": 201,\n" +
//                "    \"status\": true,\n" +
//                "    \"message\": \"Session Retrived Successfully\",\n" +
//                "    \"data\": {\n" +
//                "        \"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxMDE2NzA0LCJleHAiOjE3NTE2MjE1MDR9.WpYrU6AvEsubAarJyna2rkHFWGK3oM208RPxORKBPfk\",\n" +
//                "        \"sessionId\": \"83f29f24-704d-529f-a3b4-4a5560cd2c70\",\n" +
//                "        \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoiZTgxOTM1MGUtYWZhNS01ZmU0LWExZTEtNmNhYjZiNDk4NzZjIiwibW9iaWxlTnVtYmVyIjoiOTYxMTkwOTAxNSIsIm1vYmlsZUNvdW50cnlDb2RlIjoiKzkxIn0sInJvbGUiOiJVU0VSIiwiaWF0IjoxNzUxNDQyNDUxLCJleHAiOjE3NTE0NDI2MzF9.ziPqEqA9eFr_Co-B1b6mwYMKgzgTjjrFR8lYeUIilME\",\n" +
//                "        \"sseId\": \"a41bb04e18a5545395ba6cba4e34607c\",\n" +
//                "        \"securityKey\": \"110e5c0419135e22814945c143ab9fda\",\n" +
//                "        \"sessionData\": {\n" +
//                "            \"downPayment\": 0,\n" +
//                "            \"productId\": \"2a9a7100-bf22-5858-83f3-62e65c2e3a8c\",\n" +
//                "            \"merchantPAN\": null,\n" +
//                "            \"merchantBankAccount\": null,\n" +
//                "            \"merchantGST\": null,\n" +
//                "            \"merchantAccountHolderName\": null,\n" +
//                "            \"merchantIfscCode\": null,\n" +
//                "            \"productBrand\": null,\n" +
//                "            \"productCategory\": \"Mobile Phone\",\n" +
//                "            \"productSKUID\": \"47af53c0-0add-5c49-9365-8f42fafa18fe\",\n" +
//                "            \"productReturnWindow\": \"P7D\",\n" +
//                "            \"productModel\": null,\n" +
//                "            \"productSellingPrice\": \"4799\",\n" +
//                "            \"productCancellable\": true,\n" +
//                "            \"productReturnable\": true,\n" +
//                "            \"productName\": \"VRIDDHI\",\n" +
//                "            \"productMrpPrice\": 4800,\n" +
//                "            \"productSymbol\": \"https://storage.googleapis.com/nslive/62bab198-9391-5be4-b672-e78a6a299a1f/raw/1742808735478_1742808733756.png\",\n" +
//                "            \"productQuantity\": \"1\"\n" +
//                "        },\n" +
//                "        \"sessionType\": \"FIS_PF\"\n" +
//                "    }\n" +
//                "}"
//            if (verifySessionResponseJson != null) {
//                val verifySessionResponse = Gson().fromJson(verifySessionResponseJson, VerifySessionResponse::class.java)
//                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
//            }
//        }

        //Documents
        composable(AppScreens.TermsConditionsScreen.route) {
            TermsConditionsScreen(navController = navController)
        }

        composable(AppScreens.PrivacyPolicyScreen.route) {
            PrivacyPolicyScreen(navController = navController)
        }
        composable(AppScreens.AboutUsScreen.route) {
            AboutUsScreen(navController = navController)
        }

        //Negative Scenario
        composable("${AppScreens.FormRejectionScreen.route}/{fromFlow}/{errorTitle}/{errorMsg}") {
            backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val errorTitle = backStackEntry.arguments?.getString("errorTitle")
            val errorMsg = backStackEntry.arguments?.getString("errorMsg")

            if (errorTitle != null && errorMsg != null && fromFlow != null) {
                FormRejectionScreen(
                    navController = navController, errorTitle = errorTitle, errorMsg = errorMsg,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.FormRejectionScreen.route}/{fromFlow}/{errorMsg}") {
                backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val errorTitle = backStackEntry.arguments?.getString("errorTitle")
            val errorMsg = backStackEntry.arguments?.getString("errorMsg")

            if (errorTitle != null && errorMsg != null && fromFlow != null) {
                FormRejectionScreen(
                    navController = navController, errorTitle = errorTitle, errorMsg = errorMsg,
                    fromFlow = fromFlow
                )
            }
        }
    }
}