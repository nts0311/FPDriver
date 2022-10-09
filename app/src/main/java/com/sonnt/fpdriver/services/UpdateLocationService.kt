package com.sonnt.fpdriver.services

import com.sonnt.fpdriver.data.repos.LocationRepository
import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.network.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object UpdateLocationService {
    private val locationRepository = LocationRepository.shared
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val statusService = NetworkModule.statusService

    init {
        locationRepository.locationFlow
            .onEach { updateLastLocation(it) }
            .launchIn(coroutineScope)
    }

    private suspend fun updateLastLocation(address: Address) {
        statusService.updateLastLocation(address)
    }

    fun start() {}
}