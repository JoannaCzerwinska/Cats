package com.example.cats.api

import com.example.cats.model.BreedsItem
import retrofit2.Response
import retrofit2.http.GET

interface IApiService {
    @GET("v1/breeds")
//    suspend fun getBreedNames(): Result<List<BreedsItem>>
    suspend fun getBreedNames(): Response<List<BreedsItem>>
}
