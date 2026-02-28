package com.github.sugunasriram.fisloanlibv4.fiscode.network.core

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.GstOfferListModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.OfferListModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.PfOfferListModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.StatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.UserStatus
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AddBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AddBankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AuthOtp
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankAccount
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankList
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CancelLoan
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CancelLoanResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CreateSessionRequest
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CreateSessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Data
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.DocumentUpload
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ForgotPassword
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ForgotPasswordOtpVerify
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.GenerateAuthOtp
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.GstBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.IFSCResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.LoginDetails
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Logout
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.OrderPaymentStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PanVerificationResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PfBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PincodeModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ProfileResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.ResetPassword
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Signup
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateIncome
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateProfile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UserRole
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.AboutUsResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.ContactUsResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.PrivacyPolicyResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.TermsConditionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFDeleteUserBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFSearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstConsentResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOtpResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOtpVerify
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CheckOrderIssueModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CloseIssueBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CloseIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CreateIssueBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CreateIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUpload
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueByIdResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueCategories
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueListBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueListResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueSubCategories
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.OrderIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.ConsentApprovalRequest
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.ConsentApprovalResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.DeleteUserResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.GetLenderStatusModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LoanSearchResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OrderByIdResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateConsentHandler
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanAgreement
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanAmountBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanAmountPfResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PFLoanApprovedResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues.AllReportedUserIssuesResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues.UserReportedIssueCreateRequest
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues.UserReportedIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods.Companion.BASE_URL
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object ApiRepository {

    suspend fun verifySession(sessionId: String): VerifySessionResponse? {
        val requestBody = mapOf("id" to sessionId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifySession) {
                setBody(requestBody)
            }
        }
        return when (response.status.value) {
            200, 201 -> response.body<VerifySessionResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun createSession(createSessionRequest: CreateSessionRequest): CreateSessionResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().createSession) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(createSessionRequest)
            }.body()
        }
    }

    // Auth Flow Api Repository
    suspend fun signup(profile: Profile): Signup? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authSignIn) {
                setBody(profile)
            }.body()
        }
    }

    suspend fun userRole(): UserRole? {
        Log.d("res_H_baseURL",BASE_URL)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.get(ApiPaths().authRoles){
                contentType(ContentType.Application.Json)
            }
        }
        return when (response.status.value) {
            200, 201 -> response.body<UserRole>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun generateAuthOtp(mobileNumber: String, countryCode: String, role: String): GenerateAuthOtp? {
        val requestBody = mapOf("mobileNumber" to mobileNumber, "countryCode" to countryCode, "role" to role)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient
                .post(ApiPaths().authGenerateOtp) {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }
        }
        return when (response.status.value) {
            200, 201 -> response.body<GenerateAuthOtp>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun login(loginDetails: LoginDetails): AuthOtp? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient
                .post(ApiPaths().authLogIn) {
                    contentType(ContentType.Application.Json)
                    setBody(loginDetails)
                }
        }
        return when (response.status.value) {
            200, 201 -> response.body<AuthOtp>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun logout(refreshToken: String): Logout? {
        val requestBody = mapOf("refreshToken" to refreshToken)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authLogOut) {
                val accessToken = TokenManager.read("accessToken")
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }  }
        return when (response.status.value) {
            200, 201 -> response.body<Logout>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    private suspend fun authGetAccessToken(refreshToken: String): AuthOtp {
        val requestBody = mapOf("refreshToken" to refreshToken)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().authGetAccessToken) {
                setBody(requestBody)
            }  }
        return when (response.status.value) {
            200,201 -> response.body<AuthOtp>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
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
                        response.data?.sseId?.let { sseId ->
                            TokenManager.save("sseId", sseId)
                        }
                        return true
                    } ?: run {
                        // Handle case where accessToken is null
                        // For example, show an error or log the issue
                        Log.e("Auth", "AccessToken is null")
                        val response = false
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

    suspend fun getUserDetail(): UpdateProfile? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().profile) {
                val accessToken = TokenManager.read("accessToken")
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
            }
        }
        return when (response.status.value) {
            200, 201 -> response.body<UpdateProfile>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun updateUserDetails(profile: Profile): UpdateProfile? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateProfile) {
                val accessToken = TokenManager.read("accessToken")
                header("Authorization", "Bearer $accessToken")
                contentType(ContentType.Application.Json)
                setBody(profile)
            }  }
        return when (response.status.value) {
            200, 201 -> response.body<UpdateProfile>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun updateBankStatement(statements: List<String>): UpdateProfile? {
        val requestBody = mapOf("statements" to statements)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateProfile) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }.body()
        }
    }

    suspend fun updateUserIncome(income: String): UpdateIncome? {
        val requestBody = mapOf("income" to income)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateUserIncome) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UpdateIncome>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getUserNameFromPan(pan: String): PanVerificationResponse? {
        val requestBody = mapOf("panCard" to pan)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().panVerification) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PanVerificationResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getCity(pinCode: String): PincodeModel? {
        val apiKey = "AIzaSyDLZ2Sr9a8M9YKrWKv4vJ4V19z3VE8y39s"
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$pinCode&key=$apiKey"
        return try {
            withContext(Dispatchers.IO) {
                val client = HttpClient(Android)
                val response: String = client.get(url).body()
                client.close()
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "OK") {
                    val results = jsonObject.getJSONArray("results")
                    if (results.length() > 0) {
                        val firstResult = results.getJSONObject(0)
                        Log.d("res_HfirstResult", firstResult.toString())
                        val addressComponents = firstResult.getJSONArray("address_components")

                        val cities = mutableSetOf<String>()
                        var state = ""
                        var district = ""
                        for (i in 0 until addressComponents.length()) {
                            val component = addressComponents.getJSONObject(i)
                            val types = component.getJSONArray("types")
                            if (types.toString().contains("locality")) {
                                cities.add(component.getString("long_name"))
                            } else if (types.toString().contains("administrative_area_level_3")) {
                                district = component.getString("long_name")
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
                            PincodeModel(pinCode, cities.toList(), district, state)
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
        mobileNumber: String,
        mobileNumberCountryCode: String
    ): ForgotPassword? {
        val requestBody = mapOf(
            "mobile_number" to mobileNumber,
            "mobile_number_country_code" to mobileNumberCountryCode
        )
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().forgotPassword) {
                setBody(requestBody)
            }.body()
        }
    }

    suspend fun resetPasswordApi(
        newPassword: String,
        confirmPassword: String,
        mobileNumber: String,
        countryCode: String
    ): ResetPassword? {
        val requestBody = mapOf(
            "newPassword" to newPassword,
            "confirmPassword" to confirmPassword,
            "mobileNumber" to mobileNumber,
            "countryCode" to countryCode
        )
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().resetPassword) {
                setBody(requestBody)
            }.body()
        }
    }

    suspend fun forgotPasswordOtpVerify(orderId: String, otp: String): ForgotPasswordOtpVerify? {
        val requestBody = mapOf("orderId" to orderId, "otp" to otp)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifyOtp) {
                setBody(requestBody)
            }.body()
        }
    }

    suspend fun authResendOTP(orderId: String): Data? {
        val requestBody = mapOf("orderId" to orderId)
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().resendOTP) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }.body()
        }
    }

    // Bank Related Flow Api Repository
    suspend fun getBankAccount(): BankAccount? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getBankAccounts) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<BankAccount>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

