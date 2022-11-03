package com.sonnt.fpdriver.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.message.WSConnectedEvent
import com.sonnt.fpdriver.model.OrderStatus
import com.sonnt.fpdriver.network.Endpoint
import com.sonnt.fpdriver.network.stomp.WSMessage
import com.sonnt.fpdriver.network.stomp.WSMessageCode
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivityViewModel: BaseViewModel() {

    val screenDestination = MutableLiveData<Int>()
    val orderCanceled = MutableLiveData<String>()

    init {
        EventBus.getDefault().register(this)
    }

    fun getActiveOrder() {
        viewModelScope.launch {
            val orderInfo = OrderRepository.shared.getActiveOrder() ?: return@launch

            val destinationId = when(orderInfo.getOrderStatus()) {
                OrderStatus.PREPARING -> R.id.ordersStep2Fragment
                OrderStatus.PICKING_UP -> R.id.orderStep3Fragment
                OrderStatus.DELIVERING ->R.id.ordersStep5Fragment
                else -> -1
            }

            screenDestination.value = destinationId
        }
    }

    @Subscribe
    fun onWSConnected(event: WSConnectedEvent) {
        OrderRepository.shared.getOrderStatusFlow()
            .filter { it.code == WSMessageCode.CANCEL_ORDER.code }
            .map { it.getBody(CanceledOrderMessage::class.java)?.reason ?: "" }
            .onEach { orderCanceled.value = it }
            .launchIn(viewModelScope)

    }

}

data class CanceledOrderMessage(val id: Long, val reason: String)