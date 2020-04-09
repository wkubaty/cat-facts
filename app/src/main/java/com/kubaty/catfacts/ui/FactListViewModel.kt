package com.kubaty.catfacts.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubaty.catfacts.FactsRepository
import com.kubaty.catfacts.model.CatFact
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactListViewModel @Inject constructor(private val factsRepository: FactsRepository) :
    ViewModel() {
    private val factsLiveData: MutableLiveData<List<CatFact>> = MutableLiveData()
    fun getFactsLiveData(): LiveData<List<CatFact>> = factsLiveData

    init {
        getNewFacts(amount = 30)
    }

    fun getNewFacts(animalType: String = "cat", amount: Int) {
        viewModelScope.launch {
            try {
                factsLiveData.value = factsRepository.getFacts(animalType, amount).value
            } catch (t: Throwable) {
                Log.d(TAG, "Error when fetching data") //todo
//
            }
        }
    }

    companion object {
        private const val TAG = "FactListViewModel"
    }

}