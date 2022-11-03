package com.sonnt.fpdriver.data.repos

import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.Endpoint
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.network.stomp.WSMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrderRepository private constructor(){
    private val stompMessageHub = AppModule.provideStompMessageHub()
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    var latestOrder: OrderInfo? = null

    private var newOrderRequestFlow: Flow<OrderInfo>? = null
    private var orderStatusFlow: Flow<WSMessage>? = null

    fun getNewOrderRequestFlow(): Flow<OrderInfo> {
        if (newOrderRequestFlow == null) {
            newOrderRequestFlow = stompMessageHub.subscribeTo(Endpoint.newOrderRequest, OrderInfo::class.java)
                ?.onEach {
                    latestOrder = it
                }
        }

        return newOrderRequestFlow!!
    }

    fun getOrderStatusFlow(): Flow<WSMessage> {
        if (orderStatusFlow == null) {
            orderStatusFlow = stompMessageHub.subscribeTo(Endpoint.orderStatus, WSMessage::class.java)
        }

        return orderStatusFlow!!
    }

    suspend fun getActiveOrder(): OrderInfo? {
        val response = callApi {
            NetworkModule.orderService.getActiveOrder()
        }

        return when (response) {
            is ApiResult.Success -> {
                latestOrder = response.data?.orderInfo
                return response.data?.orderInfo
            }
            is ApiResult.Failed -> {
                null
            }
        }
    }

    companion object {
        val shared = OrderRepository()
    }
}