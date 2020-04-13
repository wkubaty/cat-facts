package com.kubaty.catfacts.api

import com.kubaty.catfacts.util.ApiErrorResponse
import com.kubaty.catfacts.util.ApiNetworkErrorResponse
import com.kubaty.catfacts.util.ApiSuccessResponse
import com.kubaty.catfacts.util.CatFactUtil.DUMMY_CAT_FACT
import com.kubaty.catfacts.util.CatFactUtil.TEST_ANIMAL_AMOUNT
import com.kubaty.catfacts.util.CatFactUtil.TEST_ANIMAL_TYPE
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException


class FactsControllerTest {
    private lateinit var factsController: FactsController
    private lateinit var mockService: FactsService
    private val errorResponseBody = ResponseBody.create(MediaType.get("text/csv"), "")

    @Before
    fun setUp() {
        mockService = mockk()
        factsController = FactsController(mockService)
    }

    @Test
    fun `facts controller returns ApiSuccessResponse when queried proper data`() {
        coEvery { mockService.getFacts(any(), any()) } coAnswers {
            Response.success(
                listOf(
                    DUMMY_CAT_FACT
                )
            )
        }

        runBlocking {
            val apiResponse = factsController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT)

            coVerify { mockService.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertTrue(apiResponse is ApiSuccessResponse)
        }
    }

    @Test
    fun `facts controller returns ApiNetworkErrorResponse when network is unavailable`() {
        coEvery { mockService.getFacts(any(), any()) } throws IOException("Network error")
        runBlocking {
            val apiResponse = factsController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT)

            coVerify { mockService.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertTrue(apiResponse is ApiNetworkErrorResponse)
        }
    }

    @Test
    fun `facts controller returns ApiErrorResponse when page not found`() {
        coEvery { mockService.getFacts(any(), any()) } coAnswers {
            Response.error(404, errorResponseBody)
        }
        runBlocking {
            val apiResponse = factsController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT)

            coVerify { mockService.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertTrue(apiResponse is ApiErrorResponse)
        }
    }

    @Test
    fun `facts controller returns ApiErrorResponse on server error`() {
        coEvery { mockService.getFacts(any(), any()) } coAnswers {
            Response.error(500, errorResponseBody)
        }
        runBlocking {
            val apiResponse = factsController.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT)
            coVerify { mockService.getFacts(TEST_ANIMAL_TYPE, TEST_ANIMAL_AMOUNT) }
            assertTrue(apiResponse is ApiErrorResponse)
        }
    }

}