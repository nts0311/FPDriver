package com.sonnt.fpdriver.features.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.features._base.BaseMapFragment
import com.sonnt.fpdriver.model.FPMapMarker


class OrdersFragment : BaseMapFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupNav()
    }

    private fun setupView() {
        setActionBarTitle("1 đơn hàng mới")
    }

    private fun setupNav() {
//        view.findViewById<Button>(R.id.step1btn).setOnClickListener {
//            findNavController().navigate(R.id.orders_next_action)
//        }
    }

    override fun mapLoadDone() {
        val list = listOf(
            FPMapMarker(20.9962628, 105.7759146, R.drawable.ic_customer_marker, "Khách hàng"),
            FPMapMarker(21.007835, 105.779126, R.drawable.ic_merchant_marker, null)
        )
        submitListMarker(list)

    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersFragment()
    }
}