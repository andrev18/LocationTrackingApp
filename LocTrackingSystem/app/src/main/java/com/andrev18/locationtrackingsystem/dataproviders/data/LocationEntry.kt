package com.andrev18.locationtrackingsystem.dataproviders.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.location.Location
import com.andrev18.locationtrackingsystem.dataproviders.database.AppDatabase

/**
 * Created by avlad on 27.01.2018.
 */

@Entity(tableName = AppDatabase.TABLE_LOCATION_ENTRIES)
data class LocationEntry (val latitude: Double?,
                          val longitude: Double?,
                          @PrimaryKey val timestamp: Long?,
                          val provider: String?){
    constructor(location: Location?):this(location?.latitude,location?.longitude,location?.time, location?.provider)
}