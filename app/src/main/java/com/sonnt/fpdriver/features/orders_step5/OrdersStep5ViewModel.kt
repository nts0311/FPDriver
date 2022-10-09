package com.sonnt.fpdriver.features.orders_step5

import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoViewModel
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.utils.formatCurrency

class OrdersStep5ViewModel : OrdersDestinationInfoViewModel() {
    override val orderInfo: OrderInfo?
        get() = OrderRepository.shared.latestOrder
    override val toAddress: Address
        get() = orderInfo?.toAddress ?: Address()
    override val phone: String
        get() = orderInfo?.customerPhone ?: ""
    override val name: String
        get() = orderInfo?.customerName ?: ""
}