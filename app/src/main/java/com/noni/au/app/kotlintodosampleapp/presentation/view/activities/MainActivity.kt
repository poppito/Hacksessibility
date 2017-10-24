package com.noni.au.app.kotlintodosampleapp.presentation.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.noni.au.app.kotlintodosampleapp.R
import com.noni.au.app.kotlintodosampleapp.app.Hacksessibility
import com.noni.au.app.kotlintodosampleapp.injection.ActivityModule
import com.noni.au.app.kotlintodosampleapp.injection.DaggerActivityComponent
import com.noni.au.app.kotlintodosampleapp.presentation.presenters.MainPresenter
import com.noni.au.app.kotlintodosampleapp.presentation.presenters.MainPresenter.ViewSurface
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewSurface {

    @Inject
    lateinit var mPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        mPresenter.onStart(this)
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
