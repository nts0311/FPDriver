package com.sonnt.fpdriver.features.main

import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.dto.response.BaseResponse

class GetActiveOrderResponse(
    val orderInfo: OrderInfo?
): BaseResponse()