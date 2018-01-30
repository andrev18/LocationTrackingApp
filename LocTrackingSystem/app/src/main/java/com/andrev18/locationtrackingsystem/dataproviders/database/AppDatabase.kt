package com.andrev18.locationtrackingsystem.dataproviders.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.andrev18.locationtrackingsystem.App
import com.andrev18.locationtrackingsystem.dataproviders.data.LocationEntry
import com.andrev18.locationtrackingsystem.dataproviders.database.daos.LocationEntryDao

/**
 * Created by avlad on 27.01.2018.
 */
@Database(entities = arrayOf(LocationEntry::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    /*
    Database initialization
     */
    companion object {
        const val TABLE_LOCATION_ENTRIES:String = "table_location_entries"
        fun instantiate(app: App): AppDatabase {

            return Room.databaseBuilder(app, AppDatabase::class.java, "location-tracking-db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
        }
    }


    abstract fun getLocationEntryDao(): LocationEntryDao;
}
