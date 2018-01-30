package com.andrev18.locationtrackingsystem.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by avlad on 28.01.2018.
 */
abstract class BaseActivity<VIEWMODEL : BaseViewModel> : AppCompatActivity() {
    /*
    Layout resource of the activity
     */
    abstract val layoutId: Int?

    /*
    Class type used for providing the ViewModel
     */
    abstract val viewmodelClass: Class<VIEWMODEL>

    /*
    The viewmodel variable
     */
    val viewmodel: VIEWMODEL by lazy {
        return@lazy ViewModelProviders.of(this).get(viewmodelClass)
    }

    /*
    Abstract method for initializing the View Components
     */
    abstract fun initViewComponent()

    /*
    Abstract method for binding the LiveData Observers
     */
    abstract fun bindObservers(viewmodel: VIEWMODEL)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId?.run {
            setContentView(this)
        }

        initViewComponent()

        bindObservers(viewmodel)

    }


}