package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GstInvoiceLoanViewModel : ViewModel() {

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

    private val _selectedItems = MutableLiveData<Set<Int>>(emptySet())
    val selectedItems: LiveData<Set<Int>> = _selectedItems

    fun toggleItem(itemId: Int) {
        _selectedItems.value = _selectedItems.value?.let {
            if (it.contains(itemId)) it - itemId else it + itemId
        }
    }

    fun selectAllItems(count: Int) {
        _selectedItems.value = (0 until count).toSet()
    }

    fun deselectAllItems() {
        _selectedItems.value = emptySet()
    }

    fun toggleSelectAll(count: Int) {
        if (_selectedItems.value?.size == count) {
            deselectAllItems()
        } else {
            selectAllItems(count)
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _loaded = MutableStateFlow(false)
    val loaded: StateFlow<Boolean> = _loaded

    private val _invoiceData = MutableStateFlow<GstInvoice?>(null)
    val invoiceData: StateFlow<GstInvoice?> = _invoiceData

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun invoiceData(context: Context, gstin: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleInvoiceData(context,gstin)
        }
    }

    private suspend fun handleInvoiceData(
        context: Context, gstin: String,checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstInvoices(gstIn = gstin)
        }.onSuccess { response ->
            handleInvoiceSuccess(response = response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleInvoiceData(
                        context = context, gstin = gstin,checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleInvoiceFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleInvoiceSuccess(response: GstInvoice?) {
        withContext(Dispatchers.Main) {
            _loading.value = false
            _loaded.value = true
            _invoiceData.value = response
        }
    }

    private suspend fun handleInvoiceFailure(error: Throwable, context: Context) {
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
            _loading.value = false
        }
    }
}