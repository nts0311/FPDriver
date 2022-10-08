package com.sonnt.fpdriver.features.main

import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonnt.fpdriver.base.BaseViewModel
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.model.OrderInfo
import com.sonnt.fpdriver.network.dto.request.AuthRequest
import com.sonnt.fpdriver.network.ApiResult
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.callApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivityViewModel: BaseViewModel() {

}