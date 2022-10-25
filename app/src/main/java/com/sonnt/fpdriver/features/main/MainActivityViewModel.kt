package com.sonnt.fpdriver.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.model.OrderStatus
import kotlinx.coroutines.launch

class MainActivityViewModel: BaseViewModel() {

    val screenDestination = MutableLiveData<Int>()

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