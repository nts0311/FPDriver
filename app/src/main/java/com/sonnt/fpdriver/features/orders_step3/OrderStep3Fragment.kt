package com.sonnt.fpdriver.features.orders_step3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrderStep3Binding
import com.sonnt.fpdriver.databinding.FragmentOrdersStep2Binding
import com.sonnt.fpdriver.databinding.ItemOrderItemBinding
import com.sonnt.fpdriver.features._base.BaseFragment
import com.sonnt.fpdriver.features._base.BaseRecyclerViewAdapter
import com.sonnt.fpdriver.model.OrderedProductItem


class OrderStep3Fragment : BaseFragment<FragmentOrderStep3Binding>() {

    override var layoutResId: Int = R.layout.fragment_order_step3

    private val viewModel: OrderStep3ViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupData()
    }

    private fun setupViews() {
        setActionBarTitle("Nhận món")
        binding.viewModel = viewModel
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.orders_next_action)
        }
    }

    private fun setupData() {
        viewModel.orderInfo.observe(viewLifecycleOwner) {
            val adapter = BaseRecyclerViewAdapter<OrderedProductItem, ItemOrderItemBinding>(R.layout.item_order_item)
            adapter.items = it.item
            binding.orderItemsRv.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderStep3Fragment()
    }
}