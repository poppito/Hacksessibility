package com.noni.au.app.Hacksessibility.injection

import com.noni.au.app.Hacksessibility.presentation.view.activities.MainActivity
import dagger.Component

@PerScreen
@Component(dependencies=arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}