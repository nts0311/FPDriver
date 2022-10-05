package com.sonnt.fpdriver.features.orders

import androidx.lifecycle.asLiveData
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import kotlinx.coroutines.flow.map

class OrdersViewModel: BaseViewModel() {
    val newOrderRequestFlow = OrderRepository.newOrderRequestFlow

    val orderInfo = newOrderRequestFlow.asLiveData()
}