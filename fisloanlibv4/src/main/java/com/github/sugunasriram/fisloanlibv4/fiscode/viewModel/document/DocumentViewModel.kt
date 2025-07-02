package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.document

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.AboutUsResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.ContactUsResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.PrivacyPolicyResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.TermsConditionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DocumentViewModel : BaseViewModel() {

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

    private val _checkBox: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkBox: LiveData<Boolean> = _checkBox

    fun onCheckChanged(checkBox: Boolean) {
        _checkBox.value = checkBox
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded

    private val _contactUsResponse = MutableStateFlow<ContactUsResponse?>(null)
    val contactUsResponse: StateFlow<ContactUsResponse?> = _contactUsResponse

    private val _privacyPolicyResponse = MutableStateFlow<PrivacyPolicyResponse?>(null)
    val privacyPolicyResponse: StateFlow<PrivacyPolicyResponse?> = _privacyPolicyResponse

    private val _aboutUsResponse = MutableStateFlow<AboutUsResponse?>(null)
    val aboutUsResponse: StateFlow<AboutUsResponse?> = _aboutUsResponse

    private val _termConditionResponse = MutableStateFlow<TermsConditionResponse?>(null)
    val termConditionResponse: StateFlow<TermsConditionResponse?> = _termConditionResponse

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun contactUs(context: Context) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleContactUs(context = context)
        }
    }
    private suspend fun handleContactUs(context: Context, checkForAccessToken: Boolean = true) {
        kotlin.runCatching {
            ApiRepository.contactUs()
        }.onSuccess { response ->
            handleContactUsSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleContactUs(context)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleDocumentFailure(error = error, context = context)
            }
            handleDocumentFailure(error = error, context = context)
        }
    }
    private suspend fun handleContactUsSuccess(response: ContactUsResponse?) {
        withContext(Dispatchers.Main) {
            _contactUsResponse.value = response
            _isLoading.value = false
            _isLoaded.value = true
        }
    }

    fun privacyPolicy(context: Context) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePrivacyPolicy(context = context)
        }
    }

    private suspend fun handlePrivacyPolicy(
        context: Context, checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.privacyPolicy()
        }.onSuccess { response ->
            handlePrivacyPolicySuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handlePrivacyPolicy(context = context)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleDocumentFailure(error = error, context = context)
            }
            handleDocumentFailure(error = error, context = context)
        }
    }

    private suspend fun handlePrivacyPolicySuccess(response: PrivacyPolicyResponse?) {
        withContext(Dispatchers.Main) {
            _privacyPolicyResponse.value = response
            _isLoading.value = false
            _isLoaded.value = true
        }
    }

    fun termsCondition(context: Context) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleTermsCondition(context = context)
        }
    }

    private suspend fun handleTermsCondition(
        context: Context, checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.termCondition()
        }.onSuccess { response ->
            handleTermConditionSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleTermsCondition(context = context)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleDocumentFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleTermConditionSuccess(response: TermsConditionResponse?) {
        withContext(Dispatchers.Main) {
            _termConditionResponse.value = response
            _isLoading.value = false
            _isLoaded.value = true
        }
    }

    fun aboutUs(context: Context) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleAboutUs(context = context)
        }
    }

    private suspend fun handleAboutUs(
        context: Context, checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.aboutUs()
        }.onSuccess { response ->
            handleAboutUsSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                //Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleAboutUs(context = context)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleDocumentFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleAboutUsSuccess(response: AboutUsResponse?) {
        withContext(Dispatchers.Main) {
            _aboutUsResponse.value = response
            _isLoading.value = false
            _isLoaded.value = true
        }
    }

    private suspend fun handleDocumentFailure(error: Throwable, context: Context) {
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
            _isLoading.value = false
        }
    }
}