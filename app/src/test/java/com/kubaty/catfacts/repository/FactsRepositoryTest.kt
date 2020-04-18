package com.kubaty.catfacts.repository

import com.kubaty.catfacts.api.FactsController
import com.kubaty.catfacts.ui.state.MainViewState
import com.kubaty.catfacts.util.ApiResponse
import com.kubaty.catfacts.util.CatFactUtil
import com.kubaty.catfacts.util.CatFactUtil.TEST_ANIMAL_AMOUNT
import com.kubaty.catfacts.util.CatFactUtil.TEST_ANIMAL_TYPE
import com.kubaty.catfacts.util.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class FactsRepositoryTest {
    private lateinit var factsRepository: FactsRepository
    private lateinit var mockController: FactsController

    @Before
    fun setUp() {
        mockController = mockk()
        factsRepository = FactsRepository(mockController)
    }

    @Test
    fun `repository returns valid dataState when queried for proper data`() {
        coEvery { mockController.getFacts(any(), any()) } coAnswers {
            ApiResponse.create(
                Response.success(
                    listOf(
                        CatFactUtil.DUMMY_CAT_FACT
                    )
                )
            )
        }

        runBlocking {
            var resp: MainViewState? = null
            factsRepository.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT)
                .collect { resp = it.data?.getContentIfNotHandled() }
            coVerify { mockController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertNotNull(resp)
        }
    }

    @Test
    fun `repository returns dataState with error message when error`() {
        coEvery { mockController.getFacts(any(), any()) } coAnswers {
            ApiResponse.create(IOException())
        }
        runBlocking {
            var resp: DataState<MainViewState>? = null
            factsRepository.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT).collect { resp = it }

            coVerify { mockController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertNull(resp?.data)
            assertEquals(
                "Error. Check your network connection.",
                resp?.message?.getContentIfNotHandled()
            )
        }
    }

}