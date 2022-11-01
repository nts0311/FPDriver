package com.sonnt.fpdriver.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.model.OrderStatus
import com.sonnt.fpdriver.network.Endpoint
import com.sonnt.fpdriver.network.stomp.WSMessage
import com.sonnt.fpdriver.network.stomp.WSMessageCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivityViewModel: BaseViewModel() {

    val screenDestination = MutableLiveData<Int>()
    val orderCanceled = OrderRepository.shared.orderStatusFlow
        ?.filter { it.code == WSMessageCode.CANCEL_ORDER.code }
        ?.map { it.getBody(CanceledOrderMessage::class.java)?.reason ?: "" }
        ?.asLiveData()

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

}

data class CanceledOrderMessage(val id: Long, val reason: String)