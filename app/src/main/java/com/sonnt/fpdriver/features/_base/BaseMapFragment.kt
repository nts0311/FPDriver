package com.sonnt.fpdriver.features._base


import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.model.FPMapMarker


open class BaseMapFragment: BaseFragment() {

    protected lateinit var mapFragment: SupportMapFragment
    protected lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync {
            onMapReady(it)
        }
    }

    protected fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.apply {
            setOnMapLoadedCallback {
                mapLoadDone()
                isTrafficEnabled = true
            }

        }
    }

    fun submitListMarker(markers: List<FPMapMarker>) {
        val maxLat = markers.maxOf { it.lat }
        val maxLong = markers.maxOf { it.long }
        val minLat = markers.minOf { it.lat }
        val minLong = markers.minOf { it.long }

        val bounds = LatLngBounds(
            LatLng(minLat, minLong), // SW bounds
            LatLng(maxLat, maxLong)  // NE bounds
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200))

        markers.forEach {
            val option = MarkerOptions()
                .position(it.toLatLng())
                .title(it.title)

            if (it.imgId != null) {
                option.icon(bitmapDescriptorFromVector(it.imgId!!))
            }

            googleMap.addMarker(option)
        }

    }

    private fun  bitmapDescriptorFromVector(vectorResId:Int): BitmapDescriptor {
        var vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId);
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        var bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        var canvas =  Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    open fun mapLoadDone() {  }
}