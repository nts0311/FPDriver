package com.sonnt.fpdriver.features.order_transfer_confirmation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrderTransferConfirmationBinding
import com.sonnt.fpdriver.features._base.BaseFragment

abstract class OrdersTransferConfirmationFragment : BaseFragment<FragmentOrderTransferConfirmationBinding>() {

    abstract val viewModel: OrdersTransferConfirmationViewModel
    abstract val transactionLabel: String
    abstract val imageLabel: String
    abstract val guideLabel: String
    abstract val buttonTitle: String
    abstract fun onConfirmButtonClicked()

    override var layoutResId: Int = R.layout.fragment_order_transfer_confirmation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setActionBarTitle("Xác nhận lấy món")
        binding.viewModel = viewModel
        binding.fragment = this
        binding.addImageButton.setOnClickListener {
            ImagePicker.with(this)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            setBillImage(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setBillImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.billImageview)
        viewModel.uploadBillImage(uri)
    }
}