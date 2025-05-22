package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstConsentResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalRequest
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ConsentApprovalResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebViewModel : BaseViewModel() {

    private val _showInternetScreen = MutableLiveData(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _loanNotFound = MutableLiveData(false)
    val loanNotFound: LiveData<Boolean> = _loanNotFound

    private val _unexpectedError = MutableLiveData(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _unAuthorizedUser = MutableLiveData(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _searchFailed = MutableStateFlow(false)
    val searchFailed: StateFlow<Boolean> = _searchFailed

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _webProgress = MutableStateFlow(false)
    val webProgress: StateFlow<Boolean> = _webProgress

    private val _webViewLoaded = MutableStateFlow(false)
    val webViewLoaded: StateFlow<Boolean> = _webViewLoaded

    private val _searchResponse = MutableStateFlow<SearchModel?>(null)
    val searchResponse: StateFlow<SearchModel?> = _searchResponse

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    private fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun searchApi(context: Context, searchBodyModel: SearchBodyModel) {
        _webProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleSearchApi(context, searchBodyModel)
        }
    }

    private suspend fun handleSearchApi(
        context: Context,
        searchBodyModel: SearchBodyModel,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.searchApi(searchBodyModel)
        }.onSuccess { response ->
            Log.d("serachApi", response?.data.toString())
            Log.d("serachApi", response?.data?.consentResponse.toString())
            response?.let {
                handleSearchApiSuccess(response)
            }
        }.onFailure { error ->
            Log.d("serachApi", error.toString())
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleSearchApi(context, searchBodyModel, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error, context, isFormSearch = true)
            }
        }
    }

    private suspend fun handleSearchApiSuccess(response: SearchModel) {
        withContext(Dispatchers.Main) {
            _webViewLoaded.value = true
            _webProgress.value = false
            _searchResponse.value = response
        }
    }

    fun updateSearchResponse(response: SearchModel) {
        _webViewLoaded.value = true
        _searchResponse.value = response
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingSuccess = MutableStateFlow(false)
    val isLoadingSuccess: StateFlow<Boolean> = _isLoadingSuccess

    private val _consentApprovalResponse = MutableStateFlow<ConsentApprovalResponse?>(null)
    val consentApprovalResponse: StateFlow<ConsentApprovalResponse?> = _consentApprovalResponse

    fun aaConsentApprovalApi(context: Context, consentBodyModel: ConsentApprovalRequest) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleAAConsentApprovalApi(context, consentBodyModel)
        }
    }

    private suspend fun handleAAConsentApprovalApi(
        context: Context,
        consentBodyModel: ConsentApprovalRequest,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.aaConsentApproval(consentBodyModel)
        }.onSuccess { response ->
            response?.let {
                handleAAConsentApprovalApiSuccess(response)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleAAConsentApprovalApi(context, consentBodyModel, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error, context)
            }
        }
    }

    private suspend fun handleAAConsentApprovalApiSuccess(response: ConsentApprovalResponse) {
        withContext(Dispatchers.Main) {
            if (response.statusCode == 417) {
                _loanNotFound.value = true
            } else {
                _consentApprovalResponse.value = response
                _isLoading.value = false
                _isLoadingSuccess.value = true
            }
        }
    }

    private val apiCalled = false

    fun searchGst(gstSearchBody: GstSearchBody, context: Context, checkForAccessToken: Boolean = true) {
        if (apiCalled) return
        _webProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.gstSearch(gstSearchBody)
            }.onSuccess { response ->
                response?.let {
                    handleSearchGstSuccess(response)
                }
            }.onFailure { error ->
                if (checkForAccessToken &&
                    error is ResponseException &&
                    error.response.status.value == 401
                ) {
                    // Get Access Token using RefreshToken
                    if (handleAuthGetAccessTokenApi()) {
                        searchGst(gstSearchBody, context, false)
                    } else {
                        _navigationToSignIn.value = true
                    }
                } else {
                    handleFailure(error, context)
                }
            }
        }
    }

    private val _gstSearchResponse = MutableStateFlow<GstSearchResponse?>(null)
    val gstSearchResponse: StateFlow<GstSearchResponse?> = _gstSearchResponse

    private suspend fun handleSearchGstSuccess(response: GstSearchResponse) {
        withContext(Dispatchers.Main) {
            _webProgress.value = false
            _webViewLoaded.value = true
            _gstSearchResponse.value = response
        }
    }

    private val _gstConsentApprovalResponse = MutableStateFlow<GstConsentResponse?>(null)
    val gstConsentApprovalResponse: StateFlow<GstConsentResponse?> = _gstConsentApprovalResponse

    fun gstConsentApproval(
        consentApproval: ConsentApprovalRequest,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.gstConsentApproval(consentApproval)
            }.onSuccess { response ->
                response?.let {
                    handleConsentApprovalSuccess(response)
                }
            }.onFailure { error ->
                if (checkForAccessToken &&
                    error is ResponseException &&
                    error.response.status.value == 401
                ) {
                    if (handleAuthGetAccessTokenApi()) {
                        gstConsentApproval(
                            consentApproval,
                            context = context,
                            checkForAccessToken =
                            false
                        )
                    } else {
                        _navigationToSignIn.value = true
                    }
                } else {
                    handleFailure(error = error, context = context)
                }
            }
        }
    }

    private suspend fun handleConsentApprovalSuccess(response: GstConsentResponse) {
        withContext(Dispatchers.Main) {
            _isLoading.value = false
            _isLoadingSuccess.value = true
            _gstConsentApprovalResponse.value = response
        }
    }

    private suspend fun handleFailure(error: Throwable, context: Context, isFormSearch: Boolean = false) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error, context = context, updateErrorMessage = ::updateErrorMessage,
                    _showServerIssueScreen = _showServerIssueScreen, _middleLoan = _middleLoan,
                    _unAuthorizedUser = _unAuthorizedUser, _unexpectedError = _unexpectedError,
                    _showLoader = _showLoader, isFormSearch = isFormSearch,
                    searchError = { _searchFailed.value = true }
                )
            } else {
                CommonMethods().handleGeneralException(
                    error = error,
                    _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen,
                    _unexpectedError = _unexpectedError
                )
            }
            _isLoading.value = false
            _webProgress.value = false
        }
    }

    fun financeSearch(context: Context, financeSearchModel: FinanceSearchModel) {
        _webProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleFinanceSearch(
                context = context,
                financeSearchModel = financeSearchModel
            )
        }
    }

    private suspend fun handleFinanceSearch(
        financeSearchModel: FinanceSearchModel,
        checkForAccessToken: Boolean = true,
        context: Context
    ) {
        kotlin.runCatching {
            ApiRepository.financeSearch(financeSearchModel)
        }.onSuccess { response ->
            response?.let {
                handleSearchApiSuccess(response = response)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException && error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleFinanceSearch(
                        context = context,
                        financeSearchModel = financeSearchModel,
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

    fun financeConsentApproval(consentApproval: ConsentApprovalRequest, context: Context, checkForAccessToken: Boolean = true) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.financeConsentApproval(consentApproval)
            }.onSuccess { response ->
                response?.let {
                    financeConsentApprovalSuccess(response)
                }
            }.onFailure { error ->
                if (checkForAccessToken &&
                    error is ResponseException && error.response.status.value == 401
                ) {
                    // Get Access Token using RefreshToken
                    if (handleAuthGetAccessTokenApi()) {
                        financeConsentApproval(
                            consentApproval,
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
    }

    private suspend fun financeConsentApprovalSuccess(response: ConsentApprovalResponse) {
        withContext(Dispatchers.Main) {
            _isLoading.value = false
            _isLoadingSuccess.value = true
            _consentApprovalResponse.value = response
        }
    }
}
