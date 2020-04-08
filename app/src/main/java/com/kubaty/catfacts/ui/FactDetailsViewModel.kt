package com.kubaty.catfacts.ui

import androidx.lifecycle.ViewModel
import com.kubaty.catfacts.model.CatFact
import javax.inject.Inject

class FactDetailsViewModel @Inject constructor() : ViewModel() {
    lateinit var catFact: CatFact

    fun setFact(updatedAt: String, factText: String) {
        catFact = CatFact("", updatedAt, factText)
    }
}