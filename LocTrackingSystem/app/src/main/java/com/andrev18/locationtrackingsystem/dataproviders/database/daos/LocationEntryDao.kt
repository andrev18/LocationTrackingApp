package com.andrev18.locationtrackingsystem.dataproviders.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.andrev18.locationtrackingsystem.dataproviders.data.LocationEntry
import com.andrev18.locationtrackingsystem.dataproviders.database.AppDatabase
import io.reactivex.Maybe

/**
 * Created by avlad on 27.01.2018.
 */

@Dao
interface LocationEntryDao : BaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntry: LocationEntry)


    @Query("SELECT * FROM " + AppDatabase.TABLE_LOCATION_ENTRIES)
    fun getAll(): Maybe<List<LocationEntry>>

}