//    suspend fun getBankName(ifscCode: String): IFSCResponse {
//        val url = "https://ifsc.razorpay.com/$ifscCode"
//
//        val client = HttpClient(OkHttp) {
//            install(ContentNegotiation) {
//                json(
//                    Json {
//                        ignoreUnknownKeys = true
//                        isLenient = true
//                    }
//                )
//            }
//            install(Logging) {
//                level = LogLevel.BODY
//            }
//        }
//
//        return client.use {
//            withContext(Dispatchers.IO) {
//                it.get(url).body()
//            }
//        }
//    }
suspend fun getBankName(ifscCode: String): IFSCResponse? {
    val code = ifscCode.trim().uppercase()
    if (code.isEmpty()) return null

    val url = "https://ifsc.razorpay.com/$code"

    return try {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.get(url) // no Authorization header for Razorpay IFSC endpoint
        }

        when (response.status.value) {
            200 -> response.body<IFSCResponse>()
            404 -> null
            else -> throw ResponseException(response, "IFSC lookup failed: ${response.status}")
        }
    } catch (e: Exception) {
        Log.e("getBankName", "Error: ${e.message}", e)
        null
    }
}

    suspend fun getBankList(): BankList? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getBanksList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<BankList>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun addBank(bankDetail: AddBankDetail): AddBankDetailResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addBank) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(bankDetail)
