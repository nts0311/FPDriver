package com.sonnt.fpdriver.features.orders_step6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.features.order_detail.OrderDetailViewModel
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import com.sonnt.fpdriver.utils.formatCurrency
import kotlinx.coroutines.launch

class OrderStep6ViewModel: OrderDetailViewModel() {

    override val hideMerchantName: Boolean
        get() = true
    override val price: LiveData<String> = orderInfo.map {
        it.paymentInfo.calculatePrice().formatCurrency()
    }

}