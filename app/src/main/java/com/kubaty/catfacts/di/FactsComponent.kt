package com.kubaty.catfacts.di

import android.app.Application
import com.kubaty.catfacts.FactsApplication
import com.kubaty.catfacts.di.network.NetworkModule
import com.kubaty.catfacts.di.network.NetworkServiceModule
import com.kubaty.catfacts.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilderModule::class, ViewModelModule::class, NetworkModule::class, NetworkServiceModule::class])
interface FactsComponent : AndroidInjector<FactsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): FactsComponent
    }
}