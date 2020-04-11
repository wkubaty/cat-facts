package com.kubaty.catfacts.api

import com.kubaty.catfacts.model.CatFact
import com.kubaty.catfacts.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactsController @Inject constructor(private val factsService: FactsService) {
    suspend fun getFacts(
        animalType: String,
        amount: Int
    ): ApiResponse<List<CatFact>> = withContext(Dispatchers.IO) {
        try {
            val response = factsService.getFacts(animalType, amount)
            ApiResponse.create(response)
        } catch (t: Throwable) {
            val errorResponse: ApiResponse<List<CatFact>> = ApiResponse.create(t)
            errorResponse
        }
    }
}