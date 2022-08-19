package com.sonnt.fpdriver

import android.util.Log
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.network.dto.request.AuthRequest
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.network.dto.SimpleMessage
import com.sonnt.fpdriver.network.stomp.StompMessageHub
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityViewModel: BaseViewModel() {

    val stompMessageHub = AppModule.provideStompMessageHub()
    var count = 0

    val latestMessage = stompMessageHub.subscribeTo("/users/queue/messages", SimpleMessage::class.java)
        .map { "${it.username}: ${it.message}"  }
        .asLiveData()

    init {
        setupMessage()
    }

    fun setupMessage() {
        viewModelScope.launch {
            val authenticationResponse = callApi {
                NetworkModule.authService.login(AuthRequest("son", "123456"))
            }
            if (authenticationResponse is ApiResult.Success) {
                authenticationResponse.data?.also {
                    AuthDataSource.authToken = it.jwtToken
                }
            }
        }
    }

    fun sendMessage(view: View) {
        stompMessageHub.sendJson(SimpleMessage("son1", "Hello iphone: ${count++}"), "/app/hello").onEach {
            Log.e("aAA", "send done")
        }
            .launchIn(viewModelScope)
    }
}