package com.sonnt.fpdriver.data.repos

import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.StompEndpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object OrderRepository {
    private val stompMessageHub = AppModule.provideStompMessageHub()
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    var latestOrder: OrderInfo? = null

    val newOrderRequestFlow: Flow<OrderInfo> = stompMessageHub.subscribeTo(StompEndpoint.newOrderRequest, OrderInfo::class.java)

    init {
        newOrderRequestFlow.onEach {
            latestOrder = it
        }
    }

}