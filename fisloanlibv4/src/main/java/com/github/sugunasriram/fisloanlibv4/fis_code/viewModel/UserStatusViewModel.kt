package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.StatusResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.UserStatus
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserStatusViewModel : BaseViewModel() {

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

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _userStatus = MutableStateFlow<UserStatus?>(null)
    val userStatus: StateFlow<UserStatus?> = _userStatus

    private val _checkingStatus = MutableStateFlow(false)
    val checkingStatus: StateFlow<Boolean> = _checkingStatus

    private val _checked = MutableStateFlow(false)
    val checked: StateFlow<Boolean> = _checked


    private val _navigationToSignup = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignup

    fun getUserStatus(loanType: String, context: Context) {
        _checkingStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstUserStatus(loanType = loanType, context = context)
        }
    }

    private suspend fun handleGstUserStatus(loanType: String, context: Context,
                                            checkForAccessToken: Boolean = true) {
        kotlin.runCatching {
            ApiRepository.getUserStatus(loanType = loanType)
        }.onSuccess { response ->
            handleGstUserStatusSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleGstUserStatus(
                        loanType = loanType, context = context, checkForAccessToken =
                        false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleGstUserStatusFailure(error = error, context = context)

            }
        }
    }

    private suspend fun handleGstUserStatusSuccess(response: UserStatus?) {
        withContext(Dispatchers.Main) {
            _checkingStatus.value = false
            _checked.value = true
            _userStatus.value = response
        }
    }

    private suspend fun handleGstUserStatusFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error, context = context, updateErrorMessage = ::updateErrorMessage,
                    _showServerIssueScreen = _showServerIssueScreen, _middleLoan = _middleLoan,
                    _unAuthorizedUser = _unAuthorizedUser, _unexpectedError = _unexpectedError,
                    _showLoader = _showLoader
                )
            } else {
                CommonMethods().handleGeneralException(
                    error = error, _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen, _unexpectedError = _unexpectedError
                )
            }
            _checkingStatus.value = false
        }
    }

    private val _status = MutableStateFlow<StatusResponse?>(null)
    val status: StateFlow<StatusResponse?> = _status

    fun status(loanType: String,orderId:String, context: Context) {
        _checkingStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleStatus(loanType = loanType, context = context,orderId = orderId)
        }
    }

    private suspend fun handleStatus(loanType: String, context: Context,orderId: String,
                                     checkForAccessToken: Boolean = true) {
        kotlin.runCatching {
            ApiRepository.status(loanType = loanType,orderId = orderId)
        }.onSuccess { response ->
            handleStatusSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleStatus(
                        loanType = loanType, context = context, orderId=orderId,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleGstUserStatusFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleStatusSuccess(response: StatusResponse?) {
        withContext(Dispatchers.Main) {
            _checkingStatus.value = false
            _checked.value = true
            _status.value = response
        }
    }
}