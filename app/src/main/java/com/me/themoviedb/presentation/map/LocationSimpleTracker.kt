package com.me.themoviedb.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import timber.log.Timber

@SuppressLint("MissingPermission")
data class LocationSimpleTracker(private val context: Context) {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback? = null


    fun detectGPS(onGPSChanged: () -> Unit) {
        trackLocation(onGpsStateChange = onGPSChanged)
    }

    fun detectNewLocation(onNewLocation: ((Location?) -> Unit)) {
        trackLocation(onNewLocation = onNewLocation)
    }

    private fun trackLocation(
        onGpsStateChange: (() -> Unit)? = null,
        onNewLocation: ((Location?) -> Unit)? = null
    ) {
        if (locationCallback != null) {
            stop()
        }

        locationCallback = object : LocationCallback() {

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                Timber.i("GPS changed")
                onGpsStateChange?.invoke()
            }

            override fun onLocationResult(result: LocationResult?) {
                Timber.i("New location: ${result?.lastLocation}")
                onNewLocation?.invoke(result?.lastLocation)
            }
        }

        locationCallback?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                buildLocationRequest(),
                it,
                null
            )
        }
    }

    private fun buildLocationRequest(): LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 5000 //5 seconds
        fastestInterval = 5000 //5 seconds
        maxWaitTime = 1000 //1 seconds
    }

    fun stop() {
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
        locationCallback = null
    }
}