package com.kubaty.catfacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubaty.catfacts.repository.IFactsRepository
import com.kubaty.catfacts.ui.MainViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeViewModelFactory @Inject constructor(private val factsRepository: IFactsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(factsRepository) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }

}