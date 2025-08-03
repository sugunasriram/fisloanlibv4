package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan

import android.content.Context
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AddBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AddBankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankAccount
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankList
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.GstBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.IFSCResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PfBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class AccountDetailViewModel : BaseViewModel() {

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

    private val _accountHolder: MutableLiveData<String> = MutableLiveData("")
    val accountHolder: LiveData<String> = _accountHolder

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun setAccountHolder(accountHolder: String) {
        _accountHolder.value = accountHolder
    }

    fun onAccountHolderChanged(accountHolder: String) {
//        val sanitizedInput = accountHolder.replace(Regex("[^a-zA-Z ]"), "").take(35)
        if (accountHolder.length <= 35) {
            _accountHolder.value = accountHolder
            updateGeneralError(null)
        }
    }

    private val _accountNumber: MutableLiveData<String> = MutableLiveData("")
    val accountNumber: LiveData<String> = _accountNumber

    fun setAccountNumber(accountNumber: String) {
        _accountNumber.value = accountNumber
    }
    fun onAccountNumberChanged(accountNumber: String) {
//        val sanitizedInput = accountNumber.replace(Regex("[^0-9]"), "").take(18)
        if (accountNumber.length <= 18) {
            _accountNumber.value = accountNumber
            updateGeneralError(null)
        }
    }

    private val _ifscCode: MutableLiveData<String> = MutableLiveData("")
    val ifscCode: LiveData<String> = _ifscCode

    fun setIFSCCode(ifscCode: String) {
        _ifscCode.value = ifscCode
    }

    fun onIfscCodeChanged(ifscCode: String) {
//        val sanitizedInput = ifscCode.replace(Regex("[^a-zA-Z0-9]"), "")
        if (ifscCode.length <= 11) {
            _ifscCode.value = ifscCode
            updateGeneralError(null)
        }
    }

    private val _accountType: MutableLiveData<String> = MutableLiveData("")
    val accountType: LiveData<String> = _accountType

    fun setAccountType(accountType: String) {
        _accountType.value = accountType
    }

    fun onAccountTypeChanged(accountType: String) {
        _accountType.value = accountType
        updateGeneralError(null)
    }

    private val _accountHolderError: MutableLiveData<String?> = MutableLiveData("")
    val accountHolderError: LiveData<String?> = _accountHolderError

    fun updateAccountHolderError(accountHolderError: String?) {
        _accountHolderError.value = accountHolderError
    }

    private val _accountTypeError: MutableLiveData<String?> = MutableLiveData("")
    val accountTypeError: LiveData<String?> = _accountTypeError

    fun updateAccountTypeError(accountTypeError: String?) {
        _accountTypeError.value = accountTypeError
    }

    private val _accountNumberError: MutableLiveData<String?> = MutableLiveData("")
    val accountNumberError: LiveData<String?> = _accountNumberError

    fun updateAccountNumberError(accountNumberError: String?) {
        _accountNumberError.value = accountNumberError
    }

    private val _ifscCodeError: MutableLiveData<String?> = MutableLiveData("")
    val ifscCodeError: LiveData<String?> = _ifscCodeError

    fun updateIfscCodeError(ifscCodeError: String?) {
        _ifscCodeError.value = ifscCodeError
    }

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError
    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    private val _bankDetailCollecting = MutableStateFlow(false)
    val bankDetailCollecting: StateFlow<Boolean> = _bankDetailCollecting

    private val _bankDetailCollected = MutableStateFlow(false)
    val bankDetailCollected: StateFlow<Boolean> = _bankDetailCollected

    private val _bankDetailUpdating = MutableStateFlow(false)
    val bankDetailUpdating: StateFlow<Boolean> = _bankDetailUpdating

    private val _bankDetailUpdated = MutableStateFlow(false)
    val bankDetailUpdated: StateFlow<Boolean> = _bankDetailUpdated

    private val _bankDetailUpdateResponse = MutableStateFlow<AddBankDetailResponse?>(null)
    val bankDetailUpdateResponse: StateFlow<AddBankDetailResponse?> = _bankDetailUpdateResponse

    private val _bankDetailDeleting = MutableStateFlow(false)
    val bankDetailDeleting: StateFlow<Boolean> = _bankDetailDeleting

    private val _bankDetailDeleted = MutableStateFlow(false)
    val bankDetailDeleted: StateFlow<Boolean> = _bankDetailDeleted

    private val _bankDetailResponse = MutableStateFlow<BankDetailResponse?>(null)
    val bankDetailResponse: StateFlow<BankDetailResponse?> = _bankDetailResponse

    private val _addBankDetail = MutableLiveData<AddBankDetailResponse?>(null)
    val addBankDetail: LiveData<AddBankDetailResponse?> = _addBankDetail

    private val _deleteBankDetail = MutableLiveData<AddBankDetailResponse?>(null)
    val deleteBankDetail: LiveData<AddBankDetailResponse?> = _deleteBankDetail

    fun addBankDetail(context: Context, bankDetail: BankDetail, navController: NavHostController) {
        _bankDetailCollecting.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleAddBankDetail(context, bankDetail, navController)
        }
    }
    private suspend fun handleAddBankDetail(
        context: Context,
        bankDetail: BankDetail,
        navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.addAccountDetail(bankDetail)
        }.onSuccess { response ->
            response?.let {
                handleAddBankDetailSuccess(response, context)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleAddBankDetail(context, bankDetail, navController, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleAddBankDetailSuccess(response: BankDetailResponse, context: Context) {
        withContext(Dispatchers.Main) {
            _bankDetailResponse.value = response
            _bankDetailCollected.value = true
            _bankDetailCollecting.value = false

            if (_bankAccount.value == null) {
                addBank(
                    bankDetail = AddBankDetail(
                        accountHolderName = _accountHolder.value.toString(),
                        accountNumber = _accountNumber.value.toString(),
                        accountType = accountType.value.toString(),
                        ifscCode = ifscCode.value.toString()
                    ),
                    context
                )
            }
        }
    }

    fun updateBankAccountDetail(context: Context, bankDetail: UpdateBankDetail, navController: NavHostController) {
        _bankDetailUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleUpdateBankDetail(context, bankDetail, navController)
        }
    }
    private suspend fun handleUpdateBankDetail(
        context: Context,
        bankDetail: UpdateBankDetail,
        navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.updateBank(bankDetail)
        }.onSuccess { response ->
            response?.let {
                handleUpdateBankDetailSuccess(response, context)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleUpdateBankDetail(context, bankDetail, navController, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleUpdateBankDetailSuccess(response: AddBankDetailResponse, context: Context) {
        withContext(Dispatchers.Main) {
//            isApiCalled = false
//            getBankAccount(context)
            _bankDetailUpdateResponse.value = response
            _bankDetailUpdated.value = true
            _bankDetailUpdating.value = false

//            if (_bankAccount.value == null) {
//                addBank(
//                    bankDetail = AddBankDetail(
//                        account_holder_name = _accountHolder.value.toString(),
//                        bank_account_number = _accountNumber.value.toString(),
//                        account_type = accountType.value.toString(),
//                        bank_ifsc_code = ifscCode.value.toString()
//                    ),
//                    context
//                )
//            }
        }
    }

    fun deleteBankDetail(context: Context, id: String, navController: NavHostController) {
        _bankDetailDeleting.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleDeleteBankDetail(context, id, navController)
        }
    }

    private suspend fun handleDeleteBankDetail(
        context: Context,
        id: String,
        navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.deleteAccountDetail(id)
        }.onSuccess { response ->
            response?.let {
                handleDeleteBankDetailSuccess(response, context)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleDeleteBankDetail(context, id, navController, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleDeleteBankDetailSuccess(response: AddBankDetailResponse, context: Context) {
        withContext(Dispatchers.Main) {
            _bankDetailDeleting.value = false
            _bankDetailDeleted.value = true
            _deleteBankDetail.value = response

            isApiCalled = false // Reset so `getBankAccount()` works
            getBankAccount(context)
        }
    }

    private val _shouldShowKeyboard: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowKeyboard: LiveData<Boolean> = _shouldShowKeyboard

    fun requestKeyboard() {
        _shouldShowKeyboard.value = true
    }

    fun resetKeyboardRequest() {
        _shouldShowKeyboard.value = false
    }

    fun bankAccountDetailValidation(
        context: Context,
        accountNumber: String,
        accountHolder: String,
        ifscCode: String,
        accountSelectedText: String,
        id: String,
        focusAccountHolder: FocusRequester,
        focusAccountType: FocusRequester,
        focusAccountNumber: FocusRequester,
        focusIfscCode: FocusRequester,
//        bankDetail: AddBankDetail, navController: NavHostController
        bankDetail: BankDetail,
        navController: NavHostController
    ) {
        var isValid = true
        clearMessage()

        if (ifscCode.trim().isEmpty()) {
            updateIfscCodeError(context.getString(R.string.enter_ifsc_code))
            focusIfscCode.requestFocus()
            isValid = false
        } else if (CommonMethods().isValidIfscCode(ifscCode) != true) {
            updateIfscCodeError(context.getString(R.string.enter_valid_ifsc_code))
            focusIfscCode.requestFocus()
            isValid = false
        }

        if (accountNumber.trim().isEmpty()) {
            updateAccountNumberError(context.getString(R.string.enter_account_number))
            focusAccountNumber.requestFocus()
            isValid = false
        } else if (accountNumber.trim().length < 9) {
            updateAccountNumberError(context.getString(R.string.minimum_account_number))
            focusAccountNumber.requestFocus()
            isValid = false
        } else if (!Pattern.compile("^\\d+$").matcher(accountNumber).find()) {
            updateAccountNumberError(context.getString(R.string.special_characters_not_allowed))
            focusAccountNumber.requestFocus()
            isValid = false
        }

        if (accountSelectedText.trim().isEmpty()) {
            updateAccountTypeError(context.getString(R.string.please_select_the_account_type))
            focusAccountType.requestFocus()
            isValid = false
        }

        if (accountHolder.trim().isEmpty()) {
            updateAccountHolderError(context.getString(R.string.enter_account_holder))
            focusAccountHolder.requestFocus()
            isValid = false
        } else if (accountHolder.trim().length < 4) {
            updateAccountHolderError(context.getString(R.string.name_should_contain_minimum_4_letters))
            focusAccountHolder.requestFocus()
            isValid = false
        } else if (!Pattern.compile("^[a-zA-Z ]*$").matcher(accountHolder).find()) {
            updateAccountHolderError(context.getString(R.string.character_special_validation))
            focusAccountHolder.requestFocus()
            isValid = false
        }
        if (isValid) {
//            addBank( bankDetail,context,navController,id)
            addBankDetail(context, bankDetail, navController)
        }
    }

    fun editAccountDetailValidation(
        context: Context,
        accountNumber: String,
        accountHolder: String,
        ifscCode: String,
        accountSelectedText: String,
        focusAccountHolder: FocusRequester,
        focusAccountType: FocusRequester,
        focusAccountNumber: FocusRequester,
        focusIfscCode: FocusRequester,
        bankDetail: UpdateBankDetail,
        navController: NavHostController
    ) {
        var isValid = true
        clearMessage()
        if (ifscCode.trim().isEmpty()) {
            updateIfscCodeError(context.getString(R.string.enter_ifsc_code))
            focusIfscCode.requestFocus()
            isValid = false
        } else if (CommonMethods().isValidIfscCode(ifscCode) != true) {
            updateIfscCodeError(context.getString(R.string.enter_valid_ifsc_code))
            focusIfscCode.requestFocus()
            isValid = false
        }

        if (accountNumber.trim().isEmpty()) {
            updateAccountNumberError(context.getString(R.string.enter_account_number))
            focusAccountNumber.requestFocus()
            isValid = false
        } else if (accountNumber.trim().length < 9) {
            updateAccountNumberError(context.getString(R.string.minimum_account_number))
            focusAccountNumber.requestFocus()
            isValid = false
        } else if (!Pattern.compile("^\\d+$").matcher(accountNumber).find()) {
            updateAccountNumberError(context.getString(R.string.special_characters_not_allowed))
            focusAccountNumber.requestFocus()
            isValid = false
        }

        if (accountSelectedText.trim().isEmpty()) {
            updateAccountTypeError(context.getString(R.string.please_select_the_account_type))
            focusAccountType.requestFocus()
            isValid = false
        }

        if (accountHolder.trim().isEmpty()) {
            updateAccountHolderError(context.getString(R.string.enter_account_holder))
            focusAccountHolder.requestFocus()
            isValid = false
        } else if (accountHolder.trim().length < 4) {
            updateAccountHolderError(context.getString(R.string.name_should_contain_minimum_4_letters))
            focusAccountHolder.requestFocus()
            isValid = false
        } else if (!Pattern.compile("^[a-zA-Z ]*$").matcher(accountHolder).find()) {
            updateAccountHolderError(context.getString(R.string.character_special_validation))
            focusAccountHolder.requestFocus()
            isValid = false
        }
        if (isValid) {
            updateBankAccountDetail(context, bankDetail, navController)
        }
    }

    private val _inProgress = MutableStateFlow(false)
    val inProgress: StateFlow<Boolean> = _inProgress

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    private val _getBankList = MutableStateFlow<BankList?>(null)
    val getBankList: StateFlow<BankList?> = _getBankList

    fun getBankList(context: Context, navController: NavHostController) {
        if (_inProgress.value) {
            return
        }
        _inProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGetBankList(context, navController)
        }
    }

    private suspend fun handleGetBankList(
        context: Context,
        navController: NavHostController,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getBankList()
        }.onSuccess { response ->
            response?.let {
                handleGetBankListSuccess(response)
            }
        }.onFailure { error ->
            if (error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (checkForAccessToken && handleAuthGetAccessTokenApi()) {
                    handleGetBankList(context, navController, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGetBankListSuccess(response: BankList) {
        withContext(Dispatchers.Main) {
            _inProgress.value = false
            _isCompleted.value = true
            _getBankList.value = response
        }
    }

    private val _gettingBank = MutableStateFlow(false)
    val gettingBank: StateFlow<Boolean> = _gettingBank

    private val _gotBank = MutableStateFlow(false)
    val gotBank: StateFlow<Boolean> = _gotBank

    private val _bankAccount = MutableStateFlow<BankAccount?>(null)
    val bankAccount: StateFlow<BankAccount?> = _bankAccount

    private var isApiCalled = false
    fun getBankAccount(context: Context) {
        if (isApiCalled) return
        isApiCalled = true
        _gettingBank.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGetBankAccount(context)
        }
    }

    private suspend fun handleGetBankAccount(
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getBankAccount()
        }.onSuccess { response ->
            response?.let {
                handleGetBankAccountSuccess(response)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGetBankAccount(context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGetBankAccountSuccess(response: BankAccount) {
        withContext(Dispatchers.Main) {
            _gettingBank.value = false
            _gotBank.value = true
            _bankAccount.value = response
        }
    }

    private val _bankName = MutableStateFlow<IFSCResponse?>(null)
    val bankName: StateFlow<IFSCResponse?> = _bankName

    fun getBankName(ifscCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                ApiRepository.getBankName(ifscCode)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _bankName.value = it
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _bankName.value = null
                }
            }
        }
    }

//    private fun addBank(bankDetail: AddBankDetail, context: Context,navController: NavHostController,id:String) {
    private fun addBank(bankDetail: AddBankDetail, context: Context) {
        _bankDetailCollecting.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleAddBank(context, bankDetail)
//            handleAddBank(context, bankDetail,id,navController)
        }
    }

    private suspend fun handleAddBank(
        context: Context,
        bankDetail: AddBankDetail,
        checkForAccessToken: Boolean = true
//        context: Context, bankDetail: AddBankDetail, id:String,
//         navController: NavHostController,checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.addBank(bankDetail)
        }.onSuccess { response ->
            response?.let {
                handleAddBankSuccess(response)
//                handleAddBankSuccess(response, id,context, navController)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleAddBank(context, bankDetail, false)
//                    handleAddBank(context, bankDetail, id,navController,false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

//    private suspend fun handleAddBankSuccess(response: AddBankDetailResponse,id:String,context: Context,navController: NavHostController) {
    private suspend fun handleAddBankSuccess(response: AddBankDetailResponse) {
        withContext(Dispatchers.Main) {
            _bankDetailCollecting.value = false
            _bankDetailCollected.value = true
            _addBankDetail.value = response

//            if (_bankAccount.value == null) {
//                addBankDetail(
//                    bankDetail = BankDetail(
//                        accountNumber = _accountNumber.value.toString(),
//                        accountHolderName = _accountHolder.value.toString(),
//                        ifscCode = ifscCode.value.toString(),
//                        accountType = accountType.value.toString(),
//                        id = id,
//                        loanType = "PERSONAL_LOAN"
//
//                    ),
//                    context = context,
//                    navController = navController,
//                )
//            }
        }
    }

    fun accountDetailValidation(
        context: Context,
        accountNumber: String,
        accountHolder: String,
        ifscCode: String,
        focusAccountHolder: FocusRequester,
        focusAccountNumber: FocusRequester,
        focusIfscCode: FocusRequester,
        id: String
    ) {
        clearMessage()
        if (accountHolder.trim().isEmpty()) {
            updateAccountHolderError(context.getString(R.string.enter_account_holder))
            focusAccountHolder.requestFocus()
            requestKeyboard()
        } else if (!Pattern.compile("^[a-zA-Z ]*$").matcher(accountHolder).find()) {
            updateAccountHolderError(context.getString(R.string.character_special_validation))
            focusAccountHolder.requestFocus()
            requestKeyboard()
        } else if (accountHolder.trim().length < 4) {
            updateAccountHolderError(context.getString(R.string.enter_valid_account_number))
            focusAccountHolder.requestFocus()
            requestKeyboard()
        } else if (accountNumber.trim().isEmpty()) {
            updateAccountNumberError(context.getString(R.string.enter_account_number))
            focusAccountNumber.requestFocus()
            requestKeyboard()
        } else if (accountNumber.trim().length < 9) {
            updateAccountNumberError(context.getString(R.string.enter_valid_account_number))
            focusAccountNumber.requestFocus()
            requestKeyboard()
        } else if (ifscCode.trim().isEmpty()) {
            updateIfscCodeError(context.getString(R.string.enter_ifsc_code))
            focusIfscCode.requestFocus()
            requestKeyboard()
        } else if (CommonMethods().isValidIfscCode(ifscCode) != true) {
            updateIfscCodeError(context.getString(R.string.enter_valid_ifsc_code))
            focusIfscCode.requestFocus()
            requestKeyboard()
        } else {
            gstLoanEntityApproval(
                bankDetail = GstBankDetail(
                    accountNumber = accountNumber,
                    ifscCode = ifscCode,
                    accountHolderName = accountHolder,
                    id = id,
                    loanType = "INVOICE_BASED_LOAN"
                ),
                context = context
            )
        }
    }

    private val _gstBankDetailResponse = MutableStateFlow<GstOfferConfirmResponse?>(null)
    val gstBankDetailResponse: StateFlow<GstOfferConfirmResponse?> = _gstBankDetailResponse

    fun gstLoanEntityApproval(bankDetail: GstBankDetail, context: Context) {
        _bankDetailCollecting.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleGstLoanEntityApproval(bankDetail, context)
        }
    }

    private suspend fun handleGstLoanEntityApproval(
        bankDetail: GstBankDetail,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstLoanEntityApproval(bankDetail)
        }.onSuccess { response ->
            response?.let {
                handleGstLoanEntityApprovalSuccess(response, context)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGstLoanEntityApproval(bankDetail, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGstLoanEntityApprovalSuccess(
        response: GstOfferConfirmResponse,
        context: Context
    ) {
        withContext(Dispatchers.Main) {
            _bankDetailCollecting.value = false
            _bankDetailCollected.value = true
            _gstBankDetailResponse.value = response
        }
    }

    private val _loanApprovedResponse = MutableStateFlow<GstOfferConfirmResponse?>(null)
    val loanApprovedResponse: StateFlow<GstOfferConfirmResponse?> = _loanApprovedResponse

    fun gstLoanApproved(id: String, loanType: String, context: Context) {
        _bankDetailCollecting.value = true
        viewModelScope.launch(Dispatchers.Main) {
            handleGstLoanApproved(id, loanType, context)
        }
    }

    private suspend fun handleGstLoanApproved(
        id: String,
        loanType: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.gstLoanApproved(id, loanType)
        }.onSuccess { response ->
            response?.let {
                handleGstLoanApprovedSuccess(response, id, context)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handleGstLoanApproved(id, loanType, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handleGstLoanApprovedSuccess(
        response: GstOfferConfirmResponse,
        id: String,
        context: Context
    ) {
        withContext(Dispatchers.Main) {
            _bankDetailCollecting.value = false
            _bankDetailCollected.value = true
            _loanApprovedResponse.value = response
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
            _bankDetailCollecting.value = false
            _inProgress.value = false
            _gettingBank.value = false
        }
    }

    // Purchase Finance

    private val _pfBankDetailResponse = MutableStateFlow<PfOfferConfirmResponse?>(null)
    val pfBankDetailResponse: StateFlow<PfOfferConfirmResponse?> = _pfBankDetailResponse

    fun pfLoanEntityApproval(bankDetail: PfBankDetail, context: Context) {
        _bankDetailCollecting.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handlePfLoanEntityApproval(bankDetail, context)
        }
    }

    private suspend fun handlePfLoanEntityApproval(
        bankDetail: PfBankDetail,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.pfLoanEntityApproval(bankDetail)
        }.onSuccess { response ->
            response?.let {
                handlePfLoanEntityApprovalSuccess(response, context)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                // Get Access Token using RefreshToken
                if (handleAuthGetAccessTokenApi()) {
                    handlePfLoanEntityApproval(bankDetail, context, false)
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(error = error, context = context)
            }
        }
    }

    private suspend fun handlePfLoanEntityApprovalSuccess(
        response: PfOfferConfirmResponse,
        context: Context
    ) {
        withContext(Dispatchers.Main) {
            _bankDetailCollecting.value = false
            _bankDetailCollected.value = true
            _pfBankDetailResponse.value = response
        }
    }
}
