package com.kubaty.catfacts.api

import com.kubaty.catfacts.model.CatFact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class FactsController @Inject constructor(private val factsService: FactsService) {
    suspend fun getFacts(
        animalType: String,
        amount: Int
    ): Response<List<CatFact>> =
        withContext(Dispatchers.IO) {
            factsService.getFacts(animalType, amount)
        }
}