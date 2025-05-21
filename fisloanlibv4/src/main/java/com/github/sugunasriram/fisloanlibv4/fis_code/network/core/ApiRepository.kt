package com.github.sugunasriram.fisloanlibv4.fis_code.network.core

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.GstOfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.OfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.PfOfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.StatusResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.UserStatus
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.AddBankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.AddBankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.AuthOtp
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankAccount
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankList
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Data
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.ForgotPassword
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.ForgotPasswordOtpVerify
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.GenerateAuthOtp
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.GstBankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.IFSCResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.LogIn
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.LoginDetails
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Logout
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.OrderPaymentStatusResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.PfBankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.PincodeModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.ResetPassword
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Signup
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.UpdateIncome
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.UpdateProfile
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.UserRole
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.AboutUsResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.PrivacyPolicyResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.TermsConditionResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstConsentResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOtpResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOtpVerify
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CheckOrderIssueModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CloseIssueBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CloseIssueResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CreateIssueBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CreateIssueResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.ImageUpload
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueByIdResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueCategories
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueListBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueListResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueStatusResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueSubCategories
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.OrderIssueResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalRequest
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OrderByIdResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandler
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanAgreement
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanAmountBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanAmountPfResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.pf.PfOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.storage.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object ApiRepository {

    suspend fun verifySession(sessionId: String): AuthOtp? {
        val requestBody = mapOf("id" to sessionId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifySession) {
                body = requestBody
            }
        }
    }
    //Auth Flow Api Repository
    suspend fun signup(profile: Profile): Signup? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authSignIn) {
                body = profile
            }
        }
    }

    suspend fun userRole(): UserRole? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.get(ApiPaths().authRoles) {
            }
        }
    }

    suspend fun generateAuthOtp(mobileNumber: String, countryCode: String,role:String): GenerateAuthOtp? {
        val requestBody = mapOf("mobileNumber" to mobileNumber, "countryCode" to countryCode, "role" to role)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authGenerateOtp) {
                body = requestBody
            }
        }
    }

    suspend fun login(loginDetails: LoginDetails): AuthOtp? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authLogIn) {
                body = loginDetails
            }
        }
    }

    suspend fun logout(refreshToken: String): Logout? {
        val requestBody = mapOf("refreshToken" to refreshToken)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authLogOut) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun authOtp(orderId: String, otp: String): AuthOtp? {
        val requestBody = mapOf("orderId" to orderId, "otp" to otp)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authOtp) {
                body = requestBody
            }
        }
    }

    private suspend fun authGetAccessToken(refreshToken: String): AuthOtp {
        val requestBody = mapOf("refreshToken" to refreshToken)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authGetAccessToken) {
                body = requestBody
            }
        }
    }

    /*
    * This function will read REFRESHTOKEN and get ACCESSTOKEN from BE
    * ACCESSTOKEN & REFRESHTOKEN are stored in SharedPreference
    * if ACCESSTOKEN is NULL, return FALSE, so that LOGIN Screen can be shown to user.
     */
    suspend fun handleAuthGetAccessTokenApi(): Boolean {
        val refreshToken = TokenManager.read("refreshToken")
        refreshToken?.let {
            kotlin.runCatching {
                authGetAccessToken(refreshToken)
            }.onSuccess { response ->
                response.let {
                    val accessToken = it.data?.accessToken
                    accessToken?.let {
                        // Save the access token to SharedPreferences or use it as needed
                        response.data?.accessToken?.let { accessToken ->
                            TokenManager.save("accessToken", accessToken)
                        }
                        response.data?.refreshToken?.let { refreshToken ->
                            TokenManager.save("refreshToken", refreshToken)
                        }
                        return true
                    } ?: run {
                        // Handle case where accessToken is null
                        // For example, show an error or log the issue
                        Log.e("Auth", "AccessToken is null")
                        return false

                    }
                }
            }.onFailure { error ->
//            handleAuthOtpFailure(error, context )
                Log.e("Auth", "Error fetching access token: ${error.localizedMessage}")
                return false
            }
        }
        return false
    }

    suspend fun getUserDetail(): ProfileResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().profile) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun updateUserDetails(profile: Profile): UpdateProfile? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateProfile) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = profile
            }
        }
    }

    suspend fun updateUserIncome(income: String): UpdateIncome? {
        val requestBody = mapOf("income" to income)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateUserIncome) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun getCity(pinCode: String): PincodeModel? {
        val apiKey = "AIzaSyB6XW-WRFTFqOx05Xwi2Ek5ygSGnbyLVsQ"
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$pinCode&key=$apiKey"
        return try {
            withContext(Dispatchers.IO) {
                val client = HttpClient(Android)
                val response: String = client.get(url)
                client.close()

                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "OK") {
                    val results = jsonObject.getJSONArray("results")
                    if (results.length() > 0) {
                        val firstResult = results.getJSONObject(0)
                        val addressComponents = firstResult.getJSONArray("address_components")

                        val cities = mutableSetOf<String>()
                        var state = ""
                        for (i in 0 until addressComponents.length()) {
                            val component = addressComponents.getJSONObject(i)
                            val types = component.getJSONArray("types")
                            if (types.toString().contains("locality") || types.toString().contains("administrative_area_level_3")) {
                                cities.add(component.getString("long_name"))
                            } else if (types.toString().contains("administrative_area_level_1")) {
                                state = component.getString("long_name")
                            }
                        }

                        if (firstResult.has("postcode_localities")) {
                            val postcodeLocalities = firstResult.getJSONArray("postcode_localities")
                            for (i in 0 until postcodeLocalities.length()) {
                                cities.add(postcodeLocalities.getString(i))
                            }
                        }

                        if (cities.isNotEmpty() && state.isNotEmpty()) {
                            PincodeModel(pinCode, cities.toList(), state)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "Exception: ${e.message}")
            null
        }
    }

    // Password Related Flow Api Repository
    suspend fun forgotPasswordApi(
        mobileNumber: String, mobileNumberCountryCode: String
    ): ForgotPassword? {
        val requestBody = mapOf(
            "mobile_number" to mobileNumber,
            "mobile_number_country_code" to mobileNumberCountryCode
        )
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().forgotPassword) {
                body = requestBody
            }
        }
    }

    suspend fun resetPasswordApi(
        newPassword: String, confirmPassword: String, mobileNumber: String, countryCode: String
    ): ResetPassword? {
        val requestBody = mapOf(
            "newPassword" to newPassword, "confirmPassword" to confirmPassword,
            "mobileNumber" to mobileNumber, "countryCode" to countryCode
        )
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().resetPassword) {
                body = requestBody
            }
        }
    }

    suspend fun forgotPasswordOtpVerify(orderId: String, otp: String): ForgotPasswordOtpVerify? {
        val requestBody = mapOf("orderId" to orderId, "otp" to otp)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifyOtp) {
                body = requestBody
            }
        }
    }

    suspend fun authResendOTP(orderId: String): Data? {
        val requestBody = mapOf("orderId" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().resendOTP) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    // Bank Related Flow Api Repository
    suspend fun getBankAccount(): BankAccount? {
        return KtorClient.getInstance().use { httpClient ->
//            httpClient.get(ApiPaths().getBankAccounts) {
            httpClient.post(ApiPaths().getBankAccounts) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun getBankName(ifscCode: String): IFSCResponse {
        val url = "https://ifsc.razorpay.com/$ifscCode"
        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return client.use {
            withContext(Dispatchers.IO) {
                it.get(url)
            }
        }
    }

    suspend fun getBankList(): BankList? {
        return KtorClient.getInstance().use { httpClient ->
//            httpClient.get(ApiPaths().getBanksList) {
            httpClient.post(ApiPaths().getBanksList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun addBank(bankDetail: AddBankDetail): AddBankDetailResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addBank) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = bankDetail
            }
        }
    }

    // Personal Loan Flow Api Repository
    suspend fun aaConsentApproval(aaConsentApproval: ConsentApprovalRequest): ConsentApprovalResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = aaConsentApproval
            }
        }
    }
