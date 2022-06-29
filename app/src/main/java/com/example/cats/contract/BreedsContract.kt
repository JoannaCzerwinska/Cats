package com.example.cats.contract

import com.example.cats.model.Breed
import com.example.cats.model.BreedsByName
import com.example.cats.model.VoteDeletedResponse
import com.example.cats.model.VoteResponse
import retrofit2.Response

interface IBreedsView {
    fun displayBreeds(breeds: Response<List<Breed>>)
    fun displaySpecificBreedInfo(breeds: Response<List<BreedsByName>>)
    fun displayError(message : String)
    fun displaySuccessfulVote(voteResponse : Response<VoteResponse>)
    fun displaySuccessfulDeletedVote(voteDeletedResponse : Response<VoteDeletedResponse>)
}

interface IBreedsPresenter {
    fun getBreeds()
    fun getInfoByBreedName(searchString: String)
    fun uploadVote(imageId : String, value : Int)
    fun deleteVote(voteId : String)
    fun destroyView()
}

