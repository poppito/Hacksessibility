package com.noni.au.app.hacksessibility.presentation.presenters

import com.noni.au.app.hacksessibility.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainPresenter.ViewSurface>() {

    lateinit private var viewSurface: ViewSurface

    override fun onStart(view: ViewSurface) {
        viewSurface = view
    }

    override fun onStop(v: ViewSurface) {
    }

    //region ui interaction

    //endregion

    interface ViewSurface {
    }
}
