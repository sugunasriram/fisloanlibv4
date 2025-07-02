package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.OrderPaymentStatusItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.OrderPaymentStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetOrderPaymentStatusViewModel : BaseViewModel() {

    private val _showInternetScreen = MutableLiveData<Boolean>(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData<Boolean>(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData<Boolean>(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unexpectedError = MutableLiveData<Boolean>(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _unAuthorizedUser = MutableLiveData<Boolean>(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _middleLoan = MutableLiveData<Boolean>(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn


    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _orderPaymentListLoading = MutableStateFlow(false)
    val orderPaymentListLoading: StateFlow<Boolean> = _orderPaymentListLoading

    private val _orderPaymentListLoaded = MutableStateFlow(false)
    val orderPaymentListLoaded: StateFlow<Boolean> = _orderPaymentListLoaded


    private val _orderPaymentStatusList = MutableStateFlow<ArrayList<OrderPaymentStatusItem>?>(null)
    val orderPaymentStatusList: StateFlow<ArrayList<OrderPaymentStatusItem>?> = _orderPaymentStatusList


    fun getOrderPaymentStatus(loanType:String, loanId: String, context: Context) {
        _orderPaymentListLoading.value = true
        _orderPaymentListLoaded.value = false

        viewModelScope.launch(Dispatchers.IO) {
            handleGetOrderPaymentStatus(loanType,loanId, context)
        }
    }

    private suspend fun handleGetOrderPaymentStatus(loanType:String,loanId:String, context: Context,
                                                  checkForAccessToken: Boolean=true) {
        kotlin.runCatching {
            ApiRepository.getOrderPaymentStatus(loanType, loanId)
        }.onSuccess { response ->

            if (response != null){
                if (response.statusCode?.toInt() == 200) {
                    if (response.data?.size == 0){
                        _orderPaymentListLoaded.value = true
                        _orderPaymentListLoading.value = false
                    } else {
                        handleGetOrderPaymentStatusSuccess(response)
                    }
                }
            }

        }.onFailure { error ->
            //Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401) {
                //Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()){
                    handleGetOrderPaymentStatus(loanType, loanId, context, false)
                }else{
                    _navigationToSignIn.value = true
                }
            }else {
                handleGetOrderPaymentStatusListFailure(error, context)
            }
        }
    }

    private suspend fun handleGetOrderPaymentStatusSuccess(orderPaymentStatusResponse: 
                                                           OrderPaymentStatusResponse) {
        withContext(Dispatchers.Main) {
            if (orderPaymentStatusResponse.statusCode?.toInt() == 206) {
                _orderPaymentListLoaded.value = true
                _orderPaymentStatusList.value = null
            } else {
                _orderPaymentStatusList.value = orderPaymentStatusResponse.data
                _orderPaymentListLoaded.value = true
            }
            _orderPaymentListLoading.value = false
        }
    }

    private suspend fun handleGetOrderPaymentStatusListFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                CommonMethods().handleResponseException(
                    error = error, context = context,updateErrorMessage = ::updateErrorMessage,
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
            _orderPaymentListLoading.value = false
        }
    }
}