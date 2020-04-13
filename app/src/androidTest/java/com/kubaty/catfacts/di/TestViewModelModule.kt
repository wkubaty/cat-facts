package com.kubaty.catfacts.di

import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.FakeViewModelFactory
import com.kubaty.catfacts.repository.FakeFactsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestViewModelModule {
    @Singleton
    @Provides
    fun provideViewModelFactory(
        repository: FakeFactsRepository
    ): ViewModelProvider.Factory = FakeViewModelFactory(repository)
}