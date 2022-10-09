package com.sonnt.fpdriver.features.orders_step5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.features._base.BaseMapFragment
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoFragment
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.FPMapMarker

class OrdersStep5Fragment : OrdersDestinationInfoFragment() {

    override val viewModel: OrdersStep5ViewModel by viewModels()

    override val detailText: String
        get() = "Chi tiết khách hàng"
    override val confirmButtonTitle: String
        get() = "Đã đến vị trí giao hàng"

    override val listMarker: List<FPMapMarker>
        get() = getListMarkers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Đi đến vị trí giao hàng")
        binding.confirmButtom.setOnClickListener {
            findNavController().navigate(R.id.orders_next_action)
        }
    }

    override fun buttonConfirmClicked() {
        findNavController().navigate(R.id.orders_next_action)
    }

    private fun getListMarkers(): List<FPMapMarker> {
        return listOf(
                FPMapMarker(viewModel.orderInfo?.toAddress?.lat!!, viewModel.orderInfo?.toAddress?.long!!, R.drawable.ic_customer_marker, "Khách hàng"),
                FPMapMarker(viewModel.orderInfo?.fromAddress?.lat!!, viewModel.orderInfo?.fromAddress?.long!!, R.drawable.ic_merchant_marker, null)
        )
    }

}