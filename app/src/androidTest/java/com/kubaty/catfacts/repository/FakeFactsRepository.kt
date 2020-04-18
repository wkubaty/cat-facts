package com.kubaty.catfacts.repository

import com.kubaty.catfacts.api.FakeFactsController
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeFactsRepository @Inject constructor(private val factsController: FakeFactsController) :
    IFactsRepository {

    override fun getFacts(animalType: String, amount: Int): Flow<DataState<MainViewState>> =
        flow {

            val response = factsController.getFacts(
                animalType,
                amount
            )
            val dataState: DataState<MainViewState> = when (response) {
                is ApiSuccessResponse -> {
                    DataState.data(data = MainViewState(response.body))
                }
                is ApiEmptyResponse -> {
                    DataState.error<MainViewState>(message = "Error when fetching data.")
                }
                is ApiNetworkErrorResponse -> {
                    DataState.error<MainViewState>(message = "Error. Check your network connection.")
                }
                is ApiErrorResponse -> {
                    DataState.error<MainViewState>(message = "Error when fetching data.")
                }
            }

            emit(dataState)
        }
}