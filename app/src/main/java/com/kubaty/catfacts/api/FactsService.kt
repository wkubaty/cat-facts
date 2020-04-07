package com.kubaty.catfacts.api

import com.kubaty.catfacts.model.CatFact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsService {
    @GET("facts/random")
    suspend fun getFacts(
        @Query("animal_type") animalType: String,
        @Query("amount") amount: Int
    ): Response<List<CatFact>>

}