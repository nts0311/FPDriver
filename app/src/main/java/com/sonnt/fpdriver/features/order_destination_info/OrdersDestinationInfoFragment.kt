package com.sonnt.fpdriver.features.order_destination_info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrdersDestinationInfoBinding
import com.sonnt.fpdriver.features._base.BaseMapFragment
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.model.FPMapMarker


abstract class OrdersDestinationInfoFragment : BaseMapFragment<FragmentOrdersDestinationInfoBinding>() {
    override var layoutResId: Int = R.layout.fragment_orders_destination_info

    abstract val detailText: String
    abstract val viewModel: OrdersDestinationInfoViewModel

    open val listMarker = listOf<FPMapMarker>()
    open val confirmButtonTitle: String = "Đã đến"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.viewModel = viewModel
    }

    abstract fun buttonConfirmClicked()

    fun startMapIntent() {
        val destinationLatitude = viewModel.toAddress.lat
        val destinationLongitude = viewModel.toAddress.long

        val gmmIntentUri = Uri.parse("google.navigation:q=$destinationLatitude,$destinationLongitude&mode=l")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${viewModel.phone}")
        startActivity(intent)
    }

    override fun mapLoadDone() {
        submitListMarker(listMarker)
    }

}