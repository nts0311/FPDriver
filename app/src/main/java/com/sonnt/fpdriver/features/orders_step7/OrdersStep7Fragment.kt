package com.sonnt.fpdriver.features.orders_step7

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features.order_transfer_confirmation.OrdersTransferConfirmationFragment

class OrdersStep7Fragment : OrdersTransferConfirmationFragment() {

    override val viewModel: OrdersStep7ViewModel by viewModels()
    override val transactionLabel: String = "Thu tiền của khách"
    override val imageLabel: String = "Bằng chứng giao hàng"
    override val guideLabel: String = "Thêm hình làm bằng chứng giao hàng"
    override val buttonTitle: String = "Xác nhận giao hàng"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Xác nhận giao hàng")
        setupViewModel()
    }

    override fun onConfirmButtonClicked() {
        viewModel.confirmDeliveredOrderToCustomer()
    }

    fun setupViewModel() {
        viewModel.onApiSuccess.observe(viewLifecycleOwner) {
            OrderRepository.shared.latestOrder = null
            findNavController().popBackStack(R.id.ordersFragment, false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersStep7Fragment()
    }
}