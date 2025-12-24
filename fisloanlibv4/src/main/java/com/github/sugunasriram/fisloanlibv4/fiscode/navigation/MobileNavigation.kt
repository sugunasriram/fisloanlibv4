package com.github.sugunasriram.fisloanlibv4.fiscode.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.AnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.components.KycAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoanAgreementAnimationLoader
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.views.ApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.LoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.LanguageSelectionScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.OtpScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.ReportedIssuesScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.SignInScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.SpalashScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.auth.UpdateProfileScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.authauth.ContactUsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.FISExitCofirmationScreen
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
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.EMandateESignFailedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.FormRejectionScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.KYCFailedScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.PrePaymentStatusScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.UnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.AccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.AddBankDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BankDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.BureauOffersScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.EditBankDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanDisbursementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffers
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffersListDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.LoanSummaryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.PersonaLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.PrePaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.RepaymentScheduleScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.ReviewDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.SelectAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.SelectBankScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.DownPaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.PfBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance.PfLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanStatusDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu.LoanStatusScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.AAConsentApprovalScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.AAWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.ConsentSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.FormSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.LoanAgreementWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.RepaymentWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.SearchWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.gst.GstKycWebViewScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.personalLoan.PrePartPaymentWebView
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.personalLoan.WebKycScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.purchaseFinance.PfKycWebViewScreen
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


fun NavGraphBuilder.mobileNavigation(
    navController: NavHostController,
    startDestination: String
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
                    navController = navController,
                    number = mobileNumber,
                    orderId = orderId
                )
            }
        }

        composable("${AppScreens.UpdateProfileScreen.route}/{fromFlow}") { backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (fromFlow != null) {
                UpdateProfileScreen(
                    navController = navController,
                    fromFlow = fromFlow
                )
            }
        }

        composable(AppScreens.ContactUsScreen.route) {
            ContactUsScreen(navController = navController)
        }

        composable("${AppScreens.FISExitCofirmationScreen.route}/{loanId}") { backStack ->
            val loanId = backStack.arguments?.getString("loanId")
            if (loanId != null) {
                FISExitCofirmationScreen(navController = navController, loanId = loanId)
            }
        }

        composable(AppScreens.ReportedIssuesScreen.route) {
            ReportedIssuesScreen(navController = navController)
        }

        composable(AppScreens.LanguageSelectionScreen.route) {
            LanguageSelectionScreen(navController = navController)
        }

//        composable(AppScreens.ApplyByCategoryScreen.route) {
//            ApplyByCategoryScreen(navController = navController)
//        }
//        composable(AppScreens.ApplyByCategoryScreen.route) { backStackEntry ->
//            val json = navController.previousBackStackEntry
//                ?.savedStateHandle
//                ?.get<String>("verifySessionResponse")
//
//            Log.e("Sugu ApplyByCategoryScreen", "verifySessionResponse JSON: $json")
//            val verifySessionResponse = json?.let {
//                Json.decodeFromString<VerifySessionResponse>(it)
//            }
//
//            if (verifySessionResponse != null) {
//                ApplyByCategoryScreen(
//                    navController = navController,
//                    verifySessionResponse = verifySessionResponse
//                )
//            } else {
//                Log.e("Sugu ApplyByCategoryScreen", "Missing session data")
//            }
//        }

        //Working one
