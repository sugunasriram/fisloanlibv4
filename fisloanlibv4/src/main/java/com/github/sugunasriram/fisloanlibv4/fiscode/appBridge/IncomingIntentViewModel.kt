package com.github.sugunasriram.fisloanlibv4.fiscode.appBridge

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.AuthOtp
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomingIntentViewModel : BaseViewModel() {

    private val _showInternetScreen = MutableLiveData(false)
    val showInternetScreen: LiveData<Boolean> = _showInternetScreen

    private val _showTimeOutScreen = MutableLiveData(false)
    val showTimeOutScreen: LiveData<Boolean> = _showTimeOutScreen

    private val _showServerIssueScreen = MutableLiveData(false)
    val showServerIssueScreen: LiveData<Boolean> = _showServerIssueScreen

    private val _unAuthorizedUser = MutableLiveData(false)
    val unAuthorizedUser: LiveData<Boolean> = _unAuthorizedUser

    private val _unexpectedError = MutableLiveData(false)
    val unexpectedError: LiveData<Boolean> = _unexpectedError

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError
    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }
    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }

    private val _isVerifySessionChecking = MutableStateFlow(false)
    val isVerifySessionChecking: StateFlow<Boolean> = _isVerifySessionChecking

    private val _isVerifySessionSuccess = MutableStateFlow(false)
    val isVerifySessionSuccess: StateFlow<Boolean> = _isVerifySessionSuccess

    private val _verifySessionResponse = MutableStateFlow<VerifySessionResponse?>(null)
    val verifySessionResponse: StateFlow<VerifySessionResponse?> = _verifySessionResponse

    fun verifySessionApi(
        sessionId: String,
        context: Context
    ) {
        _isVerifySessionChecking.value = true
        viewModelScope.launch(Dispatchers.IO) {
            handleVerifySessionApi(context, sessionId)
        }
    }

    private suspend fun handleVerifySessionApi(context: Context, sessionId: String) {
        kotlin.runCatching {
            ApiRepository.verifySession(sessionId)
        }.onSuccess { response ->
            response?.let {
                handleVerifySessionSuccessResponse(response)
            }
        }.onFailure { error ->
            // Session Management
            Log.e("TAG", "Session some failure: ${error.message}")
            _showServerIssueScreen.postValue(true)
        }
    }

    private suspend fun handleVerifySessionSuccessResponse(response: VerifySessionResponse) {
        withContext(Dispatchers.Main) {
            _isVerifySessionSuccess.value = true
            _isVerifySessionChecking.value = false

            // Save the tokens in the TokenManagers
            response.data?.accessToken?.let { accessToken ->
                TokenManager.save("accessToken", accessToken)
            }
            response.data?.refreshToken?.let { refreshToken ->
                TokenManager.save("refreshToken", refreshToken)
            }
            response.data?.sseId?.let { sseId ->
                TokenManager.save("sseId", sseId)
            }
            _verifySessionResponse.value = response
        }
    }


}
