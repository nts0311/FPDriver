package com.sonnt.fpdriver.features.orders_step6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.ItemOrderItemBinding
import com.sonnt.fpdriver.features._base.BaseFragment
import com.sonnt.fpdriver.features._base.BaseRecyclerViewAdapter
import com.sonnt.fpdriver.features.order_detail.OrderDetailFragment
import com.sonnt.fpdriver.model.OrderedProductItem


class OrderStep6Fragment : OrderDetailFragment() {

    override val viewModel: OrderStep6ViewModel by viewModels()
    override val buttonTitle: String
        get() = "Giao hàng"

    override fun buttonConfirmClicked() {
        findNavController().navigate(R.id.orders_next_action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Giao hàng")
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderStep6Fragment()
    }
}