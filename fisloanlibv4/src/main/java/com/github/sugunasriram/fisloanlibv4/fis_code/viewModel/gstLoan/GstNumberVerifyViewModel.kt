package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel

class GstNumberVerifyViewModel : BaseViewModel() {

    private val _generalError: MutableLiveData<String?> = MutableLiveData("")
    val generalError: LiveData<String?> = _generalError

    fun updateGeneralError(errorMsg: String?) {
        _generalError.value = errorMsg
    }

    fun clearMessage(newData: String? = null) {
        updateGeneralError(newData)
    }
}
