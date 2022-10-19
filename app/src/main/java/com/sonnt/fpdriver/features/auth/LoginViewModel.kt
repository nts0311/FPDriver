package com.sonnt.fpdriver.features.auth

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.network.dto.request.AuthRequest
import com.sonnt.fpdriver.network.dto.response.AuthenticationResponse
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {

    var username: String = "driver"
    var password: String = "123456"

    var isLoginButtonEnabled = MutableLiveData(true)

    var isLoginSuccess = MutableLiveData(false)

    fun login() {
        viewModelScope.launch {
            loading(true)

            val authenticationResponse = callApi {
                NetworkModule.authService.login(AuthRequest(username, password))
            }
            loading(false)
            when(authenticationResponse) {
                is ApiResult.Success -> onLoginSuccess(authenticationResponse)
                is ApiResult.Failed -> onLoginFailed(authenticationResponse)
            }
        }
    }

    private fun onLoginSuccess(authResponse:  ApiResult.Success<AuthenticationResponse?>) {
        authResponse.data?.also {
            AuthDataSource.authToken = it.jwtToken
            isLoginSuccess.value = true
        }
    }

    private fun onLoginFailed(authResponse: ApiResult.Failed<AuthenticationResponse?>?) {
        val errorMessage = authResponse?.error?.message ?: "Lỗi hệ thống, vui lòng thử lại sau!"
        error(errorMessage)
    }

    fun validateInput() {
        var isEnabled = true

        if(username == null || password == null || username?.isEmpty() == true || password?.isEmpty() == true)
            isEnabled = false

        if ((password.count()) < 6)
            isEnabled = false

        isLoginButtonEnabled.value = isEnabled
    }
}