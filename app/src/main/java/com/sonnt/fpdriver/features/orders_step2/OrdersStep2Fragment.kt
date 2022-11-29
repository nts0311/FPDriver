package com.sonnt.fpdriver.features.orders_step2

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.data.repos.LocationRepository
import com.sonnt.fpdriver.features._base.createDialog
import com.sonnt.fpdriver.features.order_destination_info.OrdersDestinationInfoFragment
import com.sonnt.fpdriver.model.FPMapMarker

class OrdersStep2Fragment : OrdersDestinationInfoFragment() {

    override val viewModel: OrdersStep2ViewModel by viewModels()

    override val detailText: String = "Chi tiết nhà hàng"

    override val listMarker: List<FPMapMarker>
        get() = getListMarkers()

    override val confirmButtonTitle: String
        get() = "Đã đến nhà hàng"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Đi đến nhà hàng")
        binding.viewModel = viewModel
        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_step_2_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_cancel_order) {
            viewModel.cancelOrder()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun buttonConfirmClicked() {
        viewModel.arrivedAtMerchant()
    }

    fun setupViewModel() {
        viewModel.onApiSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.orders_next_action)
        }

        viewModel.cancelOrderResult.observe(viewLifecycleOwner) {isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.nav_to_order_step1)
            } else {
                requireContext().createDialog(content = "Lỗi khi huỷ đơn hàng")
            }
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