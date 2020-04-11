package com.kubaty.catfacts.ui.state

import com.kubaty.catfacts.model.CatFact

data class MainViewState(var facts: List<CatFact>? = null)