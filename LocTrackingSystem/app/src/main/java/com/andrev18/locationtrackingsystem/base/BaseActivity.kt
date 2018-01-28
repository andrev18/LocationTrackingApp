package com.andrev18.locationtrackingsystem.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by avlad on 28.01.2018.
 */
abstract class BaseActivity<VIEWMODEL : BaseViewModel> : AppCompatActivity() {
    abstract val layoutId: Int?
    abstract val viewmodelClass: Class<VIEWMODEL>

    val viewmodel: VIEWMODEL by lazy {
        return@lazy ViewModelProviders.of(this).get(viewmodelClass)
    }

    abstract fun initViewComponent()
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