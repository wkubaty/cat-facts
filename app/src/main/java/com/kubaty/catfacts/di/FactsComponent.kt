package com.kubaty.catfacts.di

import android.app.Application
import com.kubaty.catfacts.FactsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilderModule::class, NetworkModule::class, NetworkServiceModule::class])
interface FactsComponent : AndroidInjector<FactsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): FactsComponent
    }
}