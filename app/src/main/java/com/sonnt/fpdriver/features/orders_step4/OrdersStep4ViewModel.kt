package com.sonnt.fpdriver.features.orders_step4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_transfer_confirmation.OrdersTransferConfirmationViewModel
import com.sonnt.fpdriver.features.orders_step2.ArrivedAtMerchantRequest
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.utils.formatCurrency
import kotlinx.coroutines.launch

class OrdersStep4ViewModel: OrdersTransferConfirmationViewModel() {
    override val price: LiveData<String>
        get() = orderInfo.map { it.paymentInfo.price.formatCurrency() }

    val onApiSuccess = MutableLiveData<Boolean>()

    fun confirmReceivedOrderFromMerchant() {

        suspend fun sendApiRequest() {
            loading(true)

            val orderId = OrderRepository.shared.latestOrder?.orderId ?: return
            val params = ConfirmReceiveOrderRequest(orderId, imageUrl)

            val response = callApi {
                NetworkModule.orderService.confirmReceivedOrder(params)
            }
            loading(false)
            when(response) {
                is ApiResult.Success -> onApiSuccess.value = true
                is ApiResult.Failed -> error("Xác nhận nhận đơn thất bại!")
            }
        }

        viewModelScope.launch {
            sendApiRequest()
        }
    }

}