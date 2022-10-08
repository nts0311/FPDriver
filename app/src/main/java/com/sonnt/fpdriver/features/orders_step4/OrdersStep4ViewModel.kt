package com.sonnt.fpdriver.features.orders_step4

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseViewModel
import com.sonnt.fpdriver.model.OrderInfo

class OrdersStep4ViewModel: BaseViewModel(), UploadCallback {
    var orderInfo = MutableLiveData<OrderInfo>()
    var isUploadingImage = MutableLiveData(false)
    var isBillImageUploaded = MutableLiveData(false)

    var billImageUrl = ""

    init {
        getCurrentOrder()?.let { orderInfo.value = it }
        if(MediaManager.get() == null) {
            MediaManager.init(FpDriverApplication.instance, mapOf("cloud_name" to "dgksrtctm"))
        }
    }

    fun getCurrentOrder(): OrderInfo? = OrderRepository.shared.latestOrder

    fun uploadBillImage(uri: Uri) {
        isBillImageUploaded.value = false
        MediaManager.get()
            .upload(uri)
            .callback(this)
            .unsigned("kypwklls")
            .dispatch(FpDriverApplication.instance)
    }

    override fun onStart(requestId: String?) {
        isUploadingImage.value = true
    }

    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

    }

    override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
        billImageUrl = resultData?.get("url") as? String ?: ""
        isUploadingImage.value = false
        isBillImageUploaded.value = true
    }

    override fun onError(requestId: String?, error: ErrorInfo?) {
        isUploadingImage.value = false
    }

    override fun onReschedule(requestId: String?, error: ErrorInfo?) {

    }
}