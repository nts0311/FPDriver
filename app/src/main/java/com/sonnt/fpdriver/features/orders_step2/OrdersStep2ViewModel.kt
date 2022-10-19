package com.sonnt.fpdriver.features.orders_step2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoViewModel
import com.sonnt.fpdriver.features.orders.AcceptOrderRequest
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.utils.formatCurrency
import kotlinx.coroutines.launch

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

    val onApiSuccess = MutableLiveData<Boolean>()

    fun arrivedAtMerchant() {
        viewModelScope.launch {
            loading(true)

            val orderId = OrderRepository.shared.latestOrder?.orderId ?: return@launch
            val params = ArrivedAtMerchantRequest(orderId)

            val response = callApi {
                NetworkModule.orderService.arrivedAtMerchant(params)
            }
            loading(false)
            when(response) {
                is ApiResult.Success -> onApiSuccess.value = true
                is ApiResult.Failed -> error("Xác nhận yêu cầu thất bại!")
            }
        }
    }

}