package com.example.cats.di

import com.example.cats.Constants
import com.example.cats.api.IApiService
import com.example.cats.utils.CoroutineDispatcherProvider
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule() {

    @Provides
    fun provideIApiService() : IApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    @Provides
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider = DefaultCoroutineDispatcherProvider()
}
