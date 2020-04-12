package com.kubaty.catfacts.ui

import android.content.Context
import androidx.lifecycle.*
import com.kubaty.catfacts.R
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


    fun setViewStateFacts(
        facts: List<CatFact>,
        context: Context
    ) {
        val update = getCurrentViewState()
        val newFacts = getNewFacts(facts, context)
        update.facts = newFacts
        mutableViewState.value = update
    }

    private fun getNewFacts(facts: List<CatFact>, context: Context): List<CatFact> {
        val randomDrawables = getRandomDrawables(context, facts.size)
        facts.mapIndexed { index, catFact ->
            catFact.drawableId = randomDrawables[index]
        }
        return facts
    }

    private fun getRandomDrawables(context: Context, size: Int): List<Int> {
        val drawableIds = mutableListOf<Int>()
        val images = context.resources.obtainTypedArray(R.array.cat_images)
        (1..size).forEach {
            drawableIds.add(images.getResourceId(it % images.length(), R.drawable.cat_1))
        }
        images.recycle()
        drawableIds.shuffle()

        return drawableIds
    }

    fun setStateEvent(event: MainStateEvent) {
        mutableStateEvent.value = event
    }

}