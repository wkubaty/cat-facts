package com.kubaty.catfacts.di

import com.kubaty.catfacts.ui.FactDetailsFragment
import com.kubaty.catfacts.ui.FactListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesFactListFragment(): FactListFragment

    @ContributesAndroidInjector
    abstract fun contributesFactDetailsFragment(): FactDetailsFragment
}
