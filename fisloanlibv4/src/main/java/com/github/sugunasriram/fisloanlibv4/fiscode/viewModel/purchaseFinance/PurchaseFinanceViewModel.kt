package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFDeleteUserBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFSearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.DeleteUserResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LoanSearchResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PurchaseFinanceViewModel : BaseViewModel() {
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

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _searchInProgress = MutableStateFlow(false)
    val searchInProgress: StateFlow<Boolean> = _searchInProgress

    private val _searchLoaded = MutableStateFlow(false)
    val searchLoaded: StateFlow<Boolean> = _searchLoaded

    private val _pFSearchResponse = MutableStateFlow<LoanSearchResponse?>(null)
    val pFSearchResponse: StateFlow<LoanSearchResponse?> = _pFSearchResponse

    fun pFSearch(context: Context, searchBodyModel: PFSearchBodyModel) {
        _searchInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePFSearchApi(context, searchBodyModel)
        }
    }

    private suspend fun handlePFSearchApi(
        context: Context,
        searchBodyModel: PFSearchBodyModel,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.pFSearchApi(searchBodyModel)
        }.onSuccess { response ->
            response?.let {
                Log.d("res_H-search-pf", response.toString())
                handleSearchApiSuccess(response)
            }
        }.onFailure { error ->
            Log.d("res_H-search-pf", error.toString())
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handlePFSearchApi(context, searchBodyModel, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error, context, isFormSearch = true)
            }
        }
    }

    private suspend fun handleSearchApiSuccess(response: LoanSearchResponse) {
        withContext(Dispatchers.Main) {
            _searchLoaded.value = true
            _searchInProgress.value = false
            _pFSearchResponse.value = response
        }
    }

    private suspend fun handleFailure(error: Throwable, context: Context, isFormSearch: Boolean = false) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error, context = context, updateErrorMessage = ::updateErrorMessage,
                    _showServerIssueScreen = _showServerIssueScreen, _middleLoan = _middleLoan,
                    _unAuthorizedUser = _unAuthorizedUser, _unexpectedError = _unexpectedError,
                    _showLoader = _showLoader, isFormSearch = isFormSearch

                )
            } else {
                CommonMethods().handleGeneralException(
                    error = error,
                    _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen,
                    _unexpectedError = _unexpectedError
                )
            }

            _searchInProgress.value = false
        }
    }




//    private val _deleteApiInProgress = MutableStateFlow(false)
//    val deleteApiInProgress: StateFlow<Boolean> = _deleteApiInProgress
//
//    private val _deleteApiLoaded = MutableStateFlow(false)
//    val deleteApiLoaded: StateFlow<Boolean> = _deleteApiLoaded
//
//    //private val _pFSearchResponse = MutableStateFlow<LoanSearchResponse?>(null)
//    //val pFSearchResponse: StateFlow<LoanSearchResponse?> = _pFSearchResponse
//
//    fun pFDeleteUser(context: Context, deleteUserBodyModel: PFDeleteUserBodyModel) {
//        _deleteApiInProgress.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            handlePFDeleteUserApi(context, deleteUserBodyModel)
//        }
//    }
//
//    private suspend fun handlePFDeleteUserApi(
//        context: Context,
//        deleteUserBodyModel: PFDeleteUserBodyModel,
//        checkForAccessToken: Boolean = true
//    ) {
//        kotlin.runCatching {
//            ApiRepository.pFDeleteUserApi(deleteUserBodyModel)
//        }.onSuccess { response ->
//            response?.let {
//                Log.d("res_H-search-pf", response.toString())
//                handleDeleteUserApiSuccess(response)
//            }
//        }.onFailure { error ->
//            Log.d("res_H-search-pf", error.toString())
//            if (checkForAccessToken &&
//                error is ResponseException &&
//                error.response.status.value == 401
//            ) {
//                // Get Access Token using RefreshToken
//                if (handleAuthGetAccessTokenApi()) {
//                    handlePFDeleteUserApi(context, deleteUserBodyModel, false)
//                } else {
//                    _navigationToSignIn.value = true
//                }
//            } else {
//                handleDeleteUserFailure(error, context, isFormSearch = true)
//            }
//        }
//    }
//
//    private suspend fun handleDeleteUserApiSuccess(response: DeleteUserResponse) {
//        withContext(Dispatchers.Main) {
//            _deleteApiLoaded.value = true
//            _deleteApiInProgress.value = false
////            _pFSearchResponse.value = response
//        }
//    }
//
//    private suspend fun handleDeleteUserFailure(error: Throwable, context: Context, isFormSearch: Boolean = false) {
//        withContext(Dispatchers.Main) {
//            if (error is ResponseException) {
//                CommonMethods().handleResponseException(
//                    error = error, context = context, updateErrorMessage = ::updateErrorMessage,
//                    _showServerIssueScreen = _showServerIssueScreen, _middleLoan = _middleLoan,
//                    _unAuthorizedUser = _unAuthorizedUser, _unexpectedError = _unexpectedError,
//                    _showLoader = _showLoader, isFormSearch = isFormSearch
//
//                )
//            } else {
//                CommonMethods().handleGeneralException(
//                    error = error,
//                    _showInternetScreen = _showInternetScreen,
//                    _showTimeOutScreen = _showTimeOutScreen,
//                    _unexpectedError = _unexpectedError
//                )
//            }
//
//            _deleteApiInProgress.value = false
//        }
//    }
}