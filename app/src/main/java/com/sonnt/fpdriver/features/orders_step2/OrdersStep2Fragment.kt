package com.sonnt.fpdriver.features.orders_step2

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

class OrdersStep2Fragment : OrdersDestinationInfoFragment() {

    override val viewModel: OrdersStep2ViewModel by viewModels()

    override val detailText: String = "Chi tiết nhà hàng"

    override val listMarker: List<FPMapMarker>
        get() = getListMarkers()

    override val confirmButtonTitle: String
        get() = "Đã đến nhà hàng"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Đi đến nhà hàng")
        binding.viewModel = viewModel
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