//        composable("${AppScreens.ApplyByCategoryScreen.route}/{fromFlow}/{encodedVerifySessionResponse}") { backStackEntry ->
//            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
//            val encodedVerifySessionResponse = backStackEntry.arguments?.getString("encodedVerifySessionResponse")
//            if (encodedVerifySessionResponse != null && fromFlow != null) {
//                val verifySessionResponse = encodedVerifySessionResponse?.let {
//                    Json.decodeFromString<VerifySessionResponse>(it)
//                }
//
//                if (verifySessionResponse != null) {
//                    ApplyByCategoryScreen(
//                        navController = navController,
//                        verifySessionResponse = verifySessionResponse
//                    )
//                } else {
//                    Log.e("Sugu ApplyByCategoryScreen", "Missing session data")
//                }
//
//            }else{
//                ApplyByCategoryScreen(
//                    navController = navController,
//                    verifySessionResponse = null
//                )
//            }
//        }

        //Fix2
        composable(
            route = "${AppScreens.ApplyByCategoryScreen.route}/{fromFlow}?encodedVerifySessionResponse={encodedVerifySessionResponse}",
            arguments = listOf(
                navArgument("fromFlow") { nullable = false },
                navArgument("encodedVerifySessionResponse") { nullable = true }
            )
        ) { backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val encoded = backStackEntry.arguments?.getString("encodedVerifySessionResponse")

            val verifySessionResponse = encoded?.let {
                Json.decodeFromString<VerifySessionResponse>(it)
            }

            ApplyByCategoryScreen(
                navController = navController,
                verifySessionResponse = verifySessionResponse
            )
        }

        composable(
            route = "${AppScreens.DownPaymentScreen.route}/{fromFlow}?encodedVerifySessionResponse={encodedVerifySessionResponse}",
            arguments = listOf(
                navArgument("fromFlow") { nullable = false },
                navArgument("encodedVerifySessionResponse") { nullable = true }
            )
        ) { backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val encoded = backStackEntry.arguments?.getString("encodedVerifySessionResponse")

            val verifySessionResponse = encoded?.let {
                Json.decodeFromString<VerifySessionResponse>(it)
            }

            if (verifySessionResponse != null) {
                DownPaymentScreen(
                    navController = navController,
                    verifySessionResponse = verifySessionResponse
                )
            }
        }



