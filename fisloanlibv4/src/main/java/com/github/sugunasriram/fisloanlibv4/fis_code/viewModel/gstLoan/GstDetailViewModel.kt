package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan

import android.content.Context
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOtpData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOtpResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOtpVerify
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class GstDetailViewModel : BaseViewModel() {

    private val _showInternetScreen = MutableLiveData(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unexpectedError = MutableLiveData(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _unAuthorizedUser = MutableLiveData(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _errorHandling = MutableLiveData(false)
    val errorHandling: LiveData<Boolean> = _errorHandling

    private val _paymentStatusListEmpty = MutableLiveData(false)
    val loanListEmpty: LiveData<Boolean> = _paymentStatusListEmpty

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _gstUserName: MutableLiveData<String> = MutableLiveData("")
    val gstUserName: LiveData<String> = _gstUserName

    fun onGstUserNameChanged(gstUserName: String) {
        if (gstUserName.length <= 30) {
            _gstUserName.value = gstUserName
        }
    }

    private val _gstNumber: MutableLiveData<String> = MutableLiveData("")
    val gstNumber: LiveData<String> = _gstNumber

    fun onGstNumberChanged(gstNumber: String) {
        if (gstNumber.length <= 15) {
            _gstNumber.value = gstNumber
        }
    }

    private val _gstNameError: MutableLiveData<String?> = MutableLiveData("")
    val gstNameError: LiveData<String?> = _gstNameError

    fun updateGstNameError(gstNameError: String?) {
        _gstNameError.value = gstNameError
    }

    private val _gstNumberError: MutableLiveData<String?> = MutableLiveData("")
    val gstNumberError: LiveData<String?> = _gstNumberError

    fun updateGstNumberError(gstNumberError: String?) {
        _gstNumberError.value = gstNumberError
    }

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError

    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    private fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    private val _shouldShowKeyboard: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowKeyboard: LiveData<Boolean> = _shouldShowKeyboard

    private fun requestKeyboard() {
        _shouldShowKeyboard.value = true
    }

    fun resetKeyboardRequest() {
        _shouldShowKeyboard.value = false
    }

    fun verifyGstValidation(
        gstNumber: String,
        gstUserName: String,
        navController: NavHostController,
        fromFlow: String,
        context: Context,
        focusGstName: FocusRequester,
        focusGstNumber: FocusRequester
    ) {
        clearMessage()
        if (gstUserName.trim().isEmpty()) {
            updateGstNameError(context.getString(R.string.enter_gst_name))
            focusGstName.requestFocus()
            requestKeyboard()
        } else if (!Pattern.compile("^[a-zA-Z ]*$").matcher(gstUserName).find()) {
            updateGstNameError(context.getString(R.string.character_special_validation))
            focusGstName.requestFocus()
            requestKeyboard()
        } else if (gstUserName.trim().length < 4) {
            updateGstNameError(context.getString(R.string.please_enter_valid_name))
            focusGstName.requestFocus()
            requestKeyboard()
        } else if (gstNumber.trim().isEmpty()) {
            updateGstNumberError(context.getString(R.string.enter_gst_number))
            focusGstNumber.requestFocus()
            requestKeyboard()
        } else if (CommonMethods().isValidGstNumber(gstNumber) != true) {
            updateGstNumberError(context.getString(R.string.enter_valid_gst_number))
            focusGstNumber.requestFocus()
            requestKeyboard()
        } else {
            cygnetGenerateOtp(gstin = gstNumber, username = gstUserName, context = context)
        }
    }

    private val _generatingOtp = MutableStateFlow(false)
    val generatingOtp: StateFlow<Boolean> = _generatingOtp

    private val _generatedOtp = MutableStateFlow(false)
    val generatedOtp: StateFlow<Boolean> = _generatedOtp

    private val _cygnetGenerateOtpResponse = MutableStateFlow<GstOtpResponse?>(null)
    val cygnetGenerateOtpResponse: StateFlow<GstOtpResponse?> = _cygnetGenerateOtpResponse

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun cygnetGenerateOtp(gstin: String, username: String, context: Context) {
        /** Commented temporarily - Sugu - to check
         _generatingOtp.value = true
         viewModelScope.launch(Dispatchers.IO) {
         handleCygnetGenerateOtp(gstin = gstin, username = username, context = context)
         }
         ***/

        // To remove below - Sugu
        _generatedOtp.value = true
        _generatingOtp.value = false
        _cygnetGenerateOtpResponse.value = dummyResponse()
    }

    fun dummyResponse(): GstOtpResponse {
        val gstOtpResponse = GstOtpResponse(
            data = GstOtpData(
                id = "999999999",
                message = null // You can replace `null` with an actual message if required
            ),
            status = true, // Replace with a value like `true` or `false` if required
            statusCode = 200 // Replace with an actual status code like `200` if required
        )
        return gstOtpResponse
    }

    private suspend fun handleCygnetGenerateOtp(
        gstin: String,
        username: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.cygnetGenerateOtp(gstIn = gstin, username = username)
        }.onSuccess { response ->
            handleCygnetGenerateOtpSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken && error is ResponseException && error.response.status.value == 401) {
                if (handleAuthGetAccessTokenApi()) {
                    handleCygnetGenerateOtp(
                        gstin = gstin,
                        username = username,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error, context)
            }
        }
    }

    private suspend fun handleCygnetGenerateOtpSuccess(response: GstOtpResponse?) {
        withContext(Dispatchers.Main) {
            _generatedOtp.value = true
            _generatingOtp.value = false
            _cygnetGenerateOtpResponse.value = response
        }
    }

    private val _verifyOtpForGstin = MutableStateFlow<GstOtpVerify?>(null)
    val verifyOtpForGstIn: StateFlow<GstOtpVerify?> = _verifyOtpForGstin

    fun verifyOtpForGstIn(id: String, otp: String, context: Context) {
        _generatingOtp.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleVerifyOtpForGstin(id = id, otp = otp, context = context)
        }
    }

    private suspend fun handleVerifyOtpForGstin(
        id: String,
        otp: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.verifyOtpForGstIn(id = id, otp = otp)
        }.onSuccess { response ->
            handleVerifyOtpForGstinSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken && error is ResponseException && error.response.status.value == 401) {
                if (handleAuthGetAccessTokenApi()) {
                    handleVerifyOtpForGstin(
                        id = id,
                        otp = otp,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error, context)
            }
        }
    }

    private fun handleVerifyOtpForGstinSuccess(response: GstOtpVerify?) {
        _generatedOtp.value = true
        _generatingOtp.value = false
        _verifyOtpForGstin.value = response
    }

    private suspend fun handleFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error,
                    context = context,
                    updateErrorMessage = ::updateErrorMessage,
                    _showServerIssueScreen = _showServerIssueScreen,
                    _middleLoan = _middleLoan,
                    _unAuthorizedUser = _unAuthorizedUser,
                    _unexpectedError = _unexpectedError,
                    _showLoader = _showLoader
                )
            } else {
                CommonMethods().handleGeneralException(
                    error = error,
                    _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen,
                    _unexpectedError = _unexpectedError
                )
            }
            _generatingOtp.value = false
        }
    }

    fun otpValidation(enteredOtp: String, orderId: String, context: Context) {
        clearMessage()
        when {
            enteredOtp.isBlank() -> CommonMethods().toastMessage(
                context = context,
                toastMsg = context.getString(R.string.enter_the_otp)
            )

            enteredOtp.trim().length < 6 -> CommonMethods().toastMessage(
                context = context,
                toastMsg = context.getString(R.string.enter_valid_otp)
            )

            else -> {
                verifyOtpForGstIn(id = orderId, otp = enteredOtp, context = context)
            }
        }
    }
}
