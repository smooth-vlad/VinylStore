package com.android.vinylstore

import android.app.Application
import android.content.Context
import com.android.vinylstore.di.AppComponent
import com.android.vinylstore.di.DaggerAppComponent

class Root : Application() {

    private lateinit var _appComponent: AppComponent

    companion object {
        fun getAppComponent(context: Context): AppComponent {
            return (context.applicationContext as Root)._appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.create()
    }
}