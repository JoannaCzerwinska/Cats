package com.example.cats.common.composition

import com.example.cats.Constants
import com.example.cats.api.IApiService
import com.example.cats.breeds.FetchBreedsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppCompositionRoot {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: IApiService = retrofit.create(IApiService::class.java)

    val fetchBreedsUseCase get() = FetchBreedsUseCase(apiService)
}
