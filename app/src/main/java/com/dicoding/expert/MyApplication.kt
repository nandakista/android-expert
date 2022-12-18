package com.dicoding.expert

import android.app.Application
import com.dicoding.expert.di.app.AppComponent
import com.dicoding.expert.di.app.DaggerAppComponent
import com.dicoding.expert.di.core.CoreComponent
import com.dicoding.expert.di.core.DaggerCoreComponent

class MyApplication: Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}