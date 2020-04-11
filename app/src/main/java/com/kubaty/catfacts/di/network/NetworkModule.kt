package com.kubaty.catfacts.di.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object NetworkModule {
    private const val API_BASE = "https://cat-fact.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @Singleton
    @Provides
    fun provideGson(
    ): Gson = GsonBuilder()
        .setDateFormat(DATE_FORMAT)
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(API_BASE)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

}
