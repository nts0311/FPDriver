package com.sonnt.fpdriver.features.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.OrderInfo

abstract class OrderDetailViewModel: BaseViewModel() {

    abstract val hideMerchantName: Boolean
    abstract val price: LiveData<String>

    var orderInfo = MutableLiveData<OrderInfo>()

    init {
        getCurrentOrder()?.let { orderInfo.value = it }
    }

    fun getCurrentOrder(): OrderInfo? = OrderRepository.shared.latestOrder
}