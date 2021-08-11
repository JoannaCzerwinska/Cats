package com.example.cats

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/breeds")
    fun getBreedNames(): Call<List<BreedsItem>>

//    @GET("v1/images/search?breed_id=${breedId}")
    @GET("v1/images/search")
    fun getImagesForSpecificBreed(
        @Query("breed_id", encoded = true) breedId: String
    ): Call<List<Image>>

}