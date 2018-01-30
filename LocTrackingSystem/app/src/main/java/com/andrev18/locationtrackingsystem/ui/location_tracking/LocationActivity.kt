package com.andrev18.locationtrackingsystem.ui.location_tracking

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.widget.Toast
import com.andrev18.locationtrackingsystem.services.LocationService
import com.andrev18.locationtrackingsystem.R
import com.andrev18.locationtrackingsystem.base.BaseActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

/**
 * Created by avlad on 27.01.2018.
 */

const val LOCATION_PERMISSION_REQUEST_CODE = 333

class LocationActivity : BaseActivity<LocationViewModel>(), OnMapReadyCallback {
    override val layoutId: Int? = R.layout.activity_location
    override val viewmodelClass: Class<LocationViewModel> = LocationViewModel::class.java
    var map: GoogleMap? = null
    var route: Polyline? = null

    override fun initViewComponent() {
        (fragmentManager.findFragmentById(R.id.map_fragment) as MapFragment)
                .getMapAsync(this)
    }

    override fun bindObservers(viewmodel: LocationViewModel) {
        viewmodel
                .onNewLocationEntries
                .observe(this, Observer {
                    it?.run {
                        if (route == null) {
                            map?.run {
                                val polyLineOptions = PolylineOptions()
                                        .addAll(it)
                                        .color(Color.MAGENTA)
                                        .geodesic(true)
                                        .width(20f)
                                route = addPolyline(polyLineOptions)
                            }
                        } else {
                            route?.points = it
                        }

                    }
                })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (verifyLocationPermission()) {
            onLocationGrantedAndMapReady()
        } else {
            askForLocationPermission()
        }
    }


    @SuppressLint("MissingPermission")
    fun onLocationGrantedAndMapReady() {
        map?.isMyLocationEnabled = true
        viewmodel.loadLocationPoints()
        startService(Intent(this, LocationService::class.java))
    }


    fun verifyLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            return true
        }
        return false
    }


    fun askForLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    onLocationGrantedAndMapReady()
                } else {
                    Toast.makeText(this, "App can't be used without location permission", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}