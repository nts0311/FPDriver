package com.sonnt.fpdriver.features.orders_step7

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.features._base.SingleLiveEvent
import com.sonnt.fpdriver.features.order_transfer_confirmation.OrdersTransferConfirmationViewModel
import com.sonnt.fpdriver.features.orders_step4.ConfirmReceiveOrderRequest
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.utils.formatCurrency
import kotlinx.coroutines.launch
import java.lang.Exception

class OrdersStep7ViewModel: OrdersTransferConfirmationViewModel() {
    override val price: LiveData<String>
        get() = orderInfo.map { it.paymentInfo.calculatePrice().formatCurrency() }

    val onApiSuccess = SingleLiveEvent<Boolean>()

    fun confirmDeliveredOrderToCustomer() {

        suspend fun sendApiRequest() {
            loading(true)

            val orderId = OrderRepository.shared.latestOrder?.orderId ?: return
            val params = ConfirmCompletedOrderRequest(orderId, imageUrl)

            val response = callApi {
                NetworkModule.orderService.confirmCompletedOrder(params)
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