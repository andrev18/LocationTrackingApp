package com.andrev18.locationtrackingsystem.ui.location_tracking

import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.andrev18.locationtrackingsystem.App
import com.andrev18.locationtrackingsystem.base.BaseViewModel
import com.andrev18.locationtrackingsystem.dataproviders.DataProvider
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by avlad on 27.01.2018.
 */
class LocationViewModel : BaseViewModel() {

    val onNewLocationEntries: MutableLiveData<MutableList<LatLng>> = MutableLiveData()
    var subscribedToDataChanges = false


    fun loadLocationPoints() {
        if (!subscribedToDataChanges) {
            DataProvider
                    .locationEntryRepository?.getAll()?.run {
                doOnSubscribe {
                    subscribedToDataChanges = true
                    disposables?.add(it)
                }.subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        map {
                            val listOfLatLng = ArrayList<LatLng>()
                            for (location in it) {
                                listOfLatLng.add(LatLng(location.latitude!!, location.longitude!!))
                            }
                            return@map listOfLatLng
                        }
                        .subscribe({
                            onNewLocationEntries.value = it
                            subscribeToDatabaseChanges()
                        }, {
                            it.printStackTrace()
                        }, {
                            subscribeToDatabaseChanges()
                        })
            }
        }

    }

    private fun subscribeToDatabaseChanges() {
        DataProvider.locationEntryRepository?.
                onNewItemsInserted?.run {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        disposables?.add(it)
                    }
                    .subscribe({
                        it?.run {
                            var list = onNewLocationEntries.value
                            if (list == null) {
                                list = ArrayList()
                            }
                            list.add(LatLng(latitude!!, longitude!!))
                            onNewLocationEntries.value = list
                        }
                    })
        }
    }

}