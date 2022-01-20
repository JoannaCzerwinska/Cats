package com.example.cats.ui

import com.example.cats.breeds.FetchBreedsUseCase
import com.example.cats.model.BreedsItem
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class MainActivityTest {

    private val mockMainActivity : MainActivity = Mockito.mock(MainActivity::class.java)
    private val mockFetchBreedsUseCase : FetchBreedsUseCase = Mockito.mock(FetchBreedsUseCase::class.java)

    private val mockBreedsItem : BreedsItem = BreedsItem(2, "url", "Fake description", 3, "Fake id", 2, "Cat name")
    var mockBreeds: ArrayList<BreedsItem> = arrayListOf(mockBreedsItem)

    @Test
    fun whenApiCallIsSuccessful_thenBreedNamesAreReturned() = runBlocking {
        whenever(mockFetchBreedsUseCase.getBreedNames()).thenReturn(FetchBreedsUseCase.Result.Success(mockBreeds))

        mockMainActivity.loadBreedsData()

        assertNotNull(mockMainActivity.breeds)

        return@runBlocking
    }

    @Test
    fun whenApiCallIsUnsuccessful_thenErrorMessageShows() = runBlocking {
        whenever(mockFetchBreedsUseCase.getBreedNames()).thenReturn(FetchBreedsUseCase.Result.Failure)

        return@runBlocking
    }
}
