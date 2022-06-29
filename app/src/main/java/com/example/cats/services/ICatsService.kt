package com.example.cats.services

import com.example.cats.model.Breed
import com.example.cats.model.BreedsByName
import com.example.cats.model.Vote
import com.example.cats.model.VoteDeletedResponse
import com.example.cats.model.VoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ICatsService {

    @GET("v1/breeds")
    suspend fun getBreedNames() : Response<List<Breed>>

    @GET("v1/breeds/search")
    suspend fun getInfoByBreedName(@Query("q") searchString: String) : Response<List<BreedsByName>>

    @Headers("x-api-key: a27b4435-bb1d-4800-a80d-3e356921c388")
    @POST("v1/votes")
    suspend fun uploadAVote(@Body vote : Vote) : Response<VoteResponse>

    @Headers("x-api-key:a27b4435-bb1d-4800-a80d-3e356921c388")
    @DELETE("v1/votes/{vote_id}")
    suspend fun deleteVote(@Path("vote_id") voteId : String) : Response<VoteDeletedResponse>
}
