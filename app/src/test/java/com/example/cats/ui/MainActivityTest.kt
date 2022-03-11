package com.example.cats.ui

import com.example.cats.breeds.FetchBreedsUseCase
import com.example.cats.model.BreedsItem
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {
    private val mockFetchBreedsUseCase : FetchBreedsUseCase = Mockito.mock(FetchBreedsUseCase::class.java)
    private val mockBreedsAdapter : BreedAdapter = Mockito.mock(BreedAdapter::class.java)
    private val mockBreedsItem : BreedsItem = BreedsItem(2, "url", "Fake description", 3, "Fake id", 2, "Cat name")
    private lateinit var mainActivity: MainActivity

    var mockBreeds: ArrayList<BreedsItem> = arrayListOf(mockBreedsItem)

    @Before
    fun setUp() {
        mainActivity = MainActivity()
        mainActivity.fetchBreedsUseCase = mockFetchBreedsUseCase
        mainActivity.breedsAdapter = mockBreedsAdapter
    }

    @Test
    fun whenApiCallIsSuccessful_thenBreedNamesAreReturned() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        whenever(mockFetchBreedsUseCase.getBreedNames()).thenReturn(FetchBreedsUseCase.Result.Success(mockBreeds))

        val actual = mainActivity.loadBreedsData()

        assertThat(actual, instanceOf(FetchBreedsUseCase.Result.Success::class.java))
        verify(mockBreedsAdapter).bindBreeds(mockBreeds)
        assertTrue(mainActivity.isDataLoaded)
    }

    @Test
    fun whenApiCallIsUnsuccessful_thenErrorMessageShows() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        whenever(mockFetchBreedsUseCase.getBreedNames()).thenReturn(FetchBreedsUseCase.Result.Failure)

        val actual = mainActivity.loadBreedsData()

        assertThat(actual, instanceOf(FetchBreedsUseCase.Result.Failure::class.java))
    }
}
