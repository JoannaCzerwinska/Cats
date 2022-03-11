package com.example.cats.breeds

import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import kotlinx.coroutines.withContext

class FetchBreedsUseCase(private val apiService: IApiService) {
    private val defaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider = DefaultCoroutineDispatcherProvider()

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
