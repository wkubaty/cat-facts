package com.kubaty.catfacts.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kubaty.catfacts.util.*

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            handleNetworkCall(response)
        }
    }


    abstract fun createCall(): LiveData<ApiResponse<ResponseObject>>

    private fun handleNetworkCall(response: ApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                onApiSuccessResponse(response)
            }
            is ApiEmptyResponse -> {
                result.value = DataState.error(message = "Error when fetching data.")
            }
            is ApiNetworkErrorResponse -> {
                result.value = DataState.error(message = "Error. Check your network connection.")
            }
            is ApiErrorResponse -> {
                result.value = DataState.error(message = "Error when fetching data.")
            }
        }
    }

    abstract fun onApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

}