package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateLoanAmountBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.UpdateResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirm
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.PfFlow
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditLoanRequestViewModel(maxAmount: String, minAmount: String, tenure: String) :
    BaseViewModel() {

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

    private val _loanAmount = MutableStateFlow(maxAmount.toDoubleOrNull() ?: 0.0)
    val loanAmount: StateFlow<Double> = _loanAmount

    fun onLoanAmountChanged(loanAmountInput: String) {
        val cleaned = loanAmountInput.replace("₹", "").replace(",", "")
        _loanAmount.value = cleaned.toDoubleOrNull() ?: 0.0
    }

    private val _downpaymentAmount = MutableStateFlow(maxAmount.toDoubleOrNull() ?: 0.0)
    val downpaymentAmount: StateFlow<Double> = _downpaymentAmount

    fun onDownpaymentAmountChanged(loanAmountInput: String) {
        val cleaned = loanAmountInput.replace("₹", "").replace(",", "")
        _downpaymentAmount.value = cleaned.toDoubleOrNull() ?: 0.0
    }

    private val _loanTenure = MutableStateFlow((tenure.toIntOrNull() ?: 7).coerceIn(3, 36))
    val loanTenure: MutableStateFlow<Int> = _loanTenure

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError

    private val _isEditProcess = MutableStateFlow(false)
    val isEditProcess: StateFlow<Boolean> = _isEditProcess

    private val _isEdited = MutableStateFlow(false)
    val isEdited: StateFlow<Boolean> = _isEdited

    private var pfApiFlow = PfFlow.Normal.status

    private val _isPfEdited = MutableStateFlow(false)
    val isPfEdited: StateFlow<Boolean> = _isPfEdited

    private val _editLoanResponse = MutableStateFlow<UpdateResponse?>(null)
    val editLoanResponse: StateFlow<UpdateResponse?> = _editLoanResponse

    // Break down larger functions into smaller functions
    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    fun onLoanTenureChanged(tenureInput: String) {
        val tenureValue = tenureInput.substringBefore(" ").toIntOrNull() ?: 0
        _loanTenure.value = tenureValue
    }

    fun updatePfApiFlow(flow: String) {
        pfApiFlow = flow
    }

    // Update loan maxAmount
    fun updateLoanAmount(updateLoanAmountBody: UpdateLoanAmountBody, context: Context) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateLoanAmount(updateLoanAmountBody, context)
        }
    }

    private suspend fun handleUpdateLoanAmount(
        updateLoanAmountBody: UpdateLoanAmountBody,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateLoanAmount(updateLoanAmountBody)
        }.onSuccess { response ->
            response?.let {
                handleUpdateSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleUpdateLoanAmount(updateLoanAmountBody, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleUpdateSuccess(response: UpdateResponse) {
        withContext(Dispatchers.Main) {
            _editLoanResponse.value = response
            _isEditProcess.value = false
            _isEdited.value = true
        }
    }

    fun checkValid(
        loanAmount: String,
        context: Context,
        updateLoanAmountBody: UpdateLoanAmountBody,
    ) {
        if (isInputValid(loanAmount, context)) {
            updateLoanAmount(updateLoanAmountBody, context)
        }
    }

    private fun isInputValid(
        loanAmount: String,
        context: Context
    ): Boolean {
        return when {
            loanAmount.isEmpty() -> {
                CommonMethods().toastMessage(context, context.getString(R.string.loan_amount_message))
                false
            }

//            tenureValue < initialLoanBeginTenure || tenureValue > initialLoanEndTenure -> {
//                CommonMethods().toastMessage(context,context.getString(R.string.please_enter_valid_tenure))
//                false
//            }

            else -> true
        }
    }

    private val _gstOfferConfirmResponse = MutableStateFlow<GstOfferConfirmResponse?>(null)
    val gstOfferConfirmResponse: StateFlow<GstOfferConfirmResponse?> = _gstOfferConfirmResponse

    private fun gstOfferConfirmApi(gstOfferConfirm: GstOfferConfirm, context: Context) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstOfferConfirmApi(gstOfferConfirm, context)
        }
    }

    private suspend fun handleGstOfferConfirmApi(
        gstOfferConfirm: GstOfferConfirm,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstConfirmOffer(gstOfferConfirm)
        }.onSuccess { response ->
            response?.let {
                handleGstOfferConfirmApiSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGstOfferConfirmApi(gstOfferConfirm, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGstOfferConfirmApiSuccess(response: GstOfferConfirmResponse) {
        withContext(Dispatchers.Main) {
            _isEditProcess.value = false
            _isEdited.value = true
            _gstOfferConfirmResponse.value = response
        }
    }

    fun gstInitiateOffer(id: String, loanType: String, context: Context) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstInitiateOfferApi(id, loanType, context)
        }
    }

    private suspend fun handleGstInitiateOfferApi(id: String, loanType: String, context: Context, checkForAccessToken: Boolean = true) {
        kotlin.runCatching {
            ApiRepository.gstInitiateOffer(id, loanType)
        }.onSuccess { response ->
            response?.let {
                handleGstInitiateOfferApiSuccess(response, context)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleGstInitiateOfferApi(
                        id = id,
                        loanType = loanType,
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

    private suspend fun handleGstInitiateOfferApiSuccess(response: GstOfferConfirmResponse, context: Context) {
        withContext(Dispatchers.Main) {
            _isEdited.value = true
            _gstOfferConfirmResponse.value = response

            // Get Principal
            var loanAmount = response?.data?.offerResponse?.itemTags
                ?.firstNotNullOfOrNull { it?.tags?.principal }
                ?: response?.data?.catalog?.itemTags
                    ?.firstNotNullOfOrNull { it?.tags?.principal }
                ?: response?.data?.catalog?.itemPrice?.value
                ?: ""

            // Get id
            var id = response?.data?.catalog?.itemID
                ?: response?.data?.offerResponse?.itemID
                ?: ""

            loanAmount.let { loanAmount ->
                gstOfferConfirmApi(
                    GstOfferConfirm(
                        id = id,
                        requestAmount = loanAmount,
                        loanType = "INVOICE_BASED_LOAN"
                    ),
                    context
                )
            }
        }
    }

    fun gstInitiateOffer(offerId: String, loanType: String, context: Context, loanAmount: String, id: String) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstInitiateOfferApi(offerId, loanType, context, loanAmount, id)
        }
    }

    private suspend fun handleGstInitiateOfferApi(
        offerId: String,
        loanType: String,
        context: Context,
        loanAmount: String,
        id: String,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstInitiateOffer(id, loanType)
        }.onSuccess { response ->
            response?.let {
                handleGstInitiateOfferApiSuccess(response, context, loanAmount, offerId)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleGstInitiateOfferApi(
                        offerId = offerId,
                        loanType = loanType,
                        context = context,
                        loanAmount =
                        loanAmount,
                        id = id,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGstInitiateOfferApiSuccess(
        response: GstOfferConfirmResponse,
        context: Context,
        loanAmount: String,
        offerId: String
    ) {
        withContext(Dispatchers.Main) {
            _isEdited.value = true
            _gstOfferConfirmResponse.value = response
            gstOfferConfirmApi(
                GstOfferConfirm(
                    id = offerId,
                    requestAmount = loanAmount,
                    loanType = "INVOICE_BASED_LOAN"
                ),
                context
            )
        }
    }

    // Purchase Finance

    private val _pfOfferConfirmResponse = MutableStateFlow<PfOfferConfirmResponse?>(null)
    val pfOfferConfirmResponse: StateFlow<PfOfferConfirmResponse?> = _pfOfferConfirmResponse

    private fun pfOfferConfirmApi(pfOfferConfirm: PfOfferConfirm, context: Context) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePfOfferConfirmApi(pfOfferConfirm, context)
        }
    }

    private suspend fun handlePfOfferConfirmApi(
        pfOfferConfirm: PfOfferConfirm,
        context:
            Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.pfConfirmOffer(pfOfferConfirm)
        }.onSuccess { response ->
            response?.let {
                handlePfOfferConfirmApiSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handlePfOfferConfirmApi(pfOfferConfirm, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handlePfOfferConfirmApiSuccess(response: PfOfferConfirmResponse) {
        withContext(Dispatchers.Main) {
            _isEditProcess.value = false
            if (pfApiFlow == PfFlow.Normal.status) {
                _isEdited.value = true
                _pfOfferConfirmResponse.value = response
            } else if (pfApiFlow == PfFlow.Edited.status) {
                _isPfEdited.value = true
                _pfOfferConfirmResponse.value = response
            }
        }
    }

    fun pfInitiateOffer(
        id: String,
        loanType: String,
        context: Context,
        paymentAmount: String,
        loanTenure: String
    ) {
        _isEditProcess.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePfInitiateOfferApi(id, loanType, context, paymentAmount = paymentAmount, loanTenure = loanTenure)
        }
    }

    private suspend fun handlePfInitiateOfferApi(id: String, loanType: String, context: Context, checkForAccessToken: Boolean = true, paymentAmount: String, loanTenure: String) {
        kotlin.runCatching {
            ApiRepository.pfInitiateOffer(id, loanType)
        }.onSuccess { response ->
            response?.let {
                handlePfInitiateOfferApiSuccess(response, context, paymentAmount, loanTenure)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handlePfInitiateOfferApi(
                        id = id,
                        loanType = loanType,
                        context = context,
                        checkForAccessToken =
                        false,
                        paymentAmount,
                        loanTenure
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handlePfInitiateOfferApiSuccess(
        response: PfOfferConfirmResponse,
        context: Context,
        downPaymentAmount: String,
        loanTenure: String
    ) {
        withContext(Dispatchers.Main) {
            _isEdited.value = true
            _pfOfferConfirmResponse.value = response

            // Get id
            var id = response.data?.catalog?.id
                ?: response.data?.offerResponse?.id
                ?: ""

            pfOfferConfirmApi(
                PfOfferConfirm(
                    id = id,
                    requestAmount = downPaymentAmount,
                    loanType = "PURCHASE_FINANCE",
                    requestTerm = loanTenure
                ),
                context
            )
        }
    }

    private suspend fun handleFailure(error: Throwable, context: Context) {
        Log.d("res_H","pfInitiateOffer-failure" )
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
            _isEditProcess.value = false
        }
    }
}

class EditLoanRequestViewModelFactory(
    private val amount: String,
    private val minAmount: String,
    private val tenure: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLoanRequestViewModel::class.java)) {
            return EditLoanRequestViewModel(amount, minAmount, tenure) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
