package com.example.cats.breeds

import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class FetchBreedsUseCaseTest {

    @Mock
    private lateinit var mockApiService: IApiService

    private lateinit var fetchBreedsUseCase : FetchBreedsUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        fetchBreedsUseCase = FetchBreedsUseCase(mockApiService)
    }

    @Test
    fun whenGetBreedNamesCallIsSuccessful_thenReturnSuccess(): Unit = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val mockBreed = BreedsItem(2, "Fake description", "Fake id", 2, "2", 12, "Cat name")
        val mockBreedsList: List<BreedsItem> = arrayListOf(mockBreed)
        val response: Response<List<BreedsItem>> = Response.success(200, mockBreedsList)

        whenever(mockApiService.getBreedNames()).thenReturn(response)

        val result = fetchBreedsUseCase.getBreedNames()

        assertThat(result, instanceOf(FetchBreedsUseCase.Result.Success::class.java))
    }

    @Test
    fun whenGetBreedNamesCallIsNotSuccessful_thenReturnFailure(): Unit = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val response: Response<List<BreedsItem>> = Response.error(404, "{}"
            .toResponseBody("application/json; charset=utf-8".toMediaType())
        )

        whenever(mockApiService.getBreedNames()).thenReturn(response)

        val result = fetchBreedsUseCase.getBreedNames()

        assertThat(result, instanceOf(FetchBreedsUseCase.Result.Failure::class.java))
    }
}
