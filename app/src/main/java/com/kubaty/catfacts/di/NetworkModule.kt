package com.kubaty.catfacts.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val API_BASE = "https://cat-fact.herokuapp.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(API_BASE)
//            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}
