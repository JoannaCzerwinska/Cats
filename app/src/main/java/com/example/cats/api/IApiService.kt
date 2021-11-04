package com.example.cats.api

import com.example.cats.model.BreedsItem
import retrofit2.Response
import retrofit2.http.GET

interface IApiService {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
    }

    @GET("v1/breeds")
    suspend fun getBreedNames(): Response<List<BreedsItem>>
}
