package com.me.themoviedb.presentation.map

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.me.themoviedb.common.util.toast
import com.me.themoviedb.databinding.FragmentMapBinding
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private var locationPermissionGranted = false
    private val locationPermissionResult =
        registerForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                toast("Granted")
                onLocationPermissionGranted()
            } else {
                locationPermissionGranted = false
                toast("Rejected")
            }
        }

    private val locationSimpleTracker by lazy {
        LocationSimpleTracker(requireContext())
    }

    private val locationManager: LocationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding?.mapView?.let {
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            requireContext().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            onLocationPermissionGranted()
        } else {

            locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun onLocationPermissionGranted() {
        locationPermissionGranted = true
        checkGpsEnabled()
    }

    private fun checkGpsEnabled() {
        if (isGpsEnabled()) {
            moveToCurrentLocation()
        } else {
            displayGpsDisabledAlert()
            locationSimpleTracker.detectGPS {
                if (isGpsEnabled()) {
                    locationSimpleTracker.stop()
                    moveToCurrentLocation()
                }
            }
        }
    }

    private fun displayGpsDisabledAlert() {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setPositiveButton("YES") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun isGpsEnabled() =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun moveToCurrentLocation() {
        locationSimpleTracker.detectNewLocation { location ->
            if (location != null) {
                locationSimpleTracker.stop()
                mGoogleMap?.run {
                    val latLng = LatLng(location.latitude, location.longitude)
                    val cameraPosition = CameraPosition.builder()
                        .target(latLng)
                        .zoom(17F)
                        .build()
                    val cameraUpdate =
                        CameraUpdateFactory.newCameraPosition(cameraPosition)
                    animateCamera(cameraUpdate)
                    addMarker(MarkerOptions().position(latLng))
                    setOnMarkerClickListener {
                        goToNextScreen()
                        true
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding?.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapView?.onLowMemory()
    }

    private fun setup() {
        binding?.run {
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }

    private fun goToNextScreen() {
        val directions = MapFragmentDirections.openLanding()
        navigate(directions)
    }

    companion object {
        const val RC_LOCATION_PERMISSION = 1
    }
}