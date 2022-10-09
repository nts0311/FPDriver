package com.sonnt.fpdriver.features.order_destination_info

import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.OrderInfo

abstract class OrdersDestinationInfoViewModel: BaseViewModel() {
    abstract val orderInfo: OrderInfo?

    abstract val toAddress: Address
    abstract val phone: String
    abstract val name: String
    open val guide: String? = null
}