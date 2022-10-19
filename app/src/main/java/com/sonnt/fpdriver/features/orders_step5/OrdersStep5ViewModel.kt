package com.sonnt.fpdriver.features.orders_step5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoViewModel
import com.sonnt.fpdriver.features.orders_step2.ArrivedAtMerchantRequest
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.utils.formatCurrency
import kotlinx.coroutines.launch

class OrdersStep5ViewModel : OrdersDestinationInfoViewModel() {
    override val orderInfo: OrderInfo?
        get() = OrderRepository.shared.latestOrder
    override val toAddress: Address
        get() = orderInfo?.toAddress ?: Address()
    override val phone: String
        get() = orderInfo?.customerPhone ?: ""
    override val name: String
        get() = orderInfo?.customerName ?: ""

    val onApiSuccess = MutableLiveData<Boolean>()

    fun arrivedAtCustomer() {
        viewModelScope.launch {
            loading(true)

            val orderId = OrderRepository.shared.latestOrder?.orderId ?: return@launch
            val params = ArrivedAtCustomerRequest(orderId)

            val response = callApi {
                NetworkModule.orderService.arrivedAtCustomer(params)
            }
            loading(false)
            when(response) {
                is ApiResult.Success -> onApiSuccess.value = true
                is ApiResult.Failed -> error("Có lỗi xảy ra, vui lòng thử lại.")
            }
        }
    }

}