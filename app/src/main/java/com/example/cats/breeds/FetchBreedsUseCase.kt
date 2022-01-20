package com.example.cats.breeds

import com.example.cats.Constants
import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchBreedsUseCase {
    var breeds: ArrayList<BreedsItem> = arrayListOf()
    private val defaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider = DefaultCoroutineDispatcherProvider()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: IApiService = retrofit.create(IApiService::class.java)

    sealed class Result {
        class Success(val breedNames: List<BreedsItem>) : Result()
        object Failure : Result()
    }

    suspend fun getBreedNames() : Result {
        return withContext(defaultCoroutineDispatcherProvider.ioDispatcher()) {
            try {
                val response = apiService.getBreedNames()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }
            } catch (e: Exception) {
                return@withContext Result.Failure
            }
        }
    }
}
