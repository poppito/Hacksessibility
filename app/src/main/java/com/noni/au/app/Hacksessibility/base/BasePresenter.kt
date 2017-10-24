package com.noni.au.app.Hacksessibility.base

open abstract class BasePresenter<T:Any> {

    abstract fun onStart(v: T)
     abstract fun onStop(v: T)
}