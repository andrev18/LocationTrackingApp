package com.andrev18.locationtrackingsystem.dataproviders.database.repositories


import com.andrev18.locationtrackingsystem.dataproviders.database.daos.BaseDao

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by avlad on 27.01.2018.
 */

abstract class BaseRepository<DAO : BaseDao, ENTITY>(val dao: DAO) {
    val onNewItemsInserted: PublishSubject<ENTITY> = PublishSubject.create<ENTITY>()

    fun getOnNewItemsInserted(): Observable<ENTITY> {
        return onNewItemsInserted
    }
}