//        composable(AppScreens.DownPaymentScreen.route) { backStackEntry ->
//            val json = backStackEntry.savedStateHandle.get<String>("verifySessionResponse")
//            val verifySessionResponse = json?.let {
//                Json.decodeFromString<VerifySessionResponse?>(it)
//            }
//
//            if (verifySessionResponse != null) {
//                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
//            } else {
//                Log.e("Sugu", "Missing session data")
//            }
//        }


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
            if (fromFlow != null && loanPurpose != null) {
                BasicDetailsScreen(navController = navController, fromFlow = fromFlow, loanPurpose = loanPurpose)
            }
        }

        composable("${AppScreens.ReviewDetailsScreen.route}/{purpose}/{fromFlow}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (purpose != null && fromFlow != null) {
                ReviewDetailsScreen(
                    navController = navController,
                    loanPurpose = purpose,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.BureauOffersScreen.route}/{offerResponse}/{fromFlow}/{encodedSearchResponse}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val withoutAAResponse = backStackEntry.arguments?.getString("encodedSearchResponse")
            if (offerResponse != null && fromFlow != null) {
                BureauOffersScreen(
                    navController = navController,
                    loanPurpose = offerResponse,
                    fromFlow = fromFlow,
                    withoutAAResponse = withoutAAResponse ?: ""
                )
            }
        }

        composable("${AppScreens.AccountAggregatorScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id != null && transactionId != null && url != null) {
                AccountAggregatorScreen(
                    navController = navController,
                    loanPurpose = purpose,
                    fromFlow = fromFlow,
                    id = id,
                    transactionId = transactionId,
                    url = url
                )
            }
        }

        composable("${AppScreens.SelectAccountAggregatorScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id != null && transactionId != null && url != null) {
                SelectAccountAggregatorScreen(
                    navController = navController,
                    loanPurpose = purpose,
                    fromFlow = fromFlow,
                    id = id,
                    transactionId = transactionId,
                    url = url
                )
            }
        }

        composable("${AppScreens.SelectBankScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            if (purpose != null && fromFlow != null && id != null && transactionId != null && url != null) {
                SelectBankScreen(
                    navController = navController,
                    loanPurpose = purpose,
                    fromFlow = fromFlow,
                    id = id,
                    transactionId = transactionId,
                    url = url
                )
            }
        }

        composable("${AppScreens.SearchWebViewScreen.route}/{purpose}/{fromFlow}/{id}/{transactionId}/{url}/{lenderStatusData}") { backStack ->
            val purpose = backStack.arguments?.getString("purpose")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val url = backStack.arguments?.getString("url")
            val lenderStatusData = backStack.arguments?.getString("lenderStatusData")
            if (purpose != null && fromFlow != null && id != null && transactionId != null
                && url != null && lenderStatusData!=null) {
                SearchWebViewScreen(
                    navController = navController,
                    purpose = purpose,
                    fromFlow = fromFlow,
                    id = id,
                    transactionId = transactionId,
                    url = url, lenderStatusData=lenderStatusData
                )
            }
        }

        composable(
            "${AppScreens.LoanProcessScreen
                .route}/{transactionId}/{statusId}/{responseItem}/{offerId}/{fromFlow}"
        ) { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val statusId = backStack.arguments?.getString("statusId")
            val responseItem = backStack.arguments?.getString("responseItem")
            val offerId = backStack.arguments?.getString("offerId")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && statusId != null && responseItem != null && offerId !=
                null &&
                fromFlow != null
            ) {
                LoanProcessScreen(
                    navController = navController,
                    transactionId = transactionId,
                    statusId =
                    statusId,
                    responseItem = responseItem,
                    offerId = offerId,
                    fromFlow = fromFlow
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

        composable("${AppScreens.SearchWebView.route}/{transactionId}/{urlToOpen}/{searchId}/{fromFlow}/{lenderStatusData}") {
                backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val urlToOpen = backStack.arguments?.getString("urlToOpen")
            val searchId = backStack.arguments?.getString("searchId")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val lenderStatusData = backStack.arguments?.getString("lenderStatusData")
            val urlState = remember { mutableStateOf(urlToOpen) }

            if (transactionId != null && urlToOpen != null && searchId != null && fromFlow != null && lenderStatusData !=null) {
                AAWebScreen(
                    navController = navController,
                    urlToOpen = urlToOpen,
                    searchId = searchId,
                    fromFlow = fromFlow,
                    transactionId = transactionId,
                    loadedUrl = urlState,
                    lenderStatusData=lenderStatusData
                ) { }
            }
        }

        composable("${AppScreens.AAConsentApprovalScreen.route}/{searchId}/{url}/{fromFlow}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("searchId")
            val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (id != null && url != null && fromFlow != null) {
                AAConsentApprovalScreen(
                    navController = navController,
                    id = id,
                    url = url,
                    fromFlow = fromFlow
                )
            }
        }
        composable("${AppScreens.LoanOffersScreen.route}/{offerResponse}/{fromFlow}/{encodedSearchResponse}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val withoutAAResponse = backStackEntry.arguments?.getString("encodedSearchResponse")
            if (offerResponse != null && fromFlow != null) {
                LoanOffers(
                    navController = navController,
                    purpose = offerResponse,
                    fromFlow = fromFlow,
                    withoutAAResponse = withoutAAResponse ?: ""
                )
            }
        }

        composable("${AppScreens.LoanOffersListScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                LoanOffersListScreen(
                    navController = navController,
                    offerItem = offerResponse,
                    fromFlow = fromFlow
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
                    navController = navController,
                    responseItem = responseItem,
                    id = id,
                    showButtonId = showButton,
                    fromFlow = fromFlow
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
                    navController = navController,
                    transactionId = transactionId,
                    url = url,
                    id = id,
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
                    navController = navController,
                    transactionId = transactionId,
                    offerId = offerID,
                    responseItem = responseItem,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.AccountDetailsScreen.route}/{id}/{fromFlow}/{fromScreen}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            if (id != null && fromFlow != null && fromScreen != null) {
                AddBankDetailScreen(navController = navController, id = id, fromFlow = fromFlow, fromScreen = fromScreen)
            }
        }

        composable(
            "${AppScreens.EditAccountDetailsScreen.route}/{id}/{fromFlow}/{accountId}/" +
                "{bankAccountHolderName}/{bankAccountType}/{bankIfsc}/{bankAccountNumber}"
        ) { backStack ->
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val accountId = backStack.arguments?.getString("accountId")
            val bankAccountHolderName = backStack.arguments?.getString("bankAccountHolderName")
            val bankAccountType = backStack.arguments?.getString("bankAccountType")
            val bankIfsc = backStack.arguments?.getString("bankIfsc")
            val bankAccountNumber = backStack.arguments?.getString("bankAccountNumber")
            if (id != null && fromFlow != null &&
                accountId != null && bankAccountHolderName != null &&
                bankAccountType != null && bankIfsc != null && bankAccountNumber != null
            ) {
                EditBankDetailScreen(
                    navController = navController,
                    id = id,
                    fromFlow = fromFlow,
                    accountId = accountId,
                    bankAccountHolderName = bankAccountHolderName,
                    bankAccountType = bankAccountType,
                    bankIfsc = bankIfsc,
                    bankAccountNumber = bankAccountNumber
                )
            }
        }

        composable("${AppScreens.FormSubmissionWebScreen.route}/{fromFlow}/{lenderStatusData}") { backStack ->
            val lenderStatusData = backStack.arguments?.getString("lenderStatusData")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null && lenderStatusData!=null) {
                FormSubmissionWebScreen(
                    navController = navController,
                    fromFlow = fromFlow,lenderStatusData=lenderStatusData
                ) {}
            }
        }
        composable("${AppScreens.ConsentSubmissionWebScreen.route}/{fromFlow}/{lenderStatusData}") { backStack ->
            val lenderStatusData = backStack.arguments?.getString("lenderStatusData")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null && lenderStatusData!=null) {
                ConsentSubmissionWebScreen(
                    navController = navController,
                    fromFlow = fromFlow,lenderStatusData=lenderStatusData
                ) {}
            }
        }

        composable("${AppScreens.RepaymentWebScreen.route}/{transactionId}/{url}/{id}/{fromFlow}") { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val url = backStack.arguments?.getString("url")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && url != null && id != null && fromFlow != null) {
                RepaymentWebScreen(
                    navController = navController,
                    transactionId = transactionId,
                    url = url,
                    id = id,
                    fromFlow = fromFlow
                ) {}
            }
        }

        composable("${AppScreens.LoanAgreementWebScreen.route}/{transactionId}/{id}/{fromFlow}/{url}"
        ) { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val url = backStack.arguments?.getString("url")
            if (transactionId != null && id != null && fromFlow != null && url != null) {
                LoanAgreementWebScreen(
                    navController = navController,
                    id = id,
                    transactionId = transactionId,
                    fromFlow = fromFlow,
                    url = url
                )
            }
        }

        composable("${AppScreens.LoanDisbursementScreen.route}/{transactionId}/{id}/{fromFlow}") {
                backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && id != null && fromFlow != null) {
                LoanDisbursementScreen(
                    navController = navController,
                    transactionId =
                    transactionId,
                    id = id,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.LoanSummary.route}/{id}/{consentHandler}/{fromFlow}") { backStack ->
            val id = backStack.arguments?.getString("id")
            val consentHandler = backStack.arguments?.getString("consentHandler")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (id != null && consentHandler != null && fromFlow != null) {
                LoanSummaryScreen(
                    navController = navController,
                    id = id,
                    consentHandler = consentHandler,
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
                    navController = navController,
                    orderId = orderId,
                    fromFlow = fromFlow,
                    fromScreen = fromScreen
                )
            }
        }

        composable("${AppScreens.AnimationLoader.route}/{transactionId}/{id}/{fromFlow}/{loanAgreementURL}") {
                backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val loanAgreementURL = backStack.arguments?.getString("loanAgreementURL")
            if (transactionId != null && id != null && fromFlow != null && loanAgreementURL != null) {
                AnimationLoader(
                    navController = navController,
                    transactionId = transactionId,
                    id = id,
                    fromFlow = fromFlow,
                    loanAgreementURL = loanAgreementURL
                )
            }
        }
        composable("${AppScreens.LoanAgreementAnimationLoader.route}/{transactionId}/{id}/{fromFlow}/{formUrl}") {
                backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val id = backStack.arguments?.getString("id")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val formUrl = backStack.arguments?.getString("formUrl")
            if (transactionId != null && id != null && fromFlow != null && formUrl!=null ) {
                LoanAgreementAnimationLoader(
                    navController = navController,
                    transactionId = transactionId,
                    id = id,
                    fromFlow = fromFlow,formUrl=formUrl
                )
            }
        }

        composable("${AppScreens.PrePaymentStatusScreen.route}/{offer}/{headerText}/{fromFlow}") { backStack ->
            val offer = backStack.arguments?.getString("offer")
            val headerText = backStack.arguments?.getString("headerText")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (offer != null && headerText != null && fromFlow != null) {
                PrePaymentStatusScreen(
                    navController = navController,
                    orderId = offer,
                    headerText = headerText,
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
            if (orderId != null && headerText != null && status != null && fromFlow != null && paymentOption != null) {
                PrePartPaymentWebView(
                    navController = navController,
                    url = headerText,
                    orderId = orderId,
                    status = status,
                    fromFlow = fromFlow,
                    paymentOption = paymentOption
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
                    navController = navController,
                    orderId = orderId,
                    providerId = providerId,
                    orderState = orderState,
                    fromFlow = fromFlow
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
                    navController = navController,
                    orderId = orderId,
                    loanState = loanState,
                    providerId = providerId,
                    fromFlow = fromFlow,
                    fromScreen = fromScreen
                )
            }
        }

        composable(AppScreens.UnexpectedErrorScreen.route) {
            UnexpectedErrorScreen(navController = navController, onClick = {})
        }

        composable(
            "${AppScreens.BankKycVerificationScreen
                .route}/{transactionId}/{kycUrl}/{offerId}/{verificationStatus}/{fromFlow}"
        ) {
                backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val verificationStatus = backStack.arguments?.getString("verificationStatus")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && verificationStatus !=
                null && fromFlow != null
            ) {
                if (fromFlow == "Purchase Finance") {
                    PfBankKycVerificationScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = kycUrl,
                        offerId = offerId,
                        fromFlow = fromFlow
                    )
                } else {
                    GstBankKycVerificationScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = kycUrl,
                        offerId = offerId,
                        verificationStatus = verificationStatus,
                        fromFlow = fromFlow
                    )
                }
            }
        }
        composable("${AppScreens.LoanStatusScreen.route}/{loanType}") { backStack ->
            val loanType = backStack.arguments?.getString("loanType")
            if(loanType != null) {
                LoanStatusScreen(navController = navController, loanType = loanType)
            }
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
                    navController = navController,
                    fromFlow = fromFlow,
                    invoiceId = invoiceId
                )
            }
        }

        composable("${AppScreens.GstNumberVerifyScreen.route}/{mobileNumber}/{fromFlow}") { backStack ->
            val mobileNumber = backStack.arguments?.getString("mobileNumber")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (fromFlow != null && mobileNumber != null) {
                GstNumberVerifyScreen(
                    navController = navController,
                    gstMobileNumber = mobileNumber,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.GstInvoiceDetailScreen.route}/{fromFlow}/{invoiceId}") { backStack ->
            val fromFlow = backStack.arguments?.getString("fromFlow")
            val invoiceId = backStack.arguments?.getString("invoiceId")
            if (fromFlow != null && invoiceId != null) {
                GstInvoiceDetailScreen(
                    navController = navController,
                    fromFlow = fromFlow,
                    invoiceId = invoiceId
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
                    navController = navController,
                    fromFlow = fromFlow,
                    invoiceId = invoiceId
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
                    navController = navController,
                    transactionId = transactionId,
                    fromFlow = fromFlow,
                    offerResponse = offerResponse
                )
            }
        }

        composable(
            "${AppScreens.GstKycWebViewScreen
                .route}/{transactionId}/{kycUrl}/{offerId}/{fromScreen}/{fromFlow}"
        ) { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && fromScreen != null &&
                fromFlow != null
            ) {
                GstKycWebViewScreen(
                    navController = navController,
                    transactionId = transactionId,
                    url = kycUrl,
                    id =
                    offerId,
                    fromScreen = fromScreen,
                    fromFlow = fromFlow,
                    pageContent = {}
                )
            }
        }

        composable(
            "${AppScreens.PfKycWebViewScreen
                .route}/{transactionId}/{kycUrl}/{offerId}/{fromScreen}/{fromFlow}"
        ) { backStack ->
            val transactionId = backStack.arguments?.getString("transactionId")
            val kycUrl = backStack.arguments?.getString("kycUrl")
            val offerId = backStack.arguments?.getString("offerId")
            val fromScreen = backStack.arguments?.getString("fromScreen")
            val fromFlow = backStack.arguments?.getString("fromFlow")
            if (transactionId != null && kycUrl != null && offerId != null && fromScreen != null &&
                fromFlow != null
            ) {
                PfKycWebViewScreen(
                    navController = navController,
                    transactionId = transactionId,
                    url = kycUrl,
                    id =
                    offerId,
                    fromScreen = fromScreen,
                    fromFlow = fromFlow,
//                    pageContent = {}
                )
            }
        }

        composable("${AppScreens.GstInvoiceLoanOfferScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                GstInvoiceLoanOfferScreen(
                    navController = navController,
                    offerResponse = offerResponse,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.PfLoanOfferScreen.route}/{offerResponse}/{fromFlow}") { backStackEntry ->
            val offerResponse = backStackEntry.arguments?.getString("offerResponse")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (offerResponse != null && fromFlow != null) {
                PfLoanOfferScreen(
                    navController = navController,
                    offerResponse = offerResponse,
                    fromFlow = fromFlow
                )
            }
        }

        composable("${AppScreens.IssueDetailScreen.route}/{issueId}/{orderId}/{fromFlow}") { backStackEntry ->
            val issueId = backStackEntry.arguments?.getString("issueId")
            val orderId = backStackEntry.arguments?.getString("orderId")
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            if (issueId != null && fromFlow != null && orderId !=null) {
                IssueDetailScreen(
                    navController = navController,
                    issueId = issueId,
                    orderId=orderId,
                    fromFlow = fromFlow
                )
            }
        }

        // Purchase Finance

