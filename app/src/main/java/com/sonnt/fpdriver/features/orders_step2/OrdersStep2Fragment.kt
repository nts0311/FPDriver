package com.sonnt.fpdriver.features.orders_step2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.data.repos.LocationRepository
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoFragment
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
        setupViewModel()
    }

    override fun buttonConfirmClicked() {
        viewModel.arrivedAtMerchant()
    }

    fun setupViewModel() {
        viewModel.onApiSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.orders_next_action)
        }
    }

    private fun getListMarkers(): List<FPMapMarker> {
        val driverAddress = LocationRepository.shared.latestLocation ?: return listOf()
        val merchantLocation = viewModel.orderInfo?.fromAddress ?: return listOf()

        return listOf(
                FPMapMarker(driverAddress.lat ?: 0.0, driverAddress.lng ?: 0.0, R.drawable.ic_driver_marker, null),
                FPMapMarker(merchantLocation.lat ?: 0.0, merchantLocation.lng ?: 0.0, R.drawable.ic_merchant_marker, null)
        )
    }

}