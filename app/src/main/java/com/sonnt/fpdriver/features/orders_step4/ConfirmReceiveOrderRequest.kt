package com.sonnt.fpdriver.features.orders_step4

data class ConfirmReceiveOrderRequest(
    val orderId: Long,
    val billImageUrl: String
)