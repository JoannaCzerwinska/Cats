package com.example.cats

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/breeds")
    suspend fun getBreedNames(): Response<List<BreedsItem>>
}