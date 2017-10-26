package com.noni.au.app.hacksessibility.app

import android.app.Application
import com.noni.au.app.hacksessibility.injection.AppComponent
import com.noni.au.app.hacksessibility.injection.AppModule
import com.noni.au.app.hacksessibility.injection.DaggerAppComponent

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