//        composable("${AppScreens.DownPaymentScreen.route}/{fromFlow}") {
//            val fromFlow = it.arguments?.getString("fromFlow")
//            if (fromFlow != null) {
//                DownPaymentScreen(navController = navController, fromFlow = fromFlow)
//            }
//        }
        composable(AppScreens.DownPaymentScreen.route) { backStackEntry ->
            val json = backStackEntry.savedStateHandle.get<String>("verifySessionResponse")
//            val verifySessionResponse = json?.let {
//                Gson().fromJson(it, VerifySessionResponse::class.java)
//            }
            val verifySessionResponse = json?.let {
                Json.decodeFromString<VerifySessionResponse?>(it)
            }

            if (verifySessionResponse != null) {
                DownPaymentScreen(navController = navController, verifySessionResponse = verifySessionResponse)
            } else {
                Log.e("Sugu", "Missing session data")
            }
        }

        // Documents
        composable(AppScreens.TermsConditionsScreen.route) {
            TermsConditionsScreen(navController = navController)
        }

        composable(AppScreens.PrivacyPolicyScreen.route) {
            PrivacyPolicyScreen(navController = navController)
        }
        composable(AppScreens.AboutUsScreen.route) {
            AboutUsScreen(navController = navController)
        }

        // Negative Scenario
        composable("${AppScreens.FormRejectionScreen.route}/{fromFlow}/{errorTitle}/{errorMsg}") {
                backStackEntry ->
            val fromFlow = backStackEntry.arguments?.getString("fromFlow")
            val errorTitle = backStackEntry.arguments?.getString("errorTitle")
            val errorMsg = backStackEntry.arguments?.getString("errorMsg")

            if (errorTitle != null && errorMsg != null && fromFlow != null) {
                FormRejectionScreen(
                    navController = navController,
                    errorTitle = errorTitle,
                    errorMsg = errorMsg,
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
                    navController = navController,
                    errorTitle = errorTitle,
                    errorMsg = errorMsg,
                    fromFlow = fromFlow
                )
            }
        }
    }
}
