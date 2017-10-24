package com.noni.au.app.kotlintodosampleapp.injection

import dagger.Component

@PerScreen
@Component (dependencies = arrayOf(AppComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
}