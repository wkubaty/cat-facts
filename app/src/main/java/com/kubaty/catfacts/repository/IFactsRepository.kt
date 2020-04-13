package com.kubaty.catfacts.repository

import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.DataState

interface IFactsRepository {
    suspend fun getFacts(animalType: String, amount: Int): DataState<MainViewState>
}