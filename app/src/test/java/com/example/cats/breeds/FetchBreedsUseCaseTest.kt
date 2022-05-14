package com.example.cats.breeds

import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class FetchBreedsUseCaseTest {

    @Mock
    private lateinit var mockDefaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider

    @Mock
    private lateinit var mockApiService: IApiService

    @Mock
    private lateinit var mockBreeds: List<BreedsItem>

    private lateinit var fetchBreedsUseCase : FetchBreedsUseCase

    @Before
    fun setUp(){
        fetchBreedsUseCase = FetchBreedsUseCase(mockApiService)

        fetchBreedsUseCase.defaultCoroutineDispatcherProvider = mockDefaultCoroutineDispatcherProvider
    }

    @Test
    fun whenGetBreedNamesCallSuccessful_thenReturnSuccess(): Unit = runBlocking {
        // given
        whenever(mockApiService.getBreedNames().isSuccessful).thenReturn(true)
        whenever(mockApiService.getBreedNames().body()).thenReturn(mockBreeds)

        // when
        val result = fetchBreedsUseCase.getBreedNames()

        // then
        verify(result).FetchBreedsUseCase.Result.Success
    }

    @Test
    fun whenGetBreedNamesCallSuccessful_thenReturnFailure(): Unit = runBlocking {
        // given
        whenever(mockApiService.getBreedNames().isSuccessful).thenReturn(false)
        whenever(mockApiService.getBreedNames().body()).thenReturn(mockBreeds)

        // when
        val result = fetchBreedsUseCase.getBreedNames()

        // then
//        assertThat(result).isInstanceOf(FetchBreedsUseCase.Result.Failure)
        verify(result).FetchBreedsUseCase.Result.Failure
    }
}
