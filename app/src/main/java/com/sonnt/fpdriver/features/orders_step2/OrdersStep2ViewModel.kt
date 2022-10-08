package com.sonnt.fpdriver.features.orders_step2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.utils.formatCurrency

class OrdersStep2ViewModel: BaseViewModel() {
    var orderInfo = MutableLiveData<OrderInfo>()
    var description = orderInfo.map { order ->
        return@map """
            Mã đơn hàng: ${order.orderId}
            Phí ship: ${order.paymentInfo.deliveryFee.formatCurrency()}
            Thu khách: ${order.paymentInfo.calculatePrice().formatCurrency()}
        """.trimIndent()
    }

    init {
        getCurrentOrder()?.let { orderInfo.value = it }
    }

    fun getCurrentOrder(): OrderInfo? = OrderRepository.shared.latestOrder
}