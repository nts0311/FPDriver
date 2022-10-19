package com.sonnt.fpdriver.features.orders_step5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoFragment
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
        setupViewModel()
    }

    override fun buttonConfirmClicked() {
        viewModel.arrivedAtCustomer()
    }

    fun setupViewModel() {
        viewModel.onApiSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.orders_next_action)
        }
    }

    private fun getListMarkers(): List<FPMapMarker> {
        val customerLocation =  viewModel.orderInfo?.toAddress ?: return listOf()
        val merchantLocation = viewModel.orderInfo?.fromAddress ?: return listOf()

        return listOf(
                FPMapMarker(customerLocation.lat ?: 0.0, customerLocation.lng ?: 0.0, R.drawable.ic_customer_marker, null),
                FPMapMarker(merchantLocation.lat ?: 0.0, merchantLocation.lng ?: 0.0, R.drawable.ic_driver_marker, null)
        )
    }

}