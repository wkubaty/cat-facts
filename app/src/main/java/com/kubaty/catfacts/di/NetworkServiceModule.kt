package com.kubaty.catfacts.di

import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.api.FactsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object NetworkServiceModule {

    @Provides
    fun provideFactsService(retrofit: Retrofit): FactsService =
        retrofit.create(FactsService::class.java)

    @Provides
    fun provideFactsController(factsService: FactsService): FactsController =
        FactsController(factsService)
}
