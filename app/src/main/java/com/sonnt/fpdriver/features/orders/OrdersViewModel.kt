package com.sonnt.fpdriver.features.orders

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.features._base.SingleLiveEvent
import com.sonnt.fpdriver.message.WSConnectedEvent
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.network.dto.request.AuthRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class OrdersViewModel: BaseViewModel() {
    val limitTimeAcceptOrder = 60000L
    val interval = 1000L

    val orderInfo = MutableLiveData<OrderInfo>()
    val hasOrder = MutableLiveData<Boolean>()
    val submitButtonText = MutableLiveData("Chấp nhận")

    val onAcceptOrderSuccess = SingleLiveEvent<Boolean>()

    private var timer: CountDownTimer? = null

    init {
        EventBus.getDefault().register(this)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(limitTimeAcceptOrder, interval) {
            override fun onTick(millisUntilFinished: Long) {
                submitButtonText.value = "Chấp nhận(${millisUntilFinished / 1000})"
            }

            override fun onFinish() {
                submitButtonText.value = "Chấp nhận"
                hasOrder.value = false
            }
        }.start()
    }

    fun clearOrder() {
        hasOrder.value = false
        timer?.cancel()
    }

    fun confirmReceiveOrder() {
        viewModelScope.launch {
            loading(true)

            val orderId = OrderRepository.shared.latestOrder?.orderId ?: return@launch
            val params = AcceptOrderRequest(orderId)

            val response = callApi {
                NetworkModule.orderService.acceptOrder(params)
            }
            loading(false)
            when(response) {
                is ApiResult.Success -> onAcceptOrderSuccess.value = true
                is ApiResult.Failed -> error("Xác nhận yêu cầu thất bại!")
            }
        }
    }

    @Subscribe
    fun onWSConnected(event: WSConnectedEvent) {
        OrderRepository.shared.getNewOrderRequestFlow()
            .onEach {
                orderInfo.value = it
                hasOrder.value = true
                startTimer()
                }.launchIn(viewModelScope)
    }

}