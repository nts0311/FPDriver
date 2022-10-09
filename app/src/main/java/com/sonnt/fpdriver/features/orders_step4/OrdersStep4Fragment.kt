package com.sonnt.fpdriver.features.orders_step4

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.features.order_transfer_confirmation.OrdersTransferConfirmationFragment

class OrdersStep4Fragment : OrdersTransferConfirmationFragment() {

    override val viewModel: OrdersStep4ViewModel by viewModels()
    override val transactionLabel: String = "Trả tiền nhà hàng"
    override val imageLabel: String = "Hoá đơn"
    override val guideLabel: String = "Chụp hình hoá đơn"
    override val buttonTitle: String = "Xác nhận lấy món"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle("Nhận món")
    }

    override fun onConfirmButtonClicked() {
        findNavController().navigate(R.id.orders_next_action)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersStep4Fragment()
    }
}