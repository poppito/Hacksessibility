package com.noni.au.app.kotlintodosampleapp.injection

import com.noni.au.app.kotlintodosampleapp.app.Hacksessibility
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: Hacksessibility)
}
