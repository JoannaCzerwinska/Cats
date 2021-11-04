package com.example.cats.di

import com.example.cats.IoDispatcher
import com.example.cats.MainDispatcher
import com.example.cats.api.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
// Note that @InstallIn(ApplicationComponent::class) seen in many tutorials is now deprecated
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Provide always the same instance
    @Provides
    fun provideIApiService() : IApiService {
        return Retrofit.Builder()
            .baseUrl(IApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
