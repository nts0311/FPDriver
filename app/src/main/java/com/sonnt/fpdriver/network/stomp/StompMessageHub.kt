package com.sonnt.fpdriver.network.stomp

import android.annotation.SuppressLint
import android.util.Log
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import okhttp3.MediaType
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompCommand
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage

class StompMessageHub {
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "https://0903-117-7-207-167.ap.ngrok.io/stomp")
    private val gson = AppModule.provideGson()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val subscriberFlows = mutableMapOf<String, SharedFlow<*>>()

    init {
        stompClient.connect(listOf(StompHeader("Authorization", "Bearer ${AuthDataSource.authToken}")))
    }

    @SuppressLint("CheckResult")
    fun <T> subscribeTo(destination: String, clazz: Class<T>): SharedFlow<T> {
        if (subscriberFlows[destination] == null) {
            subscriberFlows[destination] = stompClient.topic(destination).asFlow().map { topicMessage ->
                val jsonStr = topicMessage.payload

                if (clazz == String::class.java) {
                    return@map jsonStr
                }

                gson.fromJson(jsonStr, clazz)
            }.conflate().shareIn(coroutineScope, SharingStarted.Eagerly, 0)
        }

        return subscriberFlows[destination] as SharedFlow<T>
    }

    fun <T> sendJson(data: T, destination: String): Flow<Any> {
        val headers = listOf(
            StompHeader(StompHeader.CONTENT_TYPE, "application/json;charset=UTF-8"),
            StompHeader(StompHeader.DESTINATION, destination)
        )

        val payload = gson.toJson(data)

        val message = StompMessage(StompCommand.SEND, headers, payload)
        return stompClient.send(message).toFlowable<Any>().asFlow()
    }
}

