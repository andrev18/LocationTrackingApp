package com.andrev18.locationtrackingsystem

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by avlad on 27.01.2018.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        Stetho.initializeWithDefaults(this);

    }

    companion object {
        var instance: App? = null
    }
}