//                body = bankDetail
            }}
        return when (response.status.value) {
            200, 201 -> response.body<AddBankDetailResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    // Personal Loan Flow Api Repository
    suspend fun aaConsentApproval(aaConsentApproval: ConsentApprovalRequest): ConsentApprovalResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(aaConsentApproval)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<ConsentApprovalResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pLSearchApi(searchBodyModel: SearchBodyModel): LoanSearchResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(searchBodyModel)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<LoanSearchResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pFDeleteUserApi(deleteUserBodyModel: PFDeleteUserBodyModel): DeleteUserResponse? {
        return KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().deleteUser) {
                setBody(deleteUserBodyModel)
            }.body()
        }
    }

    suspend fun pFSearchApi(searchBodyModel: PFSearchBodyModel): LoanSearchResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(searchBodyModel)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<LoanSearchResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getLenderStatusApi(loanType: String, step: String): GetLenderStatusModel? {
        val requestBody = mapOf("loanType" to loanType, "step" to step)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getLenderStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GetLenderStatusModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    // searchApi with AA Consent
    suspend fun formSubmissionApi(searchBodyModel: SearchBodyModel): SearchModel? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().formSubmission) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(searchBodyModel)
            }}
    return when (response.status.value) {
        200, 201 -> response.body<SearchModel>()
        401 -> throw ClientRequestException(response, "Unauthorized")
        else -> throw ResponseException(response, "Unexpected error")
    }
}

    suspend fun updateConsentHandler(updateConsentHandlerBody: UpdateConsentHandlerBody): UpdateConsentHandler? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAgreement) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(updateConsentHandlerBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UpdateConsentHandler>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun updateLoanAgreement(updateLoanBody: UpdateLoanBody): UpdateLoanAgreement? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAgreement) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(updateLoanBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UpdateLoanAgreement>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getCustomerLoanList(loanType: String): CustomerLoanList? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getCustomerLoanList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<CustomerLoanList>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun cancelLoan(cancelLoan: CancelLoan): CancelLoanResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().cancelLoan) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(cancelLoan)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<CancelLoanResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun completeLoanOrders(): CustomerLoanList? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().completeListOfOrders) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<CustomerLoanList>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun loanOrdersByLoanType(loanType: String): CustomerLoanList? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().loanOrdersList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
