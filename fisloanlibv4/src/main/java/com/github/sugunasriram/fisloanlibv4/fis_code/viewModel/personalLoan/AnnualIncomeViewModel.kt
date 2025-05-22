package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan

import android.content.Context
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.UpdateIncome
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnualIncomeViewModel : ViewModel() {

    private val _showInternetScreen = MutableLiveData<Boolean>(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData<Boolean>(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _unAuthorizedUser = MutableLiveData<Boolean>(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _showServerIssueScreen = MutableLiveData<Boolean>(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unexpectedError = MutableLiveData<Boolean>(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    private val _income: MutableLiveData<Int> = MutableLiveData(30000)
    val income: LiveData<Int> = _income
    private val _incomeError: MutableLiveData<String?> = MutableLiveData(null)
    val incomeError: LiveData<String?> = _incomeError

    fun onIncomeChanged(context: Context, income: String) {
        val incomeWithoutSymbol = income.replace("₹", "").replace(",", "")
        _income.value = incomeWithoutSymbol.toIntOrNull() ?: 0
    }

    private val _sliderPosition: MutableLiveData<Float> = MutableLiveData(30000f)
    val sliderPosition: LiveData<Float> = _sliderPosition

    fun updateSliderPosition(newPosition: Float, context: Context) {
        _sliderPosition.value = newPosition
        val newIncome = newPosition.toInt().toString()
        onIncomeChanged(context = context, newIncome)
        updateGeneralError(null)
    }
    fun setInitialIncome(value: Int) {
        _income.value = value
        _sliderPosition.value = value.toFloat()
    }

    private val _selectedPurpose: MutableLiveData<String> = MutableLiveData()
    val selectedPurpose: LiveData<String> = _selectedPurpose
    private val _purposeError: MutableLiveData<String?> = MutableLiveData(null)
    val purposeError: LiveData<String?> = _purposeError

    fun onPurposeSelected(purpose: String) {
        _selectedPurpose.value = purpose
        _purposeError.value = null
    }

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError

    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    fun validateInputText(context: Context, newText: String): Boolean {
        val convertedText = newText.replace("₹", "").replace(",", "")
        if ((convertedText.toIntOrNull() ?: 0) < 30000 || (convertedText.toIntOrNull() ?: 0) > 2000000) {
            updateGeneralError(context.getString(R.string.please_enter_valid_income_within_limits))
            return false
        }
        return true
    }

    fun formatIncome(income: Int): String {
        return CommonMethods().formatIndianCurrency(income)
    }

    fun onNextClicked(
        context: Context,
        selectedPurpose: String,
        income: Int,
        focusOnPurpose: FocusRequester
    ) {
        clearMessage()
        if (selectedPurpose.isEmpty() || selectedPurpose.equals("Loan Purpose", ignoreCase = true)) {
            _purposeError.value = context.getString(R.string.please_select_loan_purpose)
            focusOnPurpose.requestFocus()
        } else if (income < 30000 || income > 2000000) {
//            updateGeneralError(context.getString(R.string.please_enter_valid_income_within_limits))
            _incomeError.value = context.getString(R.string.please_enter_valid_income_within_limits)
        } else {
            updateUserIncomeApi(context = context, income = income.toString())
        }
    }

    private val _updatingIncome = MutableStateFlow(false)
    val updatingIncome: StateFlow<Boolean> = _updatingIncome

    private val _updatedIncome = MutableStateFlow(false)
    val updatedIncome: StateFlow<Boolean> = _updatedIncome

    private val _updatedIncomeResponse = MutableStateFlow<UpdateIncome?>(null)
    val updatedIncomeResponse: StateFlow<UpdateIncome?> = _updatedIncomeResponse

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun updateUserIncomeApi(context: Context, income: String) {
        _updatingIncome.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateUserIncomeApi(context, income)
        }
    }

    private suspend fun handleUpdateUserIncomeApi(
        context: Context,
        income: String,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateUserIncome(income)
        }.onSuccess { response ->
            response?.let {
                handleUpdateUserIncomeSuccess(response)
            }
        }.onFailure { error ->
            // Session Management
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleUpdateUserIncomeApi(context, income, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }
    private suspend fun handleUpdateUserIncomeSuccess(updateIncome: UpdateIncome) {
        withContext(Dispatchers.Main) {
            _updatedIncome.value = true
            _updatedIncomeResponse.value = updateIncome
            _updatingIncome.value = false
        }
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
            _updatingIncome.value = false
        }
    }
}
