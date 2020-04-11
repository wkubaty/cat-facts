package com.kubaty.catfacts.ui

import androidx.lifecycle.*
import com.kubaty.catfacts.model.CatFact
import com.kubaty.catfacts.repository.FactsRepository
import com.kubaty.catfacts.ui.state.MainStateEvent
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.DataState
import javax.inject.Inject

class MainViewModel @Inject constructor(private val factsRepository: FactsRepository) :
    ViewModel() {

    private val mutableViewState: MutableLiveData<MainViewState> = MutableLiveData()
    val viewState: LiveData<MainViewState> = mutableViewState

    private val mutableStateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val stateEvent: LiveData<MainStateEvent> = mutableStateEvent

    val dataState: LiveData<DataState<MainViewState>> =
        Transformations.switchMap(stateEvent) { stateEvent ->
            stateEvent?.let {
                when (stateEvent) {
                    is MainStateEvent.LoadFactsEvent -> {
                        liveData(viewModelScope.coroutineContext) {
                            val facts = factsRepository.getFacts(
                                stateEvent.animalType,
                                stateEvent.amount
                            )
                            emitSource(facts)
                        }
                    }

                    is MainStateEvent.NoneEvent -> {
                        MutableLiveData<DataState<MainViewState>>()

                    }
                }
            }
        }

    init {
        setStateEvent(MainStateEvent.LoadFactsEvent())
    }


    private fun getCurrentViewState() = viewState.value ?: MainViewState()


    fun setViewStateFacts(facts: List<CatFact>) {
        val update = getCurrentViewState()
        update.facts = facts
        mutableViewState.value = update
    }

    fun setStateEvent(event: MainStateEvent) {
        mutableStateEvent.value = event
    }

}