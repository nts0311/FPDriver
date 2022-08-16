package com.sonnt.fpdriver

import androidx.lifecycle.liveData
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.dto.request.AuthRequest
import com.sonnt.fpdriver.data.network.ApiResult
import com.sonnt.fpdriver.data.network.NetworkModule
import com.sonnt.fpdriver.data.network.callApi

class MainActivityViewModel: BaseViewModel() {
    var loginInfo = liveData {
        val authenticationResponse = callApi {
            NetworkModule.authService.login(AuthRequest("son", "123456"))
        }
        if (authenticationResponse is ApiResult.Success) {
            authenticationResponse.data?.also {
                emit(it)
            }
        }
    }
}