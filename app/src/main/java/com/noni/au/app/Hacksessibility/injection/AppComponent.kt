package com.noni.au.app.Hacksessibility.injection

import com.noni.au.app.Hacksessibility.app.Hacksessibility
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: Hacksessibility)
}
