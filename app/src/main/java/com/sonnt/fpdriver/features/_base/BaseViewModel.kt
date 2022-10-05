package com.sonnt.fpdriver.features._base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sonnt.fpdriver.network.ApiResult

open class BaseViewModel: ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    protected fun error(errorMsg: String) {
        _errorMessage.value = errorMsg
    }

    protected fun loading(isLoading: Boolean) {
        _loading.value = isLoading
    }
}