package com.sonnt.fpdriver.features.orders_step4

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.databinding.FragmentOrdersStep4Binding
import com.sonnt.fpdriver.features._base.BaseFragment

class OrdersStep4Fragment : BaseFragment<FragmentOrdersStep4Binding>() {

    private val viewModel: OrdersStep4ViewModel by viewModels()

    override var layoutResId: Int = R.layout.fragment_orders_step4

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setActionBarTitle("Xác nhận lấy món")
        binding.viewModel = viewModel
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

    companion object {
        @JvmStatic
        fun newInstance() = OrdersStep4Fragment()
    }
}