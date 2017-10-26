package com.noni.au.app.hacksessibility.injection

import com.noni.au.app.hacksessibility.app.Hacksessibility
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: Hacksessibility)
}
