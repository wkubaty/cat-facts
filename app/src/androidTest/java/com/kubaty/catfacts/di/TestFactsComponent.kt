package com.kubaty.catfacts.di

import android.app.Application
import com.kubaty.catfacts.TestFactsApplication
import com.kubaty.catfacts.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class, ViewModelModule::class, TestAppModule::class])
interface TestFactsComponent : AndroidInjector<TestFactsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestFactsComponent
    }

}