package com.github.sugunasriram.fisloanlibv4.fiscode.navigation

import android.net.Uri
import android.util.Log
import androidx.navigation.NavHostController

fun shouldCloseCurrent(navController: NavHostController, closeCurrent: Boolean) {
    if (closeCurrent) {
        navController.popBackStack()
    }
}

fun navigateSignInPage(navController: NavHostController, closeCurrent: Boolean = true) {
    navController.navigate(AppScreens.SignInScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToOtpScreen(
    navController: NavHostController,
    mobileNumber: String,
    orderId: String,
    closeCurrent: Boolean = true
) {
    val otpScreenPath = "${AppScreens.OtpScreen.route}/$mobileNumber/$orderId"
    navController.navigate(otpScreenPath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToUpdateProfileScreen(navController: NavHostController, closeCurrent: Boolean = false, fromFlow: String) {
    navController.navigate("${AppScreens.UpdateProfileScreen.route}/$fromFlow") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLanguageScreen(navController: NavHostController, closeCurrent: Boolean = false) {
    navController.navigate(AppScreens.LanguageSelectionScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateApplyByCategoryScreen(navController: NavHostController, closeCurrent: Boolean = true) {
    navController.navigate(AppScreens.ApplyByCategoryScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}


fun navigateToContactUsScreen(navController: NavHostController, closeCurrent: Boolean = false) {
    navController.navigate(AppScreens.ContactUsScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}


fun navigateToReportedIssuesScreen(navController: NavHostController, closeCurrent: Boolean = false) {
    navController.navigate(AppScreens.ReportedIssuesScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

// Personal Loan Flow App Screens Navigation
fun navigateToPersonaLoanScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinationUrl = "${AppScreens.PersonalLoanScreen.route}/$fromFlow"
    navController.navigate(destinationUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToBasicDetailsScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false,
    loanPurpose: String = "Loan Purpose"
) {
    val destinationUrl = "${AppScreens.BasicDetailsScreen.route}/$fromFlow/$loanPurpose"
    navController.navigate(destinationUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToReviewDetailsScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    navController.navigate("${AppScreens.ReviewDetailsScreen.route}/$loanPurpose/$fromFlow") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToBureauOffersScreen(
    navController: NavHostController,
    offerItem: String,
    fromFlow: String,
    withoutAAResponse: String = "Not GetUserStatus flow",
    closeCurrent: Boolean = false
) {
    val encodedResponseItem = Uri.encode(offerItem)
    val encodedSearchResponse = Uri.encode(withoutAAResponse)
    val destinationUri = "${AppScreens.BureauOffersScreen.route}/$encodedResponseItem/$fromFlow/$encodedSearchResponse"
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToAccountAggregatorScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    navController.navigate("${AppScreens.AccountAggregatorScreen.route}/$loanPurpose/$fromFlow/$id/$transactionId/$encodedUrl") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToSelectAccountAggregatorScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    navController.navigate("${AppScreens.SelectAccountAggregatorScreen.route}/$loanPurpose/$fromFlow/$id/$transactionId/$encodedUrl") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToSelectBankScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    navController.navigate("${AppScreens.SelectBankScreen.route}/$loanPurpose/$fromFlow/$id/$transactionId/$encodedUrl") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToWebViewFlowOneScreen(
    navController: NavHostController,
    purpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String,lenderStatusData:String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    val encodedLenderStatusData = Uri.encode(lenderStatusData)
    navController.navigate("${AppScreens.SearchWebViewScreen.route}/$purpose/$fromFlow/$id/$transactionId/$encodedUrl/$encodedLenderStatusData") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanStatusScreen(navController: NavHostController, closeCurrent: Boolean = false) {
    navController.navigate(AppScreens.LoanStatusScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPrePaymentScreen(navController: NavHostController, closeCurrent: Boolean = false) {
    navController.navigate(AppScreens.PrePaymentScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateLoanStatusDetailScreen(
    navController: NavHostController,
    closeCurrent: Boolean = false
) {
    shouldCloseCurrent(navController, closeCurrent)
    navController.navigate(AppScreens.LoanStatusDetailScreen.route)
}
fun navigateToLoanOffersScreen(
    navController: NavHostController,
    offerItem: String,
    fromFlow: String,
    withoutAAResponse: String = "Not GetUserStatus flow",
    closeCurrent: Boolean = false
) {
    val encodedResponseItem = Uri.encode(offerItem)
    val encodedSearchResponse = Uri.encode(withoutAAResponse)
    val destinationUri = "${AppScreens.LoanOffersScreen.route}/$encodedResponseItem/$fromFlow/$encodedSearchResponse"
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanOffersListScreen(
    navController: NavHostController,
    offerItem: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedResponseItem = Uri.encode(offerItem)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.LoanOffersListScreen.route}/$encodedResponseItem/$fromFlow"
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToAAConsentApprovalScreen(
    navController: NavHostController,
    searchId: String,
    url: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    val encodedSearchId = Uri.encode(searchId)
    navController.navigate("${AppScreens.AAConsentApprovalScreen.route}/$encodedSearchId/$encodedUrl/$fromFlow") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanOffersListDetailScreen(
    navController: NavHostController,
    responseItem: String,
    id: String,
    showButtonId: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedResponseItem = Uri.encode(responseItem)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri =
        "${AppScreens.LoanOffersListDetailScreen.route}/$encodedResponseItem/$id/$showButtonId/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanSummaryScreen(
    navController: NavHostController,
    id: String,
    consentHandler: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinationUrl = "${AppScreens.LoanSummary.route}/$id/$consentHandler/$fromFlow"
    navController.navigate(destinationUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToRepaymentScheduleScreen(
    navController: NavHostController,
    orderId: String,
    fromFlow: String,
    fromScreen: String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedLoanItem = Uri.encode(orderId)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.RepaymentScheduleScreen.route}/$encodedLoanItem/$fromFlow/$fromScreen"

    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanProcessScreen(
    navController: NavHostController,
    transactionId: String,
    statusId: Int,
    responseItem: String,
    offerId: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedResponseItem = Uri.encode(responseItem)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri =
        "${AppScreens.LoanProcessScreen
            .route}/$transactionId/$statusId/$encodedResponseItem/$offerId/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToLoanDisbursementScreen(
    navController: NavHostController,
    transactionId: String,
    id: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    navController.navigate("${AppScreens.LoanDisbursementScreen.route}/$transactionId/$id/$fromFlow") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToAccountDetailsScreen(
    navController: NavHostController,
    id: String,
    fromFlow: String,
    fromScreen: String,
    closeCurrent: Boolean = false
) {
    navController.navigate("${AppScreens.AccountDetailsScreen.route}/$id/$fromFlow/$fromScreen") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToEditAccountDetailsScreen(
    navController: NavHostController,
    id: String,
    fromFlow: String,
    accountId: String,
    bankAccountHolderName: String,
    bankAccountType: String,
    bankIfsc: String,
    bankAccountNumber: String,
    closeCurrent: Boolean = false
) {
    navController.navigate(
        "${AppScreens.EditAccountDetailsScreen.route}/$id/$fromFlow/$accountId" +
            "/$bankAccountHolderName/$bankAccountType/$bankIfsc/$bankAccountNumber"
    ) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToWebViewScreen(
    navController: NavHostController,
    transactionId: String,
    urlToOpen: String,
    searchId: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(urlToOpen)
    val navigatePath = "${AppScreens.SearchWebView
        .route}/$transactionId/$encodedUrl/$searchId/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateKycScreen(
    navController: NavHostController,
    transactionId: String,
    url: String,
    id: String,
    fromFlow:
        String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedUrl = Uri.encode(url)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.WebKycScreen.route}/$transactionId/$encodedUrl/$id/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}
fun navigateToFormSubmissionWebScreen(
    navController: NavHostController,
    fromFlow: String, lenderStatusData:String,
    closeCurrent: Boolean = false
) {
    val encodedLenderStatusData = Uri.encode(lenderStatusData)
    val destinationUri = "${AppScreens.FormSubmissionWebScreen.route}/$fromFlow/$encodedLenderStatusData"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}
fun navigateToConsentSubmissionWebScreen(
    navController: NavHostController,
    fromFlow: String, lenderStatusData:String,
    closeCurrent: Boolean = false
) {
    val encodedLenderStatusData = Uri.encode(lenderStatusData)
    val destinationUri = "${AppScreens.ConsentSubmissionWebScreen.route}/$fromFlow/$encodedLenderStatusData"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToRepaymentScreen(
    navController: NavHostController,
    transactionId: String,
    url: String,
    id: String,
    fromFlow:
        String,
    closeCurrent: Boolean = false
) {
    // Encode the responseItem as URI component to ensure it is properly formatted
    val encodedUrl = Uri.encode(url)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.RepaymentWebScreen.route}/$transactionId/$encodedUrl/$id/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}


fun navigateToLoanAgreementScreen(
    navController: NavHostController,
    transactionId: String,
    id: String,
    fromFlow: String,
    url: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(url)
    val destinationUri = "${AppScreens.LoanAgreementWebScreen.route}/$transactionId/$id/$fromFlow/$encodedUrl"

    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToAnimationLoader(
    navController: NavHostController,
    transactionId: String,
    id: String,
    fromFlow: String,
    closeCurrent: Boolean = false,
    url : String = "No data"
) {

    val loanAgreementURL = Uri.encode(url)
    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.AnimationLoader.route}/$transactionId/$id/$fromFlow/$loanAgreementURL"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}
fun navigateToLoanAgreementAnimationLoader(
    navController: NavHostController,
    transactionId: String,
    id: String,
    fromFlow: String,
    formUrl:String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(formUrl)
    val destinationUri = "${AppScreens.LoanAgreementAnimationLoader.route}/$transactionId/$id/$fromFlow/$encodedUrl"
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToKYCFailedScreen(
    navController: NavHostController
) {
    navController.navigate(AppScreens.KycFailedScreen.route) {
        shouldCloseCurrent(navController, true)
    }
}

fun navigateToEMandateESignFailedScreen(
    navController: NavHostController,
    title: String
) {
    val destinationUri = "${AppScreens.EMandateESignFailedScreen.route}/$title"
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, true)
    }
}

fun navigateToKycAnimation(
    navController: NavHostController,
    transactionId: String,
    offerId: String,
    responseItem: String,
    closeCurrent: Boolean = false,
    fromFlow: String
) {
    val encodedUrl = Uri.encode(responseItem)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.KycAnimation.route}/$transactionId/$encodedUrl/$offerId/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPrePaymentStatusScreen(
    navController: NavHostController,
    orderId: String,
    headerText: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedOffer = Uri.encode(orderId)
    val encodeHeaderText = Uri.encode(headerText)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri =
        "${AppScreens.PrePaymentStatusScreen.route}/$encodedOffer/$encodeHeaderText/$fromFlow"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToCreateIssueScreen(
    navController: NavHostController,
    orderId: String,
    providerId: String,
    orderState: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinationPath =
        "${AppScreens.CreateIssueScreen.route}/$orderId/$providerId/$orderState/$fromFlow"
    navController.navigate(destinationPath) {
        launchSingleTop = true
        if (closeCurrent) {
            popUpTo(AppScreens.CreateIssueScreen.route) {
                inclusive = true
                saveState = false
            }
        }
    }
}

fun navigateToPrePaymentWebViewScreen(
    navController: NavHostController,
    orderId: String,
    headerText: String,
    status: String,
    fromFlow: String,
    paymentOption: String,
    closeCurrent: Boolean = false
) {
    val encodedOffer = Uri.encode(orderId)
    val encodeHeaderText = Uri.encode(headerText)
    val encodePaymentStatus = Uri.encode(status)

    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri =
        "${AppScreens.PrePaymentWebViewScreen.route}/$encodedOffer/$encodeHeaderText/$encodePaymentStatus/$fromFlow/$paymentOption"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToBankDetailsScreen(
    navController: NavHostController,
    id: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    navController.navigate("${AppScreens.BankDetailScreen.route}/$id/$fromFlow") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToIssueListScreen(
    navController: NavHostController,
    orderId: String,
    loanState: String,
    providerId: String,
    fromFlow: String,
    fromScreen: String,
    closeCurrent: Boolean = false
) {
    val navigatePath =
        "${AppScreens.IssueListScreen.route}/$orderId/$loanState/$providerId/$fromFlow/$fromScreen"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateTOUnexpectedErrorScreen(
    navController: NavHostController,
    closeCurrent: Boolean = true
) {
    navController.navigate(AppScreens.UnexpectedErrorScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

// Gst Loan App Navigation

fun navigateToGstInvoiceLoanScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstInvoiceLoanScreen.route}/$fromFlow"
    navController.navigate(destinatioUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstDetailsScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstDetailsScreen.route}/$fromFlow"
    navController.navigate(destinatioUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstInformationScreen(
    navController: NavHostController,
    fromFlow: String,
    invoiceId: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstInformationScreen.route}/$fromFlow/$invoiceId"
    navController.navigate(destinatioUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToBankKycVerificationScreen(
    navController: NavHostController,
    transactionId: String,
    kycUrl: String,
    offerId: String,
    verificationStatus: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedUrl = Uri.encode(kycUrl)
    navController.navigate(
        "${AppScreens.BankKycVerificationScreen
            .route}/$transactionId/$encodedUrl/$offerId/$verificationStatus/$fromFlow"
    ) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstNumberVerifyScreen(
    navController: NavHostController,
    mobileNumber: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstNumberVerifyScreen.route}/$mobileNumber/$fromFlow"
    navController.navigate(destinatioUrl)
    shouldCloseCurrent(navController, closeCurrent)
}

fun navigateToGstInvoiceDetailScreen(
    navController: NavHostController,
    fromFlow: String,
    invoiceId: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstInvoiceDetailScreen.route}/$fromFlow/$invoiceId"
    navController.navigate(destinatioUrl)
    shouldCloseCurrent(navController, closeCurrent)
}

fun navigateToGstInvoiceLoansScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val destinatioUrl = "${AppScreens.GstInvoiceLoansScreen.route}/$fromFlow"
    navController.navigate(destinatioUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToInvoiceDetailScreen(
    navController: NavHostController,
    fromFlow: String,
    invoiceId: String,
    closeCurrent: Boolean = false
) {
    val encodeData = Uri.encode(invoiceId)
    val destinatioUrl = "${AppScreens.InvoiceDetailScreen.route}/$fromFlow/$encodeData"
    navController.navigate(destinatioUrl) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstLoanOfferListScreen(
    navController: NavHostController,
    offerResponse: String,
    transactionId: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedOfferResponse = Uri.encode(offerResponse)
    val navigatePath = "${AppScreens.GstLoanOfferListScreen
        .route}/$transactionId/$encodedOfferResponse/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstKycWebViewScreen(
    navController: NavHostController,
    transactionId: String,
    kycUrl: String,
    offerId: String,
    fromScreen: String,
    fromFlow: String,
    closeCurrent: Boolean = true
) {
    val encodedUrl = Uri.encode(kycUrl)
    navController.navigate(
        "${AppScreens.GstKycWebViewScreen
            .route}/$transactionId/$encodedUrl/$offerId/$fromScreen/$fromFlow"
    ) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToGstInvoiceLoanOfferScreen(
    navController: NavHostController,
    offerResponse: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedOfferResponse = Uri.encode(offerResponse)
    val navigatePath =
        "${AppScreens.GstInvoiceLoanOfferScreen.route}/$encodedOfferResponse/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToIssueDetailScreen(
    navController: NavHostController,
    issueId: String,
    orderId:String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedIssueId = Uri.encode(issueId)
    val encodedOrderId = Uri.encode(orderId)
    val navigatePath = "${AppScreens.IssueDetailScreen.route}/$encodedIssueId/$encodedOrderId/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

// Purchase Finance
fun navigateToDownPaymentScreen(
    navController: NavHostController,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    if (fromFlow.isEmpty()) {
        Log.e("navigateToDownPaymentScreen", "fromFlow is empty")
    }
    Log.e("navigateToDownPaymentScreen fromFlow", "${fromFlow}")
    navController.navigate("${AppScreens.DownPaymentScreen.route}/Purchase Finance") {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPfLoanOfferListScreen(
    navController: NavHostController,
    offerResponse: String,
    transactionId: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedOfferResponse = Uri.encode(offerResponse)
    val navigatePath = "${AppScreens.PfLoanOfferListScreen
        .route}/$transactionId/$encodedOfferResponse/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPfKycWebViewScreen(
    navController: NavHostController,
    transactionId: String,
    kycUrl: String,
    offerId: String,
    fromScreen: String,
    fromFlow: String,
    closeCurrent: Boolean = true
) {
    val encodedUrl = Uri.encode(kycUrl)
    navController.navigate(
        "${AppScreens.PfKycWebViewScreen
            .route}/$transactionId/$encodedUrl/$offerId/$fromScreen/$fromFlow"
    ) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPfLoanOfferScreen(
    navController: NavHostController,
    offerResponse: String,
    fromFlow: String,
    closeCurrent: Boolean = false
) {
    val encodedOfferResponse = Uri.encode(offerResponse)
    val navigatePath =
        "${AppScreens.PfLoanOfferScreen.route}/$encodedOfferResponse/$fromFlow"
    navController.navigate(navigatePath) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

// Documents
fun navigateToTermsConditionsScreen(
    navController: NavHostController,
    closeCurrent: Boolean = false
) {
    navController.navigate(AppScreens.TermsConditionsScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

fun navigateToPrivacyPolicyScreen(
    navController: NavHostController,
    closeCurrent: Boolean = false
) {
    navController.navigate(AppScreens.PrivacyPolicyScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}
fun navigateToAboutUsScreen(
    navController: NavHostController,
    closeCurrent: Boolean = false
) {
    navController.navigate(AppScreens.AboutUsScreen.route) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

// Negative Scenario
fun navigateToFormRejectedScreen(
    navController: NavHostController,
    fromFlow: String,
    errorTitle: String?,
    errorMsg: String?,
    closeCurrent: Boolean = false
) {
    // Construct the destination URI by appending the encoded responseItem to the route
    val destinationUri = "${AppScreens.FormRejectionScreen.route}/$fromFlow/$errorTitle/$errorMsg"

    // Navigate to the destination URI with proper navigation options
    navController.navigate(destinationUri) {
        shouldCloseCurrent(navController, closeCurrent)
    }
}

