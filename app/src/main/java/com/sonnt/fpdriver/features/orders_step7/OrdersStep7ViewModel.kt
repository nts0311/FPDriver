package com.sonnt.fpdriver.features.orders_step7

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.features.order_transfer_confirmation.OrdersTransferConfirmationViewModel
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.utils.formatCurrency
import java.lang.Exception

class OrdersStep7ViewModel: OrdersTransferConfirmationViewModel() {
    override val price: LiveData<String>
        get() = orderInfo.map { it.paymentInfo.calculatePrice().formatCurrency() }
}