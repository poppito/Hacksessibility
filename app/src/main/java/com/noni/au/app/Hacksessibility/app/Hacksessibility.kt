package com.noni.au.app.Hacksessibility.app

import android.app.Application
import com.noni.au.app.Hacksessibility.injection.AppComponent
import com.noni.au.app.Hacksessibility.injection.AppModule
import com.noni.au.app.Hacksessibility.injection.DaggerAppComponent

class Hacksessibility : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        mAppComponent.inject(this)
    }
}
