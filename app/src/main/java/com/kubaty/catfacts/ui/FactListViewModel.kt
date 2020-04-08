package com.kubaty.catfacts.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.model.CatFact
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactListViewModel @Inject constructor(private val factsController: FactsController) :
    ViewModel() {
    private val factsLiveData: MutableLiveData<List<CatFact>> = MutableLiveData()
    fun getFactsLiveData(): LiveData<List<CatFact>> = factsLiveData

    fun getNewFacts(amount: Int) {
        viewModelScope.launch {
            try {
                val factsResponse = factsController.getFacts(amount = amount)
                if (factsResponse.isSuccessful) {
                    val facts = factsResponse.body()
                    factsLiveData.value = facts
                } else {
                    Log.d(TAG, "Error when fetching data")
                }
            } catch (t: Throwable) {
                Log.d(TAG, "Error when fetching data")

            }
        }
    }

    companion object {
        private const val TAG = "FactListViewModel"
    }

}