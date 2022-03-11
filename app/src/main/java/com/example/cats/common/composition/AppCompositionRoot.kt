package com.example.cats.common.composition

import androidx.annotation.UiThread
import com.example.cats.Constants
import com.example.cats.api.IApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@UiThread
class AppCompositionRoot {

    // "by lazy" - instantiated object will be cashed and then re-used on subsequent invocations
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: IApiService by lazy {
        retrofit.create(IApiService::class.java)
    }
}
