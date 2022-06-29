package com.example.cats.view

import com.example.cats.contract.IBreedsView
import com.example.cats.model.Breed
import com.example.cats.model.BreedsByName
import com.example.cats.model.VoteDeletedResponse
import com.example.cats.presenter.BreedsPresenter
import com.example.cats.services.ICatsService
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class BreedsPresenterTest {

    @Mock
    private lateinit var mockView : IBreedsView

    @Mock
    private lateinit var mockApiService : ICatsService

    private lateinit var breedsPresenter : BreedsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        breedsPresenter = BreedsPresenter(mockView)
        breedsPresenter.apiService = mockApiService
    }

    @Test
    fun whenGetBreedsIsCalled_andApiResponseIsSuccessful_thenDisplayBreedsIsInvoked() = runBlocking{
        Dispatchers.setMain(Dispatchers.Unconfined)

        val mockBreed = Breed(2, "Fake description", 3, "Fake id", 2, "Cat name")
        val mockBreedsList: ArrayList<Breed> = arrayListOf(mockBreed)
        val response: Response<List<Breed>> = Response.success(200, mockBreedsList)

        whenever(mockApiService.getBreedNames()).thenReturn(response)

        breedsPresenter.getBreeds()

        verify(mockView).displayBreeds(response)
    }

    @Test
    fun whenGetBreedsIsCalled_andApiResponseReturns404_thenDisplayErrorIsInvoked() = runBlocking{
        val response: Response<List<Breed>> = Response.error(404, "{}"
            .toResponseBody("application/json; charset=utf-8".toMediaType())
        )

        whenever(mockApiService.getBreedNames()).thenReturn(response)

        breedsPresenter.getBreeds()

        verify(mockView).displayError("Error code ${response.code()}")
    }

    @Test
    fun whenGetBreedsIsCalled_andApiResponseIsAnotherError_thenDisplayErrorIsInvoked() = runBlocking{
        Dispatchers.setMain(Dispatchers.Unconfined)

        val response: Response<List<Breed>> = Response.error(500, "{}"
            .toResponseBody("application/json; charset=utf-8".toMediaType())
        )

        whenever(mockApiService.getBreedNames()).thenReturn(response)

        breedsPresenter.getBreeds()

        verify(mockView).displayError("Other error: ${response.message()}")
    }

    @Test
    fun whenGetInfoByBreedNameIsCalled_andApiResponseIsSuccessful_thenDisplaySpecificBreedInfoIsInvoked() = runBlocking{
        val mockBreed = BreedsByName("2", "Fake name", "Fake origin")
        val mockBreedsList: ArrayList<BreedsByName> = arrayListOf(mockBreed)
        val response: Response<List<BreedsByName>> = Response.success(200, mockBreedsList)
        val searchTerm = "Kot"

        whenever(mockApiService.getInfoByBreedName(searchTerm)).thenReturn(response)

        breedsPresenter.getInfoByBreedName(searchTerm)

        verify(mockView).displaySpecificBreedInfo(response)
    }

    @Test
    fun whenGetInfoByBreedNameIsCalled_andApiResponseReturns404_thenDisplayErrorIsInvoked() = runBlocking{
        val response: Response<List<BreedsByName>> = Response.error(404, "{}"
            .toResponseBody("application/json; charset=utf-8".toMediaType())
        )
        val searchTerm = "Kot"

        whenever(mockApiService.getInfoByBreedName(searchTerm)).thenReturn(response)

        breedsPresenter.getInfoByBreedName(searchTerm)

        verify(mockView).displayError("Error code ${response.code()}")
    }

    @Test
    fun whenGetInfoByBreedNameIsCalled_andApiResponseIsAnotherError_thenDisplayErrorIsInvoked() = runBlocking{
        Dispatchers.setMain(Dispatchers.Unconfined)

        val response: Response<List<BreedsByName>> = Response.error(500, "{}"
            .toResponseBody("application/json; charset=utf-8".toMediaType())
        )
        val searchTerm = "Kot"

        whenever(mockApiService.getInfoByBreedName(searchTerm)).thenReturn(response)

        breedsPresenter.getInfoByBreedName(searchTerm)

        verify(mockView).displayError("Other error: ${response.message()}")
    }

    @Test
    fun whenDeleteVoteIsCalled_andApiResponseIsSuccessful_thenDisplayMessageIsCalled() = runBlocking{
        Dispatchers.setMain(Dispatchers.Unconfined)

        val voteId = "1"
        val voteDeletedResponse = VoteDeletedResponse()
        val response : Response<VoteDeletedResponse> = Response.success(200, voteDeletedResponse)

        whenever(mockApiService.deleteVote(voteId)).thenReturn(response)

        breedsPresenter.deleteVote(voteId)

        verify(mockView).displaySuccessfulDeletedVote(response)
    }

    @Test
    fun whenDeleteVoteIsCalled_andApiResponseReturns404_thenDisplayErrorIsCalled() = runBlocking{
        Dispatchers.setMain(Dispatchers.Unconfined)

        val voteId = "1"
        val response : Response<VoteDeletedResponse> = Response.error(404, "{}".toResponseBody("application/json; charset=utf-8".toMediaType()))

        whenever(mockApiService.deleteVote(voteId)).thenReturn(response)

        breedsPresenter.deleteVote(voteId)

        verify(mockView).displayError("Error code ${response.code()}")
    }
}
