package com.sonnt.fpdriver.features.orders_step2

import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoViewModel
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.utils.formatCurrency

class OrdersStep2ViewModel : OrdersDestinationInfoViewModel() {
    override val orderInfo: OrderInfo?
        get() = OrderRepository.shared.latestOrder
    override val toAddress: Address
        get() = orderInfo?.fromAddress ?: Address()
    override val phone: String
        get() = orderInfo?.merchantPhone ?: ""
    override val name: String
        get() = orderInfo?.merchantName ?: ""

    override var guide = """
        Mã đơn hàng: ${orderInfo?.orderId}
        Phí ship: ${orderInfo?.paymentInfo?.deliveryFee?.formatCurrency()}
        Thu khách: ${orderInfo?.paymentInfo?.calculatePrice()?.formatCurrency()}
    """.trimIndent()
}