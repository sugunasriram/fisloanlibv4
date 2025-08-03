package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.igm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CloseIssueBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CloseIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CreateIssueBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.CreateIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUpload
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.ImageUploadBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueByIdResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueCategories
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueListBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueListResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.IssueSubCategories
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm.OrderIssueResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateIssueViewModel : ViewModel() {

    private val _showInternetScreen = MutableLiveData(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _unexpectedError = MutableLiveData(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _unAuthorizedUser = MutableLiveData(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _showServerIssueScreen = MutableLiveData(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _middleLoan = MutableLiveData(false)
    val middleLoan: LiveData<Boolean> = _middleLoan

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

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
    fun clearImageUploadError() {
        _showImageNotUploadedError.value = false
        _imageUploadError.value=null
    }

    private val _shortDesc: MutableLiveData<String?> = MutableLiveData("")
    val shortDesc: LiveData<String?> = _shortDesc

    fun onShortDescChanged(shortDesc: String) {
        val sanitized = shortDesc
            .replace(Regex("[^\\p{ASCII}₹]"), "") // Remove non-ASCII except ₹
            .replace(Regex("\\s{2,}"), " ")
        if (sanitized.length <= 30) {
            _shortDesc.value = sanitized
            updateGeneralError(null)
        }
    }

    fun clearShortDesc() {
        _shortDesc.value = ""
    }

    private val _shortDescError: MutableLiveData<String?> = MutableLiveData("")
    val shortDescError: LiveData<String?> = _shortDescError

    fun updateShortDescError(shortDescError: String?) {
        _shortDescError.value = shortDescError
    }

    private val _longDesc: MutableLiveData<String?> = MutableLiveData("")
    val longDesc: LiveData<String?> = _longDesc

    private val _longDescError: MutableLiveData<String?> = MutableLiveData("")
    val longDescError: LiveData<String?> = _longDescError

    fun updateLongDescError(longDescError: String?) {
        _longDescError.value = longDescError
    }

    fun onLongDescriptionChanged(value: String) {
        val sanitized = value
            .replace(Regex("[^\\p{ASCII}\\s₹]"), "")  // Remove non-ASCII except ₹
            .replace(Regex(" {2,}"), " ")
        _longDesc.value = sanitized
    }

    private val _category: MutableLiveData<String?> = MutableLiveData("")
    val category: LiveData<String?> = _category

    private val _categoryError: MutableStateFlow<String?> = MutableStateFlow(null)
    val categoryError: StateFlow<String?> = _categoryError

    private val _subCategoryError: MutableStateFlow<String?> = MutableStateFlow(null)
    val subCategoryError: StateFlow<String?> = _subCategoryError

    private val _issueListResponse = MutableStateFlow<IssueListResponse?>(null)
    val issueListResponse: StateFlow<IssueListResponse?> = _issueListResponse

    private val _navigationToSignIn = MutableStateFlow(false)
    val navigationToSignIn: StateFlow<Boolean> = _navigationToSignIn

    fun updateCategoryError(categoryError: String?) {
        _categoryError.value = categoryError
    }

    fun updateSubCategoryError(subCategoryError: String?) {
        _subCategoryError.value = subCategoryError
    }

    fun getIssueListForUser(context: Context, issueListBody: IssueListBody) {
        _issueListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleIssuesListForUser(context, issueListBody)
        }
    }

    private suspend fun handleIssuesListForUser(
        context: Context,
        issueListBody: IssueListBody,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getIssueListForUser(issueListBody)
        }.onSuccess { response ->
            response?.let {
                handleIssueListForUserResponse(response)
            }
        }.onFailure { error ->
            error.let {
                if (checkForAccessToken &&
                    error is ResponseException &&
                    error.response.status.value == 401
                ) {
                    if (handleAuthGetAccessTokenApi()) {
                        handleIssuesCategories(context, false)
                    } else {
                        _navigationToSignIn.value = true
                    }
                } else {
                    handleFailure(context = context, error = error)
                }
            }
        }
    }

    private suspend fun handleIssueListForUserResponse(response: IssueListResponse) {
        withContext(Dispatchers.Main) {
            _issueListLoading.value = false
            _issueListLoaded.value = true
            _issueListResponse.value = response
        }
    }

    private val _imageUploadError: MutableStateFlow<String?> = MutableStateFlow(null)
    val imageUploadError: StateFlow<String?> = _imageUploadError
    fun updateImageUploadError(error: String?) {
        _imageUploadError.value = error
    }

    fun updateImageNotUploadedErrorMessage(errorMsg: String?) {
        _showImageNotUploadedError.value = true
        _imageUploadError.value = errorMsg
    }

    private val _showImageNotUploadedError = MutableStateFlow(false)
    val showImageNotUploadedError: StateFlow<Boolean> = _showImageNotUploadedError

    fun updateValidation(
        shortDesc: String,
        longDesc: String,
        categorySelectedText: String,
        fromFlow: String,
        subCategorySelectedText: String,
        image: List<String>,
        context: Context,
        orderId: String,
        providerId: String,
        orderState: String
    ) {
        val loanType = if (fromFlow.equals(
                "Personal Loan",
                ignoreCase = true
            ) || fromFlow.equals("PERSONAL_LOAN", ignoreCase = true)
        ) {
            "PERSONAL_LOAN"
        } else {
            "INVOICE_BASED_LOAN"
        }
        createIssue(
            createIssueBody = CreateIssueBody(
                loanType = loanType, orderID = orderId, orderState = orderState,
                providerID = providerId, issueCategory = categorySelectedText,
                issueSubCategory = subCategorySelectedText, issueShortDisc = shortDesc,
                issueLongDisc = longDesc, issueType = "ISSUE", status = "OPEN",
//                    discriptionContentType = "image/png",
                discriptionContentType = "text/plain",
                discriptionURL = "https://abc.com",
                descriptionImages = image
            ),
            context = context
        )
    }

    private val _issueListLoading = MutableStateFlow(false)
    val issueListLoading: StateFlow<Boolean> = _issueListLoading

    private val _issueListLoaded = MutableStateFlow(false)
    val issueListLoaded: StateFlow<Boolean> = _issueListLoaded

    private val _issueCategories = MutableStateFlow<IssueCategories?>(null)
    val issueCategories: StateFlow<IssueCategories?> = _issueCategories

    fun getIssueCategories(context: Context) {
        _issueListLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleIssuesCategories(context)
        }
    }

    private suspend fun handleIssuesCategories(
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getIssueCategories()
        }.onSuccess { response ->
            response?.let {
                handleIssueCategoryResponse(response)
            }
        }.onFailure { error ->
            error.let {
                if (checkForAccessToken && error is ResponseException && error.response.status.value == 401) {
                    if (handleAuthGetAccessTokenApi()) {
                        handleIssuesCategories(context, false)
                    } else {
                        _navigationToSignIn.value = true
                    }
                } else {
                    handleFailure(context = context, error = error)
                }
            }
        }
    }

    private suspend fun handleIssueCategoryResponse(response: IssueCategories) {
        withContext(Dispatchers.Main) {
            _issueListLoading.value = false
            _issueListLoaded.value = true
            _issueCategories.value = response
        }
    }

    private val _subIssueLoading = MutableStateFlow(false)
    val subIssueLoading: StateFlow<Boolean> = _subIssueLoading

    private val _subIssueLoaded = MutableStateFlow(false)
    val subIssueLoaded: StateFlow<Boolean> = _subIssueLoaded

    private val _subIssueCategory = MutableStateFlow<IssueSubCategories?>(null)
    val subIssueCategory: StateFlow<IssueSubCategories?> = _subIssueCategory

    fun getIssueWithSubCategories(context: Context, category: String) {
        _subIssueLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleIssueWithSubCategories(category, context)
        }
    }

    private suspend fun handleIssueWithSubCategories(
        category: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.getIssueWithSubCategories(category = category)
        }.onSuccess { response ->
            response?.let {
                handleIssueWithSubCategoriesResponse(response)
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleIssueWithSubCategories(
                        category = category,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(context = context, error = error)
            }
        }
    }

    private suspend fun handleIssueWithSubCategoriesResponse(response: IssueSubCategories) {
        withContext(Dispatchers.Main) {
            _subIssueLoading.value = false
            _subIssueLoaded.value = true
            _subIssueCategory.value = response
        }
    }

    private val _imageUploading = MutableStateFlow(false)
    val imageUploading: StateFlow<Boolean> = _imageUploading

    private val _imageUploaded = MutableStateFlow(false)
    val imageUploaded: StateFlow<Boolean> = _imageUploaded

    private val _imageUploadResponse = MutableStateFlow<ImageUpload?>(null)
    val imageUploadResponse: StateFlow<ImageUpload?> = _imageUploadResponse

    fun imageUpload(imageUploadBody: ImageUploadBody, context: Context) {
        _imageUploading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleImageUpload(imageUploadBody, context)
        }
    }

    fun removeImage() {
        _imageUploading.value = false
        _imageUploaded.value = false
        _imageUploadResponse.value = null
    }

    private suspend fun handleImageUpload(
        imageUploadBody: ImageUploadBody,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.imageUpload(imageUploadBody)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _imageUploading.value = false
                _imageUploaded.value = true
                _imageUploadResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleImageUpload(
                        imageUploadBody = imageUploadBody,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(context = context, error = error)
            }
        }
    }

    private val _issueCreating = MutableStateFlow(false)
    val issueCreating: StateFlow<Boolean> = _issueCreating

    private val _issueCreated = MutableStateFlow(false)
    val issueCreated: StateFlow<Boolean> = _issueCreated

    private val _createIssueResponse = MutableStateFlow<CreateIssueResponse?>(null)
    val createIssueResponse: StateFlow<CreateIssueResponse?> = _createIssueResponse

    private fun createIssue(createIssueBody: CreateIssueBody, context: Context) {
        _issueCreating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleCreateIssue(createIssueBody, context)
        }
    }

    private suspend fun handleCreateIssue(
        createIssueBody: CreateIssueBody,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.createIssue(createIssueBody)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _issueCreating.value = false
                _issueCreated.value = true
                _createIssueResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleCreateIssue(
                        createIssueBody = createIssueBody,
                        context = context,
                        checkForAccessToken = false
                    )
                } else {
                    _navigationToSignIn.value = true
                }
            } else {
                handleFailure(context = context, error = error)
            }
        }
    }

    private val _issueClosing = MutableStateFlow(false)
    val issueClosing: StateFlow<Boolean> = _issueClosing

    private val _issueClosed = MutableStateFlow(false)
    val issueClosed: StateFlow<Boolean> = _issueClosed

    private val _closeIssueResponse = MutableStateFlow<CloseIssueResponse?>(null)
    val closeIssueResponse: StateFlow<CloseIssueResponse?> = _closeIssueResponse

    fun closeIssue(closeIssueBody: CloseIssueBody, context: Context) {
        _issueClosing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleCloseIssue(closeIssueBody, context)
        }
    }

    private suspend fun handleCloseIssue(
        closeIssueBody: CloseIssueBody,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.closeIssue(closeIssueBody)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _issueClosing.value = false
                _issueClosed.value = true
                _closeIssueResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleCloseIssue(
                        closeIssueBody = closeIssueBody,
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

    private val _checkingStatus = MutableStateFlow(false)
    val checkingStatus: StateFlow<Boolean> = _checkingStatus

    private val _checkedStatus = MutableStateFlow(false)
    val checkedStatus: StateFlow<Boolean> = _checkedStatus

    private val _issueStatusResponse = MutableStateFlow<IssueStatusResponse?>(null)
    val issueStatusResponse: StateFlow<IssueStatusResponse?> = _issueStatusResponse

    fun issueStatus(issueId: String, context: Context) {
        _checkingStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleIssueStatus(issueId = issueId, context = context)
        }
    }

    private suspend fun handleIssueStatus(
        issueId: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.issueStatus(issueId)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _checkingStatus.value = false
                _checkedStatus.value = true
                _issueStatusResponse.value = it

                // Sugu - Start
                issueById(issueId, context)
                // Sugu - End

                it?.let {
                    it.data?.message?.let { it1 ->
                        CommonMethods().toastMessage(context, it1)
                    }
                }
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleIssueStatus(
                        issueId = issueId,
                        context = context,
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

    private val _loadingIssue = MutableStateFlow(false)
    val loadingIssue: StateFlow<Boolean> = _loadingIssue

    private val _loadedIssue = MutableStateFlow(false)
    val loadedIssue: StateFlow<Boolean> = _loadedIssue

    private val _issueByIdResponse = MutableStateFlow<IssueByIdResponse?>(null)
    val issueByIdResponse: StateFlow<IssueByIdResponse?> = _issueByIdResponse

    fun issueById(issueId: String, context: Context) {
        _loadingIssue.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleIssueById(issueId = issueId, context = context)
        }
    }

    private suspend fun handleIssueById(
        issueId: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.issueById(issueId)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _loadingIssue.value = false
                _loadedIssue.value = true
                _issueByIdResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleIssueById(
                        issueId = issueId,
                        context = context,
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

    private val _orderIssuesLoading = MutableStateFlow(false)
    val orderIssuesLoading: StateFlow<Boolean> = _orderIssuesLoading

    private val _orderIssuesLoaded = MutableStateFlow(false)
    val orderIssuesLoaded: StateFlow<Boolean> = _orderIssuesLoaded

    private val _orderIssuesResponse = MutableStateFlow<OrderIssueResponse?>(null)
    val orderIssuesResponse: StateFlow<OrderIssueResponse?> = _orderIssuesResponse

    fun orderIssues(orderId: String, context: Context) {
        _orderIssuesLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleOrderIssues(orderId = orderId, context = context)
        }
    }

    private suspend fun handleOrderIssues(
        orderId: String,
        context: Context,
        checkForAccessToken: Boolean = true
    ) {
        kotlin.runCatching {
            ApiRepository.orderIssues(orderId)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                _orderIssuesLoading.value = false
                _orderIssuesLoaded.value = true
                _orderIssuesResponse.value = it
            }
        }.onFailure { error ->
            if (checkForAccessToken &&
                error is ResponseException &&
                error.response.status.value == 401
            ) {
                if (handleAuthGetAccessTokenApi()) {
                    handleOrderIssues(
                        orderId = orderId,
                        context = context,
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
            _subIssueLoading.value = false
            _imageUploading.value = false
            _issueCreating.value = false
            _checkingStatus.value = false
            _loadingIssue.value = false
            _orderIssuesLoading.value = false
            _issueListLoading.value = false
        }
    }
}
