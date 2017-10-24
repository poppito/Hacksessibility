package com.noni.au.app.Hacksessibility.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.noni.au.app.Hacksessibility.R
import com.noni.au.app.Hacksessibility.app.Hacksessibility
import com.noni.au.app.Hacksessibility.injection.ActivityModule
import com.noni.au.app.Hacksessibility.injection.DaggerActivityComponent
import com.noni.au.app.Hacksessibility.presentation.presenters.MainPresenter
import com.noni.au.app.Hacksessibility.presentation.presenters.MainPresenter.ViewSurface
import com.noni.au.app.Hacksessibility.presentation.view.services.AccessibilityBindingService
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewSurface {

    @Inject
    lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        mPresenter.onStart(this)
        startService(Intent(this, AccessibilityBindingService::class.java))
    }

    private fun inject() {
        val app = application as Hacksessibility
        DaggerActivityComponent.builder()
                .appComponent(app.mAppComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }
}
