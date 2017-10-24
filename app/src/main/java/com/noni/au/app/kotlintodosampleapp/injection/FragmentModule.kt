package com.noni.au.app.kotlintodosampleapp.injection

import android.content.Context
import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

@PerScreen
@Module(includes = arrayOf(AppModule::class))
class FragmentModule(private val fragment: Fragment) {
    @Provides
    fun getContext(): Context = fragment.activity

}