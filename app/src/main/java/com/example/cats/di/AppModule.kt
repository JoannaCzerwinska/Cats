package com.example.cats.di

import com.example.cats.api.IApiService
import com.example.cats.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
// Note that @InstallIn(ApplicationComponent::class) seen in many tutorials is now deprecated
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Provides always the same instance
    @Provides
    fun provideIApiService() : IApiService {
        return Retrofit.Builder()
            .baseUrl(IApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()
}
