package com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.BaseViewModel

class UserDetailViewModel : BaseViewModel() {
    private val _phNumber: MutableLiveData<String> = MutableLiveData("")
    val phNumber: LiveData<String> = _phNumber

    fun onPhNumberChanged(phNuber: String) {
        _phNumber.value = phNuber
    }
}