//                body = requestBody
            }}
        return when (response.status.value) {
            200, 201 -> response.body<CustomerLoanList>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun updateLoanAmount(updateLoanAmountBody: UpdateLoanAmountBody): UpdateResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(updateLoanAmountBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UpdateResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun addAccountDetail(bankDetail: BankDetail): BankDetailResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(bankDetail)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<BankDetailResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun updateBank(bankDetail: UpdateBankDetail): AddBankDetailResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateBank) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(bankDetail)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<AddBankDetailResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun deleteAccountDetail(id: String): AddBankDetailResponse? {
        val requestBody = mapOf("_id" to id)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().deleteBank) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<AddBankDetailResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getOrderPaymentStatus(
        loanType: String,
        loanId: String
    ): OrderPaymentStatusResponse? {
        val requestBody = mapOf("loanType" to loanType, "orderId" to loanId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getOrderStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<OrderPaymentStatusResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    // Gst Flow Api Repository
    suspend fun gstSearch(gstSearchBody: GstSearchBody): GstSearchResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().search) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(gstSearchBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstSearchResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstConsentApproval(aaConsentApproval: ConsentApprovalRequest): GstConsentResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(aaConsentApproval)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstConsentResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstConfirmOffer(gstOfferConfirm: GstOfferConfirm): GstOfferConfirmResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(gstOfferConfirm)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstInitiateOffer(id: String, loanType: String): GstOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().initialOfferSelect) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstLoanApproved(id: String, loanType: String): GstOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().loanApproved) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstLoanEntityApproval(bankDetail: GstBankDetail): GstOfferConfirmResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(bankDetail)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getOrderById(loanType: String, orderId: String): OrderByIdResponse? {
        Log.d("Sugu loanType", loanType)
        val requestBody = mapOf("loanType" to loanType, "orderId" to orderId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getOrderById) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<OrderByIdResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    // Purchase Finance
    suspend fun pfConfirmOffer(pfOfferConfirm: PfOfferConfirm): PfOfferConfirmResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().updateLoanAmount) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(pfOfferConfirm)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PfOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pfInitiateOffer(id: String, loanType: String): PfOfferConfirmResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().initialOfferSelect) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PfOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pfLoanApproved(id: String, loanType: String): PFLoanApprovedResponse? {
        val requestBody = mapOf("id" to id, "loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().loanApproved) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PFLoanApprovedResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pfLoanEntityApproval(bankDetail: PfBankDetail): PfOfferConfirmResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().addAccountDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(bankDetail)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PfOfferConfirmResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    // Igm Flow Api Repository
    suspend fun getIssueCategories(): IssueCategories? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getIssueCategories) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<IssueCategories>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getIssueWithSubCategories(category: String): IssueSubCategories? {
        val requestBody = mapOf("category" to category)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getIssueWithSubCategories) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<IssueSubCategories>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun imageUpload(imageUploadBody: ImageUploadBody): ImageUpload? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueImageUpload) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(imageUploadBody)
            }}
        return when (response.status.value) {
            200,201 -> response.body<ImageUpload>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun documentUpload(imageUploadBody: ImageUploadBody): DocumentUpload? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueImageUpload) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(imageUploadBody)
            }}
        return when (response.status.value) {
            200,201 -> response.body<DocumentUpload>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun createIssue(createIssueBody: CreateIssueBody): CreateIssueResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().createIssue) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(createIssueBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<CreateIssueResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getIssueListForUser(issueListBody: IssueListBody): IssueListResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(issueListBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<IssueListResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun closeIssue(closeIssueBody: CloseIssueBody): CloseIssueResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().closeIssue) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(closeIssueBody)
            }}
        Log.d("res_Hapirepo",response.status.value.toString())
        return when (response.status.value) {
            200, 201 -> response.body<CloseIssueResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun issueStatus(issueId: String): IssueStatusResponse? {
        val requestBody = mapOf("issueId" to issueId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<IssueStatusResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun issueById(issueId: String): IssueByIdResponse? {
        val requestBody = mapOf("issue_id" to issueId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().issueById) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<IssueByIdResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun checkOrderIssues(orderId: String): CheckOrderIssueModel? {
        val requestBody = mapOf("order_id" to orderId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().checkOrderIssues) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200,201 -> response.body<CheckOrderIssueModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun orderIssues(orderId: String): OrderIssueResponse? {
        val requestBody = mapOf("order_id" to orderId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().orderIssues) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<OrderIssueResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun cygnetGenerateOtp(gstIn: String, username: String): GstOtpResponse? {
        val requestBody = mapOf("gstin" to gstIn, "username" to username)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().cygnetGenerateOtp) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOtpResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun verifyOtpForGstIn(id: String, otp: String): GstOtpVerify? {
        val requestBody = mapOf("id" to id, "otp" to otp)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().verifyOtpForGstIn) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOtpVerify>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstInvoices(gstIn: String): GstInvoice? {
        val requestBody = mapOf("gstin" to gstIn)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().gstInDetails) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstInvoice>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getUserStatus(loanType: String): UserStatus? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().getUserStatus) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UserStatus>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun status(loanType: String, orderId: String): StatusResponse? {
        val requestBody = mapOf("loanType" to loanType, "orderId" to orderId)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().status) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<StatusResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    // Document Related Api Repo
    suspend fun contactUs(): ContactUsResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.get(ApiPaths().contactUs) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<ContactUsResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun privacyPolicy(): PrivacyPolicyResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().privacyPolicy) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PrivacyPolicyResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun termCondition(): TermsConditionResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().termsOfUse) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<TermsConditionResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun aboutUs(): AboutUsResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aboutUs) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<AboutUsResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun offerList(loanType: String): OfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<OfferListModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun gstOfferList(loanType: String): GstOfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<GstOfferListModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun pfOfferList(loanType: String): PfOfferListModel? {
        val requestBody = mapOf("loanType" to loanType)
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().offerList) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<PfOfferListModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }
    suspend fun pFFormSubmissionApi(financeSearchModel: FinanceSearchModel): SearchModel? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().formSubmission) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(financeSearchModel)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<SearchModel>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun financeConsentApproval(aaConsentApproval: ConsentApprovalRequest): ConsentApprovalResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().aaConsentApproval) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(aaConsentApproval)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<ConsentApprovalResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun userReportedIssueCreate(requestBody: UserReportedIssueCreateRequest): UserReportedIssueResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.post(ApiPaths().userReportedIssueCreate) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
                setBody(requestBody)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<UserReportedIssueResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }

    suspend fun getAllUserReportedIssueFindUserId(): AllReportedUserIssuesResponse? {
        val response = KtorClient.getInstance().use { httpClient ->
            httpClient.get(ApiPaths().userReportedIssueFindUserId) {
                val accessToken = TokenManager.read("accessToken")
                val bearerToken = "Bearer $accessToken"
                header("Authorization", bearerToken)
            }}
        return when (response.status.value) {
            200, 201 -> response.body<AllReportedUserIssuesResponse>()
            401 -> throw ClientRequestException(response, "Unauthorized")
            else -> throw ResponseException(response, "Unexpected error")
        }
    }


}