package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.GstOfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.OfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.PfOfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.StatusResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CheckOrderIssueModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OrderByIdResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandler
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateConsentHandlerBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanAgreement
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateLoanBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.sse.SSEData
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoanAgreementViewModel : BaseViewModel() {

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

    private val _loanListEmpty = MutableLiveData(false)
    val loanListEmpty: LiveData<Boolean> = _loanListEmpty

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loanListLoading = MutableStateFlow(false)
    val loanListLoading: StateFlow<Boolean> = _loanListLoading

    private val _loanListLoaded = MutableStateFlow(false)
    val loanListLoaded: StateFlow<Boolean> = _loanListLoaded

    private val _loanList = MutableStateFlow<CustomerLoanList?>(null)
    val loanList: StateFlow<CustomerLoanList?> = _loanList

    private var hasApiBeenCalled = false

    fun getCustomerLoanList(loanType: String, context: Context) {
        if (hasApiBeenCalled) return
        hasApiBeenCalled = true
        _loanListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGetCustomerLoanList(loanType, context)
        }
    }

    private suspend fun handleGetCustomerLoanList(
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getCustomerLoanList(loanType)
        }.onSuccess { response ->

            if (response != null) {
                if (response.statusCode?.toInt() == 200) {
                    if (response.data?.size == 0) {
                        _loanListEmpty.value = true
                    } else {
                        handleGetCustomerLoanListSuccess(response)
                    }
                }
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGetCustomerLoanList(loanType, context, false)
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGetCustomerLoanListSuccess(loanData: CustomerLoanList) {
        withContext(Dispatchers.Main) {
            if (loanData.statusCode?.toInt() == 206) {
                _loanListLoaded.value = true
                _loanList.value = null
            } else {
                _loanList.value = loanData
                _loanListLoaded.value = true
            }
            _loanListLoading.value = false
            _consentHandling.value = false
        }
    }

    private val _updateProcessing = MutableStateFlow(false)
    val updateProcessing: StateFlow<Boolean> = _updateProcessing

    private val _updateProcessed = MutableStateFlow(false)
    val updateProcessed: StateFlow<Boolean> = _updateProcessed

    private val _showPaymentFailedScreen = MutableStateFlow(false)
    val showPaymentFailedScreen: StateFlow<Boolean> = _showPaymentFailedScreen

    private val _updatedLoanAgreement = MutableStateFlow<UpdateLoanAgreement?>(null)
    val updatedLoanAgreement: StateFlow<UpdateLoanAgreement?> = _updatedLoanAgreement

    fun updateLoanAgreementApi(context: Context, updateLoanBody: UpdateLoanBody) {
        _updateProcessing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateLoanAgreementApi(context, updateLoanBody)
        }
    }

    private suspend fun handleUpdateLoanAgreementApi(
        context: Context,
        updateLoanBody: UpdateLoanBody,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateLoanAgreement(updateLoanBody)
        }.onSuccess { response ->
            response?.let {
                handleUpdateLoanAgreementApiSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleUpdateLoanAgreementApi(context, updateLoanBody, false)
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleUpdateLoanAgreementApiSuccess(response: UpdateLoanAgreement) {
        withContext(Dispatchers.Main) {
            if (response.statusCode == 200) {
                _updatedLoanAgreement.value = response
                _updateProcessing.value = false
                _updateProcessed.value = true
            } else if (response.statusCode == 400) {
                _showPaymentFailedScreen.value = true
            }
        }
    }

    private val _consentHandling = MutableStateFlow(false)
    val consentHandling: StateFlow<Boolean> = _consentHandling

    private val _consentHandled = MutableStateFlow(false)
    val consentHandled: StateFlow<Boolean> = _consentHandled.asStateFlow()

    private val _sseData = MutableStateFlow<SSEData?>(null)
    val sseData: StateFlow<SSEData?> = _sseData.asStateFlow()

    private val _updatedConsentResponse = MutableStateFlow<UpdateConsentHandler?>(null)
    val updatedConsentResponse: StateFlow<UpdateConsentHandler?> = _updatedConsentResponse

    private val _apiCalled = MutableStateFlow(false)

    fun updateConsentHandler(updateConsentHandlerBody: UpdateConsentHandlerBody, context: Context) {
        if (_apiCalled.value) return
        _consentHandling.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateConsentHandler(updateConsentHandlerBody, context)
        }
    }

    private suspend fun handleUpdateConsentHandler(
        updateConsentHandlerBody: UpdateConsentHandlerBody,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateConsentHandler(updateConsentHandlerBody)
        }.onSuccess { response ->
            response?.let {
                handleUpdateConsentHandlerResult(response, context, updateConsentHandlerBody)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleUpdateConsentHandler(updateConsentHandlerBody, context, false)
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleUpdateConsentHandlerResult(
        response: UpdateConsentHandler,
        context: Context,
        updateConsentHandlerBody: UpdateConsentHandlerBody
    ) {
        withContext(Dispatchers.Main) {
            _updatedConsentResponse.value = response
            _consentHandled.value = true
            delay(20000)
            val loanType = updateConsentHandlerBody.loanType
            if (loanType != "PURCHASE_FINANCE") {
                if (loanType.equals("INVOICE_BASED_LOAN", ignoreCase = true)) {
                    getCustomerLoanList("INVOICE_BASED_LOAN", context)
                } else {
                    getCustomerLoanList("PERSONAL_LOAN", context)
                }
            }
        }
    }

    fun updateSSEData(sseData: SSEData) {
        _sseData.value = sseData
    }

    private val _gettingOrderById = MutableStateFlow(false)
    val gettingOrderById: StateFlow<Boolean> = _gettingOrderById

    private val _orderByIdLoaded = MutableStateFlow(false)
    val orderByIdLoaded: StateFlow<Boolean> = _orderByIdLoaded

    private val _orderByIdResponse = MutableStateFlow<OrderByIdResponse?>(null)
    val orderByIdResponse: StateFlow<OrderByIdResponse?> = _orderByIdResponse

    fun getOrderById(orderId: String, loanType: String, context: Context) {
        if (_apiCalled.value) return
        _apiCalled.value = true
        _gettingOrderById.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGetOrderById(loanType = loanType, orderId = orderId, context = context)
        }
    }

    private suspend fun handleGetOrderById(
        orderId: String,
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getOrderById(loanType = loanType, orderId = orderId)
        }.onSuccess {
            handleGetOrderByIdSuccess(response = it, context = context)
        }.onFailure { error ->

            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGetOrderById(orderId, loanType, context, false)
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGetOrderByIdSuccess(response: OrderByIdResponse?, context: Context) {
        withContext(Dispatchers.Main) {
            _orderByIdResponse.value = response
            _orderByIdLoaded.value = true
            _gettingOrderById.value = false
            response?.data?.id?.let { orderId ->
                checkOrderIssues(orderId = orderId, context = context)
            }
        }
    }

    fun completeLoanList(context: Context) {
        if (hasApiBeenCalled) return
        hasApiBeenCalled = true
        _loanListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleCompleteLoanList(context)
        }
    }

    private suspend fun handleCompleteLoanList(
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.completeLoanOrders()
        }.onSuccess { response ->
            response?.let {
                handleGetCustomerLoanListSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleCompleteLoanList(context, false)
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private val _checkingOrderIssues = MutableStateFlow(false)
    val checkingOrderIssues: StateFlow<Boolean> = _checkingOrderIssues

    private val _checkedOrderIssues = MutableStateFlow(false)
    val checkedOrderIssues: StateFlow<Boolean> = _checkedOrderIssues

    private val _checkOrderIssueResponse = MutableStateFlow<CheckOrderIssueModel?>(null)
    val checkOrderIssueResponse: StateFlow<CheckOrderIssueModel?> = _checkOrderIssueResponse

    private var alreadyDone = false

    private fun checkOrderIssues(orderId: String, context: Context) {
        if (alreadyDone) return
        alreadyDone = true
        _checkingOrderIssues.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleCheckOrderIssues(orderId = orderId, context = context)
        }
    }

    private suspend fun handleCheckOrderIssues(
        orderId: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.checkOrderIssues(orderId)
        }.onSuccess { response ->
            withContext(Dispatchers.Main) {
                _checkingOrderIssues.value = false
                _checkedOrderIssues.value = true
                _checkOrderIssueResponse.value = response
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleCheckOrderIssues(
                        orderId = orderId,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleFailure(error: Throwable, context: Context) {
        withContext(Dispatchers.Main) {
            if (error is ResponseException) {
                if (error.response.status.value == 401) {
                    val checkForAccessToken = true
                    // Get Access Token using RefreshToken
                    if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
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
                        _navigationToSignup.value = true
                    }
                } else {
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
                }
            } else {
                CommonMethods().handleGeneralException(
                    error = error,
                    _showInternetScreen = _showInternetScreen,
                    _showTimeOutScreen = _showTimeOutScreen,
                    _unexpectedError = _unexpectedError
                )
            }
            _checkingOrderIssues.value = false
            _checkingStatus.value = false
            _offerListLoading.value = false
            _loanListLoading.value = false
            _consentHandling.value = false
            _gettingOrderById.value = false
            _updateProcessing.value = false
            _consentHandling.value = false
        }
    }

    private val _checkingStatus = MutableStateFlow(false)
    val checkingStatus: StateFlow<Boolean> = _checkingStatus

    private val _checked = MutableStateFlow(false)
    val checked: StateFlow<Boolean> = _checked

    private val _status = MutableStateFlow<StatusResponse?>(null)
    val status: StateFlow<StatusResponse?> = _status

    fun status(loanType: String, orderId: String, context: Context) {
        _checkingStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleStatus(loanType = loanType, context = context, orderId = orderId)
        }
    }

    private suspend fun handleStatus(
        loanType: String,
        context: Context,
        orderId: String,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.status(loanType = loanType, orderId = orderId)
        }.onSuccess { response ->
            handleStatusSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleStatus(
                        orderId = orderId,
                        loanType = loanType,
                        context = context,
                        checkForAccessToken =
                        false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
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

    private val _offerList = MutableStateFlow<OfferListModel?>(null)
    val offerList: StateFlow<OfferListModel?> = _offerList

    private val _offerListLoading = MutableStateFlow(false)
    val offerListLoading: StateFlow<Boolean> = _offerListLoading

    private val _navigationToSignup = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignup

    private val _offerListLoaded = MutableStateFlow(false)
    val offerListLoaded: StateFlow<Boolean> = _offerListLoaded

    fun offerList(loanType: String, context: Context) {
        _offerListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleOfferList(loanType = loanType, context = context)
        }
    }

    private suspend fun handleOfferList(
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.offerList(loanType = loanType)
        }.onSuccess { response ->
            handleOfferListSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleOfferList(
                        loanType = loanType,
                        context = context,
                        checkForAccessToken =
                        false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleOfferListSuccess(response: OfferListModel?) {
        withContext(Dispatchers.Main) {
            _offerList.value = response
            _offerListLoading.value = false
            _offerListLoaded.value = true
        }
    }

    private val _gstOfferList = MutableStateFlow<GstOfferListModel?>(null)
    val gstOfferList: StateFlow<GstOfferListModel?> = _gstOfferList

    private val _gstOfferListLoading = MutableStateFlow(false)
    val gstOfferListLoading: StateFlow<Boolean> = _gstOfferListLoading

//    private val _navigationToSignup = MutableStateFlow(false)
//    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignup

    private val _gstOfferListLoaded = MutableStateFlow(false)
    val gstOfferListLoaded: StateFlow<Boolean> = _gstOfferListLoaded

    fun gstOfferList(loanType: String, context: Context) {
        _gstOfferListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstOfferList(loanType = loanType, context = context)
        }
    }

    private suspend fun handleGstOfferList(
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstOfferList(loanType = loanType)
        }.onSuccess { response ->
            handleGstOfferListSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleGstOfferList(
                        loanType = loanType,
                        context = context,
                        checkForAccessToken =
                        false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGstOfferListSuccess(response: GstOfferListModel?) {
        withContext(Dispatchers.Main) {
            _gstOfferList.value = response
            _gstOfferListLoading.value = false
            _gstOfferListLoaded.value = true
        }
    }

    private val _pfOfferList = MutableStateFlow<PfOfferListModel?>(null)
    val pfOfferList: StateFlow<PfOfferListModel?> = _pfOfferList

//    private val _navigationToSignup = MutableStateFlow(false)
//    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignup

    private val _pfOfferListLoaded = MutableStateFlow(false)
    val pfOfferListLoaded: StateFlow<Boolean> = _pfOfferListLoaded

    private val _pfOfferListLoading = MutableStateFlow(false)
    val pfOfferListLoading: StateFlow<Boolean> = _pfOfferListLoading

    fun pfOfferList(loanType: String, context: Context) {
        _pfOfferListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePfOfferList(loanType = loanType, context = context)
        }
    }

    private suspend fun handlePfOfferList(
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.pfOfferList(loanType = loanType)
        }.onSuccess { response ->
            handlePfOfferListSuccess(response)
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handlePfOfferList(
                        loanType = loanType,
                        context = context,
                        checkForAccessToken =
                        false
                    )
                } else {
                    _navigationToSignup.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handlePfOfferListSuccess(response: PfOfferListModel?) {
        withContext(Dispatchers.Main) {
            _pfOfferList.value = response
            _pfOfferListLoading.value = false
            _pfOfferListLoaded.value = true
        }
    }
}
