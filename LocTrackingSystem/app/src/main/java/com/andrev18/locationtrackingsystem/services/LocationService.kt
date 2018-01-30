package com.andrev18.locationtrackingsystem.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.andrev18.locationtrackingsystem.dataproviders.DataProvider
import com.andrev18.locationtrackingsystem.dataproviders.data.LocationEntry
import com.google.android.gms.location.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by avlad on 27.01.2018.
 */
class LocationService : Service() {
    /*
    Flag for avoiding restarting the service. Once started, this variable becomes true
     */
    private var isStarted = false;

    /*
    Location Request Builder object
     */
    private val locationRequest = LocationRequest()
    /*
    Location Service Provider
     */
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    /*
    Disposable list for managing the database queries
     */
    private val disposables: CompositeDisposable? = CompositeDisposable()


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isStarted) {

            /*
            Configure the location request
             */
            locationRequest.interval = 1
            locationRequest.fastestInterval = 1
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


            /*
            Starting the Location Provider
             */
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    /*
                    On location available -> insert the location in database
                     */
                    Completable.fromAction({
                        DataProvider.locationEntryRepository?.insert(LocationEntry(locationResult?.lastLocation))
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe {
                                disposables?.add(it)
                            }
                            .subscribe()
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                    super.onLocationAvailability(locationAvailability)
                }
            }
            fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, null)
            isStarted = true
        }
        return START_STICKY
    }


    override fun onDestroy() {
        /*
        Releaase database queries on service destroy
         */
        disposables?.run {
            if (!isDisposed) {
                disposables.dispose()
            }
        }
        super.onDestroy()
    }


}