package com.kubaty.catfacts.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.*
import javax.inject.Inject

class FactsRepository @Inject constructor(private val factsController: FactsController) {
    suspend fun getFacts(animalType: String, amount: Int): LiveData<DataState<MainViewState>> {

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
        return MutableLiveData(dataState)
//        return object : NetworkBoundResource<List<CatFact>, MainViewState>() {
//
//            override fun createCall(): LiveData<ApiResponse<List<CatFact>>> {
//                return factsController.getFacts(
//                    animalType,
//                    amount
//                )
//            }
//
//            override fun onApiSuccessResponse(response: ApiSuccessResponse<List<CatFact>>) {
//                result.value = DataState.data(data = MainViewState(response.body))
//            }
//        }.asLiveData()
    }
}