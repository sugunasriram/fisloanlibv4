package com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.gstLoan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.BaseViewModel

class GstInvoiceDetailViewModel : BaseViewModel() {
    private val _checkBoxDetail: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkBoxDetail: LiveData<Boolean> = _checkBoxDetail

    fun onCheckBoxDetailChanged(checkBoxDetail: Boolean) {
        _checkBoxDetail.value = checkBoxDetail
    }
}
