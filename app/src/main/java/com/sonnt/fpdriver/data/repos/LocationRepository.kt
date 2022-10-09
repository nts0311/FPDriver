package com.sonnt.fpdriver.data.repos

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.model.Address
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

class LocationRepository private constructor() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val locationRequest = LocationRequest.create()
        .setInterval(TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS))
        .setFastestInterval(TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS))
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

    private val geocoder = Geocoder(FpDriverApplication.instance)

    var latestLocation: Address? = null
        private set

    var locationFlow=
        LocationServices.getFusedLocationProviderClient(FpDriverApplication.instance)
            .locationFlow(locationRequest)
            .mapNotNull { toAddress(it) }
            .onEach { latestLocation = it }
            .shareIn(coroutineScope, replay = 1, started = SharingStarted.WhileSubscribed())

    fun toAddress(location: Location): Address? {
        geocoder.getFromLocation(location.latitude, location.longitude, 1).firstOrNull()?.let { address ->
            return Address(
                ward = address.subAdminArea,
                district = address.subAdminArea,
                city = address.adminArea,
                detail = "${address.subThoroughfare}, ${address.thoroughfare}",
                lat = location.latitude,
                lng = location.longitude
            )
        }

        return null
    }

    companion object {
        val shared = LocationRepository()

        private const val UPDATE_INTERVAL_SECS = 10L
        private const val FASTEST_UPDATE_INTERVAL_SECS = 2L
    }
}


@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.locationFlow(locationRequest: LocationRequest) = callbackFlow<Location> {

    val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result ?: return
            for (location in result.locations) {
                try {
                    trySend(location).isSuccess // Send location to the flow
                } catch (t: Throwable) {
                }
            }
        }
    }

    requestLocationUpdates(
        locationRequest,
        callback,
        Looper.getMainLooper()
    ).addOnFailureListener { e ->
        close(e)
    }

    awaitClose {
        removeLocationUpdates(callback)
    }
}