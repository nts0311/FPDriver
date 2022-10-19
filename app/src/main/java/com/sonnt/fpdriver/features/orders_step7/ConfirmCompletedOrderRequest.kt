package com.sonnt.fpdriver.features.orders_step7

data class ConfirmCompletedOrderRequest(
    val orderId: Long,
    val evidenceImageUrl: String
)