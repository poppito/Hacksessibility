package com.noni.au.app.hacksessibility.base

open abstract class BasePresenter<T:Any> {

    abstract fun onStart(v: T)
     abstract fun onStop(v: T)
}