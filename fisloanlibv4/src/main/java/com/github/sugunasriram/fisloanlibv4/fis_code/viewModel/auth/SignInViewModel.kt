package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth

import android.content.Context
import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.GenerateAuthOtp
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.LogIn
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.UserRole
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.regex.Pattern

class SignInViewModel : BaseViewModel() {

    private val _showInternetScreen = MutableLiveData<Boolean>(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData<Boolean>(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData<Boolean>(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unexpectedError = MutableLiveData<Boolean>(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError


    private val _mobileNumber: MutableLiveData<String?> = MutableLiveData("")
    val mobileNumber: LiveData<String?> = _mobileNumber

    private val _mobileNumberError: MutableLiveData<String?> = MutableLiveData(null)
    val mobileNumberError: LiveData<String?> = _mobileNumberError

    fun onMobileNumberChanged(mobileNumber: String) {
        val extractedNumbers = mobileNumber.replace(Regex("(^\\+91|[^0-9])"), "")
        if (extractedNumbers.length <= 10) {
            _mobileNumber.value = extractedNumbers
            updateGeneralError(null)
        } else {
            _mobileNumber.value = extractedNumbers.take(10)
            updateGeneralError("Mobile number must be 10 digits long")
        }
    }
    fun updateMobileNumberError(errorMsg: String?) {
        _mobileNumberError.value = errorMsg
    }

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError

    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    private fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    fun signInValidation(navController: NavHostController,
                         mobileNumber: String,
                         mobileNumberFocus: FocusRequester,
                         context: Context
    ){
        clearMessage()
        if (mobileNumber.trim().isEmpty()) {
            updateMobileNumberError(context.getString(R.string.please_enter_phone_number))
            mobileNumberFocus.requestFocus()
            requestKeyboard()
        } else if (!Pattern.compile("^[0-9]*\$").matcher(mobileNumber).find()) {
            updateMobileNumberError(context.getString(R.string.should_not_contain_characters_alphabets))
            mobileNumberFocus.requestFocus()
            requestKeyboard()
        } else if (mobileNumber.trim().length < 10) {
            updateMobileNumberError(context.getString(R.string.please_enter_valid_mobile_number))
            mobileNumberFocus.requestFocus()
            requestKeyboard()
        } else {
            getUserRole(mobileNumber, context.getString(R.string.country_code), context)
        }

    }

    private val _isLoginInProgress = MutableStateFlow(false)
    val isLoginInProgress: StateFlow<Boolean> = _isLoginInProgress

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    private val _loginSuccessData = MutableStateFlow<LogIn?>(null)
    val loginSuccessData: StateFlow<LogIn?> = _loginSuccessData

    private val _generatedOtpData = MutableStateFlow<GenerateAuthOtp?>(null)
    val generatedOtpData: StateFlow<GenerateAuthOtp?> = _generatedOtpData

    private val _userRoleSuccessData = MutableStateFlow<UserRole?>(null)
    val userRoleSuccessData: StateFlow<UserRole?> = _userRoleSuccessData

    fun getUserRole(
        mobileNumber: String,
        countryCode: String,
        context: Context
    ) {
        _isLoginInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGetUserRole(mobileNumber, countryCode, context)
        }
    }
    private suspend fun handleGetUserRole(
        mobileNumber: String,
        countryCode: String,
        context: Context
    ) {
        kotlin.runCatching {
            ApiRepository.userRole()
        }.onSuccess { response ->
            handleGetUserRoleSuccess(response,mobileNumber,countryCode,context)
        }.onFailure { error ->
            handleGenerateLoginOtpFailure(error, context)
        }
    }
    private suspend fun handleGetUserRoleSuccess(response: UserRole?, mobileNumber: String,
                                                 countryCode: String,context: Context) {
        withContext(Dispatchers.Main) {
            if (response != null) {
                response.data?.find { it.role == "USER" }?.let { userRole ->
                    val userId = userRole._id
                    _userRoleSuccessData.value = response
                    generateLoginOtp(mobileNumber,countryCode,userId,context)
                }
            }
        }
    }

    private fun generateLoginOtp(
        mobileNumber: String,
        countryCode: String,userRole:String,
        context: Context
    ) {
        _isLoginInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGenerateLoginOtp(mobileNumber, countryCode,userRole, context)
        }
    }

    private suspend fun handleGenerateLoginOtp(
        mobileNumber: String,
        countryCode: String, userRole:String,
        context: Context
    ) {
        kotlin.runCatching {
            ApiRepository.generateAuthOtp(mobileNumber, countryCode,userRole)
        }.onSuccess { response ->
            handleGenerateLoginOtpSuccess(response)
        }.onFailure { error ->
            Log.d("Sugu : ",error.message.toString())
            handleGenerateLoginOtpFailure(error, context)
        }
    }

//    private suspend fun handleLoginSuccess(response: LogIn?) {
    private suspend fun handleGenerateLoginOtpSuccess(response: GenerateAuthOtp?) {
        withContext(Dispatchers.Main) {
            if (response != null) {
                _generatedOtpData.value = response
                _isLoginInProgress.value = false
                _isLoginSuccess.value = true
            }
        }
    }

    private suspend fun handleGenerateLoginOtpFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                handleLogInResponseException(error, context)
            } else {
                handleLogInGeneralException(error)
            }
            _isLoginInProgress.value = false
        }
    }

    private fun handleLogInResponseException(error: ResponseException, context: Context) {
        val statusCode = error.response.status.value
        when (statusCode) {
//            400 -> {
//                CommonMethods().toastMessage(context,context.getString(R.string.user_name_password_wrong))
//            }

            500 -> {
                _showServerIssueScreen.value = true
            }

            else -> {
                _unexpectedError.value = true
            }
        }
    }

    private fun handleLogInGeneralException(error: Throwable) {
        when (error) {
            is IOException -> _showInternetScreen.value = true
            is TimeoutCancellationException -> _showTimeOutScreen.value = true
            else -> _unexpectedError.value = true
        }
    }
    private val _shouldShowKeyboard: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowKeyboard: LiveData<Boolean> = _shouldShowKeyboard

    private fun requestKeyboard() {
        _shouldShowKeyboard.value = true
    }

    fun resetKeyboardRequest() {
        _shouldShowKeyboard.value = false
    }

}
