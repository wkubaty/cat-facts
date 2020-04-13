package com.kubaty.catfacts

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class TestFactsApplication : Application(), HasSupportFragmentInjector, HasAndroidInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }
}
