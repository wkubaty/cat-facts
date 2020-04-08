package com.kubaty.catfacts.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.ui.FactDetailsViewModel
import com.kubaty.catfacts.ui.FactListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FactListViewModel::class)
    abstract fun bindFactListViewModel(factListViewModel: FactListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FactDetailsViewModel::class)
    abstract fun bindFactDetailsViewModel(factDetailsViewModel: FactDetailsViewModel): ViewModel
}