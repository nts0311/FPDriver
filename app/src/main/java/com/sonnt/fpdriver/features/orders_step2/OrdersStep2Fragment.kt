package com.sonnt.fpdriver.features.orders_step2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.databinding.FragmentOrdersStep2Binding
import com.sonnt.fpdriver.features._base.BaseMapFragment
import com.sonnt.fpdriver.model.FPMapMarker


class OrdersStep2Fragment : BaseMapFragment<FragmentOrdersStep2Binding>() {
    private val viewModel: OrdersStep2ViewModel by viewModels()

    var listMarker = listOf<FPMapMarker>()

    override var layoutResId: Int = R.layout.fragment_orders_step2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupData()
        setupView()
    }

    private fun setupData() {
        viewModel.orderInfo.observe(viewLifecycleOwner) {
            listMarker = listOf(
                FPMapMarker(it.toAddress.lat!!, it.toAddress.long!!, R.drawable.ic_customer_marker, "Khách hàng"),
                FPMapMarker(it.fromAddress.lat!!, it.fromAddress.long!!, R.drawable.ic_merchant_marker, null)
            )
        }
    }

    private fun setupView() {
        setActionBarTitle("Đi đến nhà hàng")
        binding.directionToRestaurantButton.setOnClickListener {
            startMapIntent()
        }

        binding.callMerchantButton.setOnClickListener {
            callMerchant()
        }

        binding.confirmButtom.setOnClickListener {
            findNavController().navigate(R.id.orders_next_action)
        }
    }

    private fun startMapIntent() {
        val order = viewModel.getCurrentOrder() ?: return

        val destintationLatitude = order.fromAddress.lat
        val destintationLongitude = order.fromAddress.long

        val gmmIntentUri = Uri.parse("google.navigation:q=$destintationLatitude,$destintationLongitude&mode=l")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun callMerchant() {
        val order = viewModel.getCurrentOrder() ?: return

        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${order.merchantPhone}")
        startActivity(intent)
    }

    override fun mapLoadDone() {
        submitListMarker(listMarker)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersStep2Fragment()
    }
}