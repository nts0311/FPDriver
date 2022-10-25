package com.sonnt.fpdriver.features.orders

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrdersBinding
import com.sonnt.fpdriver.features._base.BaseMapFragment
import com.sonnt.fpdriver.model.FPMapMarker


class OrdersFragment : BaseMapFragment<FragmentOrdersBinding>() {
    private val viewModel: OrdersViewModel by viewModels()

    var listMarker = listOf<FPMapMarker>()

    override var layoutResId: Int = R.layout.fragment_orders

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupView()
        setupData()
    }

    private fun setupView() {
        setActionBarTitle("Đơn hàng mới")
    }

    private fun setupData() {
        viewModel.orderInfo?.observe(viewLifecycleOwner) {
            if (it.fromAddress.lat == null || it.fromAddress.lng == null
                || it.toAddress.lat == null || it.toAddress.lng == null) {
                return@observe
            }

            listMarker = listOf(
                FPMapMarker(it.toAddress.lat!!, it.toAddress.lng!!, R.drawable.ic_customer_marker, "Khách hàng"),
                FPMapMarker(it.fromAddress.lat!!, it.fromAddress.lng!!, R.drawable.ic_merchant_marker, null)
            )
        }

        viewModel.onAcceptOrderSuccess.observe(viewLifecycleOwner) {
            binding.rootLayout.visibility = View.GONE
            viewModel.clearOrder()
            Handler(Looper.getMainLooper()).post {
                findNavController().navigate(R.id.orders_next_action)
            }
        }

    }

    override fun mapLoadDone() {
        submitListMarker(listMarker)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersFragment()
    }
}