package com.kubaty.catfacts.di

import com.kubaty.catfacts.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun mainActivity(): MainActivity
}