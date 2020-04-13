package com.kubaty.catfacts.repository

import com.kubaty.catfacts.api.FakeFactsController
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeFactsRepository @Inject constructor(private val factsController: FakeFactsController) : IFactsRepository {

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