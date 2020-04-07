package com.kubaty.catfacts.ui

import androidx.lifecycle.ViewModel
import com.kubaty.catfacts.model.CatFact

class FactDetailsViewModel : ViewModel() {
    lateinit var catFact: CatFact

    fun setFact(updatedAt: String, factText: String) {
        catFact = CatFact("", updatedAt, factText)
    }
}