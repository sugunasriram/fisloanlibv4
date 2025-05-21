package com.github.sugunasriram.fisloanlibv4.fis_code.navigation

sealed class AppScreens(var route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object SignInScreen : AppScreens("sign_in_screen")
    object OtpScreen : AppScreens("otp_screen")
    object UpdateProfileScreen : AppScreens("update_profile_screen")
    object LanguageSelectionScreen : AppScreens("language_selection_screen")
    object ApplyByCategoryScreen : AppScreens("apply_by_category_screen")

    // Personal Loan Flow App Screens Navigation
    object PersonalLoanScreen : AppScreens("personal_loan_screen")
    object BasicDetailsScreen : AppScreens("basic_details_screen")
    object ReviewDetailsScreen : AppScreens("review_detail_screen")
    object BureauOffersScreen : AppScreens("bureau_offers_screen")
    object AccountAggregatorScreen : AppScreens("account_aggregator_screen")
    object SelectAccountAggregatorScreen : AppScreens("select_account_aggregator_screen")
    object SelectBankScreen : AppScreens("select_bank_screen")

    object AAConsentApprovalScreen : AppScreens("aa_consent_approval_screen")
    object LoanStatusScreen : AppScreens("loan_status_screen")
    object LoanListScreen : AppScreens("loan_list_screen")
    object LoanStatusDetailScreen : AppScreens("loan_status_detail_screen")
    object LoanOffersScreen : AppScreens("loan_offers_screen")
    object LoanOffersListScreen : AppScreens("loan_offers_list_screen")
    object ApplyBycategoryScreen : AppScreens("apply_by_category_screen")
    object LoanOffersListDetailScreen : AppScreens("loan_offers_list_detail_screen")
    object LoanSummary : AppScreens("dashboard_screen")
    object RepaymentScheduleScreen : AppScreens("repayment_Schedule_Screen")
    object EditLoanRequestScreen : AppScreens("edit_loan_request_screen")


    object LoanProcessScreen : AppScreens("loan_process_screen")

    object BankKycVerificationScreen : AppScreens("bank_kyc_verification_screen")
    object LoanDisbursementScreen : AppScreens("loan_disbursement_screen")
    object AccountDetailsScreen : AppScreens("account_details_screen")
    object SearchWebViewScreen : AppScreens("search_web_view_screen")
    object SearchWebView : AppScreens("search_web_view")
    object WebKycScreen : AppScreens("web_kyc_screen")
    object RepaymentWebScreen : AppScreens("repayment_web_screen")
    object LoanAgreementWebScreen : AppScreens("loan_agreement_web_screen")
    object AnimationLoader : AppScreens("animation_loader")
    object KycAnimation : AppScreens("kyc_animation")
    object KycFailedScreen: AppScreens("kyc_failed_screen")
    object EMandateESignFailedScreen: AppScreens("emandate_esign_failed_screen")
    object AccountAgreegatorScreen : AppScreens("account_agreegator_screen")
    object SelectAccountAgreegatorScreen : AppScreens("select_account_agreegator_screen")
    object PrePaymentStatusScreen : AppScreens("pre_payment_status_screen")
    object PrePaymentScreen : AppScreens("pre_payment_screen")
    object PrePaymentWebViewScreen : AppScreens("pre_payment_web_view_screen")
    object CreateIssueScreen : AppScreens("create_issue_pl_screen")
    object BankDetailScreen : AppScreens("bank_detail_screen")
    object IssueListScreen : AppScreens("issue_list_screen")
    object UnexpectedErrorScreen : AppScreens("un_expected_error_screen")

    //Negative Scenario
    object FormRejectionScreen : AppScreens("form_rejection_screen")


    // Gst Loan Flow App Screens Navigation
    object GstInvoiceLoanScreen : AppScreens("gst_invoice_loan_screen")
    object GstDetailsScreen : AppScreens("gst_details_screen")
    object GstInformationScreen : AppScreens("gst_information_screen")
    object GstNumberVerifyScreen : AppScreens("gst_number_verify_screen")
    object GstInvoiceDetailScreen : AppScreens("gst_invoice_detail_screen")
    object GstInvoiceLoansScreen : AppScreens("gst_invoice_loans_screen")
    object InvoiceDetailScreen : AppScreens("gst_due_date_screen")
    object GstLoanOfferListScreen : AppScreens("gst_loan_offer_screen")
    object GstKycWebViewScreen : AppScreens("gst_kyc_web_view_screen")
    object GstInvoiceLoanOfferScreen : AppScreens("gst_invoice_loan_offer_screen")
    object IssueDetailScreen : AppScreens("igm_issue_detail_screen")

    //Purchase Finance
    object DownPaymentScreen : AppScreens("down_payment_screen")
    object PfLoanOfferListScreen : AppScreens("pf_loan_offer_screen")
    object PfInvoiceLoanOfferScreen : AppScreens("pf_invoice_loan_offer_screen")
    object PfKycWebViewScreen : AppScreens("pf_kyc_web_view_screen")

    //Documents
    object TermsConditionsScreen :AppScreens("terms_conditions_screen")
    object PrivacyPolicyScreen : AppScreens("privacy_policy_screen")
    object AboutUsScreen : AppScreens("about_us_screen")

}
