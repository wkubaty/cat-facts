package com.kubaty.catfacts.di

import android.app.Application
import com.kubaty.catfacts.api.FakeFactsController
import com.kubaty.catfacts.api.FakeFactsService
import com.kubaty.catfacts.repository.FakeFactsRepository
import com.kubaty.catfacts.repository.IFactsRepository
import com.kubaty.catfacts.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {
    @Singleton
    @Provides
    fun provideJsonUtil(
        application: Application
    ): JsonUtil = JsonUtil(application)

    @Provides
    fun provideFactsRepository(
        fakeFactsController: FakeFactsController
    ): IFactsRepository =
        FakeFactsRepository(fakeFactsController)

    @Provides
    fun provideFactsController(
        fakeFactsService: FakeFactsService
    ): FakeFactsController =
        FakeFactsController(fakeFactsService)

    @Provides
    fun provideFactsService(
        jsonUtil: JsonUtil
    ): FakeFactsService =
        FakeFactsService(jsonUtil)
}