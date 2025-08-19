package com.github.sugunasriram.fisloanlibv4.fiscode.network.core

import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods.Companion.BASE_URL

class ApiPaths {

    // Session related Api Paths
    val createSession = "superAppSessions/createSession"
    val verifySession = "superAppSessions/verifySession"

    // Base URL Flow Api Path
    val baseUrl = "/api/v1"

    // Auth Flow Api Paths
    val authLogIn = "auth/login"

    val authRoles = "auth/roles"

    val authGenerateOtp = "auth/generateOtp"

    val authLogOut = "auth/logout"

    val authSignIn = "auth/signUp"

    val authOtp = "auth/authOtp"

    val authGetAccessToken = "auth/getAccessToken"

    val profile = "users/profile"

    val updateProfile = "users/updateUserDetails"

    val updateUserIncome = "users/updateUserIncome"

    val getUserStatus = "users/getUserstatus"

    val status = "lender/status"

    val offerList = "users/offerList"

    val panVerification = "users/pan-verification"

    // Password related Api Paths
    val forgotPassword = "users/forgotPassword"

    val resetPassword = "users/resetPassword"

    val verifyOtp = "users/verifyOtp"

    val resendOTP = "auth/resendOTP"

    // Bank Related Api Paths
    val getBankAccounts = "Bank/getBankAccounts"

    val addBank = "Bank/addBank"

    val deleteBank = "Bank/delete"

    val updateBank = "Bank/update"

    val getBanksList = "static/getBanksList"

    // Server side Events Api Paths
    val sse = "${BASE_URL}" + "/api/v1/sse"
    // Prod
//    val sse = "https://ondcfs.jtechnoparks.in/jt-bap/api/v1/sse"
    // Preprod
//    val sse = "https://stagingondcfs.jtechnoparks.in/jt-bap/api/v1/sse"

    // PreProd - with and Without AA
//    val sse = "https://stagingondcfs.jtechnoparks.in/jt-bap-test/api/v1/sse"

    // Staging
//    val sse = "https://stagingondcfs.jtechnoparks.in/staging-jt-bap/api/v1/sse"

    // ONDC Flow Api paths
    val search = "lender/search"

    val formSubmission = "lender/formSubmissionRequest"

    val getLenderStatus = "lender/in-progress"

    val aaConsentApproval = "lender/customer_aa_consent_approval"

    val updateLoanAmount = "lender/update_loan_amount"

    val updateLoanAgreement = "lender/update_loan_agreement"

    val getIssueCategories = "static/getIssueCategories"

    val getIssueWithSubCategories = "static/getIssueWithSubCategories"

    val initialOfferSelect = "lender/offer_select"

    val loanApproved = "lender/loanApproved"

    val addAccountDetails = "lender/add_account_details"

    val getCustomerLoanList = "loans/getCustomerLoanList"

    val loanOrdersList = "loans/ordersList"

    val completeListOfOrders = "loans/completeListOfOrders"

    val getOrderStatus = "loans/getOrderStatus"

    val getOrderById = "loans/getOrderById"

    // IGm Related Api Paths
    val issueImageUpload = "issues/issueImageUpload"

    val createIssue = "issues/createIssue"

    val closeIssue = "issues/close"

    val issueList = "issues/issueList"

    val issueStatus = "issues/issue_status"

    val issueById = "issues/issueById"

    val checkOrderIssues = "issues/checkOrderIssues"

    val orderIssues = "issues/orderIssues"

    // Cygnet Related Api Paths

    val cygnetGenerateOtp = "cygnet/cygnetGenerateOtp"

    val verifyOtpForGstIn = "cygnet/verifyOtpForGstin"

    val gstInDetails = "cygnet/gstinDetails"

    // Document
    val aboutUs = "static/about-us"

    val termsOfUse = "static/terms-of-use"

    val privacyPolicy = "static/privacy-policy"

    val contactUs = "static/contact-us"


    //User Reported App issues
    val userReportedIssueCreate = "user-reported-issue/create"

    val userReportedIssueFindUserId = "user-reported-issue/findByUserId"
}
