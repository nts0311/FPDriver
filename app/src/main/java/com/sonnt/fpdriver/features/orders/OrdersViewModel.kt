package com.sonnt.fpdriver.features.orders

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.OrderInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class OrdersViewModel: BaseViewModel() {
    val limitTimeAcceptOrder = 60000L
    val interval = 1000L

    private val newOrderRequestFlow = OrderRepository.shared.newOrderRequestFlow
        ?.onEach {
            hasOrder.value = true
            startTimer()
        }

    val orderInfo = newOrderRequestFlow?.asLiveData()
    val hasOrder = MutableLiveData(false)
    val submitButtonText = MutableLiveData("Chấp nhận")


    private fun startTimer() {
        object : CountDownTimer(limitTimeAcceptOrder, interval) {
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
    }

}