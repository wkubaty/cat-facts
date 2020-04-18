package com.kubaty.catfacts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kubaty.catfacts.repository.FactsRepository
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.CatFactUtil.DUMMY_CAT_FACT
import com.kubaty.catfacts.util.DataState
import com.kubaty.catfacts.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private lateinit var mockRepository: FactsRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockRepository = mockk()
        viewModel = MainViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `mainViewState changes when setting new viewState`() {
        viewModel.setViewStateFacts(listOf(DUMMY_CAT_FACT), null)
        val mainViewState = viewModel.viewState.getOrAwaitValue()
        assertEquals(1, mainViewState.facts?.size)
    }

    @Test
    fun `dataState changes when setting new viewState`() {
        coEvery { mockRepository.getFacts(any(), any()) } coAnswers {
            flow { emit(DataState.data(data = MainViewState(listOf(DUMMY_CAT_FACT)))) }
        }
        viewModel.setViewStateFacts(listOf(DUMMY_CAT_FACT), null)
        val dataState = viewModel.dataState.getOrAwaitValue()
        assertEquals(1, dataState.data?.getContentIfNotHandled()?.facts?.size)
    }
}