package com.kubaty.catfacts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.model.CatFact
import javax.inject.Inject

class FactsRepository @Inject constructor(private val factsController: FactsController) {
    private val catFactsCache = MutableLiveData<List<CatFact>>()

    suspend fun getFacts(animalType: String, amount: Int): MutableLiveData<List<CatFact>> {
        if (catFactsCache.value != null) {
            return catFactsCache
        }
        val factsResponse = factsController.getFacts(animalType, amount)
        if (factsResponse.isSuccessful) {
            val facts = factsResponse.body()
            catFactsCache.value = facts
        } else {
            Log.d(TAG, "Error when fetching data")
        }
        return catFactsCache
    }

    companion object {
        private const val TAG = "FactsRepository"
    }
}