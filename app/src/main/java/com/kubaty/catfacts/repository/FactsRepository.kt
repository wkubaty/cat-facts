package com.kubaty.catfacts.repository

import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.*
import javax.inject.Inject

class FactsRepository @Inject constructor(private val factsController: FactsController) :
    IFactsRepository {
    override suspend fun getFacts(animalType: String, amount: Int): DataState<MainViewState> {

        val response = factsController.getFacts(
            animalType,
            amount
        )
        val dataState = when (response) {
            is ApiSuccessResponse -> {
                DataState.data(data = MainViewState(response.body))
            }
            is ApiEmptyResponse -> {
                DataState.error(message = "Error when fetching data.")
            }
            is ApiNetworkErrorResponse -> {
                DataState.error(message = "Error. Check your network connection.")
            }
            is ApiErrorResponse -> {
                DataState.error(message = "Error when fetching data.")
            }
        }
        return dataState
    }
}