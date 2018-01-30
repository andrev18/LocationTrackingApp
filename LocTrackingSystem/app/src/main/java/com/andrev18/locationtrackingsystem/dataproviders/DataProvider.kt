package com.andrev18.locationtrackingsystem.dataproviders

import com.andrev18.locationtrackingsystem.App
import com.andrev18.locationtrackingsystem.dataproviders.database.AppDatabase
import com.andrev18.locationtrackingsystem.dataproviders.database.repositories.LocationEntryRepository

/**
 * Created by avlad on 27.01.2018.
 */
/*
Class that contains the dataproviders of the application
 */
class DataProvider {

    companion object {
        /*
        Database initialization
         */
        private val database: AppDatabase? by lazy {
            App.instance?.run {
                return@lazy AppDatabase.instantiate(this)
            }
            return@lazy null
        }

        /*
        Repository initialization
         */
        val locationEntryRepository: LocationEntryRepository? by lazy {
            database?.run {
                return@lazy LocationEntryRepository(getLocationEntryDao())

            }
            return@lazy null
        }
    }
}