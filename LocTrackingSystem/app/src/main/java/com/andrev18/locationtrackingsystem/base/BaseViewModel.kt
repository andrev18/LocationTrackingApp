package com.andrev18.locationtrackingsystem.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by avlad on 28.01.2018.
 */
abstract class BaseViewModel : ViewModel() {

    /*
    Disposable list for managing the RxJava subscriptions
     */
    val disposables: CompositeDisposable? = CompositeDisposable()

    override fun onCleared() {
        disposables?.run {
            if (!isDisposed) {
                disposables.dispose()
            }
        }
        super.onCleared()
    }
}