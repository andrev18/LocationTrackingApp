package com.andrev18.locationtrackingsystem.dataproviders.database.repositories

import com.andrev18.locationtrackingsystem.dataproviders.data.LocationEntry
import com.andrev18.locationtrackingsystem.dataproviders.database.daos.LocationEntryDao

import io.reactivex.Maybe

/**
 * Created by avlad on 27.01.2018.
 */

class LocationEntryRepository(dao: LocationEntryDao) : BaseRepository<LocationEntryDao, LocationEntry>(dao), LocationEntryDao {


    override fun insert(locationEntry: LocationEntry) {
        dao.insert(locationEntry)
        onNewItemsInserted.onNext(locationEntry)
    }

    override fun getAll(): Maybe<List<LocationEntry>> {
        return dao.getAll()
    }
}
