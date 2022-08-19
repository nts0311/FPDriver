package com.sonnt.fpdriver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.databinding.ActivityMainBinding
import com.sonnt.fpdriver.network.stomp.StompMessageHub

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}