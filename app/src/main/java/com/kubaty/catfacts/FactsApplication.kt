package com.kubaty.catfacts

import com.kubaty.catfacts.di.DaggerFactsComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FactsApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerFactsComponent.builder().application(this).build()
    }
}