//searchApi with AA Consent
    suspend fun searchApi(searchBodyModel: SearchBodyModel): SearchModel? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = searchBodyModel
            }
        }
    }

    suspend fun updateConsentHandler(updateConsentHandlerBody: UpdateConsentHandlerBody): UpdateConsentHandler? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAgreement) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = updateConsentHandlerBody
            }
        }
    }

    suspend fun updateLoanAgreement(updateLoanBody: UpdateLoanBody): UpdateLoanAgreement? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAgreement) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = updateLoanBody
            }
        }
    }

    suspend fun getCustomerLoanList(loanType: String): CustomerLoanList? {
        val requestBody = mapOf("loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getCustomerLoanList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun completeLoanOrders(): CustomerLoanList? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().completeListOfOrders) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun updateLoanAmount(updateLoanAmountBody: UpdateLoanAmountBody): UpdateResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = updateLoanAmountBody
            }
        }
    }

    suspend fun updatePfLoanAmount(updateLoanAmountBody: UpdateLoanAmountBody): UpdateLoanAmountPfResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = updateLoanAmountBody
            }
        }
    }

    suspend fun addAccountDetail(bankDetail: BankDetail): BankDetailResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = bankDetail
            }
        }
    }


    suspend fun getOrderPaymentStatus(
        loanType: String,
        loanId: String
    ): OrderPaymentStatusResponse? {
        val requestBody = mapOf("loanType" to loanType, "orderId" to loanId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getOrderStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    // Gst Flow Api Repository
    suspend fun gstSearch(gstSearchBody: GstSearchBody): GstSearchResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = gstSearchBody
            }
        }
    }

    suspend fun gstConsentApproval(aaConsentApproval: ConsentApprovalRequest): GstConsentResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = aaConsentApproval
            }
        }
    }

    suspend fun gstConfirmOffer(gstOfferConfirm: GstOfferConfirm): GstOfferConfirmResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = gstOfferConfirm
            }
        }
    }

    suspend fun gstInitiateOffer(id: String, loanType: String): GstOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().initialOfferSelect) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun gstLoanApproved(id: String, loanType: String): GstOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().loanApproved) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun gstLoanEntityApproval(bankDetail: GstBankDetail): GstOfferConfirmResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = bankDetail
            }
        }
    }

    suspend fun getOrderById(loanType: String, orderId: String): OrderByIdResponse? {
        val requestBody = mapOf("loanType" to loanType, "orderId" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getOrderById) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }



    //Purchase Finance

    suspend fun pfConfirmOffer(pfOfferConfirm: PfOfferConfirm): PfOfferConfirmResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = pfOfferConfirm
            }
        }
    }

    suspend fun pfInitiateOffer(id: String, loanType: String): PfOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().initialOfferSelect) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun pfLoanApproved(id: String, loanType: String): PfOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().loanApproved) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun pfLoanEntityApproval(bankDetail: PfBankDetail): PfOfferConfirmResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = bankDetail
            }
        }
    }




    //Igm Flow Api Repository

    suspend fun getIssueCategories(): IssueCategories? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getIssueCategories) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun getIssueWithSubCategories(category: String): IssueSubCategories? {
        val requestBody = mapOf("category" to category)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getIssueWithSubCategories) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun imageUpload(imageUploadBody: ImageUploadBody): ImageUpload? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueImageUpload) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = imageUploadBody
            }
        }
    }

    suspend fun createIssue(createIssueBody: CreateIssueBody): CreateIssueResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().createIssue) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = createIssueBody
            }
        }
    }

    suspend fun getIssueListForUser(issueListBody: IssueListBody): IssueListResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = issueListBody
            }
        }
    }

    suspend fun closeIssue(closeIssueBody: CloseIssueBody): CloseIssueResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().closeIssue) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = closeIssueBody
            }
        }
    }

    suspend fun issueStatus(issueId: String): IssueStatusResponse? {
        val requestBody = mapOf("issueId" to issueId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun issueById(issueId: String): IssueByIdResponse? {
        val requestBody = mapOf("issue_id" to issueId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueById) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun checkOrderIssues(orderId: String): CheckOrderIssueModel? {
        val requestBody = mapOf("order_id" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().checkOrderIssues) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun orderIssues(orderId: String): OrderIssueResponse? {
        val requestBody = mapOf("order_id" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().orderIssues) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun cygnetGenerateOtp(gstIn: String, username: String): GstOtpResponse? {
        val requestBody = mapOf("gstin" to gstIn, "username" to username)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().cygnetGenerateOtp) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun verifyOtpForGstIn(id: String, otp: String): GstOtpVerify? {
        val requestBody = mapOf("id" to id, "otp" to otp)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifyOtpForGstIn) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun gstInvoices(gstIn: String): GstInvoice? {
        val requestBody = mapOf("gstin" to gstIn)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().gstInDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun getUserStatus(loanType: String): UserStatus? {
        val requestBody = mapOf("loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getUserStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }
    suspend fun status(loanType: String,orderId:String): StatusResponse? {
        val requestBody = mapOf("loanType" to loanType,"orderId" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().status) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    // Document Related Api Repo

    suspend fun privacyPolicy(): PrivacyPolicyResponse? {
        return KtorClient.getInstance().use { httpClient ->
//            httpClient.get(ApiPaths().privacyPolicy) {
            httpClient.post(ApiPaths().privacyPolicy) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun termCondition(): TermsConditionResponse? {
        return KtorClient.getInstance().use { httpClient ->
//            httpClient.get(ApiPaths().termsOfUse) {
            httpClient.post(ApiPaths().termsOfUse) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }

    suspend fun aboutUs(): AboutUsResponse? {
        return KtorClient.getInstance().use { httpClient ->
//            httpClient.get(ApiPaths().aboutUs) {
            httpClient.post(ApiPaths().aboutUs) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }
        }
    }
    suspend fun offerList(loanType: String): OfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun gstOfferList(loanType: String): GstOfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }

    suspend fun pfOfferList(loanType: String): PfOfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = requestBody
            }
        }
    }


    suspend fun financeSearch(financeSearchModel: FinanceSearchModel): SearchModel? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = financeSearchModel
            }
        }
    }

    suspend fun financeConsentApproval(aaConsentApproval: ConsentApprovalRequest): ConsentApprovalResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                body = aaConsentApproval
            }
        }
    }
}
