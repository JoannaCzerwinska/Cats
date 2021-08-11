package com.example.cats

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("v1/breeds")
    fun getBreedNames(): Call<List<BreedsItem>>

}