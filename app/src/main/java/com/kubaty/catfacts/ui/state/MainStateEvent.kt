package com.kubaty.catfacts.ui.state

import com.kubaty.catfacts.util.Config

sealed class MainStateEvent {
    data class LoadFactsEvent(
        val animalType: String = Config.ANIMAL_TYPE,
        val amount: Int = Config.AMOUNT
    ) : MainStateEvent()

    object NoneEvent : MainStateEvent()
}