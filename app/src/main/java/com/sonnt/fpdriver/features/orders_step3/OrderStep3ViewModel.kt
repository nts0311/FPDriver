package com.sonnt.fpdriver.features.orders_step3

import androidx.lifecycle.MutableLiveData
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.OrderInfo

class OrderStep3ViewModel: BaseViewModel() {
    var orderInfo = MutableLiveData<OrderInfo>()

    init {
        getCurrentOrder()?.let { orderInfo.value = it }
    }

    fun getCurrentOrder(): OrderInfo? = OrderRepository.shared.latestOrder
}