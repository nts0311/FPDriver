package com.sonnt.fpdriver.features.orders_step6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.features.order_detail.OrderDetailViewModel
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.utils.formatCurrency

class OrderStep6ViewModel: OrderDetailViewModel() {

    override val hideMerchantName: Boolean
        get() = true
    override val price: LiveData<String> = orderInfo.map {
        it.paymentInfo.calculatePrice().formatCurrency()
    }


}