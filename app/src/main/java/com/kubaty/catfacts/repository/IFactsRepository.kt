package com.kubaty.catfacts.repository

import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.DataState
import kotlinx.coroutines.flow.Flow

interface IFactsRepository {
    fun getFacts(animalType: String, amount: Int): Flow<DataState<MainViewState>>
}