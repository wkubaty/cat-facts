package com.kubaty.catfacts.di.network

import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.api.FactsService
import com.kubaty.catfacts.repository.FactsRepository
import com.kubaty.catfacts.repository.IFactsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object NetworkServiceModule {

    @Provides
    fun provideFactsService(retrofit: Retrofit): FactsService =
        retrofit.create(FactsService::class.java)

    @Provides
    fun provideFactsRepository(
        factsController: FactsController
    ): IFactsRepository =
        FactsRepository(factsController)
}
