package com.sonnt.fpdriver.features.order_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrderDetailBinding
import com.sonnt.fpdriver.databinding.ItemOrderItemBinding
import com.sonnt.fpdriver.features._base.BaseFragment
import com.sonnt.fpdriver.features._base.BaseRecyclerViewAdapter
import com.sonnt.fpdriver.model.OrderedProductItem

abstract class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>() {

    override var layoutResId: Int = R.layout.fragment_order_detail

    abstract val viewModel: OrderDetailViewModel
    abstract val buttonTitle: String

    abstract fun buttonConfirmClicked()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupData()
    }

    private fun setupViews() {
        binding?.viewModel = viewModel
        binding?.fragment = this
    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${viewModel.orderInfo.value?.customerPhone}")
        startActivity(intent)
    }

    private fun setupData() {
        viewModel.orderInfo.observe(viewLifecycleOwner) {
            val adapter = BaseRecyclerViewAdapter<OrderedProductItem, ItemOrderItemBinding>(R.layout.item_order_item)
            adapter.items = it.item
            binding?.orderItemsRv?.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